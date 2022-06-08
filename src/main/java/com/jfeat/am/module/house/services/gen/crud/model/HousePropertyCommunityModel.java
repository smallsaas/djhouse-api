package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;

/**
 * Created by Code generator on 2022-06-06
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
 */
public class HousePropertyCommunityModel extends HousePropertyCommunity{

    // housePropertyBuilding
    // HousePropertyBuilding
    // housePropertyBuilding
    private List<HousePropertyBuilding> items;

    public List<HousePropertyBuilding> getItems() {
        return this.items;
    }

    public void setItems(List<HousePropertyBuilding> items) {
        this.items = items;
    }
}
