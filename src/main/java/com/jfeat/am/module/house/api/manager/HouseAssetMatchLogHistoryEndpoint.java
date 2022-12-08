
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogHistoryDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogHistoryRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLogHistory;

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
@Api("HouseAssetMatchLogHistory")
@RequestMapping("/api/crud/house/houseAssetMatchLogHistory/houseAssetMatchLogHistoryies")
public class HouseAssetMatchLogHistoryEndpoint {

    @Resource
    HouseAssetMatchLogHistoryService houseAssetMatchLogHistoryService;

    @Resource
    QueryHouseAssetMatchLogHistoryDao queryHouseAssetMatchLogHistoryDao;


    @BusinessLog(name = "HouseAssetMatchLogHistory", value = "create HouseAssetMatchLogHistory")
    @Permission(HouseAssetMatchLogHistoryPermission.HOUSEASSETMATCHLOGHISTORY_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetMatchLogHistory", response = HouseAssetMatchLogHistory.class)
    public Tip createHouseAssetMatchLogHistory(@RequestBody HouseAssetMatchLogHistory entity) {
        Integer affected = 0;
        try {
            affected = houseAssetMatchLogHistoryService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetMatchLogHistoryPermission.HOUSEASSETMATCHLOGHISTORY_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetMatchLogHistory", response = HouseAssetMatchLogHistory.class)
    public Tip getHouseAssetMatchLogHistory(@PathVariable Long id) {
        return SuccessTip.create(houseAssetMatchLogHistoryService.queryMasterModel(queryHouseAssetMatchLogHistoryDao, id));
    }

    @BusinessLog(name = "HouseAssetMatchLogHistory", value = "update HouseAssetMatchLogHistory")
    @Permission(HouseAssetMatchLogHistoryPermission.HOUSEASSETMATCHLOGHISTORY_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetMatchLogHistory", response = HouseAssetMatchLogHistory.class)
    public Tip updateHouseAssetMatchLogHistory(@PathVariable Long id, @RequestBody HouseAssetMatchLogHistory entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetMatchLogHistoryService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetMatchLogHistory", value = "delete HouseAssetMatchLogHistory")
    @Permission(HouseAssetMatchLogHistoryPermission.HOUSEASSETMATCHLOGHISTORY_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetMatchLogHistory")
    public Tip deleteHouseAssetMatchLogHistory(@PathVariable Long id) {
        return SuccessTip.create(houseAssetMatchLogHistoryService.deleteMaster(id));
    }

    @Permission(HouseAssetMatchLogHistoryPermission.HOUSEASSETMATCHLOGHISTORY_VIEW)
    @ApiOperation(value = "HouseAssetMatchLogHistory 列表信息", response = HouseAssetMatchLogHistoryRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "ownerUserId", dataType = "Long"),
            @ApiImplicitParam(name = "ownerAssetId", dataType = "Long"),
            @ApiImplicitParam(name = "matchedUserId", dataType = "Long"),
            @ApiImplicitParam(name = "mathchedAssetId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetMatchLogHistoryPage(Page<HouseAssetMatchLogHistoryRecord> page,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                  // for tag feature query
                                                  @RequestParam(name = "tag", required = false) String tag,
                                                  // end tag
                                                  @RequestParam(name = "search", required = false) String search,

                                                  @RequestParam(name = "ownerUserId", required = false) Long ownerUserId,

                                                  @RequestParam(name = "ownerAssetId", required = false) Long ownerAssetId,

                                                  @RequestParam(name = "matchedUserId", required = false) Long matchedUserId,

                                                  @RequestParam(name = "mathchedAssetId", required = false) Long mathchedAssetId,

                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  @RequestParam(name = "createTime", required = false) Date createTime,

                                                  @RequestParam(name = "orgId", required = false) Long orgId,

                                                  @RequestParam(name = "status", required = false) Integer status,
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

        HouseAssetMatchLogHistoryRecord record = new HouseAssetMatchLogHistoryRecord();
        record.setOwnerUserId(ownerUserId);
        record.setOwnerAssetId(ownerAssetId);
        record.setMatchedUserId(matchedUserId);
        record.setMathchedAssetId(mathchedAssetId);
        record.setCreateTime(createTime);
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setStatus(status);


        List<HouseAssetMatchLogHistoryRecord> houseAssetMatchLogHistoryPage = queryHouseAssetMatchLogHistoryDao.findHouseAssetMatchLogHistoryPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetMatchLogHistoryPage);

        return SuccessTip.create(page);
    }
}

