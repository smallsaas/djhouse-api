package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseSupportFacilitiesTypePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSupportFacilitiesTypeDao;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSupportFacilitiesTypeModel;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.crud.plus.META;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Api("HouseSupportFacilities")
@RequestMapping("/api/u/house/sales/houseSupportFacilitiesType")
public class HouseSupportFacilitiesTypeManageEndpoint {

    @Resource
    HouseSupportFacilitiesTypeOverModelService houseSupportFacilitiesTypeOverModelService;

    @Resource
    QueryHouseSupportFacilitiesTypeDao queryHouseSupportFacilitiesTypeDao;


    @PostMapping
    @ApiOperation(value = "新建 HouseSupportFacilitiesType", response = HouseSupportFacilitiesTypeModel.class)
    public Tip createHouseSupportFacilitiesType(@RequestBody HouseSupportFacilitiesTypeModel entity) {
        Integer affected = 0;
        try {
            DefaultFilterResult filterResult = new DefaultFilterResult();
            affected = houseSupportFacilitiesTypeOverModelService.createMaster(entity, filterResult, null, null);
            if (affected > 0) {
                return SuccessTip.create(filterResult.result());
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }



    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseSupportFacilitiesType", response = HouseSupportFacilitiesTypeModel.class)
    public Tip getHouseSupportFacilitiesType(@PathVariable Long id) {
        CRUDObject<HouseSupportFacilitiesTypeModel> entity = houseSupportFacilitiesTypeOverModelService
                .registerQueryMasterDao(queryHouseSupportFacilitiesTypeDao)
                .retrieveMaster(id, null, null, null);
        if (entity != null) {
            return SuccessTip.create(entity.toJSONObject());
        } else {
            return SuccessTip.create();
        }

    }


    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseSupportFacilitiesType", response = HouseSupportFacilitiesTypeModel.class)
    public Tip updateHouseSupportFacilitiesType(@PathVariable Long id, @RequestBody HouseSupportFacilitiesTypeModel entity) {
        entity.setId(id);
        // use update flags
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items

        return SuccessTip.create(houseSupportFacilitiesTypeOverModelService.updateMaster(entity, null, null, null, newOptions));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseSupportFacilitiesType")
    public Tip deleteHouseSupportFacilitiesType(@PathVariable Long id) {
        return SuccessTip.create(houseSupportFacilitiesTypeOverModelService.deleteHouseSupportFacilitiesType(id));
    }


    @ApiOperation(value = "HouseSupportFacilitiesType 列表信息", response = HouseSupportFacilitiesTypeRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "enName", dataType = "String"),
            @ApiImplicitParam(name = "cnName", dataType = "String"),
            @ApiImplicitParam(name = "icon", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseSupportFacilitiesTypePage(Page<HouseSupportFacilitiesTypeRecord> page,
                                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                   // for tag feature query
                                                   @RequestParam(name = "tag", required = false) String tag,
                                                   // end tag
                                                   @RequestParam(name = "search", required = false) String search,

                                                   @RequestParam(name = "enName", required = false) String enName,

                                                   @RequestParam(name = "cnName", required = false) String cnName,

                                                   @RequestParam(name = "icon", required = false) String icon,
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
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);

        HouseSupportFacilitiesTypeRecord record = new HouseSupportFacilitiesTypeRecord();
        record.setEnName(enName);
        record.setCnName(cnName);
        record.setIcon(icon);


        List<HouseSupportFacilitiesTypeRecord> houseSupportFacilitiesTypePage = queryHouseSupportFacilitiesTypeDao.findHouseSupportFacilitiesTypePage(null, record, tag, search, orderBy, null, null);


//        page.setRecords(houseSupportFacilitiesTypePage);

        return SuccessTip.create(houseSupportFacilitiesTypePage);
    }

}
