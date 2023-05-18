package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2022-06-08
 */
@TableName("t_house_design_model")
@ApiModel(value = "HouseDesignModel对象", description = "")
public class HouseDesignModel extends Model<HouseDesignModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "户型名")
    private String houseType;

    @ApiModelProperty(value = "简单描述")
    private String description;

    @ApiModelProperty(value = "户型图")
    private String houseTypePicture;

    @ApiModelProperty("描述信息")
    private String note;

    @ApiModelProperty(value = "小区id")
    private Long communityId;

    @ApiModelProperty(value = "vrId")
    private Long vrId;

    @ApiModelProperty(value = "vr图")
    @TableField(exist = false)
    private String vrPicture;

    @ApiModelProperty(value = "vr图连接")
    @TableField(exist = false)
    private String vrLink;

    @ApiModelProperty(value = "vr缩略图")
    @TableField(exist = false)
    private String vrSnapshot;

    @ApiModelProperty(value = "总面积")
    private BigDecimal area;

    @ApiModelProperty(value = "真实面积")
    private BigDecimal realArea;

    @ApiModelProperty("tag")
    private String tag;

    @ApiModelProperty(value = "客厅宽度")
    private BigDecimal hallWidth;

    @ApiModelProperty(value = "客厅短墙宽")
    private BigDecimal hallSubWidth;

    @ApiModelProperty(value = "客厅长度")
    private BigDecimal hallLength;

    @ApiModelProperty(value = "客厅落地窗宽度")
    private BigDecimal hallFrenchWindowWidth;

    @ApiModelProperty(value = "客厅落地窗高度")
    private BigDecimal hallFrenchWindowHight;

    @ApiModelProperty(value = "餐厅宽度")
    private BigDecimal diningRoomWidth;

    @ApiModelProperty(value = "餐厅长度")
    private BigDecimal diningRoomLength;

    @ApiModelProperty(value = "房间宽度")
    private BigDecimal firstRoomWidth;

    @ApiModelProperty(value = "房间长度")
    private BigDecimal firstRoomLength;

    @ApiModelProperty(value = "房间门墙宽度")
    private BigDecimal firstRoomDoorwallWidth;

    @ApiModelProperty(value = "房间窗宽度")
    private BigDecimal firstRoomWindowWidth;

    @ApiModelProperty(value = "房间窗转节宽度")
    private BigDecimal firstRoomWindowSubWidth;

    @ApiModelProperty(value = "房间窗高度度")
    private BigDecimal firstRoomWindowHight;

    @ApiModelProperty(value = "房间宽度")
    private BigDecimal secondRoomWidth;

    @ApiModelProperty(value = "房间长度")
    private BigDecimal secondRoomLength;

    @ApiModelProperty(value = "房间门墙宽度")
    private BigDecimal secondRoomDoorwallWidth;

    @ApiModelProperty(value = "房间窗宽度")
    private BigDecimal secondRoomWindowWidth;

    @ApiModelProperty(value = "房间窗转节宽度")
    private BigDecimal secondRoomWindowSubWidth;

    @ApiModelProperty(value = "房间窗高度度")
    private BigDecimal secondRoomWindowHight;

    @ApiModelProperty(value = "房间宽度")
    private BigDecimal thirdRoomWidth;

    @ApiModelProperty(value = "房间长度")
    private BigDecimal thirdRoomLength;

    @ApiModelProperty(value = "房间门墙宽度")
    private BigDecimal thirdRoomDoorwallWidth;

    @ApiModelProperty(value = "房间窗宽度")
    private BigDecimal thirdRoomWindowWidth;

    @ApiModelProperty(value = "房间窗转节宽度")
    private BigDecimal thirdRoomWindowSubWidth;

    @ApiModelProperty(value = "房间窗高度度")
    private BigDecimal thirdRoomWindowHight;

    @ApiModelProperty(value = "房间宽度")
    private BigDecimal fourthRoomWidth;

    @ApiModelProperty(value = "房间长度")
    private BigDecimal fourthRoomLength;

    @ApiModelProperty(value = "房间门墙宽度")
    private BigDecimal fourthRoomDoorwallWidth;

    @ApiModelProperty(value = "房间窗宽度")
    private BigDecimal fourthRoomWindowWidth;

    @ApiModelProperty(value = "房间窗转节宽度")
    private BigDecimal fourthRoomWindowSubWidth;

    @ApiModelProperty(value = "房间窗高度度")
    private BigDecimal fourthRoomWindowHight;

    @ApiModelProperty(value = "vr名称")
    private String vrName;


    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getRealArea() {
        return realArea;
    }

    public void setRealArea(BigDecimal realArea) {
        this.realArea = realArea;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getId() {
        return id;
    }

    public HouseDesignModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getHouseType() {
        return houseType;
    }

    public HouseDesignModel setHouseType(String houseType) {
        this.houseType = houseType;
        return this;
    }


    public String getHouseTypePicture() {
        return houseTypePicture;
    }

    public HouseDesignModel setHouseTypePicture(String houseTypePicture) {
        this.houseTypePicture = houseTypePicture;
        return this;
    }

    public BigDecimal getHallWidth() {
        return hallWidth;
    }

    public HouseDesignModel setHallWidth(BigDecimal hallWidth) {
        this.hallWidth = hallWidth;
        return this;
    }

    public BigDecimal getHallSubWidth() {
        return hallSubWidth;
    }

    public HouseDesignModel setHallSubWidth(BigDecimal hallSubWidth) {
        this.hallSubWidth = hallSubWidth;
        return this;
    }

    public BigDecimal getHallLength() {
        return hallLength;
    }

    public HouseDesignModel setHallLength(BigDecimal hallLength) {
        this.hallLength = hallLength;
        return this;
    }

    public BigDecimal getHallFrenchWindowWidth() {
        return hallFrenchWindowWidth;
    }

    public HouseDesignModel setHallFrenchWindowWidth(BigDecimal hallFrenchWindowWidth) {
        this.hallFrenchWindowWidth = hallFrenchWindowWidth;
        return this;
    }

    public BigDecimal getHallFrenchWindowHight() {
        return hallFrenchWindowHight;
    }

    public HouseDesignModel setHallFrenchWindowHight(BigDecimal hallFrenchWindowHight) {
        this.hallFrenchWindowHight = hallFrenchWindowHight;
        return this;
    }

    public BigDecimal getDiningRoomWidth() {
        return diningRoomWidth;
    }

    public HouseDesignModel setDiningRoomWidth(BigDecimal diningRoomWidth) {
        this.diningRoomWidth = diningRoomWidth;
        return this;
    }

    public BigDecimal getDiningRoomLength() {
        return diningRoomLength;
    }

    public HouseDesignModel setDiningRoomLength(BigDecimal diningRoomLength) {
        this.diningRoomLength = diningRoomLength;
        return this;
    }

    public BigDecimal getFirstRoomWidth() {
        return firstRoomWidth;
    }

    public HouseDesignModel setFirstRoomWidth(BigDecimal firstRoomWidth) {
        this.firstRoomWidth = firstRoomWidth;
        return this;
    }

    public BigDecimal getFirstRoomLength() {
        return firstRoomLength;
    }

    public HouseDesignModel setFirstRoomLength(BigDecimal firstRoomLength) {
        this.firstRoomLength = firstRoomLength;
        return this;
    }

    public BigDecimal getFirstRoomDoorwallWidth() {
        return firstRoomDoorwallWidth;
    }

    public HouseDesignModel setFirstRoomDoorwallWidth(BigDecimal firstRoomDoorwallWidth) {
        this.firstRoomDoorwallWidth = firstRoomDoorwallWidth;
        return this;
    }

    public BigDecimal getFirstRoomWindowWidth() {
        return firstRoomWindowWidth;
    }

    public HouseDesignModel setFirstRoomWindowWidth(BigDecimal firstRoomWindowWidth) {
        this.firstRoomWindowWidth = firstRoomWindowWidth;
        return this;
    }

    public BigDecimal getFirstRoomWindowSubWidth() {
        return firstRoomWindowSubWidth;
    }

    public HouseDesignModel setFirstRoomWindowSubWidth(BigDecimal firstRoomWindowSubWidth) {
        this.firstRoomWindowSubWidth = firstRoomWindowSubWidth;
        return this;
    }

    public BigDecimal getFirstRoomWindowHight() {
        return firstRoomWindowHight;
    }

    public HouseDesignModel setFirstRoomWindowHight(BigDecimal firstRoomWindowHight) {
        this.firstRoomWindowHight = firstRoomWindowHight;
        return this;
    }

    public BigDecimal getSecondRoomWidth() {
        return secondRoomWidth;
    }

    public HouseDesignModel setSecondRoomWidth(BigDecimal secondRoomWidth) {
        this.secondRoomWidth = secondRoomWidth;
        return this;
    }

    public BigDecimal getSecondRoomLength() {
        return secondRoomLength;
    }

    public HouseDesignModel setSecondRoomLength(BigDecimal secondRoomLength) {
        this.secondRoomLength = secondRoomLength;
        return this;
    }

    public BigDecimal getSecondRoomDoorwallWidth() {
        return secondRoomDoorwallWidth;
    }

    public HouseDesignModel setSecondRoomDoorwallWidth(BigDecimal secondRoomDoorwallWidth) {
        this.secondRoomDoorwallWidth = secondRoomDoorwallWidth;
        return this;
    }

    public BigDecimal getSecondRoomWindowWidth() {
        return secondRoomWindowWidth;
    }

    public HouseDesignModel setSecondRoomWindowWidth(BigDecimal secondRoomWindowWidth) {
        this.secondRoomWindowWidth = secondRoomWindowWidth;
        return this;
    }

    public BigDecimal getSecondRoomWindowSubWidth() {
        return secondRoomWindowSubWidth;
    }

    public HouseDesignModel setSecondRoomWindowSubWidth(BigDecimal secondRoomWindowSubWidth) {
        this.secondRoomWindowSubWidth = secondRoomWindowSubWidth;
        return this;
    }

    public BigDecimal getSecondRoomWindowHight() {
        return secondRoomWindowHight;
    }

    public HouseDesignModel setSecondRoomWindowHight(BigDecimal secondRoomWindowHight) {
        this.secondRoomWindowHight = secondRoomWindowHight;
        return this;
    }

    public BigDecimal getThirdRoomWidth() {
        return thirdRoomWidth;
    }

    public HouseDesignModel setThirdRoomWidth(BigDecimal thirdRoomWidth) {
        this.thirdRoomWidth = thirdRoomWidth;
        return this;
    }

    public BigDecimal getThirdRoomLength() {
        return thirdRoomLength;
    }

    public HouseDesignModel setThirdRoomLength(BigDecimal thirdRoomLength) {
        this.thirdRoomLength = thirdRoomLength;
        return this;
    }

    public BigDecimal getThirdRoomDoorwallWidth() {
        return thirdRoomDoorwallWidth;
    }

    public HouseDesignModel setThirdRoomDoorwallWidth(BigDecimal thirdRoomDoorwallWidth) {
        this.thirdRoomDoorwallWidth = thirdRoomDoorwallWidth;
        return this;
    }

    public BigDecimal getThirdRoomWindowWidth() {
        return thirdRoomWindowWidth;
    }

    public HouseDesignModel setThirdRoomWindowWidth(BigDecimal thirdRoomWindowWidth) {
        this.thirdRoomWindowWidth = thirdRoomWindowWidth;
        return this;
    }

    public BigDecimal getThirdRoomWindowSubWidth() {
        return thirdRoomWindowSubWidth;
    }

    public HouseDesignModel setThirdRoomWindowSubWidth(BigDecimal thirdRoomWindowSubWidth) {
        this.thirdRoomWindowSubWidth = thirdRoomWindowSubWidth;
        return this;
    }

    public BigDecimal getThirdRoomWindowHight() {
        return thirdRoomWindowHight;
    }

    public HouseDesignModel setThirdRoomWindowHight(BigDecimal thirdRoomWindowHight) {
        this.thirdRoomWindowHight = thirdRoomWindowHight;
        return this;
    }

    public BigDecimal getFourthRoomWidth() {
        return fourthRoomWidth;
    }

    public HouseDesignModel setFourthRoomWidth(BigDecimal fourthRoomWidth) {
        this.fourthRoomWidth = fourthRoomWidth;
        return this;
    }

    public BigDecimal getFourthRoomLength() {
        return fourthRoomLength;
    }

    public HouseDesignModel setFourthRoomLength(BigDecimal fourthRoomLength) {
        this.fourthRoomLength = fourthRoomLength;
        return this;
    }

    public BigDecimal getFourthRoomDoorwallWidth() {
        return fourthRoomDoorwallWidth;
    }

    public HouseDesignModel setFourthRoomDoorwallWidth(BigDecimal fourthRoomDoorwallWidth) {
        this.fourthRoomDoorwallWidth = fourthRoomDoorwallWidth;
        return this;
    }

    public BigDecimal getFourthRoomWindowWidth() {
        return fourthRoomWindowWidth;
    }

    public HouseDesignModel setFourthRoomWindowWidth(BigDecimal fourthRoomWindowWidth) {
        this.fourthRoomWindowWidth = fourthRoomWindowWidth;
        return this;
    }

    public BigDecimal getFourthRoomWindowSubWidth() {
        return fourthRoomWindowSubWidth;
    }

    public HouseDesignModel setFourthRoomWindowSubWidth(BigDecimal fourthRoomWindowSubWidth) {
        this.fourthRoomWindowSubWidth = fourthRoomWindowSubWidth;
        return this;
    }

    public BigDecimal getFourthRoomWindowHight() {
        return fourthRoomWindowHight;
    }

    public HouseDesignModel setFourthRoomWindowHight(BigDecimal fourthRoomWindowHight) {
        this.fourthRoomWindowHight = fourthRoomWindowHight;
        return this;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public HouseDesignModel setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public HouseDesignModel setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getVrLink() {
        return vrLink;
    }

    public HouseDesignModel setVrLink(String vrLink) {
        this.vrLink = vrLink;
        return this;
    }

    public String getVrSnapshot() {
        return vrSnapshot;
    }

    public HouseDesignModel setVrSnapshot(String vrSnapshot) {
        this.vrSnapshot = vrSnapshot;
        return this;
    }

    public Long getVrId() {
        return vrId;
    }

    public void setVrId(Long vrId) {
        this.vrId = vrId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVrName() {
        return vrName;
    }

    public HouseDesignModel setVrName(String vrName) {
        this.vrName = vrName;
        return this;
    }

    public static final String ID = "id";

    public static final String HOUSE_TYPE = "house_type";

    public static final String HOUSE_TYPE_PICTURE = "house_type_picture";

    public static final String AREA = "area";

    public static final String HALL_WIDTH = "hall_width";

    public static final String HALL_SUB_WIDTH = "hall_sub_width";

    public static final String HALL_LENGTH = "hall_length";

    public static final String HALL_FRENCH_WINDOW_WIDTH = "hall_french_window_width";

    public static final String HALL_FRENCH_WINDOW_HIGHT = "hall_french_window_hight";

    public static final String DINING_ROOM_WIDTH = "dining_room_width";

    public static final String DINING_ROOM_LENGTH = "dining_room_length";

    public static final String FIRST_ROOM_WIDTH = "first_room_width";

    public static final String FIRST_ROOM_LENGTH = "first_room_length";

    public static final String FIRST_ROOM_DOORWALL_WIDTH = "first_room_doorwall_width";

    public static final String FIRST_ROOM_WINDOW_WIDTH = "first_room_window_width";

    public static final String FIRST_ROOM_WINDOW_SUB_WIDTH = "first_room_window_sub_width";

    public static final String FIRST_ROOM_WINDOW_HIGHT = "first_room_window_hight";

    public static final String SECOND_ROOM_WIDTH = "second_room_width";

    public static final String SECOND_ROOM_LENGTH = "second_room_length";

    public static final String SECOND_ROOM_DOORWALL_WIDTH = "second_room_doorwall_width";

    public static final String SECOND_ROOM_WINDOW_WIDTH = "second_room_window_width";

    public static final String SECOND_ROOM_WINDOW_SUB_WIDTH = "second_room_window_sub_width";

    public static final String SECOND_ROOM_WINDOW_HIGHT = "second_room_window_hight";

    public static final String THIRD_ROOM_WIDTH = "third_room_width";

    public static final String THIRD_ROOM_LENGTH = "third_room_length";

    public static final String THIRD_ROOM_DOORWALL_WIDTH = "third_room_doorwall_width";

    public static final String THIRD_ROOM_WINDOW_WIDTH = "third_room_window_width";

    public static final String THIRD_ROOM_WINDOW_SUB_WIDTH = "third_room_window_sub_width";

    public static final String THIRD_ROOM_WINDOW_HIGHT = "third_room_window_hight";

    public static final String FOURTH_ROOM_WIDTH = "fourth_room_width";

    public static final String FOURTH_ROOM_LENGTH = "fourth_room_length";

    public static final String FOURTH_ROOM_DOORWALL_WIDTH = "fourth_room_doorwall_width";

    public static final String FOURTH_ROOM_WINDOW_WIDTH = "fourth_room_window_width";

    public static final String FOURTH_ROOM_WINDOW_SUB_WIDTH = "fourth_room_window_sub_width";

    public static final String FOURTH_ROOM_WINDOW_HIGHT = "fourth_room_window_hight";

    public static final String COMMUNITY_ID = "community_id";

    public static final String DESCRIPTION = "description";
    public static final String VR_ID = "vr_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseDesignModel{" +
                "id=" + id +
                ", houseType='" + houseType + '\'' +
                ", houseTypePicture='" + houseTypePicture + '\'' +
                ", vrPicture='" + vrPicture + '\'' +
                ", area=" + area +
                ", hallWidth=" + hallWidth +
                ", hallSubWidth=" + hallSubWidth +
                ", hallLength=" + hallLength +
                ", hallFrenchWindowWidth=" + hallFrenchWindowWidth +
                ", hallFrenchWindowHight=" + hallFrenchWindowHight +
                ", diningRoomWidth=" + diningRoomWidth +
                ", diningRoomLength=" + diningRoomLength +
                ", firstRoomWidth=" + firstRoomWidth +
                ", firstRoomLength=" + firstRoomLength +
                ", firstRoomDoorwallWidth=" + firstRoomDoorwallWidth +
                ", firstRoomWindowWidth=" + firstRoomWindowWidth +
                ", firstRoomWindowSubWidth=" + firstRoomWindowSubWidth +
                ", firstRoomWindowHight=" + firstRoomWindowHight +
                ", secondRoomWidth=" + secondRoomWidth +
                ", secondRoomLength=" + secondRoomLength +
                ", secondRoomDoorwallWidth=" + secondRoomDoorwallWidth +
                ", secondRoomWindowWidth=" + secondRoomWindowWidth +
                ", secondRoomWindowSubWidth=" + secondRoomWindowSubWidth +
                ", secondRoomWindowHight=" + secondRoomWindowHight +
                ", thirdRoomWidth=" + thirdRoomWidth +
                ", thirdRoomLength=" + thirdRoomLength +
                ", thirdRoomDoorwallWidth=" + thirdRoomDoorwallWidth +
                ", thirdRoomWindowWidth=" + thirdRoomWindowWidth +
                ", thirdRoomWindowSubWidth=" + thirdRoomWindowSubWidth +
                ", thirdRoomWindowHight=" + thirdRoomWindowHight +
                ", fourthRoomWidth=" + fourthRoomWidth +
                ", fourthRoomLength=" + fourthRoomLength +
                ", fourthRoomDoorwallWidth=" + fourthRoomDoorwallWidth +
                ", fourthRoomWindowWidth=" + fourthRoomWindowWidth +
                ", fourthRoomWindowSubWidth=" + fourthRoomWindowSubWidth +
                ", fourthRoomWindowHight=" + fourthRoomWindowHight +
                '}';
    }
}
