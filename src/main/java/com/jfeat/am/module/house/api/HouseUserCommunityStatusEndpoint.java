
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;

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
 * @since 2022-06-07
 */
@RestController
@Api("HouseUserCommunityStatus")
@RequestMapping("/api/crud/house/houseUserCommunityStatus/houseUserCommunityStatuses")
public class HouseUserCommunityStatusEndpoint {

    @Resource
    HouseUserCommunityStatusService houseUserCommunityStatusService;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;


    @BusinessLog(name = "HouseUserCommunityStatus", value = "create HouseUserCommunityStatus")
    @Permission(HouseUserCommunityStatusPermission.HOUSEUSERCOMMUNITYSTATUS_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserCommunityStatus", response = HouseUserCommunityStatus.class)
    public Tip createHouseUserCommunityStatus(@RequestBody HouseUserCommunityStatus entity) {
        Integer affected = 0;
        try {
            affected = houseUserCommunityStatusService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserCommunityStatusPermission.HOUSEUSERCOMMUNITYSTATUS_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserCommunityStatus", response = HouseUserCommunityStatus.class)
    public Tip getHouseUserCommunityStatus(@PathVariable Long id) {
        return SuccessTip.create(houseUserCommunityStatusService.queryMasterModel(queryHouseUserCommunityStatusDao, id));
    }

    @BusinessLog(name = "HouseUserCommunityStatus", value = "update HouseUserCommunityStatus")
    @Permission(HouseUserCommunityStatusPermission.HOUSEUSERCOMMUNITYSTATUS_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserCommunityStatus", response = HouseUserCommunityStatus.class)
    public Tip updateHouseUserCommunityStatus(@PathVariable Long id, @RequestBody HouseUserCommunityStatus entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserCommunityStatusService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserCommunityStatus", value = "delete HouseUserCommunityStatus")
    @Permission(HouseUserCommunityStatusPermission.HOUSEUSERCOMMUNITYSTATUS_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserCommunityStatus")
    public Tip deleteHouseUserCommunityStatus(@PathVariable Long id) {
        return SuccessTip.create(houseUserCommunityStatusService.deleteMaster(id));
    }

    @Permission(HouseUserCommunityStatusPermission.HOUSEUSERCOMMUNITYSTATUS_VIEW)
    @ApiOperation(value = "HouseUserCommunityStatus 列表信息", response = HouseUserCommunityStatusRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "communityId", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserCommunityStatusPage(Page<HouseUserCommunityStatusRecord> page,
                                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                 // for tag feature query
                                                 @RequestParam(name = "tag", required = false) String tag,
                                                 // end tag
                                                 @RequestParam(name = "search", required = false) String search,

                                                 @RequestParam(name = "communityId", required = false) Long communityId,

                                                 @RequestParam(name = "userId", required = false) Long userId,
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

        HouseUserCommunityStatusRecord record = new HouseUserCommunityStatusRecord();
        record.setCommunityId(communityId);
        record.setUserId(userId);


        List<HouseUserCommunityStatusRecord> houseUserCommunityStatusPage = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserCommunityStatusPage);

        return SuccessTip.create(page);
    }
}

