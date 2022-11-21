package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.advertisement.services.service.TenantUtilsService;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseTenantMenuService;
import com.jfeat.am.module.house.services.domain.service.HouseUnlikeLogService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.module.blacklist.services.domain.service.EndUserBlacklistService;
import com.jfeat.poi.agent.util.IdWorker;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.Api;
import org.apache.poi.ss.formula.functions.T;
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

    @Resource
    HouseUnlikeLogService houseUnlikeLogService;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseUnlikeLogDao queryHouseUnlikeLogDao;


    @Resource
    HouseTenantMenuService houseTenantMenuService;

    @Resource
    EndUserBlacklistService endUserBlacklistService;


    @Resource
    HouseFloorExchangeRequestMapper houseFloorExchangeRequestMapper;


    //    新建或者修改资产交换记录并匹配
    @PostMapping
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType, @RequestBody HouseAssetExchangeRequest entity) {

        Long userId = JWTKit.getUserId();

        if (endUserBlacklistService.isUserShield(userId)){
            throw new BusinessException(BusinessCode.CodeBase,"已被拉黑");
        }

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为必填项");
        }
        if (entity.getTargetAssetRange() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "targetAssetRange为必填项");
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
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                             @RequestParam(name = "isAuto",required = false) Boolean isAuto) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

//        获取当前小区状态 用来过滤当前小区的匹配信息
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId == null) {
            return SuccessTip.create();
        }


//        获取用户全部置换记录
        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(JWTKit.getUserId());
        record.setCommunityId(communityId);
        if (isAuto!=null && isAuto){
            record.setAutoGenerateStatus(true);
        }
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList = queryHouseAssetExchangeRequestDao.queryExchangeRequestGroupByAssetId(page, record);

//        获取目标信息
        List<HouseAsset> targetList = queryHouseAssetExchangeRequestDao.queryExchangeTargetList(null, record);

        for (HouseAssetExchangeRequestRecord exchangeRequestRecord : houseAssetExchangeRequestRecordList) {
            for (HouseAsset houseAsset : targetList) {
                if (exchangeRequestRecord.getAssetId().equals(houseAsset.getExchangeRequestId())) {
                    if (exchangeRequestRecord.getTargetAssetList() == null || exchangeRequestRecord.getTargetAssetList().size() <= 0) {
                        List<HouseAsset> houseAssetList = new ArrayList<>();
                        houseAssetList.add(houseAsset);
                        exchangeRequestRecord.setTargetAssetList(houseAssetList);
                    } else {
                        List<HouseAsset> houseAssetList = exchangeRequestRecord.getTargetAssetList();
                        houseAssetList.add(houseAsset);
                        exchangeRequestRecord.setTargetAssetList(houseAssetList);
                    }
                }
            }
        }




        if (houseAssetExchangeRequestRecordList != null && houseAssetExchangeRequestRecordList.size() > 0) {
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
    public Tip getHouseAssetExchangeBuilding(@RequestParam(value = "userId", required = false) Long userId,
                                             @RequestParam(value = "buildingId", required = true) Long buildingId,
                                             @RequestParam(value = "assetId", required = true) Long assetId) {
        if (userId == null) {
            userId = JWTKit.getUserId();
            if (userId == null) {
                throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
            }
        }
        if (buildingId == null || "".equals(buildingId)) {
            throw new BusinessException(BusinessCode.BadRequest, "building为空");
        }
        if (assetId == null || "".equals(assetId)) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为空");
        }

//        获取换房功能条件
        HouseMenuRecord record1 = new HouseMenuRecord();
        record1.setType("exchangeAsset");
        List<HouseMenuRecord> secondaryMenu = houseTenantMenuService.getSecondaryMenu(userId, record1);

        Map<String, Integer> map = new HashMap<>();
        for (HouseMenuRecord houseMenuRecord : secondaryMenu) {
            map.put(houseMenuRecord.getComponent(), houseMenuRecord.getEnabled());
        }


//        别人想要的

        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(userId);

        List<HouseAssetExchangeRequestRecord> recordList = queryHouseAssetExchangeRequestDao.queryOptionExchangeRequestList(null, record, null);


        /*
        查询楼栋全部房屋
         */
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        houseAssetRecord.setAssetType(HouseAsset.ASSET_Type_HOUSE);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null, houseAssetRecord, null, null, null,
                null, null);


//        查询交换房子信息
        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);


        //        查询自己有什么房子
        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        userAssetRecord.setBuildingId(buildingId);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);


        /*
        查询匹配需求记录
         */
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setAssetId(assetId);
        List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null, exchangeRequestRecord, null, null, null, null, null);


        /*
        查询匹配成功记录
         */
        HouseAssetMatchLogRecord matchLogRecord = new HouseAssetMatchLogRecord();
        matchLogRecord.setOwnerAssetId(assetId);
        matchLogRecord.setOwnerUserId(userId);
        List<HouseAssetMatchLogRecord> matchLogRecordList = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(null, matchLogRecord, null, null, null, null, null);

        /*
          遍历房屋，标记相同户型、自己房屋、匹配成功房屋、匹配需求房屋
         */

        for (int i = 0; i < houseAssetRecordList.size(); i++) {
            /*
            是否是自己的
             */
            for (HouseUserAssetRecord houseUserAssetRecord : houseUserAssetRecordList) {
                if (houseUserAssetRecord.getAssetId().equals(houseAssetRecordList.get(i).getId())) {
                    if (houseUserAssetRecord.getUserId().equals(userId)) {
                        houseAssetRecordList.get(i).setSelf(true);
                    }
                    if (houseUserAssetRecord.getUnlike().equals(HouseUserAsset.UNLIKE_STATUS_UNLIKE)) {
                        houseAssetRecordList.get(i).setUnlikeStatus(true);
                    }

                }

            }

            for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord : recordList) {
                if (houseAssetExchangeRequestRecord.getAssetId().equals(houseAssetRecordList.get(i).getId())) {
                    houseAssetRecordList.get(i).setOptionalExchangeStatus(true);
                }
            }

            /*
            是否相同户型
             */
            if (houseAssetRecordList.get(i).getDesignModelId() != null && houseAssetRecordList.get(i).getDesignModelId().equals(houseAssetModel.getDesignModelId())) {
                houseAssetRecordList.get(i).setSameHouseType(true);
            } else {
                houseAssetRecordList.get(i).setSameHouseType(false);
            }

            if (map.get("unlimited") != null && !map.get("unlimited").equals(1)) {

                if (map.get("unitId").equals(1) && houseAssetRecordList.get(i).getUnitId().equals(houseAssetModel.getUnitId())) {
                    houseAssetRecordList.get(i).setExchangeAsset(true);
                }

                if (map.get("houseType").equals(1) && houseAssetRecordList.get(i).getDesignModelId().equals(houseAssetModel.getDesignModelId())) {
                    houseAssetRecordList.get(i).setExchangeAsset(true);
                }

                if (map.get("area").equals(1) && houseAssetRecordList.get(i).getArea().equals(houseAssetModel.getArea())) {
                    houseAssetRecordList.get(i).setExchangeAsset(true);
                }

                if (map.get("issue").equals(1) && houseAssetRecordList.get(i).getIssue().equals(houseAssetModel.getIssue())) {
                    houseAssetRecordList.get(i).setExchangeAsset(true);
                }
            } else {
                houseAssetRecordList.get(i).setExchangeAsset(true);
            }

            /*
            标记交换需求房屋
             */
            for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord : exchangeRequestRecordList) {
                if (houseAssetExchangeRequestRecord.getTargetAsset().equals(houseAssetRecordList.get(i).getId())) {
                    houseAssetRecordList.get(i).setMatchDemand(true);
                    break;
                } else {
                    houseAssetRecordList.get(i).setMatchDemand(false);
                }
            }

            /*
            标记匹配成功
             */
            for (HouseAssetMatchLogRecord assetMatchLogRecord : matchLogRecordList) {
                if (assetMatchLogRecord.getMathchedAssetId().equals(houseAssetRecordList.get(i).getId())) {
                    houseAssetRecordList.get(i).setSuccessMatch(true);
                    break;
                } else {
                    houseAssetRecordList.get(i).setSuccessMatch(false);
                }
            }

        }


        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(buildingId);
        Integer unitCount = 0;
        if (housePropertyBuilding != null && housePropertyBuilding.getUnits() != null) {
            unitCount = housePropertyBuilding.getUnits();
        }
        List<BigDecimal> unitAreas = new ArrayList<>();

        for (int i = 0; i < unitCount; i++) {
            if (houseAssetRecordList.get(i).getArea() != null) {
                unitAreas.add(houseAssetRecordList.get(i).getRealArea());
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("header", unitAreas);
        jsonObject.put("data", houseAssetRecordList);
        if (unitCount != unitAreas.size()) {
            jsonObject.put("msg", "数据有误");
        }

        return SuccessTip.create(jsonObject);

    }


    //    请求交换结果
    @GetMapping("/matchResult")
    public Tip getHouseAssetExchangeRequestResult(Page<HouseAssetMatchLogRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "1000") Integer pageSize) {

        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        page.setSize(pageSize);
        page.setPages(pageNum);
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

        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        Integer affect = 0;
        QueryWrapper<HouseAssetExchangeRequest> exchangeRequestQueryWrapper = new QueryWrapper<>();
        exchangeRequestQueryWrapper.eq(HouseAssetExchangeRequest.ASSET_ID, id);
        affect += houseAssetExchangeRequestMapper.delete(exchangeRequestQueryWrapper);

        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_ASSET_ID, id).or().eq(HouseAssetMatchLog.MATHCHED_ASSET_ID, id);
        affect += houseAssetMatchLogMapper.delete(matchLogQueryWrapper);
        return SuccessTip.create(affect);
    }

    //    获取用户某间房间 换房需求房源数组
    @GetMapping("/userAssetExchangeDemand/{assetId}")
    public Tip getUserAssetExchangeDemand(@PathVariable("assetId") Long assetId, @RequestParam(value = "userId", required = false) Long userId) {

        if (userId == null) {
            userId = JWTKit.getUserId();
            if (userId == null) {
                throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
            }
        }
        QueryWrapper<HouseAssetExchangeRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAssetExchangeRequest.USER_ID, userId).eq(HouseAssetExchangeRequest.ASSET_ID, assetId);
        List<HouseAssetExchangeRequest> exchangeRequests = houseAssetExchangeRequestMapper.selectList(queryWrapper);
        if (exchangeRequests != null && exchangeRequests.size() > 0) {
            List<Long> targetAssetId = exchangeRequests.stream().map(HouseAssetExchangeRequest::getTargetAsset).collect(Collectors.toList());
            List<String> targetAssetIdStr = targetAssetId.stream().map(String::valueOf).collect(Collectors.toList());
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
     *
     * @param assetId
     * @return
     */
    @GetMapping("/getAssetMatchedInfo")
    public Tip getAssetMatchedByAssetId(@RequestParam("assetId") Long assetId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        QueryWrapper<HouseAssetMatchLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAssetMatchLog.OWNER_ASSET_ID, assetId).eq(HouseAssetMatchLog.OWNER_USER_ID, JWTKit.getUserId());
        List<HouseAssetMatchLog> logList = houseAssetMatchLogMapper.selectList(queryWrapper);

        List<Long> matchedAssetIds = new ArrayList<>();
        for (HouseAssetMatchLog houseAssetMatchLog : logList) {
            matchedAssetIds.add(houseAssetMatchLog.getMathchedAssetId());
        }

        if (matchedAssetIds != null && matchedAssetIds.size() > 0) {
            return SuccessTip.create(queryHouseAssetDao.getHouseAssetList(matchedAssetIds));
        }

        return SuccessTip.create();
    }


    //    被人想要
    @GetMapping("/optionExchangeRequestList")
    public Tip optionExchangeRequestList(Page<HouseAssetExchangeRequestRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(name = "search", required = false) String search) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(userId);

        List<HouseAssetExchangeRequestRecord> recordList = queryHouseAssetExchangeRequestDao.queryOptionExchangeRequestList(page, record, search);
        page.setRecords(recordList);
        return SuccessTip.create(page);
    }


    //    房东确认交换房屋
    @PostMapping("/confirmExchangeAsset")
    public Tip confirmExchangeAsset(@RequestBody List<HouseAssetExchangeRequest> exchangeRequestList) {

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        if (endUserBlacklistService.isUserShield(userId)){
            throw new BusinessException(BusinessCode.CodeBase,"已被拉黑");
        }

        List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = new ArrayList<>();
        for (HouseAssetExchangeRequest houseAssetExchangeRequest : exchangeRequestList) {
            HouseAssetExchangeRequest record = new HouseAssetExchangeRequest();
            record.setUserId(JWTKit.getUserId());
            record.setAssetId(houseAssetExchangeRequest.getTargetAsset());
            record.setTargetAsset(houseAssetExchangeRequest.getAssetId());
            houseAssetExchangeRequestList.add(record);
        }

        return SuccessTip.create(houseAssetExchangeRequestService.confirmExchangeAsset(houseAssetExchangeRequestList));
    }

    //    选择不喜欢的房子请求匹配
    @PostMapping("/selectUnlikeAsset/{id}")
    public Tip selectUnlikeAssetExchange(@PathVariable("id") Long id) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (houseUserAssetModel != null && houseUserAssetModel.getCommunityId() != null) {

//            查看全部交货记录
            QueryWrapper<HouseAssetExchangeRequest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseAssetExchangeRequest.TARGET_ASSET, houseUserAssetModel.getAssetId());
            List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = houseAssetExchangeRequestMapper.selectList(queryWrapper);


//            我的全部房子
            HouseUserAssetRecord record = new HouseUserAssetRecord();
            record.setCommunityId(houseUserAssetModel.getCommunityId());
            record.setUserId(userId);
            List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, record, null, null, null, null, null);


            if (houseUserAssetRecordList != null && houseUserAssetRecordList.size() > 0) {
                List<HouseAssetExchangeRequest> exchangeRequestList = new ArrayList<>();


                for (HouseUserAssetRecord houseUserAssetRecord : houseUserAssetRecordList) {
                    if (houseUserAssetRecord.getLocked().equals(HouseUserAsset.LOCKED_STATUS_UNLOCKED)) {

                        if (houseAssetExchangeRequestList != null && houseAssetExchangeRequestList.size() > 0) {
                            if (houseAssetExchangeRequestList.stream().filter(item -> item.getAssetId().equals(houseUserAssetRecord.getAssetId())).findAny().isPresent()) {
                                continue;
                            }
                        }
                        HouseAssetExchangeRequest houseAssetExchangeRequest = new HouseAssetExchangeRequest();
                        houseAssetExchangeRequest.setUserId(userId);
                        houseAssetExchangeRequest.setAssetId(houseUserAssetRecord.getAssetId());
                        houseAssetExchangeRequest.setTargetAsset(houseUserAssetModel.getAssetId());
                        exchangeRequestList.add(houseAssetExchangeRequest);
                    }

                }

                if (exchangeRequestList != null && exchangeRequestList.size() > 0) {
                    return SuccessTip.create(houseAssetExchangeRequestService.batchAddExchangeRequest(exchangeRequestList));
                }


            }
        }

        return SuccessTip.create(0);
    }


    //    选择不喜欢的房子请求匹配
    @PostMapping("/selectUnlikeAssetList")
    public Tip selectUnlikeAssetExchangeList(@RequestBody List<HouseUserAsset> userAssetList) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        if (endUserBlacklistService.isUserShield(userId)){
            throw new BusinessException(BusinessCode.CodeBase,"已被拉黑");
        }

        Long communityId = userCommunityAsset.getUserCommunityStatus(userId);
        if (communityId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有找到小区");
        }

        List<Long> unlikeAssetIds = userAssetList.stream().map(HouseUserAsset::getAssetId).collect(Collectors.toList());
        HouseAssetRecord assetRecord = new HouseAssetRecord();
        assetRecord.setCommunityId(communityId);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.batchQueryAsset(null, assetRecord, unlikeAssetIds);

        //            我的全部房子
        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setCommunityId(communityId);
        record.setUserId(userId);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, record, null, null, null, null, null);


        if (userAssetList != null && userAssetList.size() > 0) {

//            查看全部交货记录
            QueryWrapper<HouseAssetExchangeRequest> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(HouseAssetExchangeRequest.TARGET_ASSET, unlikeAssetIds);
            List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = houseAssetExchangeRequestMapper.selectList(queryWrapper);

            List<HouseAssetExchangeRequest> exchangeRequestList = new ArrayList<>();
            List<HouseUnlikeLog> houseUnlikeLogList = new ArrayList<>();

            for (HouseAssetRecord houseAssetRecord : houseAssetRecordList) {

                if (houseUserAssetRecordList != null && houseUserAssetRecordList.size() > 0) {

                    for (HouseUserAssetRecord houseUserAssetRecord : houseUserAssetRecordList) {
                        if (houseUserAssetRecord.getLocked().equals(HouseUserAsset.LOCKED_STATUS_UNLOCKED)) {

//                            如果需求记录不等于空 就过滤那些已经在需求记录的房子
                            if (houseAssetExchangeRequestList != null && houseAssetExchangeRequestList.size() > 0) {
                                if (houseAssetExchangeRequestList.stream().filter(item -> item.getAssetId().equals(houseUserAssetRecord.getAssetId())).findAny().isPresent()) {
                                    continue;
                                }
                            }
                            HouseAssetExchangeRequest houseAssetExchangeRequest = new HouseAssetExchangeRequest();
                            houseAssetExchangeRequest.setUserId(userId);
                            houseAssetExchangeRequest.setAssetId(houseUserAssetRecord.getAssetId());
                            houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                            exchangeRequestList.add(houseAssetExchangeRequest);
                        }

                    }
                }


//                    添加点击不喜欢记录
                HouseUnlikeLog houseUnlikeLog = new HouseUnlikeLog();
                houseUnlikeLog.setAssetId(houseAssetRecord.getId());
                houseUnlikeLog.setUserId(userId);

                houseUnlikeLogList.add(houseUnlikeLog);

            }
            if (exchangeRequestList != null && exchangeRequestList.size() > 0) {
                return SuccessTip.create(houseAssetExchangeRequestService.addUnlikeAssetExchangeRequest(exchangeRequestList, houseUnlikeLogList));
            }


        }

        return SuccessTip.create(0);
    }

    @DeleteMapping("/cancelUnlikeLog/{id}")
    public Tip cancelUnlikeLog(@PathVariable("id") Long assetId) {

        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        return SuccessTip.create(houseUnlikeLogService.deleteUnlikeLog(userId, assetId));

    }


    //    获取指定小区不喜欢房产列表
    @GetMapping("/unlikeList")
    public Tip getUnlikeAssetList(Page<HouseUserAssetRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(name = "search", required = false) String search,
                                  @RequestParam(value = "communityId", required = false) Long communityId) {

        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        if (communityId == null) {
            communityId = userCommunityAsset.getUserCommunityStatus(userId);
            if (communityId == null) {
                throw new BusinessException(BusinessCode.NoPermission, "没有找到小区");
            }
        }

        HouseUnlikeLogRecord unlikeLogRecord = new HouseUnlikeLogRecord();
        unlikeLogRecord.setUserId(userId);
        List<HouseUnlikeLogRecord> houseUnlikeLogRecordList = queryHouseUnlikeLogDao.findHouseUnlikeLogPage(null, unlikeLogRecord, null, null, null, null, null);

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setCommunityId(communityId);
        record.setNotUserId(userId);
        record.setUnlike(HouseUserAsset.UNLIKE_STATUS_UNLIKE);
        record.setLocked(HouseUserAsset.LOCKED_STATUS_UNLOCKED);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, null, null, null, null, null);

        if ((houseUnlikeLogRecordList != null && houseUnlikeLogRecordList.size() > 0) && (houseUnlikeLogRecordList != null && houseUnlikeLogRecordList.size() > 0)) {
            for (HouseUserAssetRecord houseUserAssetRecord : houseUserAssetRecordList) {
                for (HouseUnlikeLogRecord houseUnlikeLogRecord : houseUnlikeLogRecordList) {
                    if (houseUnlikeLogRecord.getAssetId().equals(houseUserAssetRecord.getAssetId())) {
                        houseUserAssetRecord.setUnlikeStatus(true);
                    }
                }
            }
        }


        page.setRecords(houseUserAssetRecordList);
        return SuccessTip.create(page);
    }


//    交换上下房屋
    @PostMapping("/upAndDownStairs")
    public Tip upAndDownStairsExchangeRequest(@RequestParam("assetId") Long assetId,@RequestParam("isUp") Boolean isUp){
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.USER_ID,userId).eq(HouseUserAsset.ASSET_ID,assetId);
        HouseUserAsset houseUserAsset =  houseUserAssetMapper.selectOne(queryWrapper);
        if (houseUserAsset==null){
            throw new BusinessException(BusinessCode.NoPermission,"该房屋产权不是该用户");
        }

        return SuccessTip.create(houseAssetExchangeRequestService.addUpAndDownStairsExchangeRequest(userId,assetId,isUp));



    }



}
