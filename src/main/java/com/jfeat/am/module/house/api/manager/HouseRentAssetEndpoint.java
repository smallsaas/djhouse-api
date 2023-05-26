
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

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
 * @since 2022-07-16
 */
@RestController
@Api("HouseRentAsset")
@RequestMapping("/api/crud/house/houseRentAsset/houseRentAssets")
public class HouseRentAssetEndpoint {

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;


    @BusinessLog(name = "HouseRentAsset", value = "create HouseRentAsset")
    @Permission(HouseRentAssetPermission.HOUSERENTASSET_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseRentAsset", response = HouseRentAsset.class)
    public Tip createHouseRentAsset(@RequestBody HouseRentAsset entity) {
        Integer affected = 0;
        try {
            affected = houseRentAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseRentAssetPermission.HOUSERENTASSET_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseRentAsset", response = HouseRentAsset.class)
    public Tip getHouseRentAsset(@PathVariable Long id) {
        return SuccessTip.create(houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, id));
    }

    @BusinessLog(name = "HouseRentAsset", value = "update HouseRentAsset")
    @Permission(HouseRentAssetPermission.HOUSERENTASSET_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseRentAsset", response = HouseRentAsset.class)
    public Tip updateHouseRentAsset(@PathVariable Long id, @RequestBody HouseRentAsset entity) {
        entity.setId(id);
        return SuccessTip.create(houseRentAssetService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseRentAsset", value = "delete HouseRentAsset")
    @Permission(HouseRentAssetPermission.HOUSERENTASSET_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseRentAsset")
    public Tip deleteHouseRentAsset(@PathVariable Long id) {
        return SuccessTip.create(houseRentAssetService.deleteMaster(id));
    }

    @Permission(HouseRentAssetPermission.HOUSERENTASSET_VIEW)
    @ApiOperation(value = "HouseRentAsset 列表信息", response = HouseRentAssetRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "communityId", dataType = "Long"),
            @ApiImplicitParam(name = "houseTypeId", dataType = "Long"),
            @ApiImplicitParam(name = "landlordId", dataType = "Long"),
            @ApiImplicitParam(name = "area", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "introducePicture", dataType = "String"),
            @ApiImplicitParam(name = "serverId", dataType = "Long"),
            @ApiImplicitParam(name = "cover", dataType = "String"),
            @ApiImplicitParam(name = "title", dataType = "String"),
            @ApiImplicitParam(name = "price", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "slide", dataType = "String"),
            @ApiImplicitParam(name = "describe", dataType = "String"),
            @ApiImplicitParam(name = "rentStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "rentTime", dataType = "Date"),
            @ApiImplicitParam(name = "shelvesTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "landlordSearch", dataType = "String"),
            @ApiImplicitParam(name = "serverSearch", dataType = "String")
    })
    public Tip queryHouseRentAssetPage(Page<HouseRentAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag

                                       @RequestParam(name = "assetId", required = false) Long assetId,

                                       @RequestParam(name = "communityId", required = false) Long communityId,

                                       @RequestParam(name = "houseTypeId", required = false) Long houseTypeId,

                                       @RequestParam(name = "landlordId", required = false) Long landlordId,

                                       @RequestParam(name = "area", required = false) BigDecimal area,

                                       @RequestParam(name = "introducePicture", required = false) String introducePicture,

                                       @RequestParam(name = "serverId", required = false) Long serverId,

                                       @RequestParam(name = "cover", required = false) String cover,

                                       @RequestParam(name = "title", required = false) String title,

                                       @RequestParam(name = "price", required = false) BigDecimal price,

                                       @RequestParam(name = "slide", required = false) String slide,

                                       @RequestParam(name = "describe", required = false) String describe,

                                       @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                       @RequestParam(name = "note", required = false) String note,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "rentTime", required = false) Date rentTime,
                                       @RequestParam(name = "landlordName",required = false) String landlordName,
                                       @RequestParam(name = "landlordRealName",required = false) String landlordRealName,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "shelvesTime", required = false) Date shelvesTime,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort,
                                       @RequestParam(name = "search", required = false) String search,
                                       @RequestParam(name = "landlordSearch", required = false) String landlordSearch,
                                       @RequestParam(name = "serverSearch", required = false) String serverSearch) {

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

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setAssetId(assetId);
        record.setCommunityId(communityId);
        record.setHouseTypeId(houseTypeId);
        record.setLandlordId(landlordId);
        record.setArea(area);
        record.setIntroducePicture(introducePicture);
        record.setServerId(serverId);
        record.setCover(cover);
        record.setTitle(title);
        record.setPrice(price);
        record.setSlide(slide);
        record.setRentDescribe(describe);
        record.setRentStatus(rentStatus);
        record.setNote(note);
        record.setRentTime(rentTime);
        record.setShelvesTime(shelvesTime);
        record.setLandlordName(landlordName);
        record.setLandlordRealName(landlordRealName);


//        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPage(page, record, tag, search, orderBy, null, null);
        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPageToWeb(page, record, tag, search, orderBy, null, null, landlordSearch, serverSearch);

        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }
}

