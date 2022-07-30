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

    /**
     * 删除用户房子以及相关记录
     * @param userId 用户id
     * @param assetId 资产id
     * @return 删除有效记录数
     */
    int deleteUserAsset(Long userId,Long assetId);
}