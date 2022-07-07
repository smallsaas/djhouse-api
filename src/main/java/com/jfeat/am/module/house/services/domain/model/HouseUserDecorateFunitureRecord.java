package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateFuniture;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;

/**
 * Created by Code generator on 2022-07-01
 */
public class HouseUserDecorateFunitureRecord extends HouseUserDecorateFuniture {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
