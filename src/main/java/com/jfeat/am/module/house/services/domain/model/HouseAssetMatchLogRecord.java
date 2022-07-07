package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;

import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseAssetMatchLogRecord extends HouseAssetMatchLog{
    private HouseAsset owner;
    private List<HouseAsset> ownerList;

    private HouseAsset matcher;
    private List<HouseAsset> matcherList;

    public HouseAsset getOwner() {
        return owner;
    }

    public void setOwner(HouseAsset owner) {
        this.owner = owner;
    }

    public List<HouseAsset> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<HouseAsset> ownerList) {
        this.ownerList = ownerList;
    }

    public HouseAsset getMatcher() {
        return matcher;
    }

    public void setMatcher(HouseAsset matcher) {
        this.matcher = matcher;
    }

    public List<HouseAsset> getMatcherList() {
        return matcherList;
    }

    public void setMatcherList(List<HouseAsset> matcherList) {
        this.matcherList = matcherList;
    }
}
