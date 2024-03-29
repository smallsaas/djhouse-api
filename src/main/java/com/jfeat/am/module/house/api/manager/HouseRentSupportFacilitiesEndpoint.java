
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentSupportFacilitiesDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentSupportFacilities;

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
 * @since 2022-08-08
 */
@RestController
@Api("HouseRentSupportFacilities")
@RequestMapping("/api/crud/house/houseRentSupportFacilities/houseRentSupportFacilitieses")
public class HouseRentSupportFacilitiesEndpoint {

    @Resource
    HouseRentSupportFacilitiesService houseRentSupportFacilitiesService;

    @Resource
    QueryHouseRentSupportFacilitiesDao queryHouseRentSupportFacilitiesDao;


    @BusinessLog(name = "HouseRentSupportFacilities", value = "create HouseRentSupportFacilities")
    @Permission(HouseRentSupportFacilitiesPermission.HOUSERENTSUPPORTFACILITIES_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseRentSupportFacilities", response = HouseRentSupportFacilities.class)
    public Tip createHouseRentSupportFacilities(@RequestBody HouseRentSupportFacilities entity) {
        Integer affected = 0;
        try {
            affected = houseRentSupportFacilitiesService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseRentSupportFacilitiesPermission.HOUSERENTSUPPORTFACILITIES_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseRentSupportFacilities", response = HouseRentSupportFacilities.class)
    public Tip getHouseRentSupportFacilities(@PathVariable Long id) {
        return SuccessTip.create(houseRentSupportFacilitiesService.queryMasterModel(queryHouseRentSupportFacilitiesDao, id));
    }

    @BusinessLog(name = "HouseRentSupportFacilities", value = "update HouseRentSupportFacilities")
    @Permission(HouseRentSupportFacilitiesPermission.HOUSERENTSUPPORTFACILITIES_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseRentSupportFacilities", response = HouseRentSupportFacilities.class)
    public Tip updateHouseRentSupportFacilities(@PathVariable Long id, @RequestBody HouseRentSupportFacilities entity) {
        entity.setId(id);
        return SuccessTip.create(houseRentSupportFacilitiesService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseRentSupportFacilities", value = "delete HouseRentSupportFacilities")
    @Permission(HouseRentSupportFacilitiesPermission.HOUSERENTSUPPORTFACILITIES_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseRentSupportFacilities")
    public Tip deleteHouseRentSupportFacilities(@PathVariable Long id) {
        return SuccessTip.create(houseRentSupportFacilitiesService.deleteMaster(id));
    }

    @Permission(HouseRentSupportFacilitiesPermission.HOUSERENTSUPPORTFACILITIES_VIEW)
    @ApiOperation(value = "HouseRentSupportFacilities 列表信息", response = HouseRentSupportFacilitiesRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "facilitiesId", dataType = "Long"),
            @ApiImplicitParam(name = "rentId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseRentSupportFacilitiesPage(Page<HouseRentSupportFacilitiesRecord> page,
                                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                   // for tag feature query
                                                   @RequestParam(name = "tag", required = false) String tag,
                                                   // end tag
                                                   @RequestParam(name = "search", required = false) String search,

                                                   @RequestParam(name = "facilitiesId", required = false) Long facilitiesId,

                                                   @RequestParam(name = "rentId", required = false) Long rentId,
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

        HouseRentSupportFacilitiesRecord record = new HouseRentSupportFacilitiesRecord();
        record.setFacilitiesId(facilitiesId);
        record.setRentId(rentId);


        List<HouseRentSupportFacilitiesRecord> houseRentSupportFacilitiesPage = queryHouseRentSupportFacilitiesDao.findHouseRentSupportFacilitiesPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseRentSupportFacilitiesPage);

        return SuccessTip.create(page);
    }
}

