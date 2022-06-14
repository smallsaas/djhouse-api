package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetExchangeRequestService extends CRUDHouseAssetExchangeRequestService {
    List<HouseAssetExchangeRequest> assetMachResult(HouseAssetExchangeRequest assetExchangeRequest,Boolean isSameHouseType);
}