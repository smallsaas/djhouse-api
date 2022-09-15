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
@TableName("t_house_asset")
@ApiModel(value = "HouseAsset对象", description = "")
public class HouseAsset extends Model<HouseAsset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "楼栋id")
    private Long buildingId;

    @ApiModelProperty(value = "单元id")
    private Long unitId;

    @ApiModelProperty(value = "第几楼陈")
    private Integer floor;

    @ApiModelProperty(value = "房产编号")
    private String number;

    @ApiModelProperty(value = "资产卡位 备用")
    private String assetSlot;

    @ApiModelProperty(value = "资产类型")
    private Integer assetType;

    @ApiModelProperty(value = "0-无效房子 1-平房  2-复式房")
    private Integer assetFlag;

    @ApiModelProperty("当为复式时有户型id")
    private Long houseTypeId;

    @ApiModelProperty(value = "门牌编号")
    private String houseNumber;


    @ApiModelProperty(value = "社区Id")
    @TableField(exist = false)
    private Long communityId;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;

    @ApiModelProperty(value = "区域")
    @TableField(exist = false)
    private String buildingArea;

    @ApiModelProperty(value ="期数")
    @TableField(exist = false)
    private Integer issue;

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

    @ApiModelProperty("用户id")
    @TableField(exist = false)
    private Long userId;

    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("用户电话")
    @TableField(exist = false)
    private String userPhone;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userAvatar;

    @TableField(exist = false)
    private Boolean existRent;

    @TableField(exist = false)
    private Boolean existUser;

    @TableField(exist = false)
    private Long exchangeRequestId;

    public Long getExchangeRequestId() {
        return exchangeRequestId;
    }

    public void setExchangeRequestId(Long exchangeRequestId) {
        this.exchangeRequestId = exchangeRequestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getExistUser() {
        return existUser;
    }

    public void setExistUser(Boolean existUser) {
        this.existUser = existUser;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Boolean getExistRent() {
        return existRent;
    }

    public void setExistRent(Boolean existRent) {
        this.existRent = existRent;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public HouseAsset setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
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

    public Long getId() {
        return id;
    }

    public HouseAsset setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public HouseAsset setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public HouseAsset setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public HouseAsset setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public HouseAsset setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getAssetSlot() {
        return assetSlot;
    }

    public HouseAsset setAssetSlot(String assetSlot) {
        this.assetSlot = assetSlot;
        return this;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAddress() {
        return address;
    }

    public HouseAsset setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCadPicture() {
        return cadPicture;
    }

    public HouseAsset setCadPicture(String cadPicture) {
        this.cadPicture = cadPicture;
        return this;
    }

    public Long getDesignModelId() {
        return designModelId;
    }

    public HouseAsset setDesignModelId(Long designModelId) {
        this.designModelId = designModelId;
        return this;
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

    public HouseAsset setHouseTypePicture(String houseTypePicture) {
        this.houseTypePicture = houseTypePicture;
        return this;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public HouseAsset setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
        return this;
    }

    public String getVrLink() {
        return vrLink;
    }

    public HouseAsset setVrLink(String vrLink) {
        this.vrLink = vrLink;
        return this;
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

    public Integer getAssetFlag() {
        return assetFlag;
    }

    public void setAssetFlag(Integer assetFlag) {
        this.assetFlag = assetFlag;
    }

    public Long getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(Long houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public static final String ID = "id";

    public static final String BUILDING_ID = "building_id";

    public static final String UNIT_ID = "unit_id";

    public static final String FLOOR = "floor";

    public static final String NUMBER = "number";

    public static final String ASSET_SLOT = "asset_slot";

    public static final String ASSET_TYPE = "asset_type";

    public static final String HOUSE_NUMBER = "house_number";
    /*
    房子类型
     */
    public static final Integer ASSET_Type_HOUSE = 1;

    /*
    车位类型
     */
    public static final Integer ASSET_TYPE_PARKING = 2;

    /*
    无效房子
     */
    public static final Integer ASSET_FLAG_INVALID = 0;

    /*
    平房
     */
    public static final Integer ASSET_FLAG_BUNGALOW = 1;

    /*
    复式
     */
    public static final Integer ASSET_FLAG_MULTIPLE = 2;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAsset{" +
                "id=" + id +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", floor=" + floor +
                ", number=" + number +
                ", assetSlot=" + assetSlot +
                ", assetType=" + assetType +
                "}";
    }
}
