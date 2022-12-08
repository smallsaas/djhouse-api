
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetHistoryDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetHistoryRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAssetHistory;

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
 * @since 2022-12-08
 */
@RestController
@Api("HouseUserAssetHistory")
@RequestMapping("/api/crud/house/houseUserAssetHistory/houseUserAssetHistoryies")
public class HouseUserAssetHistoryEndpoint {

    @Resource
    HouseUserAssetHistoryService houseUserAssetHistoryService;

    @Resource
    QueryHouseUserAssetHistoryDao queryHouseUserAssetHistoryDao;


    @BusinessLog(name = "HouseUserAssetHistory", value = "create HouseUserAssetHistory")
    @Permission(HouseUserAssetHistoryPermission.HOUSEUSERASSETHISTORY_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserAssetHistory", response = HouseUserAssetHistory.class)
    public Tip createHouseUserAssetHistory(@RequestBody HouseUserAssetHistory entity) {
        Integer affected = 0;
        try {
            affected = houseUserAssetHistoryService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserAssetHistoryPermission.HOUSEUSERASSETHISTORY_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserAssetHistory", response = HouseUserAssetHistory.class)
    public Tip getHouseUserAssetHistory(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetHistoryService.queryMasterModel(queryHouseUserAssetHistoryDao, id));
    }

    @BusinessLog(name = "HouseUserAssetHistory", value = "update HouseUserAssetHistory")
    @Permission(HouseUserAssetHistoryPermission.HOUSEUSERASSETHISTORY_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserAssetHistory", response = HouseUserAssetHistory.class)
    public Tip updateHouseUserAssetHistory(@PathVariable Long id, @RequestBody HouseUserAssetHistory entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserAssetHistoryService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserAssetHistory", value = "delete HouseUserAssetHistory")
    @Permission(HouseUserAssetHistoryPermission.HOUSEUSERASSETHISTORY_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserAssetHistory")
    public Tip deleteHouseUserAssetHistory(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetHistoryService.deleteMaster(id));
    }

    @Permission(HouseUserAssetHistoryPermission.HOUSEUSERASSETHISTORY_VIEW)
    @ApiOperation(value = "HouseUserAssetHistory 列表信息", response = HouseUserAssetHistoryRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "trust", dataType = "Integer"),
            @ApiImplicitParam(name = "rentStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "finalFlag", dataType = "Integer"),
            @ApiImplicitParam(name = "userType", dataType = "Integer"),
            @ApiImplicitParam(name = "locked", dataType = "Integer"),
            @ApiImplicitParam(name = "unlike", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserAssetHistoryPage(Page<HouseUserAssetHistoryRecord> page,
                                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              // for tag feature query
                                              @RequestParam(name = "tag", required = false) String tag,
                                              // end tag
                                              @RequestParam(name = "search", required = false) String search,

                                              @RequestParam(name = "userId", required = false) Long userId,

                                              @RequestParam(name = "assetId", required = false) Long assetId,

                                              @RequestParam(name = "trust", required = false) Integer trust,

                                              @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(name = "createTime", required = false) Date createTime,

                                              @RequestParam(name = "note", required = false) String note,

                                              @RequestParam(name = "finalFlag", required = false) Integer finalFlag,

                                              @RequestParam(name = "userType", required = false) Integer userType,

                                              @RequestParam(name = "locked", required = false) Integer locked,

                                              @RequestParam(name = "unlike", required = false) Integer unlike,
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

        HouseUserAssetHistoryRecord record = new HouseUserAssetHistoryRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setTrust(trust);
        record.setRentStatus(rentStatus);
        record.setCreateTime(createTime);
        record.setNote(note);
        record.setFinalFlag(finalFlag);
        record.setUserType(userType);
        record.setLocked(locked);
        record.setUnlike(unlike);


        List<HouseUserAssetHistoryRecord> houseUserAssetHistoryPage = queryHouseUserAssetHistoryDao.findHouseUserAssetHistoryPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserAssetHistoryPage);

        return SuccessTip.create(page);
    }
}

