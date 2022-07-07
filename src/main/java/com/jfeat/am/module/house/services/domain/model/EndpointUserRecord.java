package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;

/**
 * Created by Code generator on 2022-06-14
 */
public class EndpointUserRecord extends EndpointUser{
    private Integer assetCount;
    private Integer bulkCount;
    private Integer exchangeCount;

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }

    public Integer getBulkCount() {
        return bulkCount;
    }

    public void setBulkCount(Integer bulkCount) {
        this.bulkCount = bulkCount;
    }

    public Integer getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(Integer exchangeCount) {
        this.exchangeCount = exchangeCount;
    }
}
