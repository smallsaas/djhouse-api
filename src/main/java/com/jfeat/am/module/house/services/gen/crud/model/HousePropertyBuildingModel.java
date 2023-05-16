package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;

/**
 * Created by Code generator on 2022-06-11
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
 */
public class HousePropertyBuildingModel extends HousePropertyBuilding{

    // houseAsset
    // HouseAsset
    // houseAsset
    private List<HouseAsset> items;

    private String communityName;

    public List<HouseAsset> getItems() {
        return this.items;
    }

    public void setItems(List<HouseAsset> items) {
        this.items = items;
    }


    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
