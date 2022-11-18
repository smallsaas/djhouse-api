package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.*;

import java.util.List;

public interface HouseEmailService {

    void sendAssetMatchLog(List<HouseAssetMatchLog> matchLogList,List<HouseAssetExchangeRequestRecord> exchangeRequests);

//    发送申诉房产信息
    void sendComplaintAssetInfo(HouseAssetComplaint complaint);

//    发送出租房屋消息
    void sendRentAssetInfo(HouseRentAsset entity);

//    发送权益信息
    void sendEquityDemand(HouseEquityDemandSupply entity);

    void sendMoreThanAssetLimit(Long oldUser,Long newUser,HouseAssetModel houseAssetModel);

    boolean verifyEmail(String email);



}
