package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.frontproduct.services.domain.model.FrontProductModel;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseStatistics;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.am.module.order.services.gen.persistence.dao.OrderMapper;
import com.jfeat.am.module.order.services.gen.persistence.model.OrderItem;
import com.jfeat.am.module.order.services.gen.persistence.model.TOrder;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
    HouseStatisticsDao houseStatisticsDao;

    @Resource
    HouseStatistics houseStatistics;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    OrderMapper orderMapper;


    @GetMapping("/houseOverStatistics")
    public Tip getHouseOverStatistics() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        List<String> title = Arrays.asList("第一期", "第二期", "第三期", "第四期");

        Long communityId = null;

//        查询用户小区状态
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId == null) {
            return SuccessTip.create();
        }

        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID, communityId).groupBy(HousePropertyBuilding.ISSUE);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);


        JSONArray jsonArray = new JSONArray();

        for (HousePropertyBuilding building : buildingList) {
            //list 0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数
            List<Integer> houseStatistics = houseStatisticsDao.communityStatistics(communityId, building.getIssue());

            JSONObject item = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            Integer houseNumber = houseStatistics.get(0) - houseStatistics.get(1) - houseStatistics.get(3);
            jsonObject.put("buildingNumber", houseStatistics.get(4));
            jsonObject.put("multipleNumber", houseStatistics.get(3));
            jsonObject.put("unitNumber", houseStatistics.get(5));
            jsonObject.put("houseNumber", houseNumber);
            jsonObject.put("parkingNumber", houseStatistics.get(9));
//        jsonObject.put("totalArea",totalArea);
            jsonObject.put("houseTypeNumber", houseStatistics.get(6));
            jsonObject.put("houseExchangeNumber", houseStatistics.get(7));
            jsonObject.put("equityNumber", houseStatistics.get(8));

            String tab = "";
            if (building.getIssue() <= title.size()) {
                tab = title.get(building.getIssue() - 1);
            }
            item.put("tab", tab);
            item.put("issue", building.getIssue());
            item.put("data", jsonObject);
            jsonArray.add(item);
        }


        return SuccessTip.create(jsonArray);
    }


    @GetMapping("/buildingOverStatistics")
    public Tip getBuildingOverStatistics() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long communityId = null;
        Integer buildingNumber = 0;


//        查询用户小区状态
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询用户资产表

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> userAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null, buildingRecord, null, null, null, null, null);
        buildingNumber = buildingRecordList.size();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < buildingRecordList.size(); i++) {

            Integer multipleNumber = 0;
            Integer unitNumber = 0;
            Integer houseNumber = 0;
            Integer parkingNumber = 0;
            Double totalArea = 0.0;
            HashMap<String, Integer> houseTypeMap = new HashMap<>();

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
            jsonObject.put("buildingCode", buildingRecordList.get(i).getCode());
            jsonObject.put("multipleNumber", multipleNumber);
            jsonObject.put("unitNumber", unitNumber);
            jsonObject.put("houseNumber", houseNumber);
            jsonObject.put("parkingNumber", parkingNumber);
            jsonObject.put("totalArea", totalArea);
            jsonObject.put("houseTypeNumber", houseTypeMap.size());
//            jsonObject.put("houseRemainNumber",houseNumber-selectedNumber);
            jsonArray.add(jsonObject);

        }
        return SuccessTip.create(jsonArray);
    }


    @GetMapping("/houseTypeOverStatistics")
    public Tip getHouseTypeOverStatistics() {
        /*
        获取社区
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long communityId = null;
        Integer buildingNumber = 0;
        Integer multipleNumber = 0;
        Integer unitNumber = 0;
        Integer houseNumber = 0;
        Integer parkingNumber = 0;
        Double totalArea = 0.0;
        HashMap<String, Integer> houseTypeMap = new HashMap<>();

//        查询用户小区状态
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null, buildingRecord, null, null, null, null, null);
        buildingNumber = buildingRecordList.size();

        HouseDesignModelRecord houseDesignModelRecord = new HouseDesignModelRecord();

        List<HouseDesignModelRecord> designModelRecordList = queryHouseDesignModelDao.findHouseDesignModelPage(null, houseDesignModelRecord, null, null, null, null, null);

        JSONObject resultJson = new JSONObject();
        JSONObject houseTypeJson = new JSONObject();
        JSONObject buildingJson = new JSONObject();
        Set<Double> areaSet = new HashSet<>();


        for (HousePropertyBuildingRecord record : buildingRecordList) {
            HouseAssetRecord assetRecord = new HouseAssetRecord();
            assetRecord.setBuildingId(record.getId());
            assetRecord.setAssetType(HouseAsset.ASSET_Type_HOUSE);
            List<HouseAssetRecord> assetRecordList = queryHouseAssetDao.findHouseAssetPage(null, assetRecord, null, null, null, null, null);

            HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
            unitRecord.setBuildingId(record.getId());
            List<HousePropertyBuildingUnitRecord> unitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null, unitRecord, null, null
                    , null, null, null);

            JSONObject unitJsonObject = new JSONObject();
            for (HousePropertyBuildingUnitRecord buildingUnitRecord : unitRecordList) {
                for (HouseDesignModelRecord designModelRecord : designModelRecordList) {
                    if (buildingUnitRecord.getDesignModelId() != null && buildingUnitRecord.getDesignModelId().equals(designModelRecord.getId())) {
                        JSONObject jsonObject = new JSONObject();
                        String direction = buildingUnitRecord.getDirection();
                        BigDecimal area = designModelRecord.getArea();
                        jsonObject.put("area", area.doubleValue());
                        jsonObject.put("direction", direction);

                        String key = "".concat(String.valueOf(area)).concat("-").concat(direction);
                        houseTypeJson.put(key, jsonObject);


                        jsonObject.put("unitCode", buildingUnitRecord.getUnitCode());
                        jsonObject.put("houseTypeId", designModelRecord.getId());
                        jsonObject.put("houseType", designModelRecord.getHouseType());
                        String json = JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);

                        areaSet.add(area.doubleValue());

                        String[] unitStr = buildingUnitRecord.getUnitCode().split("-");
                        Integer unitIndex = null;
                        String unitKey = "";
                        if (unitStr.length >= 2) {
                            unitIndex = Integer.valueOf(unitStr[1]);
                        }
                        if (unitIndex != null) {
                            unitKey = HousePropertyBuildingUnit.UNIT_NUMBER[unitIndex - 1];
                        }
                        unitJsonObject.put(unitKey, JSONObject.parseObject(json));
                    }
                }
            }
//            添加楼栋


            JSONObject buildingItem = new JSONObject();
            buildingItem.put("houseCount", assetRecordList.size());
            buildingItem.put("multipleNumber", record.getMultipleNumber());
            buildingItem.put("unitNumber", unitRecordList.size());
            buildingItem.put("unit", unitJsonObject);
            buildingItem.put("buildingName", record.getCode());

            buildingJson.put(record.getCode(), buildingItem);

        }
        /*
        遍历单元
         */
        resultJson.put("area", areaSet);
        resultJson.put("houseType", houseTypeJson);
        resultJson.put("building", buildingJson);

        return SuccessTip.create(resultJson);
    }


    //    用户统计信息
    @GetMapping("/userOverStatistics")
    public Tip getUserOverStatistics() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        if (communityId == null) {
            throw new BusinessException(BusinessCode.CodeBase, "小区未找到");
        }

        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID, communityId);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);

        List<Long> buildingIds = new ArrayList<>();
        for (HousePropertyBuilding building : buildingList) {
            buildingIds.add(building.getId());
        }
        //        我的回迁数
        List<HouseAsset> houseAssetList = new ArrayList<>();
        if (buildingIds != null && buildingIds.size() > 0) {
            String sql = "SELECT t_house_user_asset.asset_id FROM t_house_user_asset WHERE t_house_user_asset.user_id=".concat(String.valueOf(JWTKit.getUserId()));
            QueryWrapper<HouseAsset> houseAssetQueryWrapper = new QueryWrapper<>();
            houseAssetQueryWrapper.in(HouseAsset.BUILDING_ID, buildingIds).inSql(HouseAsset.ID, sql);
            houseAssetList = houseAssetMapper.selectList(houseAssetQueryWrapper);
        }


        List<Long> unitIds = new ArrayList<>();
        for (HouseAsset houseAsset : houseAssetList) {
            if (houseAsset.getUnitId() != null && !unitIds.contains(houseAsset.getUnitId())) {
                unitIds.add(houseAsset.getUnitId());
            }
        }

//        单元统计
        List<HousePropertyBuildingUnit> unitList = new ArrayList<>();
        if (unitIds != null && unitIds.size() > 0) {
            QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
            unitQueryWrapper.in(HousePropertyBuildingUnit.ID, unitIds);
            unitList = housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);

        }


//        户型统计
        List<Long> houseTypeIds = new ArrayList<>();
        for (HousePropertyBuildingUnit unit : unitList) {
            if (unit.getDesignModelId() != null && !houseTypeIds.contains(unit.getDesignModelId())) {
                houseTypeIds.add(unit.getDesignModelId());
            }
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("assetCount", houseAssetList.size());
        resultJson.put("houseTypeCount", houseTypeIds.size());


        QueryWrapper<TOrder> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(TOrder.CATEGORY, "bulk").eq(TOrder.USER_ID, JWTKit.getUserId());
        Integer orderNum = orderMapper.selectList(orderQueryWrapper).size();


        resultJson.put("bulkCount", orderNum);

        return SuccessTip.create(resultJson);
    }

    @GetMapping("/currentCommunityBuildingStatistics")
    public Tip getCurrentCommunityBuildingStatistics() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            Long communityId = communityStatusRecordList.get(0).getCommunityId();
            if (communityId != null) {
                return SuccessTip.create(houseStatistics.getCommunityBuildingStatistics(communityId));
            }
        }
        return SuccessTip.create();
    }


    //    用户方数统计
    @GetMapping("userAreaStatistics")
    public Tip getUserAreaStatistics() {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        Long communityId = userCommunityAsset.getUserCommunityStatus(userId);
        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        userAssetRecord.setUserId(JWTKit.getUserId());
        userAssetRecord.setCommunityId(communityId);
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);

        BigDecimal totalArea = new BigDecimal(0);
        BigDecimal overArea = new BigDecimal(0);
        for (HouseUserAssetRecord record : houseUserAssets) {
            BigDecimal realArea = record.getRealArea() == null ? new BigDecimal(0) : record.getRealArea();
            totalArea = totalArea.add(realArea);
            BigDecimal area = record.getUnitArea() == null ? new BigDecimal(0) : record.getUnitArea();
            overArea = overArea.add(area);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalArea", totalArea);
        jsonObject.put("overArea", totalArea.subtract(overArea));


        return SuccessTip.create(jsonObject);
    }


}
