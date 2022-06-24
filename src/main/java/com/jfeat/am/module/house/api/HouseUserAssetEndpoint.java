
package com.jfeat.am.module.house.api;


import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDecoratePlanDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
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
import java.util.ArrayList;
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

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;



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

                                       @RequestParam(name = "username" ,required = false) String username,

                                       @RequestParam(name = "userPhone",required = false) String userPhone,

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
        record.setUsername(username);
        record.setUserPhone(userPhone);


        List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, tag, search, orderBy, null, null);
        for (int i = 0; i < houseUserAssetPage.size(); i++) {

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
//        List<HouseUserAsset> houseUserAssetList = queryHouseUserAssetDao.queryALlRentStatus(status);
//        List<HouseAssetRecord> houseAssetRecordList = new ArrayList<>();
//        for (int i=0;i<houseUserAssetList.size();i++) {
//            HouseAssetRecord houseAssetRecord = queryHouseAssetDao.queryHouseAssetDetails(houseUserAssetList.get(i).getAssetId());
//            EndpointUser endpointUser = queryEndpointUserDao.queryMasterModel(houseUserAssetList.get(i).getUserId());
//
//            if (houseAssetRecord != null && endpointUser!=null) {
//
//                houseAssetRecord.setPhone(endpointUser.getPhone());
//                houseAssetRecord.setUsername(endpointUser.getName());
//                houseAssetRecord.setUserAvatar(endpointUser.getAvatar());
//                houseAssetRecordList.add(houseAssetRecord);
//            }
//        }
        List<HouseAssetRecord>houseAssetRecordList =  queryHouseAssetDao.queryUserAssetRent(status,username,search);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(houseAssetRecordList);
        return SuccessTip.create(page);
    }


    @GetMapping("/rent/details/{id}")
    public Tip getALlRentAsset(@PathVariable("id") Long id) {
        HouseAssetRecord houseAssetRecord = queryHouseAssetDao.queryHouseAssetDetails(id);
        houseAssetRecord.setDecoratePlanProductList(queryHouseDecoratePlanDao.queryProductListByDesignModel(houseAssetRecord.getDesignModelId()));
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
        newHouseUserAsset.setRentStatus(false);
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

