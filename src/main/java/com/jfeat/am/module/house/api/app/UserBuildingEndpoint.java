package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.HouseStatisticsDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.utility.RedisScript;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.core.util.RedisKit;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.management.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api("HouseAssetExchangeRequest")
@RequestMapping("/api/u/house/building")
public class UserBuildingEndpoint {

    private final Log logger = LogFactory.getLog(getClass());

    @Resource
    HouseStatisticsDao houseStatisticsDao;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    HouseDesignModelMapper houseDesignModelMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    RedisScript redisScript;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    //    返回当前小区楼栋列表
    @GetMapping("/getCurrentCommunityBuilding")
    public Tip getCurrentCommunityBuilding() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long userCommunityStatus = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        if (userCommunityStatus == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有选择小区");
        }

        HousePropertyBuildingRecord record = new HousePropertyBuildingRecord();
        record.setCommunityId(userCommunityStatus);
        return SuccessTip.create(queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null, record, null, null, null, null, null));
    }

    @GetMapping("/{id}")
    public Tip getBuildingStatisticsInfo(@PathVariable("id") Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(id);

//        0-房子数 1-单元数 2-户型数 3-以回迁数 4-复式数
        List<Integer> list = houseStatisticsDao.buildingStatistics(id);

//        我的回迁数
        String sql = "SELECT t_house_user_asset.asset_id FROM t_house_user_asset WHERE t_house_user_asset.user_id=".concat(String.valueOf(JWTKit.getUserId()));
        QueryWrapper<HouseAsset> houseAssetQueryWrapper = new QueryWrapper<>();
        houseAssetQueryWrapper.eq(HouseAsset.BUILDING_ID, id).inSql(HouseAsset.ID, sql);
        List<HouseAsset> houseAssetList = houseAssetMapper.selectList(houseAssetQueryWrapper);

//        单元统计
        QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq(HousePropertyBuildingUnit.BUILDING_ID, id);
        List<HousePropertyBuildingUnit> unitList = housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);


//        户型统计
        List<Long> houseTypeIds = new ArrayList<>();
        for (HousePropertyBuildingUnit unit : unitList) {
            if (unit.getDesignModelId() != null && !houseTypeIds.contains(unit.getDesignModelId())) {
                houseTypeIds.add(unit.getDesignModelId());
            }
        }


        List<HouseDesignModel> designModels = new ArrayList<>();

        if (houseTypeIds != null && houseTypeIds.size() > 0) {
            QueryWrapper<HouseDesignModel> designModelQueryWrapper = new QueryWrapper<>();
            designModelQueryWrapper.in(HouseDesignModel.ID, houseTypeIds);
            designModels = houseDesignModelMapper.selectList(designModelQueryWrapper);
        }


        JSONArray houseTypeJsonArray = new JSONArray();
        for (HouseDesignModel houseDesignModel : designModels) {
            JSONObject houseTypeJson = new JSONObject();
            houseTypeJson.put("id", houseDesignModel.getId());
            houseTypeJson.put("area", houseDesignModel.getArea());
            houseTypeJson.put("houseType", houseDesignModel.getHouseType());
            String value = "";
            if (houseDesignModel.getArea() != null && houseDesignModel.getHouseType() != null) {
                value = "".concat(String.valueOf(houseDesignModel.getArea())).concat("(").concat(houseDesignModel.getHouseType()).concat(")");
            }
            houseTypeJson.put("detailsName", value);
            houseTypeJsonArray.add(houseTypeJson);
        }

        JSONObject unitJson = new JSONObject();
        for (HousePropertyBuildingUnit unit : unitList) {
            JSONObject unitItem = new JSONObject();
            String[] unitStr = unit.getUnitCode().split("-");
            Integer unitIndex = null;
            String unitKey = "";
            if (unitStr.length >= 2) {
                unitIndex = Integer.valueOf(unitStr[1]);
            }
//            对应数据单元的英文
            if (unitIndex != null) {
                unitKey = HousePropertyBuildingUnit.UNIT_NUMBER[unitIndex - 1];
            }
            BigDecimal area = null;
            Long houseTypeId = null;
            for (HouseDesignModel houseDesignModel : designModels) {
                if (unit.getDesignModelId() != null && unit.getDesignModelId().equals(houseDesignModel.getId())) {
                    area = houseDesignModel.getArea();
                    houseTypeId = houseDesignModel.getId();
                }
            }
            unitItem.put("area", area);
            unitItem.put("unitCode", unit.getUnitCode());
            unitItem.put("unitNumber", unit.getUnitNumber());
            unitItem.put("houseTypeId", houseTypeId);
            unitItem.put("direction", unit.getDirection());
            unitItem.put("xAxis",unit.getxAxis());
            unitItem.put("yAxis",unit.getyAxis());
            unitJson.put(unitKey, unitItem);
        }

        JSONObject buildingJson = new JSONObject();
        buildingJson.put("buildingName", housePropertyBuilding.getCode());
        buildingJson.put("buildingId", housePropertyBuilding.getId());
        buildingJson.put("floorsNumber", housePropertyBuilding.getFloors());
        buildingJson.put("unitPicture",housePropertyBuilding.getUnitPicture());
        buildingJson.put("houseCount", list.get(0));
        buildingJson.put("unitNumber", list.get(1));
        buildingJson.put("houseTypeNumber", list.get(2));
        buildingJson.put("removeBackNumber", list.get(3));
        buildingJson.put("multipleNumber", list.get(4));
        buildingJson.put("myselfNumber", houseAssetList.size());
        buildingJson.put("unit", unitJson);
        buildingJson.put("houseType", houseTypeJsonArray);

        buildingJson.put("buildingObject",housePropertyBuilding);



        return SuccessTip.create(buildingJson);
    }

    @GetMapping("/getFloorsHouseNumber")
    public Tip getFloorsHouseNumber(@RequestParam(value = "buildingId",required = true) Long buildingId, @RequestParam(value = "floors",required = true) Integer floor) {


        HousePropertyBuilding building = housePropertyBuildingMapper.selectById(buildingId);
        if (building.getCommunityId()==null){
            return null;
        }
        String key =  redisScript.getBuildingFloorsKey(building.getCommunityId(),buildingId,floor);
        System.out.println(key);

        if (RedisKit.isSanity() && stringRedisTemplate.hasKey(key) && stringRedisTemplate.opsForValue().getOperations().getExpire(key)>0){
            logger.info("getFloorsHouseNumber返回缓存数据");
            String value = (String) stringRedisTemplate.opsForValue().get(key);
            JSON json = JSONObject.parseObject(value);
            return SuccessTip.create(json);
        }

        logger.info("getFloorsHouseNumber没有缓存");




        QueryWrapper<HouseAsset> houseAssetQueryWrapper = new QueryWrapper<>();
        houseAssetQueryWrapper.eq(HouseAsset.BUILDING_ID, buildingId).eq(HouseAsset.FLOOR, floor);
        List<HouseAsset> assetList = houseAssetMapper.selectList(houseAssetQueryWrapper);

        List<Long> unitId = new ArrayList<>();
        for (HouseAsset asset : assetList) {
            if (asset.getUnitId() != null && !unitId.contains(asset.getUnitId())) {
                unitId.add(asset.getUnitId());
            }
        }

        List<HousePropertyBuildingUnit> housePropertyBuildingUnitList = new ArrayList<>();
        if (unitId != null && unitId.size() > 0) {
            QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
            unitQueryWrapper.in(HousePropertyBuildingUnit.ID, unitId);
            housePropertyBuildingUnitList = housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);
        }

        JSONObject unitJson = new JSONObject();
        for (HouseAsset asset:assetList){
            for (HousePropertyBuildingUnit unit : housePropertyBuildingUnitList) {
                if (asset.getUnitId().equals(unit.getId())){
                    JSONObject unitItem = new JSONObject();
                    String[] unitStr = unit.getUnitCode().split("-");
                    Integer unitIndex = null;
                    String unitKey = "";
                    if (unitStr.length >= 2) {
                        unitIndex = Integer.valueOf(unitStr[1]);
                    }
//            对应数据单元的英文
                    if (unitIndex != null) {
                        unitKey = HousePropertyBuildingUnit.UNIT_NUMBER[unitIndex - 1];
                    }

                    unitItem.put("unitCode", unit.getUnitCode());
                    unitItem.put("unitNumber", unit.getUnitNumber());
                    unitItem.put("direction", unit.getDirection());
                    unitItem.put("houseNumer",asset.getHouseNumber());
                    unitItem.put("number",asset.getNumber());
                    unitItem.put("assetFlag",asset.getAssetFlag());
                    unitItem.put("xAxis",unit.getxAxis());
                    unitItem.put("yAxis",unit.getyAxis());
                    unitJson.put(unitKey, unitItem);
                }
            }
        }

        if (RedisKit.isSanity()){
            stringRedisTemplate.opsForValue().set(key,unitJson.toJSONString(),24, TimeUnit.HOURS);
        }

        return SuccessTip.create(unitJson);
    }
}
