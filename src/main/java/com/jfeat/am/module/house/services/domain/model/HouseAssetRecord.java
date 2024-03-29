package com.jfeat.am.module.house.services.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseAssetRecord extends HouseAsset {

    /*
    是否是最终确认房子
     */
    private Integer finalFlag;

    private Boolean isMyselfAsset;

    private Boolean isExistUser;

//    private List<Product> decoratePlanProductList;

    private String username;

    private String phone;

    private String userAvatar;

    private Date rentTime;

    private BigDecimal rentPrice;

    private String rentTags;

    private String rentDescribe;

    private String slideshow;

    private Boolean isExchangeAsset;

    /*
    不喜欢状态
     */
    private Boolean unlikeStatus;

    /**
     * 别人选了我的房状态
     */
    private Boolean optionalExchangeStatus;

    public Boolean getUnlikeStatus() {
        return unlikeStatus;
    }

    public void setUnlikeStatus(Boolean unlikeStatus) {
        this.unlikeStatus = unlikeStatus;
    }

    public Boolean getOptionalExchangeStatus() {
        return optionalExchangeStatus;
    }

    public void setOptionalExchangeStatus(Boolean optionalExchangeStatus) {
        this.optionalExchangeStatus = optionalExchangeStatus;
    }

    public Boolean getExchangeAsset() {
        return isExchangeAsset;
    }

    public void setExchangeAsset(Boolean exchangeAsset) {
        isExchangeAsset = exchangeAsset;
    }

    /*
        是否是自己的
         */
    private Boolean self;

    /*
    是否在匹配需求里
     */
    private Boolean matchDemand;

    /*
    是否匹配成功
     */
    private Boolean successMatch;

    /*
    是否是相同户型
     */
    private Boolean sameHouseType;

    private Boolean isPrincipal;


    public Boolean getPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(Boolean principal) {
        isPrincipal = principal;
    }

    public Integer getFinalFlag() {
        return finalFlag;
    }

    public void setFinalFlag(Integer finalFlag) {
        this.finalFlag = finalFlag;
    }

    public Date getRentTime() {
        return rentTime;
    }

    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getRentTags() {
        return rentTags;
    }

    public void setRentTags(String rentTags) {
        this.rentTags = rentTags;
    }

    public String getRentDescribe() {
        return rentDescribe;
    }

    public void setRentDescribe(String rentDescribe) {
        this.rentDescribe = rentDescribe;
    }

    public String getSlideshow() {
        return slideshow;
    }

    public void setSlideshow(String slideshow) {
        this.slideshow = slideshow;
    }

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

//    public List<Product> getDecoratePlanProductList() {
//        return decoratePlanProductList;
//    }
//
//    public void setDecoratePlanProductList(List<Product> decoratePlanProductList) {
//        this.decoratePlanProductList = decoratePlanProductList;
//    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public Boolean getMatchDemand() {
        return matchDemand;
    }

    public void setMatchDemand(Boolean matchDemand) {
        this.matchDemand = matchDemand;
    }

    public Boolean getSuccessMatch() {
        return successMatch;
    }

    public void setSuccessMatch(Boolean successMatch) {
        this.successMatch = successMatch;
    }

    public Boolean getSameHouseType() {
        return sameHouseType;
    }

    public void setSameHouseType(Boolean sameHouseType) {
        this.sameHouseType = sameHouseType;
    }

    public Boolean getMyselfAsset() {
        return isMyselfAsset;
    }

    public void setMyselfAsset(Boolean myselfAsset) {
        isMyselfAsset = myselfAsset;
    }

    public Boolean getExistUser() {
        return isExistUser;
    }

    public void setExistUser(Boolean existUser) {
        isExistUser = existUser;
    }
}
