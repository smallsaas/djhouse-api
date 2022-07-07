package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;

import java.util.List;

/**
 * Created by Code generator on 2022-07-01
 */
public class HouseUserDecoratePlanRecord extends HouseUserDecoratePlan {
    private HouseAsset houseAsset;
    private HouseDecoratePlan houseDecoratePlan;

    private List<HouseUserDecorateFunitureRecord> houseUserDecorateFunitureRecordList;

    public HouseAsset getHouseAsset() {
        return houseAsset;
    }

    public void setHouseAsset(HouseAsset houseAsset) {
        this.houseAsset = houseAsset;
    }

    public HouseDecoratePlan getHouseDecoratePlan() {
        return houseDecoratePlan;
    }

    public void setHouseDecoratePlan(HouseDecoratePlan houseDecoratePlan) {
        this.houseDecoratePlan = houseDecoratePlan;
    }

    public List<HouseUserDecorateFunitureRecord> getHouseUserDecorateFunitureRecordList() {
        return houseUserDecorateFunitureRecordList;
    }

    public void setHouseUserDecorateFunitureRecordList(List<HouseUserDecorateFunitureRecord> houseUserDecorateFunitureRecordList) {
        this.houseUserDecorateFunitureRecordList = houseUserDecorateFunitureRecordList;
    }
}
