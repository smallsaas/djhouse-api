package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.HouseStatisticsDao;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("HouseAssetExchangeRequest")
@RequestMapping("/api/u/house/building")
public class UserBuildingEndpoint {

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

    @RequestMapping("/{id}")
    public Tip getBuildingStatisticsInfo(@PathVariable("id") Long id){

        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(id);

//        0-房子数 1-单元数 2-户型数 3-以回迁数 4-复式数
        List<Integer> list =  houseStatisticsDao.buildingStatistics(id);

//        我的回迁数
        String sql = "SELECT t_house_user_asset.asset_id FROM t_house_user_asset WHERE t_house_user_asset.user_id=".concat(String.valueOf(JWTKit.getUserId()));
        QueryWrapper<HouseAsset> houseAssetQueryWrapper = new QueryWrapper<>();
        houseAssetQueryWrapper.eq(HouseAsset.BUILDING_ID,id).inSql(HouseAsset.ID,sql);
        List<HouseAsset> houseAssetList =  houseAssetMapper.selectList(houseAssetQueryWrapper);

//        单元统计
        QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq(HousePropertyBuildingUnit.BUILDING_ID,id);
        List<HousePropertyBuildingUnit> unitList = housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);


//        户型统计
        List<Long> houseTypeIds = new ArrayList<>();
        for (HousePropertyBuildingUnit unit:unitList){
            if (unit.getDesignModelId()!=null && !houseTypeIds.contains(unit.getDesignModelId())){
                houseTypeIds.add(unit.getDesignModelId());
            }
        }


        List<HouseDesignModel> designModels = new ArrayList<>();

        if (houseTypeIds!=null && houseTypeIds.size()>0){
            QueryWrapper<HouseDesignModel> designModelQueryWrapper = new QueryWrapper<>();
            designModelQueryWrapper.in(HouseDesignModel.ID,houseTypeIds);
            designModels =  houseDesignModelMapper.selectList(designModelQueryWrapper);
        }


        JSONObject houseTypeJson = new JSONObject();
        for (HouseDesignModel houseDesignModel:designModels){
            houseTypeJson.put("id",houseDesignModel.getId());
            houseTypeJson.put("area",houseDesignModel.getArea());
            houseTypeJson.put("houseType",houseDesignModel.getHouseType());
            String value ="";
            if (houseDesignModel.getArea()!=null && houseDesignModel.getHouseType()!=null){
                value = "".concat(String.valueOf(houseDesignModel.getArea())).concat("(").concat(houseDesignModel.getHouseType()).concat(")");
            }
            houseTypeJson.put("detailsName",value);
        }

        JSONObject unitJson  = new JSONObject();
        for (HousePropertyBuildingUnit unit:unitList){
            JSONObject unitItem = new JSONObject();
            String[] unitStr = unit.getUnitCode().split("-");
            Integer unitIndex= null;
            String unitKey="";
            if (unitStr.length>=2){
                unitIndex = Integer.valueOf(unitStr[1]);
            }
            if (unitIndex!=null){
                unitKey = HousePropertyBuildingUnit.UNIT_NUMBER[unitIndex-1];
            }
            BigDecimal area = null;
            Long houseTypeId = null;
            for (HouseDesignModel houseDesignModel:designModels){
                if (unit.getDesignModelId()!=null&&unit.getDesignModelId().equals(houseDesignModel.getId())){
                    area = houseDesignModel.getArea();
                    houseTypeId=houseDesignModel.getId();
                }
            }
            unitItem.put("area",area);
            unitItem.put("unitCode",unit.getUnitCode());
            unitItem.put("unitNumber",unit.getUnitNumber());
            unitItem.put("houseTypeId",houseTypeId);
            unitItem.put("direction",unit.getDirection());
            unitJson.put(unitKey,unitItem);
        }

        JSONObject buildingJson = new JSONObject();
        buildingJson.put("buildingName",housePropertyBuilding.getCode());
        buildingJson.put("houseCount",list.get(0));
        buildingJson.put("unitNumber",list.get(1));
        buildingJson.put("houseTypeNumber",list.get(2));
        buildingJson.put("removeBackNumber",list.get(3));
        buildingJson.put("multipleNumber",list.get(4));
        buildingJson.put("myselfNumber",houseAssetList.size());
        buildingJson.put("unit",unitJson);
        buildingJson.put("houseType",houseTypeJson);



        return SuccessTip.create(buildingJson);
    }
}
