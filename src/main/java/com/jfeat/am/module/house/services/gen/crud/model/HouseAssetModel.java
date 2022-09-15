package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

/**
 * Created by Code generator on 2022-06-11
 *  * slaves.size() : 0
 *  * modelpack : $modelpack
 */
public class HouseAssetModel extends HouseAsset{

    /*
    房子朝向
     */
    private String direction;

    private Integer exchangeNumber;



    public Integer getExchangeNumber() {
        return exchangeNumber;
    }

    public void setExchangeNumber(Integer exchangeNumber) {
        this.exchangeNumber = exchangeNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
