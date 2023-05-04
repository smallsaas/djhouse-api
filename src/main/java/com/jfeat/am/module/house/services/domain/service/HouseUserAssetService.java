package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserAssetService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseUserAssetService extends CRUDHouseUserAssetService {

    JSONObject parseMatchAssetData(List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList);

    JSONArray formatAssetMatchResult(JSONObject jsonObject);

    /**
     * 删除用户房子以及相关记录
     *
     * @param userId  用户id
     * @param assetId 资产id
     * @return 删除有效记录数
     */
    int deleteUserAsset(Long userId, Long assetId);

    void setUserAssetArea(List<HouseUserAssetRecord> recordList);


    /**
     *
     */
    /**
     * 设定用户"我的回迁房"的默认排序，有利于之后的排序等操作
     *
     * @param userId      用户id
     * @param communityId 用户所在社区id
     * @return
     */
    int setDefaultSequenceNumber(Long userId, Long communityId);

    /**
     * 变更"我的回迁房"的排序
     *
     * @param userId      用户id
     * @param communityId 用户社区id
     * @param id          要移动的房产记录的id
     * @param direction   上移/下移 1=上移，0=下移
     * @return
     */
    List<HouseUserAssetRecord> updateMyHouseSequence(Long userId, Long communityId, Long id, Integer direction);

    /**
     * 更新"我的回迁房"的水编号，电编号，燃气编号
     *
     * @param id                房产id
     * @param userId            用户id
     * @param waterNumber       水编号
     * @param electricityNumber 电编号
     * @param gasNumber         燃气编号
     * @return
     */
    HouseUserAssetRecord updateMyHouseWaterElectricity(Long id, Long userId, String waterNumber, String electricityNumber, String gasNumber);


}