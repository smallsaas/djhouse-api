package com.jfeat.am.module.house.api.app.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//返回房产继承数据列表
@RestController
@RequestMapping("/api/u/house/baseInfo")
public class HouseAssetBaseInfoEndpoint {

    @Resource
    HouseDesignModelMapper houseDesignModelMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseAssetMapper houseAssetMapper;

//    获取户型列表
    @GetMapping("/houseType/name")
    public Tip getHouseTypeList(){
        QueryWrapper<HouseDesignModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy(HouseDesignModel.HOUSE_TYPE);
        List<HouseDesignModel> houseDesignModels = houseDesignModelMapper.selectList(queryWrapper);

        List<String> result = new ArrayList<>();

        if (houseDesignModels!=null&&houseDesignModels.size()>0){

            for (HouseDesignModel houseDesignModel:houseDesignModels){
                if (houseDesignModel.getHouseType()!=null&&!houseDesignModel.getHouseType().equals("")){
                    result.add(houseDesignModel.getHouseType());
                }
            }

        }

        return SuccessTip.create(result);
    }

//    获取期数
    @GetMapping("/issue")
    public Tip getIssueList(){
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId = userCommunityAsset.getUserCommunityStatus(userId);

        if (communityId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有选择小区");
        }

        QueryWrapper<HousePropertyBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID,communityId);
        queryWrapper.groupBy(HousePropertyBuilding.ISSUE);

        List<HousePropertyBuilding> housePropertyBuildings = housePropertyBuildingMapper.selectList(queryWrapper);

        List<Integer> collect = housePropertyBuildings.stream().map(HousePropertyBuilding::getIssue).collect(Collectors.toList());

        return SuccessTip.create(collect);
    }

//    根据当前期数获取楼栋
    @GetMapping("/building/issue/{issue}")
    public Tip getBuildingList(@PathVariable("issue") Integer issue){
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId = userCommunityAsset.getUserCommunityStatus(userId);

        if (communityId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有选择小区");
        }

        QueryWrapper<HousePropertyBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID,communityId);
        queryWrapper.eq(HousePropertyBuilding.ISSUE,issue);

        List<HousePropertyBuilding> housePropertyBuildings = housePropertyBuildingMapper.selectList(queryWrapper);

        return SuccessTip.create(housePropertyBuildings);
    }


//    根据楼栋获取单元列表
    @GetMapping("/unit/{buildingId}")
    public Tip getUnitList(@PathVariable("buildingId")Long buildingId){

        QueryWrapper<HousePropertyBuildingUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HousePropertyBuildingUnit.BUILDING_ID,buildingId);
        return SuccessTip.create(housePropertyBuildingUnitMapper.selectList(queryWrapper));
    }


//    获取房屋详情
    @GetMapping("/asset/{id}")
    public Tip getAssetInfoById(@PathVariable Long id){
        return SuccessTip.create(houseAssetMapper.selectById(id));
    }

    @PutMapping("/asset/{id}")
    public Tip updateAssetInfoById(@PathVariable("id")Long id, HouseAsset entity){

        entity.setId(id);
        return SuccessTip.create(houseAssetMapper.updateById(entity));
    }

}
