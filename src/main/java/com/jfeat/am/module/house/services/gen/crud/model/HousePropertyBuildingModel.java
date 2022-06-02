package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;

/**
 * Created by Code generator on 2022-06-01
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
 */
public class HousePropertyBuildingModel extends HousePropertyBuilding{

    // housePropertyBuildingUnit
    // HousePropertyBuildingUnit
    // housePropertyBuildingUnit
    private List<HousePropertyBuildingUnit> items;

    public List<HousePropertyBuildingUnit> getItems() {
        return this.items;
    }

    public void setItems(List<HousePropertyBuildingUnit> items) {
        this.items = items;
    }
}
