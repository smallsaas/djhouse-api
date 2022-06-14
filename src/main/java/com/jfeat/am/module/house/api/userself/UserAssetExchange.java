package com.jfeat.am.module.house.api.userself;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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


    @BusinessLog(name = "HouseAssetExchangeRequest", value = "create HouseAssetExchangeRequest")
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip createHouseAssetExchangeRequest(@RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType, @RequestBody HouseAssetExchangeRequest entity) {
        Integer affected = 0;
        try {
            affected = houseAssetExchangeRequestService.createMaster(entity);
            houseAssetExchangeRequestService.assetMachResult(entity, isSameHouseType);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }
        return SuccessTip.create(affected);
    }

    @GetMapping("/demand")
    public Tip getHouseAssetExchangeRequest() {
        List<Map<String, Object>> list = new ArrayList<>();
        System.out.println(JWTKit.getUserId());
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

    @GetMapping("/matchResult")
    public Tip getHouseAssetExchangeRequestResult(@RequestParam("assetId") Long assetId) {
        List<HouseAssetMatchLog> houseAssetMatchLogs = queryHouseAssetMatchLogDao.queryHouseAssetMatchLogByUserId(assetId, JWTKit.getUserId());
        List<Map<String, Object>> list = new ArrayList<>();
        for (HouseAssetMatchLog houseAssetMatchLog : houseAssetMatchLogs) {
            Map<String, Object> houseAssetMap = new HashMap<>();
            HouseAsset ownerHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLog.getOwnerAssetId());
            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLog.getMathchedAssetId());
            houseAssetMap.put("owner", ownerHouseAsset);
            houseAssetMap.put("matchde", matchedHouseAsset);
            list.add(houseAssetMap);

        }
        return SuccessTip.create(list);
    }


    @PutMapping("/{id}")
    public Tip updateHouseAssetExchangeRequest(@PathVariable Long id, @RequestBody HouseAssetExchangeRequest entity, @RequestParam(value = "isSameHouseType", defaultValue = "true", required = false) Boolean isSameHouseType) {
        System.out.println(JWTKit.getUserId());
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


    @DeleteMapping("/{id}")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {
        HouseAssetExchangeRequest houseAssetExchangeRequest = queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByAssetIdAndUserId(id, JWTKit.getUserId());
        Integer effect = houseAssetExchangeRequestService.deleteMaster(houseAssetExchangeRequest.getId());
        effect +=queryHouseAssetMatchLogDao.deleteHouseAssetMatchLogByUserIdAndAssetId(id,JWTKit.getUserId());
        return SuccessTip.create(effect);

    }

    @ApiOperation(value = "HouseAssetExchangeRequest 列表信息", response = HouseAssetExchangeRequestRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "targetAssetRange", dataType = "String"),
            @ApiImplicitParam(name = "target_assetRangeLimit", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
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
