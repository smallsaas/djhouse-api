package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;

/**
 * Created by Code generator on 2022-08-05
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HouseSupportFacilitiesTypeModel;
 */
public class HouseSupportFacilitiesTypeModel extends HouseSupportFacilitiesType{

    // houseSupportFacilities
    // HouseSupportFacilities
    // houseSupportFacilities
    private List<HouseSupportFacilities> items;

    public List<HouseSupportFacilities> getItems() {
        return this.items;
    }

    public void setItems(List<HouseSupportFacilities> items) {
        this.items = items;
    }
}
