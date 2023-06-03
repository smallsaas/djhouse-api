package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONObject;
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

    /**
     * 发送自定义内容的email
     * 如果收件人列表为空，则使用系统默认收件人列表
     *
     * @param emailTitle         邮件标题 notnull
     * @param emailContent       邮件内容 notnull
     * @param toEmailAddressList 收件人邮箱列表，如果该参数传入为空，则使用系统默认收件人列表
     */
    void sendEmailByCustomization(String emailTitle, String emailContent, List<String> toEmailAddressList);

}
