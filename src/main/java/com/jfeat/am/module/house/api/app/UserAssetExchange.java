package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;


//    新建或者修改资产交换记录并匹配
    @PostMapping
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType, @RequestBody HouseAssetExchangeRequest entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId() ==null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId为必填项");
        }
        if (entity.getTargetAssetRange()==null){
            throw new BusinessException(BusinessCode.BadRequest,"targetAssetRange为必填项");
        }
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setUserId(JWTKit.getUserId());
        exchangeRequestRecord.setAssetId(entity.getAssetId());
        List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,exchangeRequestRecord,null,null,null,null,null);
        Integer affected = 0;
        /*
        判读是否存在匹配记录，如果不存在就创建匹配记录，然后匹配
                            如果存在就行修改匹配范围
         */
        if (exchangeRequestRecordList==null || exchangeRequestRecordList.size()==0){
            entity.setUserId(JWTKit.getUserId());
            try {
                affected = houseAssetExchangeRequestService.createMaster(entity);
//            匹配请求
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

//    获取用户的换房需求，一个房子对应多个房子 返回前端
    @GetMapping("/demand")
    public Tip getHouseAssetExchangeRequest() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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

    //    用户资产交换信息
    @GetMapping("/userAssetExchangeDemand")
    public Tip getUserAssetExchangeDemand(Page<HouseAssetExchangeRequestRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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

    //    换房记录
    @GetMapping("/userAllAssetExchangeDemand")
    public Tip getUserAllAssetExchangeDemand(Page<HouseAssetExchangeRequestRecord> page,
                                             @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

//        获取当前小区状态 用来过滤当前小区的匹配信息
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


//        获取用户全部置换记录
        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(JWTKit.getUserId());
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPageFilterCommunity(page, record,communityId);

        if (houseAssetExchangeRequestRecordList!=null && houseAssetExchangeRequestRecordList.size()>0){
//            将列表解析成对应json格式
            JSONObject jsonObject = houseUserAssetService.parseMatchAssetData(houseAssetExchangeRequestRecordList);
            System.out.println(jsonObject);
//            格式化返回前端
            JSONArray result = houseUserAssetService.formatAssetMatchResult(jsonObject);
            return SuccessTip.create(result);
        }
        return SuccessTip.create();

    }

    /*
    选择房屋交换匹配数据
     */

    @GetMapping("/buildingAsset")
    public Tip getHouseAssetExchangeBuilding(@RequestParam(value = "buildingId",required = true) Long buildingId,
                                            @RequestParam(value = "assetId",required = true) Long assetId){

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (buildingId==null || "".equals(buildingId)){
            throw new BusinessException(BusinessCode.BadRequest,"building为空");
        }
        if (assetId==null || "".equals(assetId)){
            throw new BusinessException(BusinessCode.BadRequest,"assetId为空");
        }
        /*
        查询楼栋全部房屋
         */
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        houseAssetRecord.setAssetType(HouseAsset.ASSET_Type_HOUSE);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,
                null,null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);
        QueryWrapper<HouseUserAsset> houseAssetQueryWrapper = new QueryWrapper<>();
        houseAssetQueryWrapper.eq(HouseUserAsset.USER_ID,JWTKit.getUserId());

        List<HouseUserAsset> houseUserAssetList = houseUserAssetMapper.selectList(houseAssetQueryWrapper);
        List<Long> userAssetIds = new ArrayList<>();
        for (HouseUserAsset userAsset:houseUserAssetList){
            userAssetIds.add(userAsset.getAssetId());
        }
        /*
        查询匹配需求记录
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
        查询匹配成功记录
         */
        HouseAssetMatchLogRecord matchLogRecord = new HouseAssetMatchLogRecord();
        matchLogRecord.setOwnerAssetId(assetId);
        matchLogRecord.setOwnerUserId(JWTKit.getUserId());
        List<HouseAssetMatchLogRecord> matchLogRecordList = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(null,matchLogRecord,null,null,null,null,null);

        /*
          遍历房屋，标记相同户型、自己房屋、匹配成功房屋、匹配需求房屋
         */

        for (int i=0;i<houseAssetRecordList.size();i++){
            /*
            是否是自己的
             */
            if (userAssetIds.contains(houseAssetRecordList.get(i).getId())){
                houseAssetRecordList.get(i).setSelf(true);
            }else {
                houseAssetRecordList.get(i).setSelf(false);
            }
            /*
            是否相同户型
             */
            if (houseAssetRecordList.get(i).getDesignModelId()!=null && houseAssetRecordList.get(i).getDesignModelId().equals(houseAssetModel.getDesignModelId())){
                houseAssetRecordList.get(i).setSameHouseType(true);
            }else {
                houseAssetRecordList.get(i).setSameHouseType(false);
            }

            /*
            标记交换需求房屋
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
            标记匹配成功
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


        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(buildingId);
        Integer unitCount = 0;
        if (housePropertyBuilding!=null && housePropertyBuilding.getUnits()!=null){
            unitCount=housePropertyBuilding.getUnits();
        }
        List<BigDecimal> unitAreas = new ArrayList<>();

        for (int i=0;i<unitCount;i++){
            if (houseAssetRecordList.get(i).getArea()!=null){
                unitAreas.add(houseAssetRecordList.get(i).getArea());
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("header",unitAreas);
        jsonObject.put("data",houseAssetRecordList);
        if (unitCount!=unitAreas.size()){
            jsonObject.put("msg","数据有误");
        }

        return SuccessTip.create(jsonObject);
    }


    //    请求交换结果
    @GetMapping("/matchResult")
    public Tip getHouseAssetExchangeRequestResult(Page<HouseAssetMatchLogRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setOwnerUserId(JWTKit.getUserId());
        List<HouseAssetMatchLogRecord> houseAssetMatchLogs = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(page, record, null, null, null, null, null);
        for (int i = 0; i < houseAssetMatchLogs.size(); i++) {
            /*
            添加 匹配双方的房屋信息
             */
            HouseAsset ownerHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getOwnerAssetId());
            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getMathchedAssetId());
            houseAssetMatchLogs.get(i).setOwner(ownerHouseAsset);
            houseAssetMatchLogs.get(i).setMatcher(matchedHouseAsset);
        }
        page.setRecords(houseAssetMatchLogs);
        return SuccessTip.create(page);
    }


    //    更新房屋请求
    @PutMapping("/{id}")
    public Tip updateHouseAssetExchangeRequest(@PathVariable Long id, @RequestBody HouseAssetExchangeRequest entity, @RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType) {
        System.out.println(JWTKit.getUserId());
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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


    //    删除资产交换请求
    @DeleteMapping("/{id}")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseAssetExchangeRequest houseAssetExchangeRequest = queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByAssetIdAndUserId(id, JWTKit.getUserId());
        Integer effect = houseAssetExchangeRequestService.deleteMaster(houseAssetExchangeRequest.getId());
        effect += queryHouseAssetMatchLogDao.deleteHouseAssetMatchLogByUserIdAndAssetId(id, JWTKit.getUserId());
        return SuccessTip.create(effect);
    }

//    获取用户某间房间 换房需求房源数组
    @GetMapping("/userAssetExchangeDemand/{assetId}")
    public Tip getUserAssetExchangeDemand(@PathVariable("assetId") Long assetId){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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

    /*
    换房需求记录列表
     */
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
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
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
