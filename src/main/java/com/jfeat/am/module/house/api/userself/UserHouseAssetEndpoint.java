package com.jfeat.am.module.house.api.userself;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.crud.tag.services.domain.dao.QueryStockTagDao;
import com.jfeat.am.crud.tag.services.domain.record.StockTagRecord;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserDecoratePlanRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
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
import javax.xml.crypto.Data;
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

    @Resource
    QueryStockTagDao queryStockTagDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;





//    获取小区
    @GetMapping("/community")
    public Tip getHousePropertyCommunityByTenantId() {
        Long tenantId = JWTKit.getOrgId();
        System.out.println(tenantId);
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

//            判断是否有人居住
            HouseUserAssetRecord record = new HouseUserAssetRecord();
            record.setAssetId(houseAssetList.get(i).getId());
            List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,record,null,null,null,null,null);
            if (recordList!=null && recordList.size()==1){
                EndpointUserModel endpointUser  = queryEndpointUserDao.queryMasterModel(recordList.get(0).getUserId());
                if (endpointUser!=null){
                    if (endpointUser.getPhone()!=null){
                        houseAssetList.get(i).setUserPhone(endpointUser.getPhone());
                    }
                    if (endpointUser.getAvatar()!=null){
                        houseAssetList.get(i).setUserAvatar(endpointUser.getAvatar());
                    }
                    if (endpointUser.getName()!=null){
                        houseAssetList.get(i).setUsername(endpointUser.getName());
                    }

                    houseAssetList.get(i).setExistUser(true);
                    if (JWTKit.getUserId().equals(endpointUser.getId())){
                        houseAssetList.get(i).setMyselfAsset(true);
                    }
                }
            }
        }
//        Page<HouseAsset> page = new Page<>();
//        page.setRecords(houseAssetList);
        return SuccessTip.create(houseAssetList);
    }

//    获取房屋详情
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
        for (int i=0;i<houseUserAssets.size();i++){
            String address = houseUserAssets.get(i).getAddress();
            String buildingCode = houseUserAssets.get(i).getBuildingCode();
            String number = houseUserAssets.get(i).getRoomNumber();
            houseUserAssets.get(i).setAddressDetail("".concat(address).concat(" ").concat(buildingCode).concat(" ").concat(number));
        }
        logger.info("userId:{},size:{}",userId,houseUserAssets.size());
        return SuccessTip.create(houseUserAssets);
    }

//    用户asset 详情信息
    @GetMapping("/user/userAsset/{id}")
    public Tip getUserAssetDetails(@PathVariable("id") Long id){
        HouseUserAssetModel houseAssetRecord = houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id);
        HouseUserDecoratePlanRecord houseUserDecoratePlanRecord = new HouseUserDecoratePlanRecord();
        if (houseAssetRecord!=null){
            houseUserDecoratePlanRecord.setUserId(JWTKit.getUserId());
            houseUserDecoratePlanRecord.setAssetId(houseAssetRecord.getAssetId());
            List<HouseUserDecoratePlanRecord> houseUserDecoratePlanRecordList =  queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null,houseUserDecoratePlanRecord,null,null,null,null,null);
            if (houseUserDecoratePlanRecordList!=null && houseUserDecoratePlanRecordList.size()>0){
                Long planId =  houseUserDecoratePlanRecordList.get(0).getDecoratePlanId();
                HouseDecoratePlan houseDecoratePlan = queryHouseDecoratePlanDao.queryMasterModel(planId);
                houseAssetRecord.setHouseDecoratePlan(houseDecoratePlan);
            }
        }
        return SuccessTip.create(houseAssetRecord);
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

//    出租列表
    @GetMapping("/user/rent/list")
    public Tip getRentList(Page<HouseUserAssetRecord> page,
                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        HouseUserAssetRecord record = new HouseUserAssetRecord();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        record.setRentStatus(1);

        List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, null, null, null, null, null);
        for (int i=0;i<houseUserAssetPage.size();i++){
            Long id = houseUserAssetPage.get(i).getId();
            HouseUserAssetModel houseAssetRecord = houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id);
            houseUserAssetPage.get(i).setExtra(houseAssetRecord.getExtra());
        }
        page.setRecords(houseUserAssetPage);

        return SuccessTip.create(page);
    }

//    用户出租详情
    @GetMapping("/user/rent/details/{id}")
    public Tip getALlRentAsset(@PathVariable("id") Long id) {
        HouseUserAssetModel houseAssetRecord = houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id);
        if (houseAssetRecord!=null){
            HousePropertyBuildingUnit housePropertyBuildingUnit =  queryHousePropertyBuildingUnitDao.queryMasterModel(houseAssetRecord.getUnitId());
            List<Product> productList= queryHouseDecoratePlanDao.queryProductListByDesignModel(housePropertyBuildingUnit.getDesignModelId());
            if (productList!=null){
                houseAssetRecord.setProductList(productList);
            }
        }
        return SuccessTip.create(houseAssetRecord);
    }


//    出租标签列表
    @GetMapping("/user/rent/tags")
    @ApiOperation("Tag List")
    public Tip queryStockTags(Page<StockTagRecord> page, @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize, @RequestParam(name = "id",required = false) Long id, @RequestParam(name = "tagName",required = false) String tagName, @RequestParam(name = "tagType",required = false) String tagType, @RequestParam(name = "isPrimary",required = false) Integer isPrimary, @RequestParam(name = "stockId",required = false) Long stockId, @RequestParam(name = "stockType",required = false) String stockType, @RequestParam(name = "orderBy",required = false) String orderBy, @RequestParam(name = "sort",required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");
                }
            } else {
                sort = "ASC";
            }

            orderBy = "`" + orderBy + "` " + sort;
        }

        page.setCurrent((long)pageNum);
        page.setSize((long)pageSize);
        StockTagRecord record = new StockTagRecord();
        record.setId(id);
        record.setTagName(tagName);
        record.setTagType(tagType);
        record.setIsPrimary(isPrimary);
        record.setStockId(stockId);
        record.setStockType(stockType);
        page.setRecords(this.queryStockTagDao.findStockTagPage(page, record, orderBy));
        return SuccessTip.create(page);
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
