
export const setCode = (state, data) =>{
  state.code = data
}

/** 改变验证码倒计时*/
export const setCountDown = (state, data) =>{
  state.countDown = data
}

/** 改变用户登录状态*/
export const setIsLogin = (state, data) =>{
  state.isLogin = data
}

/** 改变用户id*/
export const setUserId = (state, data) =>{
  state.userId = data
}

/** 改变用户名称*/
export const setUserNickName = (state, data) =>{
  state.userNickName = data
}

/** 改变用户头像*/
export const setUserImg = (state, data) =>{
  state.userImg = data
}

/** 改变用户权限*/
export const setUserRole = (state, data) =>{
  state.userRole = data
}

/** 改变我的角色列表*/
export const setMyRoleList = (state, data) =>{
  state.myRoleList = data
}

/** 改变我的分区列表*/
export const setMyZoneList = (state, data) =>{
  state.myZoneList = data
}

/** 改变分区列表*/
export const setZoneList = (state, data) =>{
  state.zoneList = data
}

/** 改变分区当前页数*/
export const setZonePage = (state, data) =>{
  state.zonePage = data
}

/** 改变分区总条数*/
export const setZoneTotal = (state, data) =>{
  state.zoneTotal = data
}
