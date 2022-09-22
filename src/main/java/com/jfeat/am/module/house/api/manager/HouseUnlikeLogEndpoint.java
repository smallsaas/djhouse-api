
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUnlikeLogDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUnlikeLogRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;

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
 * @since 2022-09-19
 */
@RestController
@Api("HouseUnlikeLog")
@RequestMapping("/api/crud/house/houseUnlikeLog/houseUnlikeLogs")
public class HouseUnlikeLogEndpoint {

    @Resource
    HouseUnlikeLogService houseUnlikeLogService;

    @Resource
    QueryHouseUnlikeLogDao queryHouseUnlikeLogDao;


    @BusinessLog(name = "HouseUnlikeLog", value = "create HouseUnlikeLog")
    @Permission(HouseUnlikeLogPermission.HOUSEUNLIKELOG_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUnlikeLog", response = HouseUnlikeLog.class)
    public Tip createHouseUnlikeLog(@RequestBody HouseUnlikeLog entity) {
        Integer affected = 0;
        try {
            affected = houseUnlikeLogService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUnlikeLogPermission.HOUSEUNLIKELOG_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUnlikeLog", response = HouseUnlikeLog.class)
    public Tip getHouseUnlikeLog(@PathVariable Long id) {
        return SuccessTip.create(houseUnlikeLogService.queryMasterModel(queryHouseUnlikeLogDao, id));
    }

    @BusinessLog(name = "HouseUnlikeLog", value = "update HouseUnlikeLog")
    @Permission(HouseUnlikeLogPermission.HOUSEUNLIKELOG_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUnlikeLog", response = HouseUnlikeLog.class)
    public Tip updateHouseUnlikeLog(@PathVariable Long id, @RequestBody HouseUnlikeLog entity) {
        entity.setId(id);
        return SuccessTip.create(houseUnlikeLogService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUnlikeLog", value = "delete HouseUnlikeLog")
    @Permission(HouseUnlikeLogPermission.HOUSEUNLIKELOG_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUnlikeLog")
    public Tip deleteHouseUnlikeLog(@PathVariable Long id) {
        return SuccessTip.create(houseUnlikeLogService.deleteMaster(id));
    }

    @Permission(HouseUnlikeLogPermission.HOUSEUNLIKELOG_VIEW)
    @ApiOperation(value = "HouseUnlikeLog 列表信息", response = HouseUnlikeLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUnlikeLogPage(Page<HouseUnlikeLogRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "userId", required = false) Long userId,

                                       @RequestParam(name = "assetId", required = false) Long assetId,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "createTime", required = false) Date createTime,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "updateTime", required = false) Date updateTime,
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

        HouseUnlikeLogRecord record = new HouseUnlikeLogRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUnlikeLogRecord> houseUnlikeLogPage = queryHouseUnlikeLogDao.findHouseUnlikeLogPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUnlikeLogPage);

        return SuccessTip.create(page);
    }
}

