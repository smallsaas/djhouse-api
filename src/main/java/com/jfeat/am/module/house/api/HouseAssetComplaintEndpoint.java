
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetComplaintDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;

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
 * @since 2022-07-22
 */
@RestController
@Api("HouseAssetComplaint")
@RequestMapping("/api/crud/house/houseAssetComplaint/houseAssetComplaints")
public class HouseAssetComplaintEndpoint {

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    QueryHouseAssetComplaintDao queryHouseAssetComplaintDao;


    @BusinessLog(name = "HouseAssetComplaint", value = "create HouseAssetComplaint")
    @Permission(HouseAssetComplaintPermission.HOUSEASSETCOMPLAINT_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetComplaint", response = HouseAssetComplaint.class)
    public Tip createHouseAssetComplaint(@RequestBody HouseAssetComplaint entity) {
        Integer affected = 0;
        try {
            affected = houseAssetComplaintService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetComplaintPermission.HOUSEASSETCOMPLAINT_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetComplaint", response = HouseAssetComplaint.class)
    public Tip getHouseAssetComplaint(@PathVariable Long id) {
        return SuccessTip.create(houseAssetComplaintService.queryMasterModel(queryHouseAssetComplaintDao, id));
    }

    @BusinessLog(name = "HouseAssetComplaint", value = "update HouseAssetComplaint")
    @Permission(HouseAssetComplaintPermission.HOUSEASSETCOMPLAINT_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetComplaint", response = HouseAssetComplaint.class)
    public Tip updateHouseAssetComplaint(@PathVariable Long id, @RequestBody HouseAssetComplaint entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetComplaintService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetComplaint", value = "delete HouseAssetComplaint")
    @Permission(HouseAssetComplaintPermission.HOUSEASSETCOMPLAINT_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetComplaint")
    public Tip deleteHouseAssetComplaint(@PathVariable Long id) {
        return SuccessTip.create(houseAssetComplaintService.deleteMaster(id));
    }

    @Permission(HouseAssetComplaintPermission.HOUSEASSETCOMPLAINT_VIEW)
    @ApiOperation(value = "HouseAssetComplaint 列表信息", response = HouseAssetComplaintRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "oldUserId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "clashCertificate", dataType = "String"),
            @ApiImplicitParam(name = "clashDescribe", dataType = "String"),
            @ApiImplicitParam(name = "solveStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "solveTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetComplaintPage(Page<HouseAssetComplaintRecord> page,
                                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                            // for tag feature query
                                            @RequestParam(name = "tag", required = false) String tag,
                                            // end tag
                                            @RequestParam(name = "search", required = false) String search,

                                            @RequestParam(name = "userId", required = false) Long userId,

                                            @RequestParam(name = "oldUserId", required = false) Long oldUserId,

                                            @RequestParam(name = "assetId", required = false) Long assetId,

                                            @RequestParam(name = "clashCertificate", required = false) String clashCertificate,

                                            @RequestParam(name = "clashDescribe", required = false) String clashDescribe,

                                            @RequestParam(name = "solveStatus", required = false) Integer solveStatus,

                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam(name = "createTime", required = false) Date createTime,

                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam(name = "solveTime", required = false) Date solveTime,
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

        HouseAssetComplaintRecord record = new HouseAssetComplaintRecord();
        record.setUserId(userId);
        record.setOldUserId(oldUserId);
        record.setAssetId(assetId);
        record.setClashCertificate(clashCertificate);
        record.setClashDescribe(clashDescribe);
        record.setSolveStatus(solveStatus);
        record.setCreateTime(createTime);
        record.setSolveTime(solveTime);


        List<HouseAssetComplaintRecord> houseAssetComplaintPage = queryHouseAssetComplaintDao.findHouseAssetComplaintPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetComplaintPage);

        return SuccessTip.create(page);
    }
}

