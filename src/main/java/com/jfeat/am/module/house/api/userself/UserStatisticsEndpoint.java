package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.models.auth.In;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/u/statistics")
public class UserStatisticsEndpoint {


    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseEquityDemandSupplyDao queryHouseEquityDemandSupplyDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;


    @GetMapping("/houseOverStatistics")
    private Tip getHouseOverStatistics(){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long communityId = null;
        Integer buildingNumber =0;
        Integer multipleNumber = 0;
        Integer unitNumber = 0;
        Integer houseNumber = 0;
        Integer parkingNumber =0;
        Double totalArea = 0.0;
        HashMap<String,Integer> houseTypeMap = new HashMap<>();

//        查询用户小区状态
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null,buildingRecord,null,null,null,null,null);
        buildingNumber = buildingRecordList.size();

        for(int i=0;i<buildingRecordList.size();i++){
//        计算复式套数
            if (buildingRecordList.get(i).getMultipleNumber()!=null && buildingRecordList.get(i).getMultipleNumber()!=0){
                multipleNumber+=buildingRecordList.get(i).getMultipleNumber();
            }

//        计算单元数
            HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
            unitRecord.setBuildingId(buildingRecordList.get(i).getId());
            List<HousePropertyBuildingUnitRecord> unitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);
            unitNumber+=unitRecordList.size();

            for (HousePropertyBuildingUnitRecord unit:unitRecordList){

                Double areaNumber = 0.0;
                Long houseTypeId = unit.getDesignModelId();
                if (houseTypeId!=null){
                    //        计算户型数
                    if (houseTypeMap.get(String.valueOf(houseTypeId))!=null){
                        houseTypeMap.put(String.valueOf(houseTypeId),houseTypeMap.get(String.valueOf(houseTypeId))+1);
                    }else {
                        houseTypeMap.put(String.valueOf(houseTypeId),1);
                    }

//                    获取户型面积
                    HouseDesignModel houseDesignModel = queryHouseDesignModelDao.queryMasterModel(houseTypeId);
                    areaNumber = houseDesignModel.getArea().doubleValue();
                }

//                计算房屋数
                HouseAssetRecord houseRecord = new HouseAssetRecord();
                houseRecord.setUnitId(unit.getId());
                houseRecord.setAssetType(1);
                List<HouseAssetRecord> houseRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseRecord,null,null,null,null,null);
                if (houseRecordList!=null && houseRecordList.size()>0 && areaNumber!=null && !areaNumber.equals(0)){
//                    添加面积
                    totalArea += areaNumber*houseRecordList.size();
                }
                houseNumber+=houseRecordList.size();

                //        计算车位
                houseRecord.setAssetType(2);
                houseRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseRecord,null,null,null,null,null);
                if (houseRecordList!=null && houseRecordList.size()>0 && areaNumber!=null && !areaNumber.equals(0)){
//                    添加面积
                    totalArea += areaNumber*houseRecordList.size();
                }
                parkingNumber+=houseRecordList.size();


            }

        }
        HouseEquityDemandSupplyRecord houseEquityDemandSupplyRecord = new HouseEquityDemandSupplyRecord();
        HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord = new HouseAssetExchangeRequestRecord();

        List<HouseEquityDemandSupplyRecord> equityDemandSupplyRecordList = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(null,houseEquityDemandSupplyRecord,null,null,null,null,null,null,null);
        List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,houseAssetExchangeRequestRecord,null,null,null,null,null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buildingNumber",buildingNumber);
        jsonObject.put("multipleNumber",multipleNumber);
        jsonObject.put("unitNumber",unitNumber);
        jsonObject.put("houseNumber",houseNumber);
        jsonObject.put("parkingNumber",parkingNumber);
        jsonObject.put("totalArea",totalArea);
        jsonObject.put("houseTypeNumber",houseTypeMap.size());
        jsonObject.put("houseExchangeNumber",exchangeRequestRecordList.size());
        jsonObject.put("equityNumber",equityDemandSupplyRecordList.size());
        return SuccessTip.create(jsonObject);
    }


    @GetMapping("/buildingOverStatistics")
    private Tip getBuildingOverStatistics(){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long communityId = null;
        Integer buildingNumber =0;


//        查询用户小区状态
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询用户资产表

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> userAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null);

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null,buildingRecord,null,null,null,null,null);
        buildingNumber = buildingRecordList.size();
        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<buildingRecordList.size();i++){

            Integer multipleNumber = 0;
            Integer unitNumber = 0;
            Integer houseNumber = 0;
            Integer parkingNumber =0;
            Double totalArea = 0.0;
            HashMap<String,Integer> houseTypeMap = new HashMap<>();

//        计算复式套数
            if (buildingRecordList.get(i).getMultipleNumber()!=null && buildingRecordList.get(i).getMultipleNumber()!=0){
                multipleNumber+=buildingRecordList.get(i).getMultipleNumber();
            }

//        计算单元数
            HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
            unitRecord.setBuildingId(buildingRecordList.get(i).getId());
            List<HousePropertyBuildingUnitRecord> unitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);
            unitNumber+=unitRecordList.size();

            for (HousePropertyBuildingUnitRecord unit:unitRecordList){

                Double areaNumber = 0.0;
                Long houseTypeId = unit.getDesignModelId();
                if (houseTypeId!=null){
                    //        计算户型数
                    if (houseTypeMap.get(String.valueOf(houseTypeId))!=null){
                        houseTypeMap.put(String.valueOf(houseTypeId),houseTypeMap.get(String.valueOf(houseTypeId))+1);
                    }else {
                        houseTypeMap.put(String.valueOf(houseTypeId),1);
                    }

//                    获取户型面积
                    HouseDesignModel houseDesignModel = queryHouseDesignModelDao.queryMasterModel(houseTypeId);
                    areaNumber = houseDesignModel.getArea().doubleValue();
                }

//                计算房屋数
                HouseAssetRecord houseRecord = new HouseAssetRecord();
                houseRecord.setUnitId(unit.getId());
                houseRecord.setAssetType(1);
                List<HouseAssetRecord> houseRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseRecord,null,null,null,null,null);
                if (houseRecordList!=null && houseRecordList.size()>0 && areaNumber!=null && !areaNumber.equals(0)){
//                    添加面积
                    totalArea += areaNumber*houseRecordList.size();
                }
                houseNumber+=houseRecordList.size();

                //        计算车位
                houseRecord.setAssetType(2);
                houseRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseRecord,null,null,null,null,null);
                if (houseRecordList!=null && houseRecordList.size()>0 && areaNumber!=null && !areaNumber.equals(0)){
//                    添加面积
                    totalArea += areaNumber*houseRecordList.size();
                }
                parkingNumber+=houseRecordList.size();
            }

//            int selectedNumber = 0;
//            HouseAssetRecord assetRecord = new HouseAssetRecord();
//            assetRecord.setBuildingId(buildingRecordList.get(i).getId());
//            assetRecord.setAssetType(1);
//            List<HouseAssetRecord> houseAssetList = queryHouseAssetDao.findHouseAssetPage(null,assetRecord,null,null,null,null,null);
//
//            for (HouseAssetRecord houseAssetRecord:houseAssetList){
//                for (HouseUserAssetRecord houseUserAssetRecord:userAssetRecordList){
//                    if (houseAssetRecord.getId().equals(houseUserAssetRecord.getAssetId())){
//                        selectedNumber+=1;
//                    }
//                }
//            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("buildingCode",buildingRecordList.get(i).getCode());
            jsonObject.put("multipleNumber",multipleNumber);
            jsonObject.put("unitNumber",unitNumber);
            jsonObject.put("houseNumber",houseNumber);
            jsonObject.put("parkingNumber",parkingNumber);
            jsonObject.put("totalArea",totalArea);
            jsonObject.put("houseTypeNumber",houseTypeMap.size());
//            jsonObject.put("houseRemainNumber",houseNumber-selectedNumber);
            jsonArray.add(jsonObject);

        }


        return SuccessTip.create(jsonArray);
    }
}
