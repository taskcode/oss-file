import LoginService from '@/service/LoginService'
import RoleService from '@/service/RoleService'
import ZoneService from '@/service/ZoneService'

/**
 * 发送验证码
 * @param commit
 */
export function sendSms({commit},param) {
  LoginService.sendSms(param).then((res)=>{
    console.log("this is getList work",res)
    commit('setCode',1)
  })
}

/**
 * 分区列表
 * @param commit
 * @param param
 */
export function getZoneList({commit},param) {
  ZoneService.getZoneList(param).then((res)=>{
    if (10000==res.error){
      commit('setZoneList',res.data)
    }
  })
}

/**
 *查询我的角色所有密匙
 * @param commit
 * @param param
 */
export function getMyRolePwd({commit},param) {
  RoleService.getMyRolePwd(param).then((res)=>{
    if (10000==res.error){
      commit('setMyRoleList',res.data)
    }
  })
}

/**
 *查询我的分区所有密匙
 * @param commit
 * @param param
 */
export function getMyZonePwd({commit},param) {
  ZoneService.getMyZonePwd(param).then((res)=>{
    if (10000==res.error){
      commit('setMyZoneList',res.data)
    }
  })
}
