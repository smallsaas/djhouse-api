package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

import java.util.List;

/**
 * Created by Code generator on 2022-07-16
 */
public class HouseRentAssetRecord extends HouseRentAsset {



    private HouseAssetModel houseAssetModel;

    private String houseType;

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
