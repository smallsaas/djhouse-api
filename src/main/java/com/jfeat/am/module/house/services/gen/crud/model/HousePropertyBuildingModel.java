package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyRoom;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;

/**
 * Created by Code generator on 2022-06-06
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
 */
public class HousePropertyBuildingModel extends HousePropertyBuilding{

    // housePropertyRoom
    // HousePropertyRoom
    // housePropertyRoom
    private List<HousePropertyRoom> items;

    public List<HousePropertyRoom> getItems() {
        return this.items;
    }

    public void setItems(List<HousePropertyRoom> items) {
        this.items = items;
    }
}
