package com.oss.service;

import com.oss.config.BaseService;
import com.oss.model.User;
import com.oss.pojo.dto.UserSelectKeyDto;
import com.oss.tool.ResponseResult;

/**
 * @InterfaceName：UserService
 * @Description: 用户
 * @Author：13738700108
 * @Data 2020/10/31 20:20
 * @Version: v1.0
 **/
public interface UserService extends BaseService {

    /**
     * 根据名字查询用户
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);

    /**
     * 分页条件查询用户实体
     * @param userSelectKeyDto
     * @return
     */
    ResponseResult pageUserBySelectKey(UserSelectKeyDto userSelectKeyDto);

    /**
     * 用户输入分区密匙获得分区
     * @param userId
     * @param pwd
     * @return
     */
    ResponseResult getZoneKey(Long userId,String pwd);

    /**
     * 用户输入角色密匙获得角色
     * @param userId
     * @param pwd
     * @return
     */
    ResponseResult getRoleKey(long userId, String pwd);
}