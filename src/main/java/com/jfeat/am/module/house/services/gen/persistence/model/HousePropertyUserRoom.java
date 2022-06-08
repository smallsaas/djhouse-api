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
 * @since 2022-06-06
 */
@TableName("t_house_property_user_room")
@ApiModel(value = "HousePropertyUserRoom对象", description = "")
public class HousePropertyUserRoom extends Model<HousePropertyUserRoom> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "房屋id")
    private Long roomId;

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




    public Long getId() {
        return id;
    }

    public HousePropertyUserRoom setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HousePropertyUserRoom setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getRoomId() {
        return roomId;
    }

    public HousePropertyUserRoom setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public HousePropertyUserRoom setCommunityId(Long communityId) {
        this.communityId = communityId;
        return this;
    }

    public String getCommunityName() {
        return communityName;
    }

    public HousePropertyUserRoom setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public HousePropertyUserRoom setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public HousePropertyUserRoom setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
        return this;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public HousePropertyUserRoom setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public HousePropertyUserRoom setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public String getHouseType() {
        return houseType;
    }

    public HousePropertyUserRoom setHouseType(String houseType) {
        this.houseType = houseType;
        return this;
    }

    public BigDecimal getUnitArea() {
        return unitArea;
    }

    public HousePropertyUserRoom setUnitArea(BigDecimal unitArea) {
        this.unitArea = unitArea;
        return this;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public HousePropertyUserRoom setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ROOM_ID = "room_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HousePropertyUserRoom{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                "}";
    }
}
