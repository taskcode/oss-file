package com.oss.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.oss.mapper.*;
import com.oss.model.*;
import com.oss.pojo.dto.RoleAllotDto;
import com.oss.pojo.dto.RoleListDto;
import com.oss.pojo.dto.RoleStatusDto;
import com.oss.pojo.dto.ZoneAllotDto;
import com.oss.pojo.vo.RoleVo;
import com.oss.service.RoleService;
import com.oss.tool.ResponseResult;
import com.oss.tool.util.ConvertScaleUtil;
import com.oss.tool.util.SnowUtil;
import com.oss.tool.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName：RoleServiceImpl
 * @Description: 角色服务
 * @Author：13738700108
 * @Data 2020/11/3 0:05
 * @Version: v1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserInfoRoleMapper userInfoRoleMapper;

    @Autowired
    private UserInfoZoneMapper userInfoZoneMapper;

    @Autowired
    private RoleInfoPermissionMapper roleInfoPermissionMapper;

    /**
     * 获取角色列表
     * @return
     */
    @Override
    public ResponseResult<Page<RoleVo>>  getRoleList(RoleListDto roleListDto) {
        PageHelper.startPage(roleListDto.getPage(),roleListDto.getSize());
        Page<RoleVo> roleList = roleMapper.getRoleList(roleListDto.getName());
        return ResponseResult.responseSuccessResult(roleList);
    }

    /**
     * 分配角色
     * @param roleAllotDto
     * @return
     */
    @Override
    public ResponseResult allotRole(RoleAllotDto roleAllotDto) {
        String userIdJoin = roleAllotDto.getUserIdList().stream().collect(Collectors.joining(","));
        //先删除所有权限
        Integer delNum  = userInfoRoleMapper.deleteByUserId(userIdJoin);
        //再分配权限
        List<UserInfoRole> userInfoRoleList = Lists.newArrayList();
        long currentTime = System.currentTimeMillis();
        roleAllotDto.getUserIdList().forEach(a->{
            roleAllotDto.getRoleIdList().forEach(b->{
                userInfoRoleList.add(new UserInfoRole(SnowUtil.generateId(),currentTime,currentTime,1l,Long.valueOf(a),Long.valueOf(b)));
            });
        });
        if (ValidateUtil.isNotEmpty(userInfoRoleList)){
            Integer insertNum = userInfoRoleMapper.insertUserInfoRoleList(userInfoRoleList);
        }

        return ResponseResult.responseOK();
    }

    /**
     * 分配分区
     * @param zoneAllotDto
     * @return
     */
    @Override
    public ResponseResult allotZone(ZoneAllotDto zoneAllotDto) {
        String userJoin =  zoneAllotDto.getUserIdList().stream().collect(Collectors.joining(","));
        //先删除角色所有分区
        Integer delNum  =   userInfoZoneMapper.deleteByUserId(userJoin);
        //再分配角色
        List<UserInfoZone> userInfoZoneList = Lists.newArrayList();
        long currentTime= System.currentTimeMillis();
        zoneAllotDto.getUserIdList().forEach(a->{
            zoneAllotDto.getZoneIdList().forEach(b->{
                userInfoZoneList.add(new UserInfoZone(SnowUtil.generateId(),currentTime,currentTime,1l,Long.valueOf(a),Long.valueOf(b)));
            });
        });
        if (ValidateUtil.isNotEmpty(userInfoZoneList)){
            Integer insertNum = userInfoZoneMapper.insertUserInfoZoneList(userInfoZoneList);
        }

        return ResponseResult.responseOK();
    }

    /**
     * 初始化项目
     * @return
     */
    @Override
    public ResponseResult start() {
        /**1.添加角色
         * 2.添加权限
         * 3.添加主账号超级管理员角色
         * 4.添加角色权限关系表
         * */
        //判断是否有角色
        Integer countRole = roleMapper.countRole();
        //判断是否有权限
        Integer countPermission = permissionMapper.countPermission();
        //判断用户关联角色
        Integer countUserInfoRole = userInfoRoleMapper.countUserInfoRole();
        //判断角色关联权限
        Integer countRoleInfoPer =roleInfoPermissionMapper.countRoleInfoPer();

        if (ValidateUtil.isCountEmpty(countRole)){
            List<Role> roleList = Lists.newArrayList();
            long timeMillis = System.currentTimeMillis();
            roleList.add(new Role(1l, timeMillis, timeMillis, 1l, "超级管理员","superAdmin", "", 0));
            roleList.add(new Role(2l, timeMillis, timeMillis, 1l, "管理员","admin", ConvertScaleUtil.get10to64(SnowUtil.generateId()), 1));
            roleList.add(new Role(3l, timeMillis, timeMillis, 1l, "上传员","uploadGeneral", ConvertScaleUtil.get10to64(SnowUtil.generateId()), 1));
            roleList.add(new Role(4l, timeMillis, timeMillis, 1l, "普通用户","general", "", 0));
            //省事直接for
            roleList.forEach(a->{
                roleMapper.insert(a);
            });
        }

        if (ValidateUtil.isCountEmpty(countPermission)){
            List<Permission> permissionList = Lists.newArrayList();
            long timeMillis = System.currentTimeMillis();
            permissionList.add(new Permission(1l,timeMillis,timeMillis,1l,"添加分区","addZone","/zone/addZone"));
            permissionList.add(new Permission(2l,timeMillis,timeMillis,1l,"根据分区ids删除分区","deleteZoneByIds","/zone/deleteZoneByIds"));
            permissionList.add(new Permission(3l,timeMillis,timeMillis,1l,"给用户分配角色","allotRole","/role/allotRole"));
            permissionList.add(new Permission(4l,timeMillis,timeMillis,1l,"给用户分配区域","allotZone","/role/allotZone"));
            permissionList.add(new Permission(5l,timeMillis,timeMillis,1l,"上传文件","uploadFile","/file/uploadFile"));
            permissionList.add(new Permission(6l,timeMillis,timeMillis,1l,"根据前缀删除文件","delPrefixFile","/file/delPrefixFile"));

            //省事直接for
            permissionList.forEach(a->{
                permissionMapper.insert(a);
            });
        }

        if (ValidateUtil.isCountEmpty(countUserInfoRole)){
            List<UserInfoRole> userInfoRoleList = Lists.newArrayList();
            long timeMillis = System.currentTimeMillis();
            userInfoRoleList.add(new UserInfoRole(1l,timeMillis,timeMillis,1l,1l,1l));
            userInfoRoleList.add(new UserInfoRole(2l,timeMillis,timeMillis,1l,1l,2l));
            userInfoRoleList.add(new UserInfoRole(3l,timeMillis,timeMillis,1l,1l,3l));
            userInfoRoleList.add(new UserInfoRole(4l,timeMillis,timeMillis,1l,1l,4l));
            userInfoRoleList.forEach(a->{
                userInfoRoleMapper.insert(a);
            });

        }

        if (ValidateUtil.isCountEmpty(countRoleInfoPer)){
            List<RoleInfoPermission> roleInfoPermissionList = Lists.newArrayList();
            long timeMillis = System.currentTimeMillis();
            roleInfoPermissionList.add(new RoleInfoPermission(1l,timeMillis,timeMillis,1l,1l,1l));
            roleInfoPermissionList.add(new RoleInfoPermission(2l,timeMillis,timeMillis,1l,1l,2l));
            roleInfoPermissionList.add(new RoleInfoPermission(3l,timeMillis,timeMillis,1l,1l,3l));
            roleInfoPermissionList.add(new RoleInfoPermission(4l,timeMillis,timeMillis,1l,1l,4l));
            roleInfoPermissionList.add(new RoleInfoPermission(5l,timeMillis,timeMillis,1l,1l,5l));
            roleInfoPermissionList.add(new RoleInfoPermission(6l,timeMillis,timeMillis,1l,1l,6l));
            roleInfoPermissionList.add(new RoleInfoPermission(7l,timeMillis,timeMillis,1l,2l,5l));
            roleInfoPermissionList.add(new RoleInfoPermission(8l,timeMillis,timeMillis,1l,2l,6l));
            roleInfoPermissionList.add(new RoleInfoPermission(9l,timeMillis,timeMillis,1l,3l,5l));
            roleInfoPermissionList.forEach(a->{
                roleInfoPermissionMapper.insert(a);
            });
        }
        return ResponseResult.responseOK();
    }


}
