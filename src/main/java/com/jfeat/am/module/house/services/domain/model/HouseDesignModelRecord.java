package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;

/**
 * Created by Code generator on 2022-06-08
 */
public class HouseDesignModelRecord extends HouseDesignModel{
    String snapshotUrl;

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }
}
