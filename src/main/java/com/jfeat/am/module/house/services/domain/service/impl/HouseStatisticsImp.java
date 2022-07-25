package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.domain.service.HouseStatistics;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.statistics.services.crud.StatisticsFieldService;
import com.jfeat.am.module.statistics.services.domain.dao.QueryStatisticsFieldDao;
import com.jfeat.am.module.statistics.services.domain.dao.QueryStatisticsRecordDao;
import com.jfeat.am.module.statistics.services.persistence.dao.StatisticsFieldMapper;
import com.jfeat.am.module.statistics.services.persistence.dao.StatisticsRecordMapper;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsField;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsRecord;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


public class HouseStatisticsImp implements HouseStatistics {

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @Resource
    QueryHouseEquityDemandSupplyDao queryHouseEquityDemandSupplyDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;


    @Resource
    StatisticsRecordMapper statisticsRecordMapper;

    @Resource
    StatisticsFieldMapper statisticsFieldMapper;









    public Tip getHouseOverStatistics() {

        HousePropertyCommunityRecord housePropertyCommunityRecord = new HousePropertyCommunityRecord();
        List<HousePropertyCommunityRecord> housePropertyCommunityRecordList = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(null,housePropertyCommunityRecord,null,null,null,null,null);
        for (HousePropertyCommunityRecord communityRecord:housePropertyCommunityRecordList){
            Long communityId = communityRecord.getId();
            Integer buildingNumber = 0;
            Integer multipleNumber = 0;
            Integer unitNumber = 0;
            Integer houseNumber = 0;
            Integer parkingNumber = 0;
            Double totalArea = 0.0;
            HashMap<String, Integer> houseTypeMap = new HashMap<>();

            QueryWrapper<StatisticsField> wrapper = new QueryWrapper<>();
//            wrapper.eq()


//        查询小区有多少楼栋
            HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
            buildingRecord.setCommunityId(communityId);
            List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null, buildingRecord, null, null, null, null, null);
            buildingNumber = buildingRecordList.size();

            for (int i = 0; i < buildingRecordList.size(); i++) {
//        计算复式套数
                if (buildingRecordList.get(i).getMultipleNumber() != null && buildingRecordList.get(i).getMultipleNumber() != 0) {
                    multipleNumber += buildingRecordList.get(i).getMultipleNumber();
                }

//        计算单元数
                HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
                unitRecord.setBuildingId(buildingRecordList.get(i).getId());
                List<HousePropertyBuildingUnitRecord> unitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null, unitRecord, null, null, null, null, null);
                unitNumber += unitRecordList.size();

                for (HousePropertyBuildingUnitRecord unit : unitRecordList) {

                    Double areaNumber = 0.0;
                    Long houseTypeId = unit.getDesignModelId();
                    if (houseTypeId != null) {
                        //        计算户型数
                        if (houseTypeMap.get(String.valueOf(houseTypeId)) != null) {
                            houseTypeMap.put(String.valueOf(houseTypeId), houseTypeMap.get(String.valueOf(houseTypeId)) + 1);
                        } else {
                            houseTypeMap.put(String.valueOf(houseTypeId), 1);
                        }

//                    获取户型面积
                        HouseDesignModel houseDesignModel = queryHouseDesignModelDao.queryMasterModel(houseTypeId);
                        areaNumber = houseDesignModel.getArea().doubleValue();
                    }

//                计算房屋数
                    HouseAssetRecord houseRecord = new HouseAssetRecord();
                    houseRecord.setUnitId(unit.getId());
                    houseRecord.setAssetType(1);
                    List<HouseAssetRecord> houseRecordList = queryHouseAssetDao.findHouseAssetPage(null, houseRecord, null, null, null, null, null);
                    if (houseRecordList != null && houseRecordList.size() > 0 && areaNumber != null && !areaNumber.equals(0)) {
//                    添加面积
                        totalArea += areaNumber * houseRecordList.size();
                    }
                    houseNumber += houseRecordList.size();

                    //        计算车位
                    houseRecord.setAssetType(2);
                    houseRecordList = queryHouseAssetDao.findHouseAssetPage(null, houseRecord, null, null, null, null, null);
                    if (houseRecordList != null && houseRecordList.size() > 0 && areaNumber != null && !areaNumber.equals(0)) {
//                    添加面积
                        totalArea += areaNumber * houseRecordList.size();
                    }
                    parkingNumber += houseRecordList.size();


                }

            }
            HouseEquityDemandSupplyRecord houseEquityDemandSupplyRecord = new HouseEquityDemandSupplyRecord();
            HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord = new HouseAssetExchangeRequestRecord();

            List<HouseEquityDemandSupplyRecord> equityDemandSupplyRecordList = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(null, houseEquityDemandSupplyRecord, null, null, null, null, null, null, null);
            List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null, houseAssetExchangeRequestRecord, null, null, null, null, null);




            JSONObject jsonObject = new JSONObject();
            jsonObject.put("buildingNumber", buildingNumber);
            jsonObject.put("multipleNumber", multipleNumber);
            jsonObject.put("unitNumber", unitNumber);
            jsonObject.put("houseNumber", houseNumber);
            jsonObject.put("parkingNumber", parkingNumber);
            jsonObject.put("totalArea", totalArea);
            jsonObject.put("houseTypeNumber", houseTypeMap.size());
            jsonObject.put("houseExchangeNumber", exchangeRequestRecordList.size());
            jsonObject.put("equityNumber", equityDemandSupplyRecordList.size());
        }


//        return SuccessTip.create(jsonObject);
        return SuccessTip.create();
    }
}
