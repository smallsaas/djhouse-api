package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-07-16
 */
@TableName("t_house_rent_asset")
@ApiModel(value = "HouseRentAsset对象", description = "")
public class HouseRentAsset extends Model<HouseRentAsset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "房子id")
    private Long assetId;

    @ApiModelProperty(value = "小区id")
    private Long communityId;

    @ApiModelProperty(value = "户型id")
    private Long houseTypeId;

    @ApiModelProperty(value = "房东id")
    private Long landlordId;


    @TableField(exist = false)
    private String landlordName;

    @TableField(exist = false)
    private String landlordRealName;

    @TableField(exist = false)
    private String landlordAvatar;

    @TableField(exist = false)
    private String landlordPhone;


    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "房东上传图片")
    private String introducePicture;

    @ApiModelProperty(value = "中介id")
    private Long serverId;

    @ApiModelProperty(value = "封面")
    @Value("/2022/image/dajiang.png")
    private String cover;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "价钱")
    private BigDecimal price;

    @ApiModelProperty(value = "轮播图")
    private String slide;

    @ApiModelProperty(value = "描述信息")
    private String rentDescribe;

    @ApiModelProperty(value = "1-出租未上架 2-出租上架")
    private Integer rentStatus;

    @ApiModelProperty(value = "1-挂盘 2-指定中介 3-已出租")
    private Integer status;

    @ApiModelProperty(value = "留空")
    private String note;

    @ApiModelProperty(value = "房东出租时间")
    private Date rentTime;

    @ApiModelProperty(value = "上架时间时间")
    private Date shelvesTime;

    @ApiModelProperty(value = "评分 0-10")
    private Integer rate;

    @ApiModelProperty(value = "房间号")
    private String houseNumber;

    @ApiModelProperty(value = "楼层")
    private Integer floor;

    @ApiModelProperty(value = "朝向")
    private String toward;

    @ApiModelProperty("楼栋号")
    private String buildingCode;

    @ApiModelProperty("小区名")
    private String communityName;

    private Date createTime;

    private Date updateTime;


    private String customImagesList;

    @TableField( updateStrategy = FieldStrategy.IGNORED)
    private Integer state;

    @TableField( updateStrategy = FieldStrategy.IGNORED)
    private Date contractStartTime;

    @TableField( updateStrategy = FieldStrategy.IGNORED)
    private Date contractEndTime;


    @TableField( updateStrategy = FieldStrategy.IGNORED)
    private Integer contractTimeLimit;

    private Boolean configurationStatus;

    @TableField(exist = false)
    private Boolean subscribeStatus;

    @TableField(exist = false)
    private String extra;

    @TableField(exist = false)
    private HouseAssetModel houseAssetModel;

    @TableField(exist = false)
    private String serverPhone;

    @TableField(exist = false)
    private String serverName;

    @TableField(exist = false)
    private String serverContact;

    @TableField(exist = false)
    private String serverAvatar;

    public String getServerContact() {
        return serverContact;
    }

    public void setServerContact(String serverContact) {
        this.serverContact = serverContact;
    }

    public Integer getContractTimeLimit() {
        return contractTimeLimit;
    }

    public void setContractTimeLimit(Integer contractTimeLimit) {
        this.contractTimeLimit = contractTimeLimit;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getLandlordRealName() {
        return landlordRealName;
    }

    public void setLandlordRealName(String landlordRealName) {
        this.landlordRealName = landlordRealName;
    }

    public String getLandlordAvatar() {
        return landlordAvatar;
    }

    public void setLandlordAvatar(String landlordAvatar) {
        this.landlordAvatar = landlordAvatar;
    }

    public String getLandlordPhone() {
        return landlordPhone;
    }

    public void setLandlordPhone(String landlordPhone) {
        this.landlordPhone = landlordPhone;
    }

    public String getCustomImagesList() {
        return customImagesList;
    }

    public void setCustomImagesList(String customImagesList) {
        this.customImagesList = customImagesList;
    }

    @ApiModelProperty(value = "出租家居列表清单")
    @TableField(exist = false)
    private List<HouseRentSupportFacilitiesRecord> supportFacilitiesList;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getConfigurationStatus() {
        return configurationStatus;
    }

    public void setConfigurationStatus(Boolean configurationStatus) {
        this.configurationStatus = configurationStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getToward() {
        return toward;
    }

    public void setToward(String toward) {
        this.toward = toward;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Boolean getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(Boolean subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public List<HouseRentSupportFacilitiesRecord> getSupportFacilitiesList() {
        return supportFacilitiesList;
    }

    public void setSupportFacilitiesList(List<HouseRentSupportFacilitiesRecord> supportFacilitiesList) {
        this.supportFacilitiesList = supportFacilitiesList;
    }

    public String getServerPhone() {
        return serverPhone;
    }

    public void setServerPhone(String serverPhone) {
        this.serverPhone = serverPhone;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAvatar() {
        return serverAvatar;
    }

    public void setServerAvatar(String serverAvatar) {
        this.serverAvatar = serverAvatar;
    }

    public HouseAssetModel getHouseAssetModel() {
        return houseAssetModel;
    }

    public void setHouseAssetModel(HouseAssetModel houseAssetModel) {
        this.houseAssetModel = houseAssetModel;
    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public Long getId() {
        return id;
    }

    public HouseRentAsset setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseRentAsset setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public HouseRentAsset setCommunityId(Long communityId) {
        this.communityId = communityId;
        return this;
    }

    public Long getHouseTypeId() {
        return houseTypeId;
    }

    public HouseRentAsset setHouseTypeId(Long houseTypeId) {
        this.houseTypeId = houseTypeId;
        return this;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public HouseRentAsset setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public HouseRentAsset setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getIntroducePicture() {
        return introducePicture;
    }

    public HouseRentAsset setIntroducePicture(String introducePicture) {
        this.introducePicture = introducePicture;
        return this;
    }

    public Long getServerId() {
        return serverId;
    }

    public HouseRentAsset setServerId(Long serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getCover() {
        return cover;
    }

    public HouseRentAsset setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HouseRentAsset setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public HouseRentAsset setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSlide() {
        return slide;
    }

    public HouseRentAsset setSlide(String slide) {
        this.slide = slide;
        return this;
    }

    public String getRentDescribe() {
        return rentDescribe;
    }

    public void setRentDescribe(String rentDescribe) {
        this.rentDescribe = rentDescribe;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public HouseRentAsset setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseRentAsset setNote(String note) {
        this.note = note;
        return this;
    }

    public Date getRentTime() {
        return rentTime;
    }

    public HouseRentAsset setRentTime(Date rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public Date getShelvesTime() {
        return shelvesTime;
    }

    public HouseRentAsset setShelvesTime(Date shelvesTime) {
        this.shelvesTime = shelvesTime;
        return this;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public static final String ID = "id";

    public static final String ASSET_ID = "asset_id";

    public static final String COMMUNITY_ID = "community_id";

    public static final String HOUSE_TYPE_ID = "house_type_id";

    public static final String LANDLORD_ID = "landlord_id";

    public static final String AREA = "area";

    public static final String INTRODUCE_PICTURE = "introduce_picture";

    public static final String SERVER_ID = "server_id";

    public static final String COVER = "cover";

    public static final String TITLE = "title";

    public static final String PRICE = "price";

    public static final String SLIDE = "slide";

    public static final String DESCRIBE = "describe";

    public static final String RENT_STATUS = "rent_status";

    public static final String NOTE = "note";

    public static final String RENT_TIME = "rent_time";

    public static final String SHELVES_TIME = "shelves_time";

    public static final String RATE = "rate";

    public static final String HOUSE_NUMBER = "house_number";

    public static final String FLOOR = "floor";

    public static final String TOWARD = "toward";

    public static final String BUILDING_CODE = "building_code";

    public static final String COMMUNITY_NAME = "community_name";

    public static final String CONFIGURATION_STATUS = "configuration_status";

    public static final Integer RENT_STATUS_SHELVES = 2;

    public static final Integer RENT_STATUS_SOLD_OUT = 1;

    public static final Integer STATUS_HAND_DISH=1;
    public static final Integer STATUS_POINT=2;
    public static final Integer STATUS_RENTED=3;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseRentAsset{" +
                "id=" + id +
                ", assetId=" + assetId +
                ", communityId=" + communityId +
                ", houseTypeId=" + houseTypeId +
                ", landlordId=" + landlordId +
                ", area=" + area +
                ", introducePicture=" + introducePicture +
                ", serverId=" + serverId +
                ", cover=" + cover +
                ", title=" + title +
                ", price=" + price +
                ", slide=" + slide +
                ", describe=" + rentDescribe +
                ", rentStatus=" + rentStatus +
                ", note=" + note +
                ", rentTime=" + rentTime +
                ", shelvesTime=" + shelvesTime +
                "}";
    }
}
