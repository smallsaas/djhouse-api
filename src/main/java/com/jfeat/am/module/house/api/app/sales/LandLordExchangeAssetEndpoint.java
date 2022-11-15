package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUnlikeLogDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUnlikeLogRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseUnlikeLogService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetExchangeRequestMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/u/house/sales/landLordExchangeAsset")
public class LandLordExchangeAssetEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    TenantUtility tenantUtility;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

    @Resource
    QueryHouseUnlikeLogDao queryHouseUnlikeLogDao;

    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

    @Resource
    HouseUnlikeLogService houseUnlikeLogService;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    //    新建或者修改资产交换记录并匹配
    @PostMapping
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "userId") Long userId, @RequestBody HouseAssetExchangeRequest entity) {

        if (entity.getAssetId() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为必填项");
        }
        if (entity.getTargetAssetRange() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "targetAssetRange为必填项");
        }
        entity.setUserId(userId);

        return SuccessTip.create(houseAssetExchangeRequestService.addHouseAssetExchangeRequest(entity));
    }

    //    获取指定小区不喜欢房产列表
    @GetMapping("/unlikeList")
    public Tip getUnlikeAssetList(Page<HouseUserAssetRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(name = "search", required = false) String search,
                                  @RequestParam(value = "communityId",required = false) Long communityId) {

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

    //    获取换房需求列表
    @GetMapping("/exchangeList")
    public Tip getExchangeRequestList(Page<HouseAssetExchangeRequestRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      @RequestParam(name = "userId",required = false) Long landLordId,
                                      @RequestParam(name = "search", required = false) String search) {
        Long userId = JWTKit.getUserId();

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long orgId = tenantUtility.getCurrentOrgId(userId);

        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setOrgId(orgId);
        record.setUserId(landLordId);
        if (search!=null && search.contains("-")){
            String[] strings = search.split("-");
            for (int i=0;i<strings.length;i++){
                if (!strings[i].equals("")){
                    if (i==0){
                        record.setBuildingCode(strings[0]);
                        record.setTargetBuildingCode(strings[0]);
                    }
                    if (i==1){
                        record.setNumber(strings[1]);
                        record.setTargetHouseNumber(strings[1]);
//                        record.set(strings[1]);
                    }
                }
            }
            search = null;
        }

        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, null, search, null, null, null);

        page.setRecords(houseAssetExchangeRequestList);

        return SuccessTip.create(page);
    }


    //    选择不喜欢的房子请求匹配
    @PostMapping("/selectUnlikeAssetList")
    public Tip selectUnlikeAssetExchangeList(@RequestParam("userId") Long userId, @RequestBody List<HouseUserAsset> userAssetList) {

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
    public Tip cancelUnlikeLog(@PathVariable("id") Long assetId, @RequestParam("userId") Long userId) {
        return SuccessTip.create(houseUnlikeLogService.deleteUnlikeLog(userId, assetId));

    }

    //    房东确认交换房屋
    @PostMapping("/confirmExchangeAsset")
    public Tip confirmExchangeAsset(@RequestBody List<HouseAssetExchangeRequest> exchangeRequestList, @RequestParam("userId") Long userId) {

        List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = new ArrayList<>();
        for (HouseAssetExchangeRequest houseAssetExchangeRequest : exchangeRequestList) {
            HouseAssetExchangeRequest record = new HouseAssetExchangeRequest();
            record.setUserId(userId);
            record.setAssetId(houseAssetExchangeRequest.getTargetAsset());
            record.setTargetAsset(houseAssetExchangeRequest.getAssetId());
            houseAssetExchangeRequestList.add(record);
        }

        return SuccessTip.create(houseAssetExchangeRequestService.confirmExchangeAsset(houseAssetExchangeRequestList));
    }

    //    被人想要
    @GetMapping("/optionExchangeRequestList")
    public Tip optionExchangeRequestList(Page<HouseAssetExchangeRequestRecord> page,
                                         @RequestParam("userId") Long userId,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(name = "search", required = false) String search) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setUserId(userId);

        List<HouseAssetExchangeRequestRecord> recordList = queryHouseAssetExchangeRequestDao.queryOptionExchangeRequestList(page, record, search);
        page.setRecords(recordList);
        return SuccessTip.create(page);
    }





}
