
package com.jfeat.am.module.house.api.manager;


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
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-09-13
 */
@RestController
@Api("HouseAssetExchangeRequest")
@RequestMapping("/api/crud/house/houseAssetExchangeRequest/houseAssetExchangeRequests")
public class HouseAssetExchangeRequestEndpoint {

    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;


    @BusinessLog(name = "HouseAssetExchangeRequest", value = "create HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip createHouseAssetExchangeRequest(@RequestBody HouseAssetExchangeRequest entity) {
        Integer affected = 0;
        try {
            affected = houseAssetExchangeRequestService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip getHouseAssetExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseAssetExchangeRequestService.queryMasterModel(queryHouseAssetExchangeRequestDao, id));
    }

    @BusinessLog(name = "HouseAssetExchangeRequest", value = "update HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetExchangeRequest", response = HouseAssetExchangeRequest.class)
    public Tip updateHouseAssetExchangeRequest(@PathVariable Long id, @RequestBody HouseAssetExchangeRequest entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetExchangeRequestService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetExchangeRequest", value = "delete HouseAssetExchangeRequest")
    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetExchangeRequest")
    public Tip deleteHouseAssetExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseAssetExchangeRequestService.deleteMaster(id));
    }

    @Permission(HouseAssetExchangeRequestPermission.HOUSEASSETEXCHANGEREQUEST_VIEW)
    @ApiOperation(value = "HouseAssetExchangeRequest 列表信息", response = HouseAssetExchangeRequestRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "targetAsset", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
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

                                                  @RequestParam(name = "targetAsset", required = false) Long targetAsset,

                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  @RequestParam(name = "createTime", required = false) Date createTime,

                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  @RequestParam(name = "updateTime", required = false) Date updateTime,
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
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setTargetAsset(targetAsset);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestPage = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetExchangeRequestPage);

        return SuccessTip.create(page);
    }
}

