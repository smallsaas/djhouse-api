
package com.jfeat.am.module.house.api;


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
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyUserUnitDao;
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
import com.jfeat.am.module.house.services.domain.model.HousePropertyUserUnitRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserUnit;

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
 * @since 2022-05-28
 */
@RestController
@Api("HousePropertyUserUnit")
@RequestMapping("/api/crud/house/housePropertyUserUnit/housePropertyUserUnits")
public class HousePropertyUserUnitEndpoint {

    @Resource
    HousePropertyUserUnitService housePropertyUserUnitService;

    @Resource
    QueryHousePropertyUserUnitDao queryHousePropertyUserUnitDao;


    @BusinessLog(name = "HousePropertyUserUnit", value = "create HousePropertyUserUnit")
    @Permission(HousePropertyUserUnitPermission.HOUSEPROPERTYUSERUNIT_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyUserUnit", response = HousePropertyUserUnit.class)
    public Tip createHousePropertyUserUnit(@RequestBody HousePropertyUserUnit entity) {
        Integer affected = 0;
        try {
            affected = housePropertyUserUnitService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HousePropertyUserUnitPermission.HOUSEPROPERTYUSERUNIT_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyUserUnit", response = HousePropertyUserUnit.class)
    public Tip getHousePropertyUserUnit(@PathVariable Long id) {
        return SuccessTip.create(housePropertyUserUnitService.queryMasterModel(queryHousePropertyUserUnitDao, id));
    }

    @BusinessLog(name = "HousePropertyUserUnit", value = "update HousePropertyUserUnit")
    @Permission(HousePropertyUserUnitPermission.HOUSEPROPERTYUSERUNIT_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyUserUnit", response = HousePropertyUserUnit.class)
    public Tip updateHousePropertyUserUnit(@PathVariable Long id, @RequestBody HousePropertyUserUnit entity) {
        entity.setId(id);
        return SuccessTip.create(housePropertyUserUnitService.updateMaster(entity));
    }

    @BusinessLog(name = "HousePropertyUserUnit", value = "delete HousePropertyUserUnit")
    @Permission(HousePropertyUserUnitPermission.HOUSEPROPERTYUSERUNIT_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyUserUnit")
    public Tip deleteHousePropertyUserUnit(@PathVariable Long id) {
        return SuccessTip.create(housePropertyUserUnitService.deleteMaster(id));
    }

    @Permission(HousePropertyUserUnitPermission.HOUSEPROPERTYUSERUNIT_VIEW)
    @ApiOperation(value = "HousePropertyUserUnit 列表信息", response = HousePropertyUserUnitRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "unitId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHousePropertyUserUnitPage(Page<HousePropertyUserUnitRecord> page,
                                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              // for tag feature query
                                              @RequestParam(name = "tag", required = false) String tag,
                                              // end tag
                                              @RequestParam(name = "search", required = false) String search,

                                              @RequestParam(name = "userId", required = false) Long userId,

                                              @RequestParam(name = "unitId", required = false) Long unitId,
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
        HousePropertyUserUnitRecord record = new HousePropertyUserUnitRecord();
        record.setUserId(userId);
        record.setUnitId(unitId);
        List<HousePropertyUserUnitRecord> housePropertyUserUnitPage = queryHousePropertyUserUnitDao.findHousePropertyUserUnitPage(page, record, tag, search, orderBy, null, null);
        page.setRecords(housePropertyUserUnitPage);

        return SuccessTip.create(page);
    }
}

