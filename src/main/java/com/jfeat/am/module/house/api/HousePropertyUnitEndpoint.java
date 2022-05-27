
package com.jfeat.am.module.house.api;


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
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyUnitDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyUnitRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUnit;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-05-23
 */
@RestController
@Api("HousePropertyUnit")
@RequestMapping("/api/crud/house/housePropertyUnit/housePropertyUnits")
public class HousePropertyUnitEndpoint {

    @Resource
    HousePropertyUnitService housePropertyUnitService;

    @Resource
    QueryHousePropertyUnitDao queryHousePropertyUnitDao;


    @BusinessLog(name = "HousePropertyUnit", value = "create HousePropertyUnit")
    @Permission(HousePropertyUnitPermission.HOUSEPROPERTYUNIT_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyUnit", response = HousePropertyUnit.class)
    public Tip createHousePropertyUnit(@RequestBody HousePropertyUnit entity) {
        Integer affected = 0;
        try {
            affected = housePropertyUnitService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HousePropertyUnitPermission.HOUSEPROPERTYUNIT_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyUnit", response = HousePropertyUnit.class)
    public Tip getHousePropertyUnit(@PathVariable Long id) {
        return SuccessTip.create(housePropertyUnitService.queryMasterModel(queryHousePropertyUnitDao, id));
    }

    @BusinessLog(name = "HousePropertyUnit", value = "update HousePropertyUnit")
    @Permission(HousePropertyUnitPermission.HOUSEPROPERTYUNIT_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyUnit", response = HousePropertyUnit.class)
    public Tip updateHousePropertyUnit(@PathVariable Long id, @RequestBody HousePropertyUnit entity) {
        entity.setId(id);
        return SuccessTip.create(housePropertyUnitService.updateMaster(entity));
    }

    @BusinessLog(name = "HousePropertyUnit", value = "delete HousePropertyUnit")
    @Permission(HousePropertyUnitPermission.HOUSEPROPERTYUNIT_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyUnit")
    public Tip deleteHousePropertyUnit(@PathVariable Long id) {
        return SuccessTip.create(housePropertyUnitService.deleteMaster(id));
    }

    @Permission(HousePropertyUnitPermission.HOUSEPROPERTYUNIT_VIEW)
    @ApiOperation(value = "HousePropertyUnit 列表信息", response = HousePropertyUnitRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "buildingId", dataType = "Long"),
            @ApiImplicitParam(name = "number", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHousePropertyUnitPage(Page<HousePropertyUnitRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          // for tag feature query
                                          @RequestParam(name = "tag", required = false) String tag,
                                          // end tag
                                          @RequestParam(name = "search", required = false) String search,

                                          @RequestParam(name = "userId", required = false) Long userId,

                                          @RequestParam(name = "buildingId", required = false) Long buildingId,

                                          @RequestParam(name = "number", required = false) String number,
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

        HousePropertyUnitRecord record = new HousePropertyUnitRecord();
        record.setUserId(userId);
        record.setBuildingId(buildingId);
        record.setNumber(number);
        List<HousePropertyUnitRecord> housePropertyUnitPage = queryHousePropertyUnitDao.findHousePropertyUnitPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(housePropertyUnitPage);

        return SuccessTip.create(page);
    }
}

