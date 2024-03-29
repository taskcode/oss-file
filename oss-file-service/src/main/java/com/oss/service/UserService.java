package com.oss.service;

import com.github.pagehelper.Page;
import com.oss.model.Permission;
import com.oss.model.Role;
import com.oss.pojo.dto.RetrievePwdDto;
import com.oss.pojo.vo.RoleVo;
import com.oss.pojo.vo.UserVo;
import com.oss.tool.BaseService;
import com.oss.model.User;
import com.oss.pojo.dto.RegisterDto;
import com.oss.pojo.dto.UserSelectKeyDto;
import com.oss.tool.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    ResponseResult<Page<UserVo>> pageUserBySelectKey(UserSelectKeyDto userSelectKeyDto);

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
    ResponseResult<List<String>> getRoleKey(long userId, String pwd);

    /**
     * 初始化项目
     * @param nickName
     * @param userImg
     * @param account
     * @param pwd
     */
    ResponseResult  start(String userId,String nickName, String userImg, String account, String pwd);

    /**
     * 根据手机号查询用户
     * @param account
     * @return
     */
    ResponseResult<User> findUserByAccount(String account);

    /**
     * 添加用户
     * @param registerDto
     * @return
     */
    ResponseResult addUser(RegisterDto registerDto);

    /**
     * 查询用户拥有的角色
     * @param userId
     * @return
     */
    ResponseResult<List<RoleVo>> selectRoleByUserId(Long userId);

    /**
     * 查询用户拥有权限
     * @param roleJoin
     * @return
     */
    ResponseResult<List<Permission>> selectPermissionByRoleIds(String roleJoin);


    /**
     * 查询我的角色及密匙
     * @param userId
     * @return
     */
    ResponseResult<List<RoleVo>> getMyRolePwd(long userId);

    /**
     * 修改用户名称
     * @param userName
     * @return
     */
    ResponseResult updateUserName(String userName,long userId);

    /**
     * 修改用户头像
     * @param file
     * @param userId
     * @return
     */
    ResponseResult updateUserImg(MultipartFile file, Long userId);

    /**
     * 修改用户密码
     * @param retrievePwdDto
     * @return
     */
    ResponseResult updateUserPwd(RetrievePwdDto retrievePwdDto);
}
