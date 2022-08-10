package com.jfeat.am.module.house.api.userself.operations;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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
    Authentication authentication;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;



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

////    以单元查询绑定的房屋
//    @GetMapping("/getHouse")
//    public Tip getHouseByUnitId(){
//        QueryWrapper<HouseAsset> queryWrapper = new QueryWrapper<>();
//        return SuccessTip.create(housePropertyBuildingUnitService.updateUnitInfo());
//    }

//    修改门牌号
    @PutMapping("/updateUnitBing/{id}")
    public Tip updateUnitBind(@PathVariable("id")Long id,@RequestBody HousePropertyBuildingUnit entity){
        return SuccessTip.create(housePropertyBuildingUnitService.updateUnitBind(id, entity));
    }

}
