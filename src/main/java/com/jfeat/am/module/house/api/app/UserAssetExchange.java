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
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetExchangeRequestMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
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

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;


//    新建或者修改资产交换记录并匹配
    @PostMapping
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType, @RequestBody HouseAssetExchangeRequest entity) {

        Long userId = JWTKit.getUserId();

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId() ==null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId为必填项");
        }
        if (entity.getTargetAssetRange()==null){
            throw new BusinessException(BusinessCode.BadRequest,"targetAssetRange为必填项");
        }
        entity.setUserId(userId);

        return SuccessTip.create(houseAssetExchangeRequestService.addHouseAssetExchangeRequest(entity));
    }

//    获取用户的换房需求，一个房子对应多个房子 返回前端
    @GetMapping("/demand")
    public Tip getHouseAssetExchangeRequest() {
        return SuccessTip.create();
    }

    //    用户资产交换信息
    @GetMapping("/userAssetExchangeDemand")
    public Tip getUserAssetExchangeDemand(Page<HouseAssetExchangeRequestRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return SuccessTip.create();
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
        record.setCommunityId(communityId);
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList  = queryHouseAssetExchangeRequestDao.queryExchangeRequestGroupByAssetId(page,record);

        List<HouseAsset> targetList = queryHouseAssetExchangeRequestDao.queryExchangeTargetList(null,record);

        for (HouseAssetExchangeRequestRecord exchangeRequestRecord:houseAssetExchangeRequestRecordList){
            for (HouseAsset houseAsset:targetList){
                if (exchangeRequestRecord.getAssetId().equals(houseAsset.getExchangeRequestId())){
                    if (exchangeRequestRecord.getTargetAssetList()==null || exchangeRequestRecord.getTargetAssetList().size()<=0){
                        List<HouseAsset> houseAssetList = new ArrayList<>();
                        houseAssetList.add(houseAsset);
                        exchangeRequestRecord.setTargetAssetList(houseAssetList);
                    }else {
                        List<HouseAsset> houseAssetList = exchangeRequestRecord.getTargetAssetList();
                        houseAssetList.add(houseAsset);
                        exchangeRequestRecord.setTargetAssetList(houseAssetList);
                    }
                }
            }
        }

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
        return SuccessTip.create();
    }


    //    删除资产交换请求
    @DeleteMapping("/{id}")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {

        return SuccessTip.create();
    }

//    获取用户某间房间 换房需求房源数组
    @GetMapping("/userAssetExchangeDemand/{assetId}")
    public Tip getUserAssetExchangeDemand(@PathVariable("assetId") Long assetId){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        QueryWrapper<HouseAssetExchangeRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAssetExchangeRequest.USER_ID,JWTKit.getUserId()).eq(HouseAssetExchangeRequest.ASSET_ID,assetId);
        List<HouseAssetExchangeRequest> exchangeRequests = houseAssetExchangeRequestMapper.selectList(queryWrapper);
        if (exchangeRequests!=null && exchangeRequests.size()>0){
            List<Long> targetAssetId = exchangeRequests.stream().map(HouseAssetExchangeRequest::getTargetAsset).collect(Collectors.toList());
            List<String> targetAssetIdStr  = targetAssetId.stream().map(String::valueOf).collect(Collectors.toList());
            return SuccessTip.create(targetAssetIdStr);
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

        return SuccessTip.create();
    }


    /**
     * 给定房号id 找出匹配到的房屋信息
     * @param assetId
     * @return
     */
    @GetMapping("/getAssetMatchedInfo")
    public Tip getAssetMatchedByAssetId(@RequestParam("assetId") Long assetId){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        QueryWrapper<HouseAssetMatchLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAssetMatchLog.OWNER_ASSET_ID,assetId).eq(HouseAssetMatchLog.OWNER_USER_ID,JWTKit.getUserId());
        List<HouseAssetMatchLog> logList = houseAssetMatchLogMapper.selectList(queryWrapper);

        List<Long> matchedAssetIds = new ArrayList<>();
        for (HouseAssetMatchLog houseAssetMatchLog:logList){
            matchedAssetIds.add(houseAssetMatchLog.getMathchedAssetId());
        }

        if (matchedAssetIds!=null && matchedAssetIds.size()>0){
            return SuccessTip.create(queryHouseAssetDao.getHouseAssetList(matchedAssetIds));
        }

        return SuccessTip.create();
    }




}
