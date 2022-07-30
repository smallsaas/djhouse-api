package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import java.util.List;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlanFuniture;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;

/**
 * Created by Code generator on 2022-05-27
 *  * slaves.size() : 1
 *  * modelpack : import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
 */
public class HouseDecoratePlanModel extends HouseDecoratePlan{

    // product
    // Product
    // product


    private List<Product> items;

    private List<HouseDecoratePlanFuniture> funitureList;

    private String furnitureItem;




    public List<HouseDecoratePlanFuniture> getFunitureList() {
        return funitureList;
    }

    public void setFunitureList(List<HouseDecoratePlanFuniture> funitureList) {
        this.funitureList = funitureList;
    }


    public List<Product> getItems() {
        return this.items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public String getFurnitureItem() {
        return furnitureItem;
    }

    public void setFurnitureItem(String furnitureItem) {
        this.furnitureItem = furnitureItem;
    }
}
