package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
