package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilitiesType;

import java.util.List;

/**
 * Created by Code generator on 2022-08-05
 */
public class HouseSurroundFacilitiesTypeRecord extends HouseSurroundFacilitiesType{
    private List<HouseSurroundFacilities> items;

    public List<HouseSurroundFacilities> getItems() {
        return this.items;
    }

    public void setItems(List<HouseSurroundFacilities> items) {
        this.items = items;
    }
    }
