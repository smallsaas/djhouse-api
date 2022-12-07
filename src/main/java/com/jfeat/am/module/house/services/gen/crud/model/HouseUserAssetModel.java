package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;

import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 *  * slaves.size() : 0
 *  * modelpack : $modelpack
 */
public class HouseUserAssetModel extends HouseUserAsset{
//    private List<Product> productList;



//    装修方案是否可修改
    private Integer decorateModifyOption;

//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }



    public Integer getDecorateModifyOption() {
        return decorateModifyOption;
    }

    public void setDecorateModifyOption(Integer decorateModifyOption) {
        this.decorateModifyOption = decorateModifyOption;
    }
}
