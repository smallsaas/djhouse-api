package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetExchangeRequestService extends CRUDHouseAssetExchangeRequestService {

    List<HouseAssetExchangeRequestRecord> assetMachResult(HouseAssetExchangeRequest assetExchangeRequest);

//    批量添加
    int batchAddExchangeRequest(List<HouseAssetExchangeRequest> exchangeRequestList);


    int batchDeleteExchangeRequest(HouseAssetExchangeRequest exchangeRequest);

    int addHouseAssetExchangeRequest(HouseAssetExchangeRequest entity);

    int confirmExchangeAsset(List<HouseAssetExchangeRequest> exchangeRequestList);

//    添加不喜欢请求
    int addUnlikeAssetExchangeRequest(List<HouseAssetExchangeRequest> assetExchangeRequest, List<HouseUnlikeLog> houseUnlikeLogList);

    int addSameFloorExchangeRequest(Long userId);
}