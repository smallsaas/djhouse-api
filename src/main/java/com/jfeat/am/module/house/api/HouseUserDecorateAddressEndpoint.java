
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserDecorateAddressDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateAddressRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateAddress;

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
 * @since 2022-05-30
 */
@RestController
@Api("HouseUserDecorateAddress")
@RequestMapping("/api/crud/house/houseUserDecorateAddress/houseUserDecorateAddresses")
public class HouseUserDecorateAddressEndpoint {

    @Resource
    HouseUserDecorateAddressService houseUserDecorateAddressService;

    @Resource
    QueryHouseUserDecorateAddressDao queryHouseUserDecorateAddressDao;


    @BusinessLog(name = "HouseUserDecorateAddress", value = "create HouseUserDecorateAddress")
    @Permission(HouseUserDecorateAddressPermission.HOUSEUSERDECORATEADDRESS_NEW)
    @PostMapping
    @ApiOperation(value = "?????? HouseUserDecorateAddress", response = HouseUserDecorateAddress.class)
    public Tip createHouseUserDecorateAddress(@RequestBody HouseUserDecorateAddress entity) {
        Integer affected = 0;
        try {
            affected = houseUserDecorateAddressService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserDecorateAddressPermission.HOUSEUSERDECORATEADDRESS_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "?????? HouseUserDecorateAddress", response = HouseUserDecorateAddress.class)
    public Tip getHouseUserDecorateAddress(@PathVariable Long id) {
        return SuccessTip.create(houseUserDecorateAddressService.queryMasterModel(queryHouseUserDecorateAddressDao, id));
    }

    @BusinessLog(name = "HouseUserDecorateAddress", value = "update HouseUserDecorateAddress")
    @Permission(HouseUserDecorateAddressPermission.HOUSEUSERDECORATEADDRESS_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "?????? HouseUserDecorateAddress", response = HouseUserDecorateAddress.class)
    public Tip updateHouseUserDecorateAddress(@PathVariable Long id, @RequestBody HouseUserDecorateAddress entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserDecorateAddressService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserDecorateAddress", value = "delete HouseUserDecorateAddress")
    @Permission(HouseUserDecorateAddressPermission.HOUSEUSERDECORATEADDRESS_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("?????? HouseUserDecorateAddress")
    public Tip deleteHouseUserDecorateAddress(@PathVariable Long id) {
        return SuccessTip.create(houseUserDecorateAddressService.deleteMaster(id));
    }

    @Permission(HouseUserDecorateAddressPermission.HOUSEUSERDECORATEADDRESS_VIEW)
    @ApiOperation(value = "HouseUserDecorateAddress ????????????", response = HouseUserDecorateAddressRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "decoratePlanId", dataType = "Long"),
            @ApiImplicitParam(name = "unitId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserDecorateAddressPage(Page<HouseUserDecorateAddressRecord> page,
                                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                 // for tag feature query
                                                 @RequestParam(name = "tag", required = false) String tag,
                                                 // end tag
                                                 @RequestParam(name = "search", required = false) String search,

                                                 @RequestParam(name = "userId", required = false) Long userId,

                                                 @RequestParam(name = "decoratePlanId", required = false) Long decoratePlanId,

                                                 @RequestParam(name = "unitId", required = false) Long unitId,
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

        HouseUserDecorateAddressRecord record = new HouseUserDecorateAddressRecord();
        record.setUserId(userId);
        record.setDecoratePlanId(decoratePlanId);
        record.setUnitId(unitId);


        List<HouseUserDecorateAddressRecord> houseUserDecorateAddressPage = queryHouseUserDecorateAddressDao.findHouseUserDecorateAddressPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserDecorateAddressPage);

        return SuccessTip.create(page);
    }
}

