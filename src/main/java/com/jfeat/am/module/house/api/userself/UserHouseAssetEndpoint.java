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
    QueryStockTagDao queryStockTagDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    QueryHouseAssetComplaintDao queryHouseAssetComplaintDao;



//    ????????????
    @GetMapping("/community")
    public Tip getHousePropertyCommunityByTenantId() {
        Long tenantId = JWTKit.getOrgId();
        System.out.println(tenantId);
        return SuccessTip.create(queryHousePropertyCommunityDao.queryHouseCommunityByTenantId(tenantId));
    }

//    ????????????????????????
    @PostMapping("/community/userCommunityStatus")
    public Tip updateUserCommunityStatusByUserId(@RequestParam("communityId") Long communityId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
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

    //    ??????????????????
    @GetMapping("/building")
    public Tip getBuildingInfo() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
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

//    ???????????????????????????
    @GetMapping("/asset/{buildingId}")
    public Tip getAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }

        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord>  houseAssetList  = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);
//        List<HouseAsset> houseAssetList = queryHouseAssetDao.queryHouseAssetByBuildingId(buildingId);

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null);

        for (int i = 0; i < houseAssetList.size(); i++) {
//            ????????????????????????
            for (int j = 0;j<recordList.size();j++){
                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())){
                    houseAssetList.get(i).setExistUser(true);

                      /*
                ?????????????????????
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
                ??????????????????
                 */
                    if (recordList.get(j).getUserId().equals(JWTKit.getUserId())){
                        houseAssetList.get(i).setMyselfAsset(true);
                    }
                }


            }
        }

        return SuccessTip.create(houseAssetList);
    }


    //    ??????????????????????????? ???????????????
    @GetMapping("/userInfoAsset/{buildingId}")
    public Tip getUserInfoAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord>  houseAssetList  = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null);

        Long uStart = System.currentTimeMillis();
        for (int i = 0; i < houseAssetList.size(); i++) {
//            ????????????????????????
            for (int j = 0;j<recordList.size();j++){
                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())){

//                    ??????????????????
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

//    ??????????????????
    @GetMapping("/asset/details/{assetId}")
    public Tip getAssetDetails(@PathVariable("assetId") Long assetId){

        return SuccessTip.create(queryHouseAssetDao.queryHouseAssetDetails(assetId));
    }

//    ????????????????????????
    @GetMapping("/user/userAsset")
    public Tip getUserUnite() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        userAssetRecord.setUserId(JWTKit.getUserId());
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.findHouseUserAssetPage(null,userAssetRecord,null,null,null,null,null);

        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null,houseRentAssetRecord,null,null,null,null,null);


        for (int i=0;i<houseUserAssets.size();i++){
            //        ??????????????????
            String address = houseUserAssets.get(i).getAddress();
            String buildingCode = houseUserAssets.get(i).getBuildingCode();
            String number = houseUserAssets.get(i).getRoomNumber();
            houseUserAssets.get(i).setAddressDetail("".concat(address).concat(" ").concat(buildingCode).concat(" ").concat(number));

//            ?????? ?????? ?????? ???????????????
            if (houseUserAssets.get(i).getTrust()!=null && houseUserAssets.get(i).getTrust()>0){
                houseUserAssets.get(i).setExistTrust(true);
            }

//            ????????????
            for (HouseRentAssetRecord record:houseRentAssetRecordList){
                if (record.getAssetId().equals(houseUserAssets.get(i).getAssetId())){
                    houseUserAssets.get(i).setExistRent(true);
                }
            }


            HouseUserDecoratePlanRecord userDecoratePlanRecord = new HouseUserDecoratePlanRecord();
            userDecoratePlanRecord.setUserId(JWTKit.getUserId());
            userDecoratePlanRecord.setAssetId(houseUserAssets.get(i).getAssetId());

            List<HouseUserDecoratePlanRecord> decoratePlanRecordList = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null,userDecoratePlanRecord,null,null,null,null,null);
            for (HouseUserDecoratePlanRecord decoratePlanRecord:decoratePlanRecordList){
//                ?????????????????????
                if (decoratePlanRecord.getOptionType().equals(HouseUserDecoratePlan.DECORATE_TYPE)){
                    houseUserAssets.get(i).setExistDecorate(true);
//                    ???????????????
                }else if (decoratePlanRecord.getOptionType().equals(HouseUserDecoratePlan.BULK_TYPE)){
                    houseUserAssets.get(i).setExistBulk(true);
                }
            }

//            ????????????????????????
            HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
            exchangeRequestRecord.setAssetId(houseUserAssets.get(i).getAssetId());
            exchangeRequestRecord.setUserId(JWTKit.getUserId());
            List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,exchangeRequestRecord,null,null,null,null,null);
            if (houseAssetExchangeRequestList.size()>0){
                houseUserAssets.get(i).setExistExchange(true);
            }

        }

        /*
        ????????????????????????
         */
        List<HouseUserAssetRecord> houseAssetRecordList = userCommunityAsset.getCommunityAsset(JWTKit.getUserId(),houseUserAssets);

        return SuccessTip.create(houseAssetRecordList);
    }

//    ??????asset ????????????
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

//                ????????????????????????
                houseAssetRecord.setDecorateModifyOption(houseUserDecoratePlanRecordList.get(0).getModifyOption());
            }

        }
        return SuccessTip.create(houseAssetRecord);
    }


    //    ????????????
    @PostMapping("/user/userAsset")
    public Tip createHousePropertyUserUnit(@RequestBody HouseUserAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }

        if (entity.getAssetId()==null || "".equals(entity.getAssetId())){
            throw new BusinessException(BusinessCode.BadRequest,"assetId????????????");
        }
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord,null,null,null,null,null);

        /*
        ????????????????????????????????????????????????????????? ?????????????????????????????????
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
            ??????????????????????????????
             */
            if (!(houseUserAssetRecordList.get(0).getUserId().equals(JWTKit.getUserId()))){
                /*
                ??????????????????????????? ????????????????????????
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
                        ??????????????????
                         */
                        try {
                            affected += houseAssetComplaintService.createMaster(complaint);
                        } catch (DuplicateKeyException e) {
                            throw new BusinessException(BusinessCode.DuplicateKey);
                        }
                    }
                    return SuccessTip.create(affected);
                }else {
                    throw new BusinessException(BusinessCode.CodeBase,"?????????????????????");
                }

            }
        }
        return SuccessTip.create();

    }

    //    ?????????????????????
    @BusinessLog(name = "HousePropertyUserUnit", value = "update HousePropertyUserUnit")
    @PutMapping("/user/userAsset/{id}")
    @ApiOperation(value = "?????? HousePropertyUserUnit", response = HouseUserAsset.class)
    public Tip updateHousePropertyUserUnit(@PathVariable Long id, @RequestBody HouseUserAsset entity) {
        entity.setId(id);
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        entity.setUserId(JWTKit.getUserId());
        return SuccessTip.create(houseUserAssetService.updateMaster(entity));
    }

    //    ??????????????????
    @BusinessLog(name = "HousePropertyUserUnit", value = "delete HousePropertyUserUnit")
    @DeleteMapping("/user/userAsset/{id}")
    @ApiOperation("?????? HousePropertyUserUnit")
    public Tip deleteHousePropertyUserUnit(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        return SuccessTip.create(houseUserAssetService.deleteMaster(id));
    }


}
