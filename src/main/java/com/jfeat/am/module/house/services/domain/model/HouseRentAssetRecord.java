package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

/**
 * Created by Code generator on 2022-07-16
 */
public class HouseRentAssetRecord extends HouseRentAsset {



    private HouseAssetModel houseAssetModel;

    public HouseAssetModel getHouseAssetModel() {
        return houseAssetModel;
    }

    public void setHouseAssetModel(HouseAssetModel houseAssetModel) {
        this.houseAssetModel = houseAssetModel;
    }
}
