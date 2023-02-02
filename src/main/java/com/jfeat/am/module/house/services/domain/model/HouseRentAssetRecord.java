package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;

import java.util.List;

/**
 * Created by Code generator on 2022-07-16
 */
public class HouseRentAssetRecord extends HouseRentAsset {



    private HouseAssetModel houseAssetModel;

    private List<HouseSupportFacilities> houseSupportFacilitiesList;

    private String houseType;

    private String houseTypeDescription;

    private String landlordName;

    private String landlordRealName;

    public String getHouseTypeDescription() {
        return houseTypeDescription;
    }

    public void setHouseTypeDescription(String houseTypeDescription) {
        this.houseTypeDescription = houseTypeDescription;
    }

    // 用户id
    private Long userId;

    // 用户名
    private String username;

    // 用户头像
    private String userAvatar;

    // 用户电话
    private String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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

    public List<HouseSupportFacilities> getHouseSupportFacilitiesList() {
        return houseSupportFacilitiesList;
    }

    public void setHouseSupportFacilitiesList(List<HouseSupportFacilities> houseSupportFacilitiesList) {
        this.houseSupportFacilitiesList = houseSupportFacilitiesList;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public HouseAssetModel getHouseAssetModel() {
        return houseAssetModel;
    }

    public void setHouseAssetModel(HouseAssetModel houseAssetModel) {
        this.houseAssetModel = houseAssetModel;
    }
}
