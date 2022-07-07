
package com.jfeat.am.module.house.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
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

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import springfox.documentation.spring.web.json.Json;

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

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;



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
            @ApiImplicitParam(name = "rentStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "rentTitle", dataType = "String"),
            @ApiImplicitParam(name = "rentPrice", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "rentDescribe", dataType = "String"),
            @ApiImplicitParam(name = "rentTags", dataType = "String"),
            @ApiImplicitParam(name = "slideshow", dataType = "String"),
            @ApiImplicitParam(name = "rentTime", dataType = "Date"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "clashUserId", dataType = "Long"),
            @ApiImplicitParam(name = "clashDescribe", dataType = "String"),
            @ApiImplicitParam(name = "clashCertificate", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
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

                                       @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                       @RequestParam(name = "rentTitle", required = false) String rentTitle,

                                       @RequestParam(name = "rentPrice", required = false) BigDecimal rentPrice,

                                       @RequestParam(name = "rentDescribe", required = false) String rentDescribe,

                                       @RequestParam(name = "rentTags", required = false) String rentTags,

                                       @RequestParam(name = "slideshow", required = false) String slideshow,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "rentTime", required = false) Date rentTime,

                                       @RequestParam(name = "note", required = false) String note,

                                       @RequestParam(name = "clashUserId", required = false) Long clashUserId,

                                       @RequestParam(name = "clashDescribe", required = false) String clashDescribe,

                                       @RequestParam(name = "clashCertificate", required = false) String clashCertificate,

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

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setRentStatus(rentStatus);
        record.setRentTitle(rentTitle);
        record.setRentPrice(rentPrice);
        record.setRentDescribe(rentDescribe);
        record.setRentTags(rentTags);
        record.setSlideshow(slideshow);
        record.setRentTime(rentTime);
        record.setNote(note);
        record.setClashUserId(clashUserId);
        record.setClashDescribe(clashDescribe);
        record.setClashCertificate(clashCertificate);
        record.setCreateTime((Data) createTime);


        List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, tag, search, orderBy, null, null);
        for (int i=0;i<houseUserAssetPage.size();i++){
            Long id = houseUserAssetPage.get(i).getId();
            HouseUserAssetModel houseAssetRecord = houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id);
            houseUserAssetPage.get(i).setExtra(houseAssetRecord.getExtra());
        }

        page.setRecords(houseUserAssetPage);

        return SuccessTip.create(page);
    }


    //   获取出租转态 0为不出租 1为出租
    @GetMapping("/rent/{status}")
    public Tip getALlRentAsset(Page<HouseAssetRecord> page,
                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(name = "username",required = false) String username,
                               @RequestParam(name = "search",required = false) String search,
                               @PathVariable Integer status) {
        List<HouseAssetRecord>houseAssetRecordList =  queryHouseAssetDao.queryUserAssetRent(status,username,search);
        for (int i=0;i<houseAssetRecordList.size();i++){
            HouseUserAsset houseUserAsset = queryHouseUserAssetDao.queryHouseUserAssetByAssetId(houseAssetRecordList.get(i).getId());
            houseAssetRecordList.get(i).setRentDescribe(houseUserAsset.getRentDescribe());
            houseAssetRecordList.get(i).setRentPrice(houseUserAsset.getRentPrice());
            houseAssetRecordList.get(i).setSlideshow(houseUserAsset.getSlideshow());
            houseAssetRecordList.get(i).setRentTime(houseUserAsset.getRentTime());
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(houseAssetRecordList);
        return SuccessTip.create(page);
    }



    @GetMapping("/rent/details/{id}")
    public Tip getALlRentAsset(@PathVariable("id") Long id) {
        HouseUserAssetModel houseAssetRecord = houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id);
        if (houseAssetRecord!=null){
            HousePropertyBuildingUnit housePropertyBuildingUnit =  queryHousePropertyBuildingUnitDao.queryMasterModel(houseAssetRecord.getUnitId());
            List<Product> productList= queryHouseDecoratePlanDao.queryProductListByDesignModel(housePropertyBuildingUnit.getDesignModelId());
            if (productList!=null){
                houseAssetRecord.setProductList(productList);
            }
        }
        return SuccessTip.create(houseAssetRecord);
    }


    @GetMapping("/clash")
    public Tip getALlClash(Page<HouseUserAsset> page,
                           @RequestParam(name = "username",required = false) String username,
                           @RequestParam(name = "search" ,required = false) String search,
                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<HouseUserAsset> houseUserAssetList = queryHouseUserAssetDao.queryClashUserAsset(username,search);
        for (int i = 0; i < houseUserAssetList.size(); i++) {
            EndpointUser endpointUser = queryEndpointUserDao.queryMasterModel(houseUserAssetList.get(i).getClashUserId());
            if (endpointUser != null) {
                houseUserAssetList.get(i).setClashUserName(endpointUser.getName());
                houseUserAssetList.get(i).setClashUserPhone(endpointUser.getPhone());
                houseUserAssetList.get(i).setClashUserAvatar(endpointUser.getAvatar());
            }

        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(houseUserAssetList);
        return SuccessTip.create(page);
    }

    //    确认冲突
    @DeleteMapping("/clash/confirm/{assetId}")
    public Tip confirmClashInfo(@PathVariable("assetId") Long assetId) {
        HouseUserAsset houseUserAsset = queryHouseUserAssetDao.queryBasicUserAsset(assetId);
        HouseUserAsset newHouseUserAsset = new HouseUserAsset();
        newHouseUserAsset.setUserId(houseUserAsset.getClashUserId());
        newHouseUserAsset.setRentStatus(0);
        newHouseUserAsset.setClashUserId(null);
        newHouseUserAsset.setClashDescribe(null);
        newHouseUserAsset.setClashCertificate(null);
        Integer effect = queryHouseUserAssetDao.updateClashAssetByAssetId(assetId, newHouseUserAsset);
        return SuccessTip.create(effect);
    }

    //    取消冲突
    @DeleteMapping("/clash/cancel/{assetId}")
    public Tip cancelClashInfo(@PathVariable("assetId") Long assetId) {
        HouseUserAsset newHouseUserAsset = new HouseUserAsset();
        newHouseUserAsset.setClashUserId(null);
        newHouseUserAsset.setClashDescribe(null);
        newHouseUserAsset.setClashCertificate(null);
        Integer effect = queryHouseUserAssetDao.updateClashAssetByAssetId(assetId, newHouseUserAsset);
        return SuccessTip.create(effect);
    }


}

