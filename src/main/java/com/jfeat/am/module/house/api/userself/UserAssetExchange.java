package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api("HouseAssetExchangeRequest")
@RequestMapping("/api/u/house/houseAssetExchangeRequest/houseAssetExchangeRequests")
public class UserAssetExchange {

    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    HouseUserAssetService houseUserAssetService;


//    ?????????????????????????????????????????????
    @PostMapping
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType, @RequestBody HouseAssetExchangeRequest entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        if (entity.getAssetId() ==null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId????????????");
        }
        if (entity.getTargetAssetRange()==null){
            throw new BusinessException(BusinessCode.BadRequest,"targetAssetRange????????????");
        }
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setUserId(JWTKit.getUserId());
        exchangeRequestRecord.setAssetId(entity.getAssetId());
        List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,exchangeRequestRecord,null,null,null,null,null);
        Integer affected = 0;
        /*
        ????????????????????????????????????????????????????????????????????????????????????
                            ????????????????????????????????????
         */
        if (exchangeRequestRecordList==null || exchangeRequestRecordList.size()==0){
            entity.setUserId(JWTKit.getUserId());
            try {
                affected = houseAssetExchangeRequestService.createMaster(entity);
//            ????????????
                houseAssetExchangeRequestService.assetMachResult(entity, isSameHouseType);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }else {
            entity.setId(exchangeRequestRecordList.get(0).getId());
            entity.setUserId(JWTKit.getUserId());
            entity.setAssetId(exchangeRequestRecordList.get(0).getAssetId());
            affected = houseAssetExchangeRequestService.updateMaster(entity);
            if (affected > 0) {
                houseAssetExchangeRequestService.assetMachResult(entity, isSameHouseType);
            }
        }


        return SuccessTip.create(affected);
    }

    @GetMapping("/demand")
    public Tip getHouseAssetExchangeRequest() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByUserId(JWTKit.getUserId());
        for (HouseAssetExchangeRequest houseAssetExchangeRequest : houseAssetExchangeRequestList) {
            Map<String, Object> houseAssetMap = new HashMap<>();

            HouseAsset houseAsset = queryHouseAssetDao.queryMasterModel(houseAssetExchangeRequest.getAssetId());
            if (houseAsset == null) {
                continue;
            }
            List<String> matchedAssetRange = Arrays.asList(houseAssetExchangeRequest.getTargetAssetRange().split(","));
            List<Long> matchedAssetRangeIds = matchedAssetRange.stream().map(Long::valueOf).collect(Collectors.toList());
            List<HouseAsset> houseAssetList = new ArrayList<>();
            for (Long assetId : matchedAssetRangeIds) {
                HouseAsset matchHouseAsset = queryHouseAssetDao.queryMasterModel(assetId);
                if (matchHouseAsset != null) {
                    houseAssetList.add(matchHouseAsset);
                }
            }
            houseAssetMap.put("matched", houseAssetList);
            houseAssetMap.put("owne", houseAsset);
            list.add(houseAssetMap);
        }
        return SuccessTip.create(list);
    }

    //    ????????????????????????
    @GetMapping("/userAssetExchangeDemand")
    public Tip getUserAssetExchangeDemand(Page<HouseAssetExchangeRequestRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(JWTKit.getUserId());
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        List<HouseAssetModel> houseAssetModels = new ArrayList<>();
        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, null, null, null, null, null);
        for (HouseAssetExchangeRequestRecord exchangeRequestRecord : houseAssetExchangeRequestRecordList) {
            List<String> matchedAssetRange = Arrays.asList(exchangeRequestRecord.getTargetAssetRange().split(","));
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(exchangeRequestRecord.getAssetId());
            houseAssetModel.setExchangeNumber(matchedAssetRange.size());
            houseAssetModels.add(houseAssetModel);
        }
        Page<HouseAssetModel> houseAssetModelPage = new Page<>();
        houseAssetModelPage.setCurrent(pageNum);
        houseAssetModelPage.setSize(pageNum);
        houseAssetModelPage.setRecords(houseAssetModels);
        return SuccessTip.create(houseAssetModelPage);
    }

    //    ????????????
    @GetMapping("/userAllAssetExchangeDemand")
    public Tip getUserAllAssetExchangeDemand(Page<HouseAssetExchangeRequestRecord> page,
                                             @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
//        ??????????????????????????????
        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(JWTKit.getUserId());
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, null, null, null, null, null);

        if (houseAssetExchangeRequestRecordList!=null && houseAssetExchangeRequestRecordList.size()>0){
            JSONObject jsonObject = houseUserAssetService.parseMatchAssetData(houseAssetExchangeRequestRecordList);
            System.out.println(jsonObject);
            JSONArray result = houseUserAssetService.formatAssetMatchResult(jsonObject);
            System.out.println(result);
            return SuccessTip.create(result);
        }
        return SuccessTip.create();

    }

    /*
    ??????????????????????????????
     */

    @GetMapping("/buildingAsset")
    public Tip getHouseAssetExchangeBuilding(@RequestParam(value = "buildingId",required = true) Long buildingId,
                                            @RequestParam(value = "assetId",required = true) Long assetId){

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        if (buildingId==null || "".equals(buildingId)){
            throw new BusinessException(BusinessCode.BadRequest,"building??????");
        }
        if (assetId==null || "".equals(assetId)){
            throw new BusinessException(BusinessCode.BadRequest,"assetId??????");
        }
        /*
        ????????????????????????
         */
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        houseAssetRecord.setAssetType(HouseAsset.ASSET_Type_HOUSE);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,
                null,null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);
        /*
        ????????????????????????
         */
        List<HouseAssetModel> exchangeRequestResult = new ArrayList<>();
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setUserId(JWTKit.getUserId());
        exchangeRequestRecord.setAssetId(assetId);
        List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,exchangeRequestRecord,null,null,null,null,null);
        if (exchangeRequestRecordList!=null && exchangeRequestRecordList.size()==1){
            List<String> matchedAssetRange = Arrays.asList(exchangeRequestRecordList.get(0).getTargetAssetRange().split(","));
            List<Long> matchedAssetRangeIds = matchedAssetRange.stream().map(Long::valueOf).collect(Collectors.toList());

            for (Long id : matchedAssetRangeIds) {
                HouseAssetModel matchHouseAsset = queryHouseAssetDao.queryMasterModel(id);
                if (matchHouseAsset != null) {
                    exchangeRequestResult.add(matchHouseAsset);
                }
            }
        }
        /*
        ????????????????????????
         */
        HouseAssetMatchLogRecord matchLogRecord = new HouseAssetMatchLogRecord();
        matchLogRecord.setOwnerAssetId(assetId);
        matchLogRecord.setOwnerUserId(JWTKit.getUserId());
        List<HouseAssetMatchLogRecord> matchLogRecordList = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(null,matchLogRecord,null,null,null,null,null);

        /*
          ??????????????????????????????????????????????????????????????????????????????????????????
         */

        for (int i=0;i<houseAssetRecordList.size();i++){
            /*
            ??????????????????
             */
            if (houseAssetRecordList.get(i).getId().equals(houseAssetModel.getId())){
                houseAssetRecordList.get(i).setSelf(true);
            }else {
                houseAssetRecordList.get(i).setSelf(false);
            }
            /*
            ??????????????????
             */
            if (houseAssetRecordList.get(i).getDesignModelId().equals(houseAssetModel.getDesignModelId())){
                houseAssetRecordList.get(i).setSameHouseType(true);
            }else {
                houseAssetRecordList.get(i).setSameHouseType(false);
            }

            /*
            ????????????????????????
             */
            for (HouseAssetModel houseAssetDemand:exchangeRequestResult){
                if (houseAssetDemand.getId().equals(houseAssetRecordList.get(i).getId())){
                    houseAssetRecordList.get(i).setMatchDemand(true);
                    break;
                }else {
                    houseAssetRecordList.get(i).setMatchDemand(false);
                }
            }

            /*
            ??????????????????
             */
            for (HouseAssetMatchLogRecord assetMatchLogRecord:matchLogRecordList){
                if (assetMatchLogRecord.getMathchedAssetId().equals(houseAssetRecordList.get(i).getId())){
                    houseAssetRecordList.get(i).setSuccessMatch(true);
                    break;
                }else {
                    houseAssetRecordList.get(i).setSuccessMatch(false);
                }
            }

        }

        return SuccessTip.create(houseAssetRecordList);
    }


    //    ??????????????????
    @GetMapping("/matchResult")
    public Tip getHouseAssetExchangeRequestResult(Page<HouseAssetMatchLogRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setOwnerUserId(JWTKit.getUserId());
        List<HouseAssetMatchLogRecord> houseAssetMatchLogs = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(page, record, null, null, null, null, null);
        for (int i = 0; i < houseAssetMatchLogs.size(); i++) {
            Map<String, Object> houseAssetMap = new HashMap<>();
            HouseAsset ownerHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getOwnerAssetId());
            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getMathchedAssetId());
            houseAssetMatchLogs.get(i).setOwner(ownerHouseAsset);
            houseAssetMatchLogs.get(i).setMatcher(matchedHouseAsset);
        }
        page.setRecords(houseAssetMatchLogs);
        return SuccessTip.create(page);
    }


    //    ??????????????????
    @PutMapping("/{id}")
    public Tip updateHouseAssetExchangeRequest(@PathVariable Long id, @RequestBody HouseAssetExchangeRequest entity, @RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType) {
        System.out.println(JWTKit.getUserId());
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseAssetExchangeRequest houseAssetExchangeRequest = queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByAssetIdAndUserId(id, JWTKit.getUserId());
        entity.setId(houseAssetExchangeRequest.getId());
        entity.setUserId(JWTKit.getUserId());
        entity.setAssetId(id);
        Integer effect = houseAssetExchangeRequestService.updateMaster(entity);
        if (effect > 0) {
            houseAssetExchangeRequestService.assetMachResult(entity, isSameHouseType);
        }
        return SuccessTip.create(effect);
    }


    //    ????????????????????????
    @DeleteMapping("/{id}")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseAssetExchangeRequest houseAssetExchangeRequest = queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByAssetIdAndUserId(id, JWTKit.getUserId());
        Integer effect = houseAssetExchangeRequestService.deleteMaster(houseAssetExchangeRequest.getId());
        effect += queryHouseAssetMatchLogDao.deleteHouseAssetMatchLogByUserIdAndAssetId(id, JWTKit.getUserId());
        return SuccessTip.create(effect);
    }

//    ???????????????????????? ????????????????????????
    @GetMapping("/userAssetExchangeDemand/{assetId}")
    public Tip getUserAssetExchangeDemand(@PathVariable("assetId") Long assetId){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        houseAssetExchangeRequestRecord.setAssetId(assetId);
        houseAssetExchangeRequestRecord.setUserId(JWTKit.getUserId());

        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,houseAssetExchangeRequestRecord,null,null,null,null,null);
        if (houseAssetExchangeRequestRecordList!=null && houseAssetExchangeRequestRecordList.size()==1){
            List<String> matchedAssetRange = Arrays.asList(houseAssetExchangeRequestRecordList.get(0).getTargetAssetRange().split(","));
//            List<Long> matchedAssetRangeIds = matchedAssetRange.stream().map(Long::valueOf).collect(Collectors.toList());
            return SuccessTip.create(matchedAssetRange);
        }
        return SuccessTip.create();
    }

    @GetMapping
    public Tip queryHouseAssetExchangeRequestPage(Page<HouseAssetExchangeRequestRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                  // for tag feature query
                                                  @RequestParam(name = "tag", required = false) String tag,
                                                  // end tag
                                                  @RequestParam(name = "search", required = false) String search,

                                                  @RequestParam(name = "userId", required = false) Long userId,

                                                  @RequestParam(name = "assetId", required = false) Long assetId,

                                                  @RequestParam(name = "targetAssetRange", required = false) String targetAssetRange,

                                                  @RequestParam(name = "targetAssetRangeLimit", required = false) String targetAssetRangeLimit,
                                                  @RequestParam(name = "orderBy", required = false) String orderBy,
                                                  @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//??????????????????????????????????????????
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
//        record.setUserId(userId);
        record.setUserId(JWTKit.getUserId());
        record.setAssetId(assetId);
        record.setTargetAssetRange(targetAssetRange);
        record.setTargetAssetRangeLimit(targetAssetRangeLimit);


        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestPage = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetExchangeRequestPage);
        return SuccessTip.create(page);
    }
}
