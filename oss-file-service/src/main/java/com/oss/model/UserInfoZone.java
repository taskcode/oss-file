package com.oss.model;

import java.io.Serializable;

public class UserInfoZone implements Serializable {

    public UserInfoZone() {
    }

    public UserInfoZone(Long id, Long createTime, Long updateTime, Long version, Long userId, Long zoneId) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.version = version;
        this.userId = userId;
        this.zoneId = zoneId;
    }

    /**
     * id
	 * 2020-10-20T17:12:13.958
     */
    private Long id;

    /**
     * 创建时间
	 * 2020-10-20T17:12:13.959
     */
    private Long createTime;

    /**
     * 修改时间
	 * 2020-10-20T17:12:13.959
     */
    private Long updateTime;

    /**
     * 版本号
	 * 2020-10-20T17:12:13.959
     */
    private Long version;

    /**
     * 用户id
	 * 2020-10-20T17:12:13.959
     */
    private Long userId;

    /**
     * 分区id
	 * 2020-10-20T17:12:13.959
     */
    private Long zoneId;

    /**
     * 2020-10-20T17:12:13.959
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 分区id
     */
    public Long getZoneId() {
        return zoneId;
    }

    /**
     * 分区id
     */
    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", version=").append(version);
        sb.append(", userId=").append(userId);
        sb.append(", zoneId=").append(zoneId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}