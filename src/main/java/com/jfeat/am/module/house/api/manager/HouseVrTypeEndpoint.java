
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrTypeDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseVrTypeRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrType;

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
 * @since 2022-08-16
 */
@RestController
@Api("HouseVrType")
@RequestMapping("/api/crud/house/houseVrType/houseVrTypes")
public class HouseVrTypeEndpoint {

    private static final int masterId = 61;

    @Resource
    HouseVrTypeService houseVrTypeService;

    @Resource
    QueryHouseVrTypeDao queryHouseVrTypeDao;


    @BusinessLog(name = "HouseVrType", value = "create HouseVrType")
    @Permission(HouseVrTypePermission.HOUSEVRTYPE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseVrType", response = HouseVrType.class)
    public Tip createHouseVrType(@RequestBody HouseVrType entity) {
        Integer affected = 0;
        try {
            affected = houseVrTypeService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseVrTypePermission.HOUSEVRTYPE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseVrType", response = HouseVrType.class)
    public Tip getHouseVrType(@PathVariable Long id) {
        return SuccessTip.create(houseVrTypeService.queryMasterModel(queryHouseVrTypeDao, id));
    }

    @BusinessLog(name = "HouseVrType", value = "update HouseVrType")
    @Permission(HouseVrTypePermission.HOUSEVRTYPE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseVrType", response = HouseVrType.class)
    public Tip updateHouseVrType(@PathVariable Long id, @RequestBody HouseVrType entity) {
        entity.setId(id);
        return SuccessTip.create(houseVrTypeService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseVrType", value = "delete HouseVrType")
    @Permission(HouseVrTypePermission.HOUSEVRTYPE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseVrType")
    public Tip deleteHouseVrType(@PathVariable Long id) {
        return SuccessTip.create(houseVrTypeService.deleteMaster(id));
    }

    @Permission(HouseVrTypePermission.HOUSEVRTYPE_VIEW)
    @ApiOperation(value = "HouseVrType 列表信息", response = HouseVrTypeRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseVrTypePage(Page<HouseVrTypeRecord> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    // for tag feature query
                                    @RequestParam(name = "tag", required = false) String tag,
                                    // end tag
                                    @RequestParam(name = "search", required = false) String search,

                                    @RequestParam(name = "name", required = false) String name,

                                    @RequestParam(name = "orgId", required = false) Long orgId,
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

        HouseVrTypeRecord record = new HouseVrTypeRecord();
        record.setName(name);

        System.out.println(JWTKit.getUserId());
        System.out.println(JWTKit.getAccount());

        if (META.enabledSaas()) {
            if(JWTKit.getUserId().equals(masterId)){
            }else{
                record.setOrgId(JWTKit.getOrgId());
            }
        }


        List<HouseVrTypeRecord> houseVrTypePage = queryHouseVrTypeDao.findHouseVrTypePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseVrTypePage);

        return SuccessTip.create(page);
    }
}

