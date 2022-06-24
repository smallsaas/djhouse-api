
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

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
@Api("HouseAsset")
@RequestMapping("/api/crud/house/houseAsset/houseAssets")
public class HouseAssetEndpoint {

    @Resource
    HouseAssetService houseAssetService;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @BusinessLog(name = "HouseAsset", value = "create HouseAsset")
    @Permission(HouseAssetPermission.HOUSEASSET_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAsset", response = HouseAsset.class)
    public Tip createHouseAsset(@RequestBody HouseAsset entity) {
        Integer affected = 0;
        try {
            affected = houseAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetPermission.HOUSEASSET_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAsset", response = HouseAsset.class)
    public Tip getHouseAsset(@PathVariable Long id) {
        return SuccessTip.create(houseAssetService.queryMasterModel(queryHouseAssetDao, id));
    }

    @BusinessLog(name = "HouseAsset", value = "update HouseAsset")
    @Permission(HouseAssetPermission.HOUSEASSET_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAsset", response = HouseAsset.class)
    public Tip updateHouseAsset(@PathVariable Long id, @RequestBody HouseAsset entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAsset", value = "delete HouseAsset")
    @Permission(HouseAssetPermission.HOUSEASSET_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAsset")
    public Tip deleteHouseAsset(@PathVariable Long id) {
        return SuccessTip.create(houseAssetService.deleteMaster(id));
    }

    @Permission(HouseAssetPermission.HOUSEASSET_VIEW)
    @ApiOperation(value = "HouseAsset 列表信息", response = HouseAssetRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "buildingId", dataType = "Long"),
            @ApiImplicitParam(name = "unitId", dataType = "Long"),
            @ApiImplicitParam(name = "floor", dataType = "Integer"),
            @ApiImplicitParam(name = "number", dataType = "String"),
            @ApiImplicitParam(name = "assetSlot", dataType = "String"),
            @ApiImplicitParam(name = "assetType", dataType = "String"),
            @ApiImplicitParam(name = "assetTypeId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetPage(Page<HouseAssetRecord> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   // for tag feature query
                                   @RequestParam(name = "tag", required = false) String tag,
                                   // end tag
                                   @RequestParam(name = "search", required = false) String search,

                                   @RequestParam(name = "buildingId", required = false) Long buildingId,

                                   @RequestParam(name = "unitId", required = false) Long unitId,

                                   @RequestParam(name = "floor", required = false) Integer floor,

                                   @RequestParam(name = "number", required = false) String number,

                                   @RequestParam(name = "assetSlot", required = false) String assetSlot,

                                   @RequestParam(name = "assetType", required = false) String assetType,
                                   @RequestParam(name = "buildingCode", required = false) String buildingCode,
                                   @RequestParam(name = "communityName", required = false) String communityName,

                                   @RequestParam(name = "assetTypeId", required = false) Long assetTypeId,
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

        HouseAssetRecord record = new HouseAssetRecord();
        record.setBuildingId(buildingId);
        record.setUnitId(unitId);
        record.setFloor(floor);
        record.setNumber(number);
        record.setAssetSlot(assetSlot);
        record.setAssetType(assetType);
        record.setAssetTypeId(assetTypeId);
        record.setBuildingCode(buildingCode);
        record.setCommunityName(communityName);
        System.out.println(buildingCode);
        List<HouseAssetRecord> houseAssetPage = queryHouseAssetDao.findHouseAssetPage(page, record, tag, search, orderBy, null, null);
        page.setRecords(houseAssetPage);

        return SuccessTip.create(page);
    }
}

