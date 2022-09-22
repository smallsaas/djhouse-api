package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Code generator on 2022-09-13
 */
public class HouseAssetExchangeRequestRecord extends HouseAssetExchangeRequest {


    private Long targetBuildingId;

    private String targetBuildingCode;

    private Integer targetIssue;

    private Long targetUnitId;

    private Long targetHouseTypeId;

    private BigDecimal targetArea;

    private String targetHouseNumber;

    private Long targetUserId;

    private List<HouseAsset> targetAssetList;

    private String realName;


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
