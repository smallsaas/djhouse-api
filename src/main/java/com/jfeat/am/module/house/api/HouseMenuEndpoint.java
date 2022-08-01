
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;

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
 * @since 2022-08-01
 */
@RestController
@Api("HouseMenu")
@RequestMapping("/api/crud/house/houseMenu/houseMenus")
public class HouseMenuEndpoint {

    @Resource
    HouseMenuService houseMenuService;

    @Resource
    QueryHouseMenuDao queryHouseMenuDao;


    @BusinessLog(name = "HouseMenu", value = "create HouseMenu")
    @Permission(HouseMenuPermission.HOUSEMENU_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseMenu", response = HouseMenu.class)
    public Tip createHouseMenu(@RequestBody HouseMenu entity) {
        Integer affected = 0;
        try {
            affected = houseMenuService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseMenuPermission.HOUSEMENU_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseMenu", response = HouseMenu.class)
    public Tip getHouseMenu(@PathVariable Long id) {
        return SuccessTip.create(houseMenuService.queryMasterModel(queryHouseMenuDao, id));
    }

    @BusinessLog(name = "HouseMenu", value = "update HouseMenu")
    @Permission(HouseMenuPermission.HOUSEMENU_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseMenu", response = HouseMenu.class)
    public Tip updateHouseMenu(@PathVariable Long id, @RequestBody HouseMenu entity) {
        entity.setId(id);
        return SuccessTip.create(houseMenuService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseMenu", value = "delete HouseMenu")
    @Permission(HouseMenuPermission.HOUSEMENU_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseMenu")
    public Tip deleteHouseMenu(@PathVariable Long id) {
        return SuccessTip.create(houseMenuService.deleteMaster(id));
    }

    @Permission(HouseMenuPermission.HOUSEMENU_VIEW)
    @ApiOperation(value = "HouseMenu 列表信息", response = HouseMenuRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "url", dataType = "String"),
            @ApiImplicitParam(name = "path", dataType = "String"),
            @ApiImplicitParam(name = "component", dataType = "String"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "iconCls", dataType = "String"),
            @ApiImplicitParam(name = "requireAuth", dataType = "Integer"),
            @ApiImplicitParam(name = "parentId", dataType = "Long"),
            @ApiImplicitParam(name = "enabled", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseMenuPage(Page<HouseMenuRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  // for tag feature query
                                  @RequestParam(name = "tag", required = false) String tag,
                                  // end tag
                                  @RequestParam(name = "search", required = false) String search,

                                  @RequestParam(name = "url", required = false) String url,

                                  @RequestParam(name = "path", required = false) String path,

                                  @RequestParam(name = "component", required = false) String component,

                                  @RequestParam(name = "name", required = false) String name,

                                  @RequestParam(name = "iconCls", required = false) String iconCls,

                                  @RequestParam(name = "requireAuth", required = false) Integer requireAuth,

                                  @RequestParam(name = "parentId", required = false) Long parentId,

                                  @RequestParam(name = "enabled", required = false) Integer enabled,
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

        HouseMenuRecord record = new HouseMenuRecord();
        record.setUrl(url);
        record.setPath(path);
        record.setComponent(component);
        record.setName(name);
        record.setIconCls(iconCls);
        record.setRequireAuth(requireAuth);
        record.setParentId(parentId);
        record.setEnabled(enabled);


        List<HouseMenuRecord> houseMenuPage = queryHouseMenuDao.findHouseMenuPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseMenuPage);

        return SuccessTip.create(page);
    }
}

