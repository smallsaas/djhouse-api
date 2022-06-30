package com.jfeat.am.module.house.services.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetDemandSupply;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by Code generator on 2022-06-29
 */
public class HouseAssetDemandSupplyRecord extends HouseAssetDemandSupply{
    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String userAvatar;



    @TableField(exist = false)
    private String houseType;

    @TableField(exist = false)
    private String houseTypePicture;

    @TableField(exist = false)
    private String vrPicture;

    @TableField(exist = false)
    private String vrLink;



    @ApiModelProperty(value = "地址")
    @TableField(exist = false)
    private String address;

    private String option;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;



    @ApiModelProperty(value = "第几楼陈")
    @TableField(exist = false)
    private Integer floor;

    @ApiModelProperty(value = "房产编号")
    @TableField(exist = false)
    private String number;

    private String combinationNumber;

    @TableField(exist = false)
    private BigDecimal area;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getCombinationNumber() {
        return combinationNumber;
    }

    public void setCombinationNumber(String combinationNumber) {
        this.combinationNumber = combinationNumber;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
