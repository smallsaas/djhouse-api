package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentLogRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseRentLogService extends CRUDHouseRentLogService {


    HouseRentLog houseRentToLog(HouseRentAsset houseRentAsset);

    Integer addHouseRentLog(HouseRentAsset houseRentAsset,String status);

    Integer addHouseRentLog(HouseRentAssetModel houseRentAssetModel,String status);

    Integer addHouseRentLog(HouseRentAssetRecord houseRentAssetRecord,String status);


    Integer addHouseRentLog(Long id,String status);

    void setStateStr(List<HouseRentLogRecord> recordList);

}