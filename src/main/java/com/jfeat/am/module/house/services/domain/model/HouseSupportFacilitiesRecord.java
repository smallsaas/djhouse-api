package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;

/**
 * Created by Code generator on 2022-08-05
 */
public class HouseSupportFacilitiesRecord extends HouseSupportFacilities {

    private Boolean top;

    private Boolean down;

    private String operation;

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getDown() {
        return down;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
