package com.jfeat.am.module.house.api;


import com.jfeat.crud.base.annotation.BusinessLog;
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
import com.jfeat.am.module.house.services.domain.dao.QuerySupplierDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.SupplierRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;

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
 * @since 2022-05-19
 */
@RestController

@Api("Supplier")
@RequestMapping("/api/crud/supplier/suppliers")
public class SupplierEndpoint {

    @Resource
    SupplierService supplierService;


    @Resource
    QuerySupplierDao querySupplierDao;

    @BusinessLog(name = "Supplier", value = "create Supplier")
    @Permission(SupplierPermission.SUPPLIER_NEW)
    @PostMapping
    @ApiOperation(value = "新建 Supplier", response = Supplier.class)
    public Tip createSupplier(@RequestBody Supplier entity) {

        Integer affected = 0;
        try {
            affected = supplierService.createMaster(entity);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(SupplierPermission.SUPPLIER_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 Supplier", response = Supplier.class)
    public Tip getSupplier(@PathVariable Long id) {
        return SuccessTip.create(supplierService.queryMasterModel(querySupplierDao, id));
    }

    @BusinessLog(name = "Supplier", value = "update Supplier")
    @Permission(SupplierPermission.SUPPLIER_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 Supplier", response = Supplier.class)
    public Tip updateSupplier(@PathVariable Long id, @RequestBody Supplier entity) {
        entity.setId(id);
        return SuccessTip.create(supplierService.updateMaster(entity));
    }

    @BusinessLog(name = "Supplier", value = "delete Supplier")
    @Permission(SupplierPermission.SUPPLIER_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 Supplier")
    public Tip deleteSupplier(@PathVariable Long id) {
        return SuccessTip.create(supplierService.deleteMaster(id));
    }

    @Permission(SupplierPermission.SUPPLIER_VIEW)
    @ApiOperation(value = "Supplier 列表信息", response = SupplierRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "type", dataType = "Integer"),
            @ApiImplicitParam(name = "url", dataType = "String"),
            @ApiImplicitParam(name = "address", dataType = "String"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip querySuppliers(Page<SupplierRecord> page,
                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "search", required = false) String search,
                              @RequestParam(name = "id", required = false) Long id,

                              @RequestParam(name = "name", required = false) String name,

                              @RequestParam(name = "note", required = false) String note,

                              @RequestParam(name = "type", required = false) Integer type,

                              @RequestParam(name = "url", required = false) String url,

                              @RequestParam(name = "address", required = false) String address,

                              @RequestParam(name = "orgId", required = false) Long orgId,
                              @RequestParam(name = "orderBy", required = false) String orderBy,
                              @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        SupplierRecord record = new SupplierRecord();
        record.setId(id);
        record.setName(name);
        record.setNote(note);
        record.setType(type);
        record.setUrl(url);
        record.setAddress(address);
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }


        List<SupplierRecord> supplierPage = querySupplierDao.findSupplierPage(page, record, search, orderBy, null, null);

        page.setRecords(supplierPage);

        return SuccessTip.create(page);
    }
}
