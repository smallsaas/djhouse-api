package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetTransactionService extends CRUDHouseAssetTransactionService {
    void setStatus(List<HouseAssetTransactionRecord> houseAssetTransactionRecordList);

    void setStatus(HouseAssetTransaction houseAssetTransaction);
}