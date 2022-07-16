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
    private String describe;

    @ApiModelProperty(value = "1-出租未上架 2-出租上架")
    private Integer rentStatus;

    @ApiModelProperty(value = "留空")
    private String note;

    @ApiModelProperty(value = "房东出租时间")
    private Date rentTime;

    @ApiModelProperty(value = "上架时间时间")
    private Date shelvesTime;

    @TableField(exist = false)
    private String extra;

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

    public String getDescribe() {
        return describe;
    }

    public HouseRentAsset setDescribe(String describe) {
        this.describe = describe;
        return this;
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
                ", describe=" + describe +
                ", rentStatus=" + rentStatus +
                ", note=" + note +
                ", rentTime=" + rentTime +
                ", shelvesTime=" + shelvesTime +
                "}";
    }
}
