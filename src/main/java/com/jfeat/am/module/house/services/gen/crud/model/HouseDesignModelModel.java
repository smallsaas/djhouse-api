package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;

import java.util.Set;

/**
 * Created by Code generator on 2022-06-08
 *  * slaves.size() : 0
 *  * modelpack : $modelpack
 */
public class HouseDesignModelModel extends HouseDesignModel{
   private Set<String> unitCode;

   private Set<String> direction;

    public Set<String> getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(Set<String> unitCode) {
        this.unitCode = unitCode;
    }

    public Set<String> getDirection() {
        return direction;
    }

    public void setDirection(Set<String> direction) {
        this.direction = direction;
    }
}
