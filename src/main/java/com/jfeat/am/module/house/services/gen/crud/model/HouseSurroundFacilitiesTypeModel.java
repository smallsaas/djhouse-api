package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilitiesType;

/**
 * Created by Code generator on 2022-08-05
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HouseSurroundFacilitiesTypeModel;
 */
public class HouseSurroundFacilitiesTypeModel extends HouseSurroundFacilitiesType{

    // houseSurroundFacilities
    // HouseSurroundFacilities
    // houseSurroundFacilities
    private List<HouseSurroundFacilities> items;

    public List<HouseSurroundFacilities> getItems() {
        return this.items;
    }

    public void setItems(List<HouseSurroundFacilities> items) {
        this.items = items;
    }
}
