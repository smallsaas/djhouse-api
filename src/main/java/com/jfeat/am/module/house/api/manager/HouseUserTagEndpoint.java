
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserTagDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserTagRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTag;

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
 * @since 2022-08-29
 */
@RestController
@Api("HouseUserTag")
@RequestMapping("/api/crud/house/houseUserTag/houseUserTags")
public class HouseUserTagEndpoint {

    @Resource
    HouseUserTagService houseUserTagService;

    @Resource
    QueryHouseUserTagDao queryHouseUserTagDao;


    @BusinessLog(name = "HouseUserTag", value = "create HouseUserTag")
    @Permission(HouseUserTagPermission.HOUSEUSERTAG_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserTag", response = HouseUserTag.class)
    public Tip createHouseUserTag(@RequestBody HouseUserTag entity) {
        Integer affected = 0;
        try {
            affected = houseUserTagService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserTagPermission.HOUSEUSERTAG_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserTag", response = HouseUserTag.class)
    public Tip getHouseUserTag(@PathVariable Long id) {
        return SuccessTip.create(houseUserTagService.queryMasterModel(queryHouseUserTagDao, id));
    }

    @BusinessLog(name = "HouseUserTag", value = "update HouseUserTag")
    @Permission(HouseUserTagPermission.HOUSEUSERTAG_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserTag", response = HouseUserTag.class)
    public Tip updateHouseUserTag(@PathVariable Long id, @RequestBody HouseUserTag entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserTagService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserTag", value = "delete HouseUserTag")
    @Permission(HouseUserTagPermission.HOUSEUSERTAG_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserTag")
    public Tip deleteHouseUserTag(@PathVariable Long id) {
        return SuccessTip.create(houseUserTagService.deleteMaster(id));
    }

    @Permission(HouseUserTagPermission.HOUSEUSERTAG_VIEW)
    @ApiOperation(value = "HouseUserTag 列表信息", response = HouseUserTagRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "salesId", dataType = "Long"),
            @ApiImplicitParam(name = "tagName", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserTagPage(Page<HouseUserTagRecord> page,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     // for tag feature query
                                     @RequestParam(name = "tag", required = false) String tag,
                                     // end tag
                                     @RequestParam(name = "search", required = false) String search,

                                     @RequestParam(name = "orgId", required = false) Long orgId,

                                     @RequestParam(name = "salesId", required = false) Long salesId,

                                     @RequestParam(name = "tagName", required = false) String tagName,

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

        HouseUserTagRecord record = new HouseUserTagRecord();
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setSalesId(salesId);
        record.setTagName(tagName);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUserTagRecord> houseUserTagPage = queryHouseUserTagDao.findHouseUserTagPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserTagPage);

        return SuccessTip.create(page);
    }
}

