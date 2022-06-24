package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.crypto.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@TableName("t_house_user_asset")
@ApiModel(value = "HouseUserAsset对象", description = "")
public class HouseUserAsset extends Model<HouseUserAsset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "资产id")
    private Long assetId;

    private Boolean rentStatus;

    private Data createTime;

    private Date rentTime;

    private String note;

    private Long clashUserId;

    private String clashDescribe;

    private String clashCertificate;





    @ApiModelProperty(value = "社区Id")
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

    @ApiModelProperty(value = "区域")
    @TableField(exist = false)
    private String buildingArea;

    @ApiModelProperty(value = "单元Id")
    @TableField(exist = false)
    private Long unitId;

    @ApiModelProperty(value = "户型")
    @TableField(exist = false)
    private String houseType;

    @ApiModelProperty(value = "房屋面积")
    @TableField(exist = false)
    private BigDecimal unitArea;

    @ApiModelProperty(value = "房屋编号")
    @TableField(exist = false)
    private String roomNumber;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String userPhone;

    @TableField(exist = false)
    private String userAvatar;

    @TableField(exist = false)
    private String address;

    @ApiModelProperty("冲突用户名")
    @TableField(exist = false)
    private String clashUserName;

    @ApiModelProperty("冲突用户电话")
    @TableField(exist = false)
    private String clashUserPhone;

    @ApiModelProperty("冲突用户头像")
    @TableField(exist = false)
    private String clashUserAvatar;



    public String getClashUserName() {
        return clashUserName;
    }

    public HouseUserAsset setClashUserName(String clashUserName) {
        this.clashUserName = clashUserName;
        return this;
    }

    public String getClashUserPhone() {
        return clashUserPhone;
    }

    public HouseUserAsset setClashUserPhone(String clashUserPhone) {
        this.clashUserPhone = clashUserPhone;
        return this;
    }

    public Boolean getRentStatus() {
        return rentStatus;
    }

    public HouseUserAsset setRentStatus(Boolean rentStatus) {
        this.rentStatus = rentStatus;
        return this;
    }

    public Data getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Data createTime) {
        this.createTime = createTime;
    }

    public Date getRentTime() {
        return rentTime;
    }

    public HouseUserAsset setRentTime(Date rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseUserAsset setNote(String note) {
        this.note = note;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HouseUserAsset setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public HouseUserAsset setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public HouseUserAsset setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
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

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public BigDecimal getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(BigDecimal unitArea) {
        this.unitArea = unitArea;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return id;
    }

    public HouseUserAsset setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseUserAsset setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseUserAsset setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public HouseUserAsset setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public Long getClashUserId() {
        return clashUserId;
    }

    public HouseUserAsset setClashUserId(Long clashUserId) {
        this.clashUserId = clashUserId;
        return this;
    }

    public String getClashDescribe() {
        return clashDescribe;
    }

    public HouseUserAsset setClashDescribe(String clashDescribe) {
        this.clashDescribe = clashDescribe;
        return this;
    }

    public String getClashCertificate() {
        return clashCertificate;
    }

    public HouseUserAsset setClashCertificate(String clashCertificate) {
        this.clashCertificate = clashCertificate;
        return this;
    }

    public String getClashUserAvatar() {
        return clashUserAvatar;
    }

    public void setClashUserAvatar(String clashUserAvatar) {
        this.clashUserAvatar = clashUserAvatar;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ASSET_ID = "asset_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseUserAsset{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                "}";
    }
}
