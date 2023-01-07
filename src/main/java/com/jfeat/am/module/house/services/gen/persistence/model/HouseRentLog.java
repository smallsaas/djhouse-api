package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
 * @since 2023-01-06
 */
@TableName("t_house_rent_log")
@ApiModel(value = "HouseRentLog对象", description = "")
public class HouseRentLog extends Model<HouseRentLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long operator;

    @TableField(exist = false)
    private String operatorName;

    @TableField(exist = false)
    private String operatorRealName;

    @TableField(exist = false)
    private String operatorPhone;

    @ApiModelProperty(value = "0-发布出租 1-房东不在出租 2-上架出租 3-下架出租")
    private Integer state;

    @TableField(exist = false)
    private String enStatus;

    @TableField(exist = false)
    private String cnStatus;

    @ApiModelProperty(value = "备注")
    private String logNote;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private Long rentId;

    @ApiModelProperty(value = "房子id")
    private Long assetId;

    @ApiModelProperty(value = "小区id")
    private Long communityId;

    @ApiModelProperty(value = "户型id")
    private Long houseTypeId;

    @ApiModelProperty(value = "房东id")
    private Long landlordId;

    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "房东上传图片")
    private String introducePicture;

    @ApiModelProperty(value = "中介id")
    private Long serverId;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "价钱")
    private BigDecimal price;

    @ApiModelProperty(value = "轮播图")
    private String slide;

    @ApiModelProperty(value = "描述信息")
    private String rentDescribe;

    @ApiModelProperty(value = "1-挂盘 2-指定中介 3-已出租")
    private Integer status;

    @ApiModelProperty(value = "1-出租未上架 2-出租上架")
    private Integer rentStatus;

    @ApiModelProperty(value = "留空")
    private String note;

    @ApiModelProperty(value = "房东出租时间")
    private Date rentTime;

    @ApiModelProperty(value = "上架时间时间")
    private Date shelvesTime;

    @ApiModelProperty(value = "评分")
    private Integer rate;

    @ApiModelProperty(value = "房间号")
    private String houseNumber;

    @ApiModelProperty(value = "楼层")
    private Integer floor;

    @ApiModelProperty(value = "朝向")
    private String toward;

    @ApiModelProperty(value = "楼栋号")
    private String buildingCode;

    @ApiModelProperty(value = "小区名")
    private String communityName;

    @ApiModelProperty(value = "是否有房")
    private Integer configurationStatus;

    @ApiModelProperty(value = "创建时间")
    private Date rentCreateTime;

    @ApiModelProperty(value = "更新时间")
    private Date rentUpdateTime;

    @ApiModelProperty(value = "自定义图片")
    private String customImagesList;


    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorRealName() {
        return operatorRealName;
    }

    public void setOperatorRealName(String operatorRealName) {
        this.operatorRealName = operatorRealName;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getEnStatus() {
        return enStatus;
    }

    public void setEnStatus(String enStatus) {
        this.enStatus = enStatus;
    }

    public String getCnStatus() {
        return cnStatus;
    }

    public void setCnStatus(String cnStatus) {
        this.cnStatus = cnStatus;
    }

    public Long getId() {
        return id;
    }

    public HouseRentLog setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getOperator() {
        return operator;
    }

    public HouseRentLog setOperator(Long operator) {
        this.operator = operator;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public HouseRentLog setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getLogNote() {
        return logNote;
    }

    public HouseRentLog setLogNote(String logNote) {
        this.logNote = logNote;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseRentLog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public HouseRentLog setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getRentId() {
        return rentId;
    }

    public HouseRentLog setRentId(Long rentId) {
        this.rentId = rentId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseRentLog setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public HouseRentLog setCommunityId(Long communityId) {
        this.communityId = communityId;
        return this;
    }

    public Long getHouseTypeId() {
        return houseTypeId;
    }

    public HouseRentLog setHouseTypeId(Long houseTypeId) {
        this.houseTypeId = houseTypeId;
        return this;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public HouseRentLog setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public HouseRentLog setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getIntroducePicture() {
        return introducePicture;
    }

    public HouseRentLog setIntroducePicture(String introducePicture) {
        this.introducePicture = introducePicture;
        return this;
    }

    public Long getServerId() {
        return serverId;
    }

    public HouseRentLog setServerId(Long serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getCover() {
        return cover;
    }

    public HouseRentLog setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HouseRentLog setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public HouseRentLog setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSlide() {
        return slide;
    }

    public HouseRentLog setSlide(String slide) {
        this.slide = slide;
        return this;
    }

    public String getRentDescribe() {
        return rentDescribe;
    }

    public HouseRentLog setRentDescribe(String rentDescribe) {
        this.rentDescribe = rentDescribe;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public HouseRentLog setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public HouseRentLog setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseRentLog setNote(String note) {
        this.note = note;
        return this;
    }

    public Date getRentTime() {
        return rentTime;
    }

    public HouseRentLog setRentTime(Date rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public Date getShelvesTime() {
        return shelvesTime;
    }

    public HouseRentLog setShelvesTime(Date shelvesTime) {
        this.shelvesTime = shelvesTime;
        return this;
    }

    public Integer getRate() {
        return rate;
    }

    public HouseRentLog setRate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public HouseRentLog setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public HouseRentLog setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public String getToward() {
        return toward;
    }

    public HouseRentLog setToward(String toward) {
        this.toward = toward;
        return this;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public HouseRentLog setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
        return this;
    }

    public String getCommunityName() {
        return communityName;
    }

    public HouseRentLog setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }

    public Integer getConfigurationStatus() {
        return configurationStatus;
    }

    public HouseRentLog setConfigurationStatus(Integer configurationStatus) {
        this.configurationStatus = configurationStatus;
        return this;
    }

    public Date getRentCreateTime() {
        return rentCreateTime;
    }

    public HouseRentLog setRentCreateTime(Date rentCreateTime) {
        this.rentCreateTime = rentCreateTime;
        return this;
    }

    public Date getRentUpdateTime() {
        return rentUpdateTime;
    }

    public HouseRentLog setRentUpdateTime(Date rentUpdateTime) {
        this.rentUpdateTime = rentUpdateTime;
        return this;
    }

    public String getCustomImagesList() {
        return customImagesList;
    }

    public HouseRentLog setCustomImagesList(String customImagesList) {
        this.customImagesList = customImagesList;
        return this;
    }

    public static final String ID = "id";

    public static final String OPERATOR = "operator";

    public static final String STATE = "state";

    public static final String LOG_NOTE = "log_note";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String RENT_ID = "rent_id";

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

    public static final String RENT_DESCRIBE = "rent_describe";

    public static final String STATUS = "status";

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

    public static final String RENT_CREATE_TIME = "rent_create_time";

    public static final String RENT_UPDATE_TIME = "rent_update_time";

    public static final String CUSTOM_IMAGES_LIST = "custom_images_list";

//    上架
    public static final Integer PUT_ON_SHELVES = 1;

//    下架
    public static final Integer PULL_OFF_SHELVES = 2;

//    更新信息
    public static final Integer UPDATE_RENT_INFO = 3;

//    发布
    public static final Integer CREATE_RENT_INFO = 4;

//    删除
    public static final Integer DELETE_RENT_INFO = 5;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseRentLog{" +
                "id=" + id +
                ", operator=" + operator +
                ", state=" + state +
                ", logNote=" + logNote +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", rentId=" + rentId +
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
                ", rentDescribe=" + rentDescribe +
                ", status=" + status +
                ", rentStatus=" + rentStatus +
                ", note=" + note +
                ", rentTime=" + rentTime +
                ", shelvesTime=" + shelvesTime +
                ", rate=" + rate +
                ", houseNumber=" + houseNumber +
                ", floor=" + floor +
                ", toward=" + toward +
                ", buildingCode=" + buildingCode +
                ", communityName=" + communityName +
                ", configurationStatus=" + configurationStatus +
                ", rentCreateTime=" + rentCreateTime +
                ", rentUpdateTime=" + rentUpdateTime +
                ", customImagesList=" + customImagesList +
                "}";
    }
}
