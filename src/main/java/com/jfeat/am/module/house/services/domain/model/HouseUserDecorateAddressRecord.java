package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateAddress;

/**
 * Created by Code generator on 2022-05-30
 */
public class HouseUserDecorateAddressRecord extends HouseUserDecorateAddress {

    HousePropertyBuilding housePropertyBuilding;
    HousePropertyBuildingUnit housePropertyBuildingUnit;
    HousePropertyCommunity housePropertyCommunity;

    public HousePropertyBuilding getHousePropertyBuilding() {
        return housePropertyBuilding;
    }

    public HouseUserDecorateAddressRecord setHousePropertyBuilding(HousePropertyBuilding housePropertyBuilding) {
        this.housePropertyBuilding = housePropertyBuilding;
        return this;
    }

    public HousePropertyBuildingUnit getHousePropertyBuildingUnit() {
        return housePropertyBuildingUnit;
    }

    public HouseUserDecorateAddressRecord setHousePropertyBuildingUnit(HousePropertyBuildingUnit housePropertyBuildingUnit) {
        this.housePropertyBuildingUnit = housePropertyBuildingUnit;
        return this;
    }

    public HousePropertyCommunity getHousePropertyCommunity() {
        return housePropertyCommunity;
    }

    public HouseUserDecorateAddressRecord setHousePropertyCommunity(HousePropertyCommunity housePropertyCommunity) {
        this.housePropertyCommunity = housePropertyCommunity;
        return this;
    }
}
