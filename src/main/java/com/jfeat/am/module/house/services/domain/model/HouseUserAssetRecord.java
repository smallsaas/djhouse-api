package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseUserAssetRecord extends HouseUserAsset {


    /**
     * 是否出售
     */
    private Boolean isTransaction;

    private Boolean isExistRent;

    /**
     * 是否被拉黑了
     */
    private Boolean shield;

    /**
     * 排名
     */
    private Integer rank;

    private BigDecimal rentPrice;

    private Integer rentStatus;

    private Boolean isExistTrust;

    private Boolean isExistBulk;

    private Boolean isExistDecorate;

    private Boolean isExistExchange;

    private String serverName;

    private String statusStr;

    private Integer status;

    private String LandlordType;

    private BigDecimal multiArea;

    private BigDecimal multiRealArea;

    private Integer assetFlag;

    private List<HouseAssetRecord> exchangeRequestRecordList;

    private List<HouseUserAssetRecord> houseUserAssetRecordList;


    /**
     * 用户对象
     */
    private UserAccount userAccount;


    /**
     * 用户标签
     */
    private List<HouseUserTag> userTagList;


    private List<HouseUserNote> userNoteList;

    public Boolean getTransaction() {
        return isTransaction;
    }

    public void setTransaction(Boolean transaction) {
        isTransaction = transaction;
    }

    public Integer getAssetFlag() {
        return assetFlag;
    }

    public void setAssetFlag(Integer assetFlag) {
        this.assetFlag = assetFlag;
    }

    public BigDecimal getMultiArea() {
        return multiArea;
    }

    public void setMultiArea(BigDecimal multiArea) {
        this.multiArea = multiArea;
    }

    public BigDecimal getMultiRealArea() {
        return multiRealArea;
    }

    public void setMultiRealArea(BigDecimal multiRealArea) {
        this.multiRealArea = multiRealArea;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getShield() {
        return shield;
    }

    public void setShield(Boolean shield) {
        this.shield = shield;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
    }

    public String getLandlordType() {
        return LandlordType;
    }

    public void setLandlordType(String landlordType) {
        LandlordType = landlordType;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<HouseUserTag> getUserTagList() {
        return userTagList;
    }

    public void setUserTagList(List<HouseUserTag> userTagList) {
        this.userTagList = userTagList;
    }

    public List<HouseUserNote> getUserNoteList() {
        return userNoteList;
    }

    public void setUserNoteList(List<HouseUserNote> userNoteList) {
        this.userNoteList = userNoteList;
    }

    public List<HouseUserAssetRecord> getHouseUserAssetRecordList() {
        return houseUserAssetRecordList;
    }

    public void setHouseUserAssetRecordList(List<HouseUserAssetRecord> houseUserAssetRecordList) {
        this.houseUserAssetRecordList = houseUserAssetRecordList;
    }




    public List<HouseAssetRecord> getExchangeRequestRecordList() {
        return exchangeRequestRecordList;
    }

    public void setExchangeRequestRecordList(List<HouseAssetRecord> exchangeRequestRecordList) {
        this.exchangeRequestRecordList = exchangeRequestRecordList;
    }

    public Boolean getExistExchange() {
        return isExistExchange;
    }

    public void setExistExchange(Boolean existExchange) {
        isExistExchange = existExchange;
    }

    public Boolean getExistRent() {
        return isExistRent;
    }

    public void setExistRent(Boolean existRent) {
        isExistRent = existRent;
    }

    public Boolean getExistTrust() {
        return isExistTrust;
    }

    public void setExistTrust(Boolean existTrust) {
        isExistTrust = existTrust;
    }

    public Boolean getExistBulk() {
        return isExistBulk;
    }

    public void setExistBulk(Boolean existBulk) {
        isExistBulk = existBulk;
    }

    public Boolean getExistDecorate() {
        return isExistDecorate;
    }

    public void setExistDecorate(Boolean existDecorate) {
        isExistDecorate = existDecorate;
    }
}
