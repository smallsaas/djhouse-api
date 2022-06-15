
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;

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
 * @since 2022-06-11
 */
@RestController
@Api("HouseUserAsset")
@RequestMapping("/api/crud/house/houseUserAsset/houseUserAssets")
public class HouseUserAssetEndpoint {

    @Resource
    HouseUserAssetService houseUserAssetService;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;


    @BusinessLog(name = "HouseUserAsset", value = "create HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserAsset", response = HouseUserAsset.class)
    public Tip createHouseUserAsset(@RequestBody HouseUserAsset entity) {
        Integer affected = 0;
        try {
            affected = houseUserAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserAsset", response = HouseUserAsset.class)
    public Tip getHouseUserAsset(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id));
    }

    @BusinessLog(name = "HouseUserAsset", value = "update HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserAsset", response = HouseUserAsset.class)
    public Tip updateHouseUserAsset(@PathVariable Long id, @RequestBody HouseUserAsset entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserAssetService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserAsset", value = "delete HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserAsset")
    public Tip deleteHouseUserAsset(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetService.deleteMaster(id));
    }

    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @ApiOperation(value = "HouseUserAsset 列表信息", response = HouseUserAssetRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserAssetPage(Page<HouseUserAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "userId", required = false) Long userId,

                                       @RequestParam(name = "assetId", required = false) Long assetId,
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

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);


        List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, tag, search, orderBy, null, null);
        for (int i=0;i<houseUserAssetPage.size();i++){

        }
        page.setRecords(houseUserAssetPage);

        return SuccessTip.create(page);
    }
}

