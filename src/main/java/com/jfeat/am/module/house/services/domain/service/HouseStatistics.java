package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONArray;
import com.jfeat.crud.base.tips.Tip;

import java.util.List;

public interface HouseStatistics {
    void timingHouseOverStatistics();

    void timingCommunityBuildingStatistics();

    JSONArray getCommunityBuildingStatistics(Long communityId);
}
