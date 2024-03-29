
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseTenantMenuDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseTenantMenuRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;

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
 * @since 2022-08-12
 */
@RestController
@Api("HouseTenantMenu")
@RequestMapping("/api/crud/house/houseTenantMenu/houseTenantMenus")
public class HouseTenantMenuEndpoint {

    @Resource
    HouseTenantMenuService houseTenantMenuService;

    @Resource
    QueryHouseTenantMenuDao queryHouseTenantMenuDao;


    @BusinessLog(name = "HouseTenantMenu", value = "create HouseTenantMenu")
    @Permission(HouseTenantMenuPermission.HOUSETENANTMENU_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseTenantMenu", response = HouseTenantMenu.class)
    public Tip createHouseTenantMenu(@RequestBody HouseTenantMenu entity) {
        Integer affected = 0;
        try {
            affected = houseTenantMenuService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseTenantMenuPermission.HOUSETENANTMENU_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseTenantMenu", response = HouseTenantMenu.class)
    public Tip getHouseTenantMenu(@PathVariable Long id) {
        return SuccessTip.create(houseTenantMenuService.queryMasterModel(queryHouseTenantMenuDao, id));
    }

    @BusinessLog(name = "HouseTenantMenu", value = "update HouseTenantMenu")
    @Permission(HouseTenantMenuPermission.HOUSETENANTMENU_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseTenantMenu", response = HouseTenantMenu.class)
    public Tip updateHouseTenantMenu(@PathVariable Long id, @RequestBody HouseTenantMenu entity) {
        entity.setId(id);
        return SuccessTip.create(houseTenantMenuService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseTenantMenu", value = "delete HouseTenantMenu")
    @Permission(HouseTenantMenuPermission.HOUSETENANTMENU_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseTenantMenu")
    public Tip deleteHouseTenantMenu(@PathVariable Long id) {
        return SuccessTip.create(houseTenantMenuService.deleteMaster(id));
    }

    @Permission(HouseTenantMenuPermission.HOUSETENANTMENU_VIEW)
    @ApiOperation(value = "HouseTenantMenu 列表信息", response = HouseTenantMenuRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "menuId", dataType = "Long"),
            @ApiImplicitParam(name = "enabled", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseTenantMenuPage(Page<HouseTenantMenuRecord> page,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        // for tag feature query
                                        @RequestParam(name = "tag", required = false) String tag,
                                        // end tag
                                        @RequestParam(name = "search", required = false) String search,

                                        @RequestParam(name = "orgId", required = false) Long orgId,

                                        @RequestParam(name = "menuId", required = false) Long menuId,

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

        HouseTenantMenuRecord record = new HouseTenantMenuRecord();
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setMenuId(menuId);
        record.setEnabled(enabled);


        List<HouseTenantMenuRecord> houseTenantMenuPage = queryHouseTenantMenuDao.findHouseTenantMenuPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseTenantMenuPage);

        return SuccessTip.create(page);
    }
}

