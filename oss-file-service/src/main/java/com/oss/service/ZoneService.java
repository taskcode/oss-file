package com.oss.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.oss.model.Zone;
import com.oss.pojo.vo.ZoneVo;
import com.oss.tool.BaseService;
import com.oss.pojo.bo.ZoneBo;
import com.oss.pojo.dto.ZoneDto;
import com.oss.pojo.dto.ZoneListDto;
import com.oss.tool.ResponseResult;


public interface ZoneService extends BaseService {

    /**
     * 查询用户拥有的区域
     * @param userId
     */
    ResponseResult<PageInfo<ZoneBo>> pageZoneByUserId(Long userId, ZoneListDto zoneListDto);

    /**
     * 删除区域
     * @param zoneId
     * @return
     */
    ResponseResult deleteZoneById(String zoneId);

    /**
     * 添加分区
     * @param zoneDto
     * @return
     */
    ResponseResult addZone(ZoneDto zoneDto);

    /**
     * 根据id查询前缀名称
     * @param zoneId
     * @return
     */
    ResponseResult findPrefixById(String zoneId);

    /**
     * 查询地区列表
     * @return
     */
    ResponseResult selectAllZoneList();

    /**
     *获取我的分区信息
     * @param userId
     * @return
     */
    ResponseResult getMyZonePwd(long userId);

    /**
     * 启动
     * @return
     */
    ResponseResult start();

    /**
     * 分配分区查询分区列表
     * @param zoneListDto
     * @return
     */
    ResponseResult<Page<ZoneVo>> pageQueryZoneByUserId(ZoneListDto zoneListDto);
}
