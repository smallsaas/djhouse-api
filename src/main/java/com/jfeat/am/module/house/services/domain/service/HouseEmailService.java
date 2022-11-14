package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

import java.util.List;

public interface HouseEmailService {

    void sendAssetMatchLog(List<HouseAssetMatchLog> matchLogList,List<HouseAssetExchangeRequestRecord> exchangeRequests);

//    发送申诉房产信息
    void sendComplaintAssetInfo(HouseAssetComplaint complaint);

//    发送出租房屋消息
    void sendRentAssetInfo(HouseRentAsset entity);

//    发送权益信息
    void sendEquityDemand(HouseEquityDemandSupply entity);

    boolean verifyEmail(String email);



}
