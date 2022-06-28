package com.jfeat.am.module.house.api.userself;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Api("UserHouseAsset")
@RequestMapping("/api/u/asset")
public class UserHouseAssetEndpoint {
    protected final static Logger logger = LoggerFactory.getLogger(UserHouseAssetEndpoint.class);

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    HouseUserCommunityStatusService houseUserCommunityStatusService;

    @Resource
    QueryHousePropertyBuildingDao housePropertyBuildingDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseUserAssetService houseUserAssetService;

//    获取小区
    @GetMapping("/community")
    public Tip getHousePropertyCommunityByTenantId() {
        Long tenantId = JWTKit.getOrgId();
        return SuccessTip.create(queryHousePropertyCommunityDao.queryHouseCommunityByTenantId(tenantId));
    }

//    修改用户小区状态
    @PostMapping("/community/userCommunityStatus")
    public Tip updateUserCommunityStatusByUserId(@RequestParam("communityId") Long communityId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        System.out.println(JWTKit.getTenantOrgId());
        HouseUserCommunityStatus entity = new HouseUserCommunityStatus();

        entity.setUserId(JWTKit.getUserId());
        entity.setTenantId(JWTKit.getOrgId());
        entity.setCommunityId(communityId);

        HouseUserCommunityStatus houseUserCommunityStatus = queryHouseUserCommunityStatusDao.queryUserCommunityStatusByUserId(JWTKit.getUserId());
        if (houseUserCommunityStatus != null) {
            return SuccessTip.create(queryHouseUserCommunityStatusDao.updateUserCommunityStatusByUserId(entity));
        } else {
            Integer affected = 0;
            try {
                affected = houseUserCommunityStatusService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
            return SuccessTip.create(affected);
        }
    }

    //    获取楼栋信息
    @GetMapping("/building")
    public Tip getBuildingInfo() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        List<HousePropertyBuilding> housePropertyBuildingList = housePropertyBuildingDao.queryHousePropertyBuildingByUserId(JWTKit.getUserId());
        for (int i = 0; i < housePropertyBuildingList.size(); i++) {
            housePropertyBuildingList.get(i).setAssertTotal(housePropertyBuildingDao.queryHouseAssetNumberByBuildingId(housePropertyBuildingList.get(i).getId()));
            housePropertyBuildingList.get(i).setHouseTypeNumber(housePropertyBuildingDao.queryHouseTypeNumberByBuildingId(housePropertyBuildingList.get(i).getId()));
        }
        return SuccessTip.create(housePropertyBuildingList);
    }

    @GetMapping("/asset/{buildingId}")
    public Tip getAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        List<HouseAsset> houseAssetList = queryHouseAssetDao.queryHouseAssetByBuildingId(buildingId);
        List<HouseUserAsset> houseUserAssetList = queryHouseUserAssetDao.queryUserAssetByUserId(JWTKit.getUserId());
        for (int i = 0; i < houseAssetList.size(); i++) {
            for (HouseUserAsset houseUserAsset : houseUserAssetList) {
                if (houseAssetList.get(i).getId().equals(houseUserAsset.getAssetId())) {
                    houseAssetList.get(i).setMyselfAsset(true);
                }
            }
        }
        Page<HouseAsset> page = new Page<>();
        page.setRecords(houseAssetList);
        return SuccessTip.create(page);
    }

    @GetMapping("/asset/details/{assetId}")
    public Tip getAssetDetails(@PathVariable("assetId") Long assetId){

        return SuccessTip.create(queryHouseAssetDao.queryHouseAssetDetails(assetId));
    }

    @ApiOperation(value = "获取用户的unit", response = QueryHouseAssetDao.class)
    @GetMapping("/user/userAsset")
    public Tip getUserUnite() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        List<HouseUserAsset> houseUserAssets = queryHouseUserAssetDao.queryUserRoomByUserId(userId);
        logger.info("userId:{},size:{}",userId,houseUserAssets.size());
        return SuccessTip.create(houseUserAssets);
    }


    //    新增安装unit位置
    @BusinessLog(name = "HousePropertyUserUnit", value = "create HousePropertyUserUnit")
    @PostMapping("/user/userAsset")
    @ApiOperation(value = "新建 HousePropertyUserUnit", response = QueryHouseUserAssetDao.class)
    public Tip createHousePropertyUserUnit(@RequestBody HouseUserAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        Integer affected = 0;
        try {
            affected = houseUserAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    //    修改用户的资产
    @BusinessLog(name = "HousePropertyUserUnit", value = "update HousePropertyUserUnit")
    @PutMapping("/user/userAsset/{id}")
    @ApiOperation(value = "修改 HousePropertyUserUnit", response = HouseUserAsset.class)
    public Tip updateHousePropertyUserUnit(@PathVariable Long id, @RequestBody HouseUserAsset entity) {
        entity.setId(id);
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        return SuccessTip.create(houseUserAssetService.updateMaster(entity));
    }

    //    删除用户资产
    @BusinessLog(name = "HousePropertyUserUnit", value = "delete HousePropertyUserUnit")
    @DeleteMapping("/user/userAsset/{id}")
    @ApiOperation("删除 HousePropertyUserUnit")
    public Tip deleteHousePropertyUserUnit(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseUserAssetService.deleteMaster(id));
    }

//    出租或者下架出租房屋
    @PutMapping("/user/rent/{id}")
    public Tip updateRentStatus(@PathVariable Long id,@RequestBody HouseUserAsset entity){
        entity.setId(id);
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        entity.setRentTime(new Date());
        return SuccessTip.create(queryHouseUserAssetDao.updateUserAssetByUserIdAndAsset(JWTKit.getUserId(),id,entity));
    }

//    添加冲突信息
    @PutMapping("/clash/{assetId}")
    public Tip updateClashInfo(@PathVariable("assetId") Long assetId,@RequestBody HouseUserAsset entity){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setClashUserId(JWTKit.getUserId());
        Integer effect =  queryHouseUserAssetDao.updateClashAssetByAssetId(assetId,entity);
        return SuccessTip.create();
    }


}
