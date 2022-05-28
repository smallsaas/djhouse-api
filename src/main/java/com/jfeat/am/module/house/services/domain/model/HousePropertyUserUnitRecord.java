package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserUnit;

/**
 * Created by Code generator on 2022-05-28
 */
public class HousePropertyUserUnitRecord extends HousePropertyUserUnit {
    HousePropertyBuilding housePropertyBuilding;
    HousePropertyBuildingUnit housePropertyBuildingUnit;

    public HousePropertyBuilding getHousePropertyBuilding() {
        return housePropertyBuilding;
    }

    public HousePropertyUserUnitRecord setHousePropertyBuilding(HousePropertyBuilding housePropertyBuilding) {
        this.housePropertyBuilding = housePropertyBuilding;
        return this;
    }

    public HousePropertyBuildingUnit getHousePropertyBuildingUnit() {
        return housePropertyBuildingUnit;
    }

    public HousePropertyUserUnitRecord setHousePropertyBuildingUnit(HousePropertyBuildingUnit housePropertyBuildingUnit) {
        this.housePropertyBuildingUnit = housePropertyBuildingUnit;
        return this;
    }
}
