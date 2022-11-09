package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseEquityDemandSupplyRecord extends HouseEquityDemandSupply {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
