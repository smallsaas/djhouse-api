package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;

import java.util.List;

public interface HouseEmailService {

    void sendAssetMatchLog(List<HouseAssetMatchLog> matchLogList,List<HouseAssetExchangeRequestRecord> exchangeRequests);
    boolean verifyEmail(String email);



}
