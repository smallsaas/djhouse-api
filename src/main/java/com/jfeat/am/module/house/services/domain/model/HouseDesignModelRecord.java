package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;

/**
 * Created by Code generator on 2022-06-08
 */
public class HouseDesignModelRecord extends HouseDesignModel{
    private String snapshotUrl;

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    private Integer houseCount;

    private Integer unitCount;

    public Integer getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Integer houseCount) {
        this.houseCount = houseCount;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }
}
