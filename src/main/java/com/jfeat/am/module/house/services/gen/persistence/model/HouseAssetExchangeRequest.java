package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-09-13
 */
@TableName("t_house_asset_exchange_request")
@ApiModel(value = "HouseAssetExchangeRequest对象", description = "")
public class HouseAssetExchangeRequest extends Model<HouseAssetExchangeRequest> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "资产id")
    private Long assetId;

    @ApiModelProperty(value = "目标资产id")
    private Long targetAsset;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否自动生成 0-不是 1-是")
    private Boolean autoGenerateStatus;


    @ApiModelProperty(value = "目标资产范围")
    @TableField(exist = false)
    private String targetAssetRange;

    @ApiModelProperty(value = "社区id")
    @TableField(exist = false)
    private Long orgId;

    @ApiModelProperty(value = "小区id")
    @TableField(exist = false)
    private Long communityId;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;


    @ApiModelProperty(value = "楼栋id")
    @TableField(exist = false)
    private Long buildingId;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;

    @ApiModelProperty(value = "期数")
    @TableField(exist = false)
    private Integer issue;

    @ApiModelProperty(value = "单元id")
    @TableField(exist = false)
    private Long unitId;

    @TableField(exist = false)
    private String direction;

    @ApiModelProperty(value = "第几楼陈")
    @TableField(exist = false)
    private Integer floor;

    @TableField(exist = false)
    private String number;

    @ApiModelProperty(value = "地址")
    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private String cadPicture;

    @TableField(exist = false)
    private Long designModelId;

    @TableField(exist = false)
    private String houseType;

    @TableField(exist = false)
    private String houseTypePicture;

    @TableField(exist = false)
    private String vrPicture;

    @TableField(exist = false)
    private String vrLink;

    @TableField(exist = false)
    private String vrSnapshot;

    @TableField(exist = false)
    private BigDecimal area;

    @TableField(exist = false)
    private String targetRangeStr;

    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("用户电话")
    @TableField(exist = false)
    private String userPhone;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userAvatar;


    public Boolean getAutoGenerateStatus() {
        return autoGenerateStatus;
    }

    public void setAutoGenerateStatus(Boolean autoGenerateStatus) {
        this.autoGenerateStatus = autoGenerateStatus;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCadPicture() {
        return cadPicture;
    }

    public void setCadPicture(String cadPicture) {
        this.cadPicture = cadPicture;
    }

    public Long getDesignModelId() {
        return designModelId;
    }

    public void setDesignModelId(Long designModelId) {
        this.designModelId = designModelId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseTypePicture() {
        return houseTypePicture;
    }

    public void setHouseTypePicture(String houseTypePicture) {
        this.houseTypePicture = houseTypePicture;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public void setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
    }

    public String getVrLink() {
        return vrLink;
    }

    public void setVrLink(String vrLink) {
        this.vrLink = vrLink;
    }

    public String getVrSnapshot() {
        return vrSnapshot;
    }

    public void setVrSnapshot(String vrSnapshot) {
        this.vrSnapshot = vrSnapshot;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getTargetRangeStr() {
        return targetRangeStr;
    }

    public void setTargetRangeStr(String targetRangeStr) {
        this.targetRangeStr = targetRangeStr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getTargetAssetRange() {
        return targetAssetRange;
    }

    public void setTargetAssetRange(String targetAssetRange) {
        this.targetAssetRange = targetAssetRange;
    }

    public Long getId() {
        return id;
    }

    public HouseAssetExchangeRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseAssetExchangeRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseAssetExchangeRequest setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Long getTargetAsset() {
        return targetAsset;
    }

    public HouseAssetExchangeRequest setTargetAsset(Long targetAsset) {
        this.targetAsset = targetAsset;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseAssetExchangeRequest setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public HouseAssetExchangeRequest setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ASSET_ID = "asset_id";

    public static final String TARGET_ASSET = "target_asset";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String AUTO_GENERATE_STATUS = "auto_generate_status";


    public static final Boolean AUTO_GENERATE_STATUS_YES = true;

    public static final Boolean AUTO_GENERATE_STATUS_NO = false;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAssetExchangeRequest{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", targetAsset=" + targetAsset +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
