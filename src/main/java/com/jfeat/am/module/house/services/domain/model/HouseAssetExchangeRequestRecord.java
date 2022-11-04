package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Code generator on 2022-09-13
 */
public class HouseAssetExchangeRequestRecord extends HouseAssetExchangeRequest {

    private List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList;

    private Long targetBuildingId;

    private String targetBuildingCode;

    private Integer targetIssue;

    private Long targetUnitId;

    private Long targetHouseTypeId;

    private BigDecimal targetArea;

    private String targetHouseNumber;

    private String targetRealName;

    private String targetEmail;

    private String targetPhone;



    private Long targetUserId;

    private List<HouseAsset> targetAssetList;

    private String realName;

    private String email;


    public String getTargetRealName() {
        return targetRealName;
    }

    public void setTargetRealName(String targetRealName) {
        this.targetRealName = targetRealName;
    }

    public String getTargetEmail() {
        return targetEmail;
    }

    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public String getTargetPhone() {
        return targetPhone;
    }

    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HouseAssetExchangeRequestRecord> getHouseAssetExchangeRequestRecordList() {
        return houseAssetExchangeRequestRecordList;
    }

    public void setHouseAssetExchangeRequestRecordList(List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList) {
        this.houseAssetExchangeRequestRecordList = houseAssetExchangeRequestRecordList;
    }

    public Long getTargetBuildingId() {
        return targetBuildingId;
    }

    public void setTargetBuildingId(Long targetBuildingId) {
        this.targetBuildingId = targetBuildingId;
    }

    public Integer getTargetIssue() {
        return targetIssue;
    }

    public void setTargetIssue(Integer targetIssue) {
        this.targetIssue = targetIssue;
    }

    public Long getTargetUnitId() {
        return targetUnitId;
    }

    public void setTargetUnitId(Long targetUnitId) {
        this.targetUnitId = targetUnitId;
    }

    public Long getTargetHouseTypeId() {
        return targetHouseTypeId;
    }

    public void setTargetHouseTypeId(Long targetHouseTypeId) {
        this.targetHouseTypeId = targetHouseTypeId;
    }

    public BigDecimal getTargetArea() {
        return targetArea;
    }

    public void setTargetArea(BigDecimal targetArea) {
        this.targetArea = targetArea;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTargetBuildingCode() {
        return targetBuildingCode;
    }

    public void setTargetBuildingCode(String targetBuildingCode) {
        this.targetBuildingCode = targetBuildingCode;
    }

    public String getTargetHouseNumber() {
        return targetHouseNumber;
    }

    public void setTargetHouseNumber(String targetHouseNumber) {
        this.targetHouseNumber = targetHouseNumber;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public List<HouseAsset> getTargetAssetList() {
        return targetAssetList;
    }

    public void setTargetAssetList(List<HouseAsset> targetAssetList) {
        this.targetAssetList = targetAssetList;
    }
}
