package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserAssetService;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseUserAssetService extends CRUDHouseUserAssetService {

    JSONObject parseMatchAssetData(List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList);

    JSONArray formatAssetMatchResult(JSONObject jsonObject);
}