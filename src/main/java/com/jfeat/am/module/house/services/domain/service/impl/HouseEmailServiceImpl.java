package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.utility.FileUtility;
import com.jfeat.am.module.kafkaEmail.util.TemplateUtil;
import com.jfeat.module.kafka.services.domain.service.KafkaEmailProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service
public class HouseEmailServiceImpl implements HouseEmailService {

    private static final String EMAIL_LIST_PATH = "./email/email-list.txt";

    @Resource
    KafkaEmailProductService kafkaEmailProductService;

    @Override
    public void sendAssetMatchLog(List<HouseAssetMatchLog> matchLogList, List<HouseAssetExchangeRequestRecord> exchangeRequests) {
        String template ="【通知】{targetBuilding}栋{targetAsset}房的户主希望与您的{building}栋{asset}房交换";
        for (HouseAssetMatchLog houseAssetMatchLog:matchLogList){
            Map<String,String> tempalteMap = new HashMap<>();
            tempalteMap.put("targetBuilding",houseAssetMatchLog.getMatchedBuilding()==null?"":houseAssetMatchLog.getMatchedBuilding());
            tempalteMap.put("targetAsset",houseAssetMatchLog.getMatchedNumber()==null?"":houseAssetMatchLog.getMatchedNumber());
            tempalteMap.put("building",houseAssetMatchLog.getOwnerBuilding()==null?"":houseAssetMatchLog.getOwnerBuilding());
            tempalteMap.put("asset",houseAssetMatchLog.getOwnerNumber()==null?"":houseAssetMatchLog.getOwnerNumber());
            String mas = TemplateUtil.getContent(template,tempalteMap);

            if (houseAssetMatchLog.getOwnerEmail()!=null && !houseAssetMatchLog.getOwnerEmail().equals("") && verifyEmail(houseAssetMatchLog.getOwnerEmail())){
                kafkaEmailProductService.sendTextEmail(houseAssetMatchLog.getOwnerEmail(), "换房意向",mas,null);
            }
        }

        List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
        if (emailList==null||emailList.size()<=0){
            return;
        }


        String salesTemp = "【通知】{targetBuilding}栋{targetAsset}房的户主与{building}栋{asset}房的户主有交换意向。{targetBuilding}栋{targetAsset}房的户主信息为:名字：{targetUserName}，电话：{targetUserPhone}。{building}栋{asset}房的户主信息为：姓名：{username},电话:{userPhone}";

        for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord:exchangeRequests){
            Map<String,String> tempalteMap = new HashMap<>();
            tempalteMap.put("targetBuilding",houseAssetExchangeRequestRecord.getTargetBuildingCode()==null?"":houseAssetExchangeRequestRecord.getTargetBuildingCode());
            tempalteMap.put("targetAsset",houseAssetExchangeRequestRecord.getTargetHouseNumber()==null?"":houseAssetExchangeRequestRecord.getTargetHouseNumber());
            tempalteMap.put("building",houseAssetExchangeRequestRecord.getBuildingCode()==null?"":houseAssetExchangeRequestRecord.getBuildingCode());
            tempalteMap.put("asset",houseAssetExchangeRequestRecord.getNumber()==null?"":houseAssetExchangeRequestRecord.getNumber());

            tempalteMap.put("targetUserName",houseAssetExchangeRequestRecord.getTargetRealName()==null?"":houseAssetExchangeRequestRecord.getTargetRealName());
            tempalteMap.put("targetUserPhone",houseAssetExchangeRequestRecord.getTargetPhone()==null?"":houseAssetExchangeRequestRecord.getTargetPhone());
            tempalteMap.put("username",houseAssetExchangeRequestRecord.getRealName()==null?"":houseAssetExchangeRequestRecord.getRealName());
            tempalteMap.put("userPhone",houseAssetExchangeRequestRecord.getUserPhone()==null?"":houseAssetExchangeRequestRecord.getUserPhone());

            String mas = TemplateUtil.getContent(salesTemp,tempalteMap);
            for (String email:emailList){
                if (!email.equals("") && verifyEmail(email)){
                    kafkaEmailProductService.sendTextEmail(email,"匹配成功用户",mas,null);
                }
            }
        }


    }

    @Override
    public boolean verifyEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();

    }
}
