package com.jfeat.am.module.house.api.app.operations;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.house.services.utility.RedisScript;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/UserUnitManageEndpoint")
public class UserUnitManageEndpoint {


    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    RedisScript redisScript;


    @GetMapping("/unit")
    public Tip getUnitList(@RequestParam("buildingId") Long buildingId){

        HousePropertyBuildingUnitRecord record = new HousePropertyBuildingUnitRecord();
        record.setBuildingId(buildingId);

        queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,record,null,null,null,null,null);


        QueryWrapper<HousePropertyBuildingUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HousePropertyBuildingUnit.BUILDING_ID,buildingId);
        List<HousePropertyBuildingUnit> unitList = housePropertyBuildingUnitMapper.selectList(queryWrapper);
        return SuccessTip.create(queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,record,null,null,null,null,null)
        );
    }


//    以楼栋id获取 楼栋的全部单元
    @GetMapping("/getUnit")
    public Tip getUnitByBuildingID(@RequestParam("buildingId") Long buildingId){
        QueryWrapper<HousePropertyBuildingUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HousePropertyBuildingUnit.BUILDING_ID,buildingId);
        List<HousePropertyBuildingUnit> unitList = housePropertyBuildingUnitMapper.selectList(queryWrapper);

        QueryWrapper<HouseAsset> assetQueryWrapper = new QueryWrapper<>();
        assetQueryWrapper.eq(HouseAsset.BUILDING_ID,buildingId);
        List<HouseAsset> houseAssetList = houseAssetMapper.selectList(assetQueryWrapper);

        HousePropertyBuilding building =  housePropertyBuildingMapper.selectById(buildingId);
        for (HousePropertyBuildingUnit unit:unitList){
            if (building!=null){
                unit.setFloorsCount(building.getFloors());
            }
            List<HouseAsset> items  = new ArrayList<>();
            for (HouseAsset houseAsset:houseAssetList){
                if (unit.getId().equals(houseAsset.getUnitId())){
                    items.add(houseAsset);
                }
            }
            unit.setItems(items);
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("units",unitList);
        jsonArray.add(jsonObject);
        return SuccessTip.create(jsonArray);
    }


//    修改门牌号
    @PutMapping("/updateUnitBing/{id}")
    public Tip updateUnitBind(@PathVariable("id")Long id,@RequestBody HousePropertyBuildingUnit entity){

        HousePropertyBuildingUnit unit =  housePropertyBuildingUnitMapper.selectById(id);

        Integer affect =  housePropertyBuildingUnitService.updateUnitBind(id, entity);

        if (affect>0 && unit.getBuildingId()!=null){
            redisScript.delRidesData("*".concat("buildingId").concat(String.valueOf(unit.getBuildingId())).concat(":*"));
        }

        return SuccessTip.create(affect);
    }

//    获取单元列表以小区id
    @GetMapping("/getUnitList")
    public Tip getUnitByCommunityId(Page<HousePropertyBuildingUnitRecord> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam("communityId")Long communityId){
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(queryHousePropertyBuildingUnitDao.queryUnitListByCommunityId(page,communityId));
        return SuccessTip.create(page);
    }

//    修改单元绑定户型
    @PutMapping("/modifyHouseType/{id}")
    public Tip updateUnitBindHouseType(@PathVariable("id")Long unitId,@RequestBody HousePropertyBuildingUnit entity){

        if (entity.getDesignModelId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"designModelId为比填项");
        }

        HousePropertyBuildingUnit housePropertyBuildingUnit =  housePropertyBuildingUnitMapper.selectById(unitId);
        if (housePropertyBuildingUnit!=null && entity.getDesignModelId()!=null){
            housePropertyBuildingUnit.setDesignModelId(entity.getDesignModelId());

            Integer affect = housePropertyBuildingUnitMapper.updateById(housePropertyBuildingUnit);

//            清除redis缓存
            HousePropertyBuildingUnit unit =  housePropertyBuildingUnitMapper.selectById(unitId);
            if (affect>0 && unit.getBuildingId()!=null){
                redisScript.delRidesData("*".concat("buildingId").concat(String.valueOf(unit.getBuildingId())).concat(":*"));
            }
            return SuccessTip.create(affect);
        }
        return SuccessTip.create();
    }

//    修改单元属性
    @PutMapping("/{id}")
    public Tip updateUnitAttribute(@PathVariable("id")Long id,@RequestBody HousePropertyBuildingUnit entity){
        HousePropertyBuildingUnit housePropertyBuildingUnit = housePropertyBuildingUnitMapper.selectById(id);
        if (housePropertyBuildingUnit!=null){
            housePropertyBuildingUnit.setArea(entity.getArea());
            housePropertyBuildingUnit.setRealArea(entity.getRealArea());
            housePropertyBuildingUnit.setDirection(entity.getDirection());
            housePropertyBuildingUnit.setMultiArea(entity.getMultiArea());
            housePropertyBuildingUnit.setMultiRealArea(entity.getMultiRealArea());
            return SuccessTip.create(housePropertyBuildingUnitMapper.updateById(housePropertyBuildingUnit));
        }
        return SuccessTip.create();
    }

    @GetMapping("/{id}")
    public Tip getUnitById(@PathVariable("id") Long id){
        return SuccessTip.create(housePropertyBuildingUnitMapper.selectById(id));
    }

}
