package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.*;

/**
 * Created by Code generator on 2022-06-06
 */
public class HousePropertyUserRoomRecord extends HousePropertyUserRoom{

    HousePropertyBuilding housePropertyBuilding;
    HousePropertyBuildingUnit housePropertyBuildingUnit;
    HousePropertyCommunity housePropertyCommunity;
    HousePropertyRoom housePropertyRoom;

    public HousePropertyRoom getHousePropertyRoom() {
        return housePropertyRoom;
    }

    public HousePropertyUserRoomRecord setHousePropertyRoom(HousePropertyRoom housePropertyRoom) {
        this.housePropertyRoom = housePropertyRoom;
        return this;
    }

    public HousePropertyBuilding getHousePropertyBuilding() {
        return housePropertyBuilding;
    }

    public HousePropertyUserRoomRecord setHousePropertyBuilding(HousePropertyBuilding housePropertyBuilding) {
        this.housePropertyBuilding = housePropertyBuilding;
        return this;
    }

    public HousePropertyBuildingUnit getHousePropertyBuildingUnit() {
        return housePropertyBuildingUnit;
    }

    public HousePropertyUserRoomRecord setHousePropertyBuildingUnit(HousePropertyBuildingUnit housePropertyBuildingUnit) {
        this.housePropertyBuildingUnit = housePropertyBuildingUnit;
        return this;
    }

    public HousePropertyCommunity getHousePropertyCommunity() {
        return housePropertyCommunity;
    }

    public HousePropertyUserRoomRecord setHousePropertyCommunity(HousePropertyCommunity housePropertyCommunity) {
        this.housePropertyCommunity = housePropertyCommunity;
        return this;
    }
    
    }
