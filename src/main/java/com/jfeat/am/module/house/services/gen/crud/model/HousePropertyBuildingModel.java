package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;

/**
 * Created by Code generator on 2022-05-23
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
 */
public class HousePropertyBuildingModel extends HousePropertyBuilding{

    // housePropertyUnit
    // HousePropertyUnit
    // housePropertyUnit
    private List<HousePropertyUnit> items;

    public List<HousePropertyUnit> getItems() {
        return this.items;
    }

    public void setItems(List<HousePropertyUnit> items) {
        this.items = items;
    }
}
