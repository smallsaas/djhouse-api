
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserNoteDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserNoteRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;

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
@Api("HouseUserNote")
@RequestMapping("/api/crud/house/houseUserNote/houseUserNotes")
public class HouseUserNoteEndpoint {

    @Resource
    HouseUserNoteService houseUserNoteService;

    @Resource
    QueryHouseUserNoteDao queryHouseUserNoteDao;


    @BusinessLog(name = "HouseUserNote", value = "create HouseUserNote")
    @Permission(HouseUserNotePermission.HOUSEUSERNOTE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserNote", response = HouseUserNote.class)
    public Tip createHouseUserNote(@RequestBody HouseUserNote entity) {
        Integer affected = 0;
        try {
            affected = houseUserNoteService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserNotePermission.HOUSEUSERNOTE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserNote", response = HouseUserNote.class)
    public Tip getHouseUserNote(@PathVariable Long id) {
        return SuccessTip.create(houseUserNoteService.queryMasterModel(queryHouseUserNoteDao, id));
    }

    @BusinessLog(name = "HouseUserNote", value = "update HouseUserNote")
    @Permission(HouseUserNotePermission.HOUSEUSERNOTE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserNote", response = HouseUserNote.class)
    public Tip updateHouseUserNote(@PathVariable Long id, @RequestBody HouseUserNote entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserNoteService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserNote", value = "delete HouseUserNote")
    @Permission(HouseUserNotePermission.HOUSEUSERNOTE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserNote")
    public Tip deleteHouseUserNote(@PathVariable Long id) {
        return SuccessTip.create(houseUserNoteService.deleteMaster(id));
    }

    @Permission(HouseUserNotePermission.HOUSEUSERNOTE_VIEW)
    @ApiOperation(value = "HouseUserNote 列表信息", response = HouseUserNoteRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "salesId", dataType = "Long"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserNotePage(Page<HouseUserNoteRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      // for tag feature query
                                      @RequestParam(name = "tag", required = false) String tag,
                                      // end tag
                                      @RequestParam(name = "search", required = false) String search,

                                      @RequestParam(name = "userId", required = false) Long userId,

                                      @RequestParam(name = "salesId", required = false) Long salesId,

                                      @RequestParam(name = "note", required = false) String note,

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

        HouseUserNoteRecord record = new HouseUserNoteRecord();
        record.setUserId(userId);
        record.setSalesId(salesId);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUserNoteRecord> houseUserNotePage = queryHouseUserNoteDao.findHouseUserNotePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserNotePage);

        return SuccessTip.create(page);
    }
}

