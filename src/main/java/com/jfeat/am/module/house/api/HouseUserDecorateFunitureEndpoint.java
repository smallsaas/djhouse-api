
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserDecorateFunitureDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateFunitureRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateFuniture;

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
 * @since 2022-07-01
 */
@RestController
@Api("HouseUserDecorateFuniture")
@RequestMapping("/api/crud/house/houseUserDecorateFuniture/houseUserDecorateFunitures")
public class HouseUserDecorateFunitureEndpoint {

    @Resource
    HouseUserDecorateFunitureService houseUserDecorateFunitureService;

    @Resource
    QueryHouseUserDecorateFunitureDao queryHouseUserDecorateFunitureDao;


    @BusinessLog(name = "HouseUserDecorateFuniture", value = "create HouseUserDecorateFuniture")
    @Permission(HouseUserDecorateFuniturePermission.HOUSEUSERDECORATEFUNITURE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserDecorateFuniture", response = HouseUserDecorateFuniture.class)
    public Tip createHouseUserDecorateFuniture(@RequestBody HouseUserDecorateFuniture entity) {
        Integer affected = 0;
        try {
            affected = houseUserDecorateFunitureService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserDecorateFuniturePermission.HOUSEUSERDECORATEFUNITURE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserDecorateFuniture", response = HouseUserDecorateFuniture.class)
    public Tip getHouseUserDecorateFuniture(@PathVariable Long id) {
        return SuccessTip.create(houseUserDecorateFunitureService.queryMasterModel(queryHouseUserDecorateFunitureDao, id));
    }

    @BusinessLog(name = "HouseUserDecorateFuniture", value = "update HouseUserDecorateFuniture")
    @Permission(HouseUserDecorateFuniturePermission.HOUSEUSERDECORATEFUNITURE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserDecorateFuniture", response = HouseUserDecorateFuniture.class)
    public Tip updateHouseUserDecorateFuniture(@PathVariable Long id, @RequestBody HouseUserDecorateFuniture entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserDecorateFunitureService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserDecorateFuniture", value = "delete HouseUserDecorateFuniture")
    @Permission(HouseUserDecorateFuniturePermission.HOUSEUSERDECORATEFUNITURE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserDecorateFuniture")
    public Tip deleteHouseUserDecorateFuniture(@PathVariable Long id) {
        return SuccessTip.create(houseUserDecorateFunitureService.deleteMaster(id));
    }

    @Permission(HouseUserDecorateFuniturePermission.HOUSEUSERDECORATEFUNITURE_VIEW)
    @ApiOperation(value = "HouseUserDecorateFuniture 列表信息", response = HouseUserDecorateFunitureRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "decoratePlanId", dataType = "Long"),
            @ApiImplicitParam(name = "funitureId", dataType = "Long"),
            @ApiImplicitParam(name = "funitureNumber", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserDecorateFuniturePage(Page<HouseUserDecorateFunitureRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                  // for tag feature query
                                                  @RequestParam(name = "tag", required = false) String tag,
                                                  // end tag
                                                  @RequestParam(name = "search", required = false) String search,

                                                  @RequestParam(name = "userId", required = false) Long userId,

                                                  @RequestParam(name = "decoratePlanId", required = false) Long decoratePlanId,

                                                  @RequestParam(name = "funitureId", required = false) Long funitureId,

                                                  @RequestParam(name = "funitureNumber", required = false) Integer funitureNumber,

                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  @RequestParam(name = "createTime", required = false) Date createTime,
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

        HouseUserDecorateFunitureRecord record = new HouseUserDecorateFunitureRecord();
        record.setUserId(userId);
        record.setDecoratePlanId(decoratePlanId);
        record.setFunitureId(funitureId);
        record.setFunitureNumber(funitureNumber);
        record.setCreateTime(createTime);


        List<HouseUserDecorateFunitureRecord> houseUserDecorateFuniturePage = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserDecorateFuniturePage);

        return SuccessTip.create(page);
    }
}

