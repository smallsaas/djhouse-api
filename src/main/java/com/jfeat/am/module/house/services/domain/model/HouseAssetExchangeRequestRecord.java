package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;

import java.util.List;

/**
 * Created by Code generator on 2022-09-13
 */
public class HouseAssetExchangeRequestRecord extends HouseAssetExchangeRequest {


    private Long targetUserId;

    private List<HouseAsset> targetAssetList;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public List<HouseAsset> getTargetAssetList() {
        return targetAssetList;
    }

    public void setTargetAssetList(List<HouseAsset> targetAssetList) {
        this.targetAssetList = targetAssetList;
    }
}
