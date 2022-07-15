package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;

import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseUserAssetRecord extends HouseUserAsset {

    private Boolean isExistRent;

    private Boolean isExistTrust;

    private Boolean isExistBulk;

    private Boolean isExistDecorate;

    private Boolean isExistExchange;

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
