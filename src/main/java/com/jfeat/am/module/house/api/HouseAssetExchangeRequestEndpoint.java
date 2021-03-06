
package com.jfeat.am.module.house.api;


import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import springfox.documentation.spring.web.json.Json;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@RestController
@Api("HouseAssetExchangeRequest")
@RequestMapping("/api/crud/house/houseAssetExchangeRequest/houseAssetExchangeRequests")
public class HouseAssetExchangeRequestEndpoint {

    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @BusinessLog(name = "HouseAssetExchangeRequest", value = "create HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_NEW)
    @PostMapping
    @ApiOperation(value = "?????? HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip createHouseAssetExchangeRequest(@RequestBody HouseAssetExchangeRequest entity) {
        Integer affected = 0;
        try {
            affected = houseAssetExchangeRequestService.createMaster(entity);
            houseAssetExchangeRequestService.assetMachResult(entity,false);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "?????? HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip getHouseAssetExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseAssetExchangeRequestService.queryMasterModel(queryHouseAssetExchangeRequestDao, id));
    }

    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_VIEW)
    @PostMapping("/match")
    public Tip matchedResult(@RequestBody HouseAssetExchangeRequest entity) {
        return SuccessTip.create(houseAssetExchangeRequestService.assetMachResult(entity,false));
    }

    @BusinessLog(name = "HouseAssetExchangeRequest", value = "update HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "?????? HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip updateHouseAssetExchangeRequest(@PathVariable Long id, @RequestBody HouseAssetExchangeRequest entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetExchangeRequestService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetExchangeRequest", value = "delete HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("?????? HouseAssetExchangeRequest")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseAssetExchangeRequestService.deleteMaster(id));
    }

    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_VIEW)
    @ApiOperation(value = "HouseAssetExchangeRequest ????????????", response = HouseAssetExchangeRequestRecord.class)
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
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setTargetAssetRange(targetAssetRange);
        record.setTargetAssetRangeLimit(targetAssetRangeLimit);


        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestPage = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetExchangeRequestPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/allAssetExchangeRequest")
    public Tip getALlAssetExchangeRequest(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(name = "search", required = false) String search){
        List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.queryAllHouseAssetExchangeRequest();
        for (int i=0;i<houseAssetExchangeRequestList.size();i++){
            List<String> targetRange = Arrays.asList(houseAssetExchangeRequestList.get(i).getTargetAssetRange().split(","));
            StringBuffer stringBuffer = new StringBuffer();
            for (String s:targetRange){
               HouseAsset houseAsset =  queryHouseAssetDao.queryMasterModel(Long.parseLong(s));
               if (houseAsset!=null){
                   String buildingCode = houseAsset.getBuildingCode();
                   String number = houseAsset.getNumber();
                   stringBuffer.append("".concat(buildingCode).concat("-").concat(number).concat("    "));
               }

            }
            houseAssetExchangeRequestList.get(i).setTargetAssetRange(stringBuffer.toString());
        }
        Page<HouseAssetExchangeRequest> page = new Page<>();
        page.setRecords(houseAssetExchangeRequestList);
        return SuccessTip.create(page);
    }
}

