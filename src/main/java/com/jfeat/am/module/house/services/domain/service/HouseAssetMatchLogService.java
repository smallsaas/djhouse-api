package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetMatchLogService extends CRUDHouseAssetMatchLogService {

    Integer setMatchLogStatusToComplete(Long id);

    Integer cancelMatchLogStatus(Long id);


    Integer changeMatchStatus(HouseAssetMatchLog houseAssetMatchLog, Integer status);

    void setMatchedStatusStr(List<HouseAssetMatchLogRecord> houseAssetMatchLogs);
}