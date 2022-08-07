package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;

import java.util.List;

/**
 * Created by Code generator on 2022-08-05
 */
public class HouseSupportFacilitiesTypeRecord extends HouseSupportFacilitiesType{
    private List<HouseSupportFacilities> items;

    public List<HouseSupportFacilities> getItems() {
        return this.items;
    }

    public void setItems(List<HouseSupportFacilities> items) {
        this.items = items;
    }
    }
