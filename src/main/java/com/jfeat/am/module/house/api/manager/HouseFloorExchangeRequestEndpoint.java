
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseFloorExchangeRequestDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseFloorExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseFloorExchangeRequest;

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
 * @since 2022-11-19
 */
@RestController
@Api("HouseFloorExchangeRequest")
@RequestMapping("/api/crud/house/houseFloorExchangeRequest/houseFloorExchangeRequests")
public class HouseFloorExchangeRequestEndpoint {

    @Resource
    HouseFloorExchangeRequestService houseFloorExchangeRequestService;

    @Resource
    QueryHouseFloorExchangeRequestDao queryHouseFloorExchangeRequestDao;


    @BusinessLog(name = "HouseFloorExchangeRequest", value = "create HouseFloorExchangeRequest")
    @Permission(HouseFloorExchangeRequestPermission.HOUSEFLOOREXCHANGEREQUEST_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseFloorExchangeRequest", response = HouseFloorExchangeRequest.class)
    public Tip createHouseFloorExchangeRequest(@RequestBody HouseFloorExchangeRequest entity) {
        Integer affected = 0;
        try {
            affected = houseFloorExchangeRequestService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseFloorExchangeRequestPermission.HOUSEFLOOREXCHANGEREQUEST_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseFloorExchangeRequest", response = HouseFloorExchangeRequest.class)
    public Tip getHouseFloorExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseFloorExchangeRequestService.queryMasterModel(queryHouseFloorExchangeRequestDao, id));
    }

    @BusinessLog(name = "HouseFloorExchangeRequest", value = "update HouseFloorExchangeRequest")
    @Permission(HouseFloorExchangeRequestPermission.HOUSEFLOOREXCHANGEREQUEST_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseFloorExchangeRequest", response = HouseFloorExchangeRequest.class)
    public Tip updateHouseFloorExchangeRequest(@PathVariable Long id, @RequestBody HouseFloorExchangeRequest entity) {
        entity.setId(id);
        return SuccessTip.create(houseFloorExchangeRequestService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseFloorExchangeRequest", value = "delete HouseFloorExchangeRequest")
    @Permission(HouseFloorExchangeRequestPermission.HOUSEFLOOREXCHANGEREQUEST_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseFloorExchangeRequest")
    public Tip deleteHouseFloorExchangeRequest(@PathVariable Long id) {
        return SuccessTip.create(houseFloorExchangeRequestService.deleteMaster(id));
    }

    @Permission(HouseFloorExchangeRequestPermission.HOUSEFLOOREXCHANGEREQUEST_VIEW)
    @ApiOperation(value = "HouseFloorExchangeRequest 列表信息", response = HouseFloorExchangeRequestRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "floor", dataType = "Integer"),
            @ApiImplicitParam(name = "floorState", dataType = "Integer"),
            @ApiImplicitParam(name = "stute", dataType = "Integer"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseFloorExchangeRequestPage(Page<HouseFloorExchangeRequestRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                  // for tag feature query
                                                  @RequestParam(name = "tag", required = false) String tag,
                                                  // end tag
                                                  @RequestParam(name = "search", required = false) String search,

                                                  @RequestParam(name = "userId", required = false) Long userId,

                                                  @RequestParam(name = "assetId", required = false) Long assetId,

                                                  @RequestParam(name = "floor", required = false) Integer floor,

                                                  @RequestParam(name = "floorState", required = false) Integer floorState,

                                                  @RequestParam(name = "stute", required = false) Integer stute,

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

        HouseFloorExchangeRequestRecord record = new HouseFloorExchangeRequestRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setFloor(floor);
        record.setFloorState(floorState);
        record.setStute(stute);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseFloorExchangeRequestRecord> houseFloorExchangeRequestPage = queryHouseFloorExchangeRequestDao.findHouseFloorExchangeRequestPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseFloorExchangeRequestPage);

        return SuccessTip.create(page);
    }
}

