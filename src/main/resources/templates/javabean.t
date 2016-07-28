package com.dianping.hui.order.shard.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lipeng on 16/7/12.
 */
public class HuiOrderDetailEntity implements Serializable {
    private static final long serialVersionUID = -5087426549484833372L;
    /**自增id*/
    private long id ;
    /**订单id*/
    private long orderId ;
    /**用户ip*/
    private String userIp ;
    /**仍然保留*/
    private String userAgent ;
    /**微信openid*/
    private String openId ;
    /**微信unionid*/
    private String unionId ;
    /**手机号*/
    private String mobileNo ;
    /**支付平台，例如主app/团app/MM站等等*/
    private int platform ;
    /**纬度*/
    private String latitude ;
    /**经度*/
    private String longitude ;
    /**诚信状态，0为正常单*/
    private int creditFlag ;
    /**创单入口来源，例如：商户详情页/正扫/反扫*/
    private int entranceType ;
    private String uuid;
    /***/
    private String equipmentId ;
    /**表示系统类型，例如iphone、ipad、android、wp7、androidhd等*/
    private String systemType ;
    /**表示推广渠道，例如appstore，tongbu等*/
    private String promotionChannel ;
    private String appVersion;
    /**的定义的结构化数据*/
    private String extraParams ;
    /**供应链方案id*/
    private long settleCouponofferId ;
    /**供应链方案id*/
    private long solutionId ;
    /**外部业务信息JSON数据串*/
    private String outbizString ;
    /**外部业务额外信息*/
    private String extraMessage ;
    /**创建时间*/
    private Date createdTime ;
    /**更新时间*/
    private Date updateTime ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getCreditFlag() {
        return creditFlag;
    }

    public void setCreditFlag(int creditFlag) {
        this.creditFlag = creditFlag;
    }

    public int getEntranceType() {
        return entranceType;
    }

    public void setEntranceType(int entranceType) {
        this.entranceType = entranceType;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getPromotionChannel() {
        return promotionChannel;
    }

    public void setPromotionChannel(String promotionChannel) {
        this.promotionChannel = promotionChannel;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public long getSettleCouponofferId() {
        return settleCouponofferId;
    }

    public void setSettleCouponofferId(long settleCouponofferId) {
        this.settleCouponofferId = settleCouponofferId;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(long solutionId) {
        this.solutionId = solutionId;
    }

    public String getOutbizString() {
        return outbizString;
    }

    public void setOutbizString(String outbizString) {
        this.outbizString = outbizString;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
