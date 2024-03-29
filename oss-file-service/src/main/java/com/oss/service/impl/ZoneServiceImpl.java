package com.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.oss.mapper.FileMapper;
import com.oss.mapper.UserInfoZoneMapper;
import com.oss.mapper.ZoneMapper;
import com.oss.model.UserInfoZone;
import com.oss.model.Zone;
import com.oss.pojo.bo.ZoneBo;
import com.oss.pojo.bo.ZonePwdBo;
import com.oss.pojo.dto.ZoneDto;
import com.oss.pojo.dto.ZoneListDto;
import com.oss.pojo.vo.ZoneVo;
import com.oss.service.ZoneService;
import com.oss.tool.ErrorCodes;
import com.oss.tool.ResponseResult;
import com.oss.tool.start.UserStart;
import com.oss.tool.util.OssUtil;
import com.oss.tool.util.SnowUtil;
import com.oss.tool.util.ValidateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private UserInfoZoneMapper userInfoZoneMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private OssUtil ossUtil;

    @Override
    public ResponseResult<PageInfo<ZoneBo>> pageZoneByUserId(Long userId, ZoneListDto zoneListDto) {
        PageHelper.startPage(zoneListDto.getPage(),zoneListDto.getSize());
        // 如果userId为空 则为游客
        Page<ZoneBo> zoneBos =null;
        if (ValidateUtil.isNotEmpty(userId)){
            zoneBos  = zoneMapper.pageZoneByUserIdAndName(userId,zoneListDto.getName());
        }else {
            zoneBos  = zoneMapper.pageZoneByVisitor(zoneListDto.getName());
        }
        PageInfo<ZoneBo> pageInfo =new PageInfo<ZoneBo>(zoneBos);

        return  ResponseResult.responseSuccessResult(pageInfo);
    }

    @Override
    public ResponseResult deleteZoneById(String zoneId) {
        /**
         *删除过程:
         *  1.查询这个分区的前缀
         *  2.查询这个分区下的所有文件
         *  3.删除这个分区下的所有文件
         *  4.删除这个分区
         */
        //查询这个分区
        String prefix = zoneMapper.selectPrefixByZoneId(zoneId);
        if (ValidateUtil.isEmpty(prefix)){
            return ResponseResult.responseSuccessResult(ErrorCodes.ZONE_NOT_FOUND);
        }
        OSS ossClient = new OSSClientBuilder().build(ossUtil.getEndpoint(), ossUtil.getAccessKeyId(), ossUtil.getAccessKeySecret());
        //查询分区下的所有文件
        ObjectListing objectListing = ossClient.listObjects(ossUtil.getBucketName(), prefix);
        List<OSSObjectSummary> sums  = objectListing.getObjectSummaries();

        //筛选这个分区下的文件
        List<String> keys = sums.stream().filter(p->p.getKey().indexOf(prefix+"/")>=0 && !p.getKey().equals(prefix+"/"))
                .map(p->p.getKey()).collect(Collectors.toList());
        //删除oss下的所有文件
        List<List<String>> fileList = Lists.partition(keys,1000);
        fileList.forEach(a->{
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(ossUtil.getBucketName()).withKeys(a).withQuiet(true));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            if (ValidateUtil.isNotEmpty(deletedObjects)){
                LOGGER.error("文件删除失败:{}",deletedObjects);
            }
        });
        //删除外层文件夹
        ossClient.deleteObject(ossUtil.getBucketName(), prefix+"/");
        ossClient.shutdown();
        //删除数据库这个分区所有文件
        Integer integer = fileMapper.deleteFileByZoneId(zoneId);
        //删除这个分区
        Integer num = zoneMapper.deleteZoneById(zoneId);
        return ResponseResult.responseOK();
    }

    @Override
    public ResponseResult addZone(ZoneDto zoneDto) {
        /** 判断是 某一个条件是否已存在*/
       Integer integer = zoneMapper.findByParam(zoneDto);
        if (ValidateUtil.isNotCountEmpty(integer)){
            return ResponseResult.responseResultWithErrorCode(ErrorCodes.ZONE_REPETITION);
        }
        Zone zone = new Zone();
        BeanUtils.copyProperties(zoneDto,zone);
        zone.setId(SnowUtil.generateId());
        zone.setCreateTime(System.currentTimeMillis());
        zone.setUpdateTime(System.currentTimeMillis());
        zone.setVersion(1l);
        int insert = zoneMapper.insert(zone);
        if (ValidateUtil.isCountEmpty(insert)){
            return ResponseResult.responseResultWithErrorCode(ErrorCodes.ADD_ERROR);
        }
        //添加主账号的分区关联的信息
        UserInfoZone userInfoZone =new UserInfoZone();
        userInfoZone.setId(SnowUtil.generateId());
        userInfoZone.setCreateTime(System.currentTimeMillis());
        userInfoZone.setUpdateTime(System.currentTimeMillis());
        userInfoZone.setVersion(1l);
        userInfoZone.setZoneId(zone.getId());
        userInfoZone.setUserId(Long.valueOf(UserStart.userId));
        int num = userInfoZoneMapper.insert(userInfoZone);
        if (ValidateUtil.isCountEmpty(num)){
            return ResponseResult.responseResultWithErrorCode(ErrorCodes.ADD_ERROR);
        }
        return ResponseResult.responseOK();
    }

    @Override
    public ResponseResult findPrefixById(String zoneId) {
        String prefix = zoneMapper.findPrefixById(zoneId);
        return ValidateUtil.isNotEmpty(prefix)?ResponseResult.responseSuccessResult(prefix):ResponseResult.responseResultWithErrorCode(ErrorCodes.ZONE_NOT_FOUND);
    }

    @Override
    public ResponseResult selectAllZoneList() {
        List<ZoneBo> zoneBos  = zoneMapper.selectAllZoneList();
        return  ResponseResult.responseSuccessResult(zoneBos);
    }

    @Override
    public ResponseResult<List<ZonePwdBo>> getMyZonePwd(long userId) {
        List<ZonePwdBo> zonePwdBoList = zoneMapper.selectMyZonePwd(userId);
        return  ResponseResult.responseSuccessResult(zonePwdBoList);
    }

    @Override
    public ResponseResult start() {
        Integer countZone = zoneMapper.countZone();
        if (ValidateUtil.isNotCountEmpty(countZone)){
            return ResponseResult.responseOK();
        }
        Zone zone = new Zone();
        zone.setId(1l);
        zone.setCreateTime(System.currentTimeMillis());
        zone.setUpdateTime(System.currentTimeMillis());
        zone.setVersion(1l);
        zone.setZoneName("游客专区");
        zone.setZonePrefix("visitor");
        zone.setZonePwd("");
        zone.setIsOpen(1);
        zoneMapper.insert(zone);
        return ResponseResult.responseOK();
    }

    @Override
    public ResponseResult<Page<ZoneVo>> pageQueryZoneByUserId(ZoneListDto zoneListDto) {
        PageHelper.startPage(zoneListDto.getPage(),zoneListDto.getSize());
        Page<ZoneVo>  zoneList  = zoneMapper.pageQueryZoneByUserId(zoneListDto.getName());
        return ResponseResult.responseSuccessResult(zoneList);
    }

}
