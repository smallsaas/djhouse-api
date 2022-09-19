
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseApplicationOperationsDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseApplicationOperationsRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;

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
 * @since 2022-08-11
 */
@RestController
@Api("HouseApplicationOperations")
@RequestMapping("/api/crud/house/houseApplicationOperations/houseApplicationOperationses")
public class HouseApplicationOperationsEndpoint {

    @Resource
    HouseApplicationOperationsService houseApplicationOperationsService;

    @Resource
    QueryHouseApplicationOperationsDao queryHouseApplicationOperationsDao;


    @BusinessLog(name = "HouseApplicationOperations", value = "create HouseApplicationOperations")
    @Permission(HouseApplicationOperationsPermission.HOUSEAPPLICATIONOPERATIONS_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseApplicationOperations", response = HouseApplicationOperations.class)
    public Tip createHouseApplicationOperations(@RequestBody HouseApplicationOperations entity) {
        Integer affected = 0;
        try {
            affected = houseApplicationOperationsService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseApplicationOperationsPermission.HOUSEAPPLICATIONOPERATIONS_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseApplicationOperations", response = HouseApplicationOperations.class)
    public Tip getHouseApplicationOperations(@PathVariable Long id) {
        return SuccessTip.create(houseApplicationOperationsService.queryMasterModel(queryHouseApplicationOperationsDao, id));
    }

    @BusinessLog(name = "HouseApplicationOperations", value = "update HouseApplicationOperations")
    @Permission(HouseApplicationOperationsPermission.HOUSEAPPLICATIONOPERATIONS_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseApplicationOperations", response = HouseApplicationOperations.class)
    public Tip updateHouseApplicationOperations(@PathVariable Long id, @RequestBody HouseApplicationOperations entity) {
        entity.setId(id);
        return SuccessTip.create(houseApplicationOperationsService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseApplicationOperations", value = "delete HouseApplicationOperations")
    @Permission(HouseApplicationOperationsPermission.HOUSEAPPLICATIONOPERATIONS_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseApplicationOperations")
    public Tip deleteHouseApplicationOperations(@PathVariable Long id) {
        return SuccessTip.create(houseApplicationOperationsService.deleteMaster(id));
    }

    @Permission(HouseApplicationOperationsPermission.HOUSEAPPLICATIONOPERATIONS_VIEW)
    @ApiOperation(value = "HouseApplicationOperations 列表信息", response = HouseApplicationOperationsRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "tenantName", dataType = "String"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "phone", dataType = "String"),
            @ApiImplicitParam(name = "idcard", dataType = "String"),
            @ApiImplicitParam(name = "evidence", dataType = "String"),
            @ApiImplicitParam(name = "reason", dataType = "String"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseApplicationOperationsPage(Page<HouseApplicationOperationsRecord> page,
                                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                   // for tag feature query
                                                   @RequestParam(name = "tag", required = false) String tag,
                                                   // end tag
                                                   @RequestParam(name = "search", required = false) String search,

                                                   @RequestParam(name = "userId", required = false) Long userId,

                                                   @RequestParam(name = "tenantName", required = false) String tenantName,

                                                   @RequestParam(name = "name", required = false) String name,

                                                   @RequestParam(name = "phone", required = false) String phone,

                                                   @RequestParam(name = "idcard", required = false) String idcard,

                                                   @RequestParam(name = "evidence", required = false) String evidence,

                                                   @RequestParam(name = "reason", required = false) String reason,

                                                   @RequestParam(name = "status", required = false) Integer status,

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

        HouseApplicationOperationsRecord record = new HouseApplicationOperationsRecord();
        record.setUserId(userId);
        record.setTenantName(tenantName);
        record.setName(name);
        record.setPhone(phone);
        record.setIdcard(idcard);
        record.setEvidence(evidence);
        record.setReason(reason);
        record.setStatus(status);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseApplicationOperationsRecord> houseApplicationOperationsPage = queryHouseApplicationOperationsDao.findHouseApplicationOperationsPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseApplicationOperationsPage);

        return SuccessTip.create(page);
    }
}

