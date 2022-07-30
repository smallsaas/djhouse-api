package com.jfeat.am.module.house.api.userself;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.crud.tag.services.domain.dao.QueryStockTagDao;
import com.jfeat.am.crud.tag.services.domain.record.StockTagRecord;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseAssetComplaintService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;


    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;






//    获取小区
    @GetMapping("/community")
    public Tip getHousePropertyCommunityByTenantId() {
        if (JWTKit.getOrgId()==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有社区");
        }
        Long tenantId = JWTKit.getOrgId();
        return SuccessTip.create(queryHousePropertyCommunityDao.queryHouseCommunityByTenantId(tenantId));
    }

    //    全部获取小区 不需要社区验证
    @GetMapping("/common/community")
    public Tip getCommonCommunity(){
        HousePropertyCommunityRecord record = new HousePropertyCommunityRecord();
        List<HousePropertyCommunityRecord> communityRecordList = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(null,record,null,null,null,null,null);
        return SuccessTip.create(communityRecordList);
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
        Long start = System.currentTimeMillis();
        List<HousePropertyBuilding> housePropertyBuildingList = housePropertyBuildingDao.queryHousePropertyBuildingByUserId(JWTKit.getUserId());


//        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
//        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);
//        HouseDesignModelRecord houseDesignModelRecord = new HouseDesignModelRecord();
//        List<HouseDesignModelRecord> houseDesignModelRecordList = queryHouseDesignModelDao.findHouseDesignModelPage(null,houseDesignModelRecord,null,null,null,null,null);
//        for (int i = 0; i < housePropertyBuildingList.size(); i++) {
//            housePropertyBuildingList.get(i).setAssertTotal(housePropertyBuildingDao.queryHouseAssetNumberByBuildingId(housePropertyBuildingList.get(i).getId()));
//            housePropertyBuildingList.get(i).setHouseTypeNumber(housePropertyBuildingDao.queryHouseTypeNumberByBuildingId(housePropertyBuildingList.get(i).getId()));
//        }
        System.out.println(System.currentTimeMillis()-start);
        return SuccessTip.create(housePropertyBuildingList);
    }

//    查询楼栋下面的房子
    @GetMapping("/asset/{buildingId}")
    public Tip getAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord>  houseAssetList  = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);
//        List<HouseAsset> houseAssetList = queryHouseAssetDao.queryHouseAssetByBuildingId(buildingId);

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null,null);

        for (int i = 0; i < houseAssetList.size(); i++) {
//            判断是否有人居住
            for (int j = 0;j<recordList.size();j++){
                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())){
                    houseAssetList.get(i).setExistUser(true);

                      /*
                是否是最终确认
                 */
                    System.out.println(recordList.get(j));
                    System.out.println(houseAssetList.get(i));
                    System.out.println(recordList.get(j).getFinalFlag());
                    if (recordList.get(j).getFinalFlag()!=null && recordList.get(j).getFinalFlag().equals(HouseUserAsset.FINAL_FLAG_CONFIRM)){
                        houseAssetList.get(i).setFinalFlag(HouseUserAsset.FINAL_FLAG_CONFIRM);
                    }else {
                        houseAssetList.get(i).setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
                    }

                     /*
                是否是自己的
                 */
                    if (recordList.get(j).getUserId().equals(JWTKit.getUserId())){
                        houseAssetList.get(i).setMyselfAsset(true);
                    }
                }


            }
        }

        return SuccessTip.create(houseAssetList);
    }


    //    查询楼栋下面的房子 带用户信息
    @GetMapping("/userInfoAsset/{buildingId}")
    public Tip getUserInfoAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord>  houseAssetList  = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null,null);

        Long uStart = System.currentTimeMillis();
        for (int i = 0; i < houseAssetList.size(); i++) {
//            判断是否有人居住
            for (int j = 0;j<recordList.size();j++){
                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())){

//                    添加用户信息
                    EndpointUserModel endpointUser  = queryEndpointUserDao.queryMasterModel(recordList.get(j).getUserId());
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
                    }
                    houseAssetList.get(i).setExistUser(true);
                }
                if (recordList.get(j).getUserId().equals(JWTKit.getUserId())){
                    houseAssetList.get(i).setMyselfAsset(true);
                }
            }
        }

        return SuccessTip.create(houseAssetList);
    }

//    获取房屋详情
    @GetMapping("/asset/details/{assetId}")
    public Tip getAssetDetails(@PathVariable("assetId") Long assetId){

        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setId(assetId);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);
        if (houseAssetRecordList!=null && houseAssetRecordList.size()>0){
            SuccessTip.create(houseAssetRecordList.get(0));
        }
        return SuccessTip.create();
    }

//    获取用户房子信息
    @GetMapping("/user/userAsset")
    public Tip getUserUnite() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long star = System.currentTimeMillis();

        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId==null){
            return SuccessTip.create();
        }

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        userAssetRecord.setUserId(JWTKit.getUserId());
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,communityId,null,null,null,null,null);

        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null,houseRentAssetRecord,null,null,null,null,null);


        HouseUserDecoratePlanRecord userDecoratePlanRecord = new HouseUserDecoratePlanRecord();
        userDecoratePlanRecord.setUserId(JWTKit.getUserId());
        List<HouseUserDecoratePlanRecord> decoratePlanRecordList = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null,userDecoratePlanRecord,null,null,null,null,null);

        //            是否存在置换房屋
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setUserId(JWTKit.getUserId());
        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,exchangeRequestRecord,null,null,null,null,null);

        Long mid = System.currentTimeMillis();

        for (int i=0;i<houseUserAssets.size();i++){
            //        连接地址信息
            String address = houseUserAssets.get(i).getAddress();
            String buildingCode = houseUserAssets.get(i).getBuildingCode();
            String number = houseUserAssets.get(i).getRoomNumber();
            houseUserAssets.get(i).setAddressDetail("".concat(address).concat(" ").concat(buildingCode).concat(" ").concat(number));

//            设置 出租 托管 是否有置换
            if (houseUserAssets.get(i).getTrust()!=null && houseUserAssets.get(i).getTrust()>0){
                houseUserAssets.get(i).setExistTrust(true);
            }

//            出租状态
            for (HouseRentAssetRecord record:houseRentAssetRecordList){
                if (record.getAssetId().equals(houseUserAssets.get(i).getAssetId())){
                    houseUserAssets.get(i).setExistRent(true);
                }
            }


            for (HouseUserDecoratePlanRecord decoratePlanRecord:decoratePlanRecordList){
                if (decoratePlanRecord.getAssetId().equals(houseUserAssets.get(i).getAssetId())){
                    //                是否有装修方案
                    if (decoratePlanRecord.getOptionType().equals(HouseUserDecoratePlan.DECORATE_TYPE)){
                        houseUserAssets.get(i).setExistDecorate(true);
//                    是否有团购
                    }else if (decoratePlanRecord.getOptionType().equals(HouseUserDecoratePlan.BULK_TYPE)){
                        houseUserAssets.get(i).setExistBulk(true);
                    }
                }

            }


            for (HouseAssetExchangeRequest houseAssetExchangeRequest:houseAssetExchangeRequestList){
                if (houseUserAssets.get(i).getAssetId().equals(houseAssetExchangeRequest.getAssetId())){
                    houseUserAssets.get(i).setExistExchange(true);
                }
            }


        }
        System.out.println("开始到中间=="+(mid-star));
        System.out.println("中间到结束=="+(System.currentTimeMillis()-mid));

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
            houseUserDecoratePlanRecord.setOptionType(HouseUserDecoratePlan.DECORATE_TYPE);
            List<HouseUserDecoratePlanRecord> houseUserDecoratePlanRecordList =  queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null,houseUserDecoratePlanRecord,null,null,null,null,null);
            if (houseUserDecoratePlanRecordList!=null && houseUserDecoratePlanRecordList.size()>0){
                Long planId =  houseUserDecoratePlanRecordList.get(0).getDecoratePlanId();
                HouseDecoratePlan houseDecoratePlan = queryHouseDecoratePlanDao.queryMasterModel(planId);
                houseAssetRecord.setHouseDecoratePlan(houseDecoratePlan);

//                装修方案是否可改
                houseAssetRecord.setDecorateModifyOption(houseUserDecoratePlanRecordList.get(0).getModifyOption());
            }

        }
        return SuccessTip.create(houseAssetRecord);
    }


    //    添加房子
    @PostMapping("/user/userAsset")
    public Tip createHousePropertyUserUnit(@RequestBody HouseUserAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (entity.getAssetId()==null || "".equals(entity.getAssetId())){
            throw new BusinessException(BusinessCode.BadRequest,"assetId为必填项");
        }
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord,null,null,null,null,null,null);

        /*
        判断这个房子是否有人，没人直接进行增加 有人判断是否是最终用户
         */

        if (houseUserAssetRecordList==null || houseUserAssetRecordList.size()==0){
            entity.setUserId(JWTKit.getUserId());
            Integer affected = 0;
            try {
                affected = houseUserAssetService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
            return SuccessTip.create(affected);
        }else {
            /*
            判读房子是否是自己的
             */
            if (!(houseUserAssetRecordList.get(0).getUserId().equals(JWTKit.getUserId()))){
                /*
                判断是否是最终用户 不是可以进行修改
                 */
                if (!(HouseUserAsset.FINAL_FLAG_CONFIRM.equals(houseUserAssetRecordList.get(0).getFinalFlag()))){
                    entity.setId(houseUserAssetRecordList.get(0).getId());
                    entity.setUserId(JWTKit.getUserId());

                    HouseAssetComplaint complaint = new HouseAssetComplaint();
                    complaint.setUserId(JWTKit.getUserId());
                    complaint.setOldUserId(houseUserAssetRecordList.get(0).getUserId());
                    complaint.setAssetId(houseUserAssetRecordList.get(0).getAssetId());

                    Integer affected = 0;
                    affected = houseUserAssetService.updateMaster(entity);
                    if (affected>0){
                        /*
                        添加产权申述
                         */
                        try {
                            affected += houseAssetComplaintService.createMaster(complaint);
                        } catch (DuplicateKeyException e) {
                            throw new BusinessException(BusinessCode.DuplicateKey);
                        }
                    }
                    return SuccessTip.create(affected);
                }else {
                    throw new BusinessException(BusinessCode.CodeBase,"该资产已被确认");
                }

            }
        }
        return SuccessTip.create();

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
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectById(id);
        if (houseUserAsset!=null && houseUserAsset.getUserId().equals(JWTKit.getUserId())){
            return SuccessTip.create(houseUserAssetService.deleteUserAsset(JWTKit.getUserId(),houseUserAsset.getAssetId()));
        }
        return SuccessTip.create();

    }


}
