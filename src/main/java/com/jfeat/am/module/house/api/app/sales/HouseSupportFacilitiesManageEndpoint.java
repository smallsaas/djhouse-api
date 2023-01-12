package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseSupportFacilitiesPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSupportFacilitiesDao;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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
@RequestMapping("/api/u/house/sales/houseSupportFacilities")
public class HouseSupportFacilitiesManageEndpoint {


    @Resource
    HouseSupportFacilitiesService houseSupportFacilitiesService;

    @Resource
    QueryHouseSupportFacilitiesDao queryHouseSupportFacilitiesDao;


    @PostMapping
    @ApiOperation(value = "新建 HouseSupportFacilities", response = HouseSupportFacilities.class)
    public Tip createHouseSupportFacilities(@RequestBody HouseSupportFacilities entity) {
        Integer affected = 0;
        try {
            affected = houseSupportFacilitiesService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseSupportFacilities", response = HouseSupportFacilities.class)
    public Tip getHouseSupportFacilities(@PathVariable Long id) {
        return SuccessTip.create(houseSupportFacilitiesService.queryMasterModel(queryHouseSupportFacilitiesDao, id));
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseSupportFacilities", response = HouseSupportFacilities.class)
    public Tip updateHouseSupportFacilities(@PathVariable Long id, @RequestBody HouseSupportFacilities entity) {
        entity.setId(id);
        return SuccessTip.create(houseSupportFacilitiesService.updateMaster(entity));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseSupportFacilities")
    public Tip deleteHouseSupportFacilities(@PathVariable Long id) {
        return SuccessTip.create(houseSupportFacilitiesService.deleteMaster(id));
    }


    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "typeId", dataType = "Long"),
            @ApiImplicitParam(name = "title", dataType = "String"),
            @ApiImplicitParam(name = "icon", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseSupportFacilitiesPage(Page<HouseSupportFacilitiesRecord> page,
                                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                               // for tag feature query
                                               @RequestParam(name = "tag", required = false) String tag,
                                               // end tag
                                               @RequestParam(name = "search", required = false) String search,

                                               @RequestParam(name = "typeId", required = true) Long typeId,

                                               @RequestParam(name = "title", required = false) String title,

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

        HouseSupportFacilitiesRecord record = new HouseSupportFacilitiesRecord();
        record.setTypeId(typeId);
        record.setTitle(title);
        record.setIcon(icon);


        List<HouseSupportFacilitiesRecord> houseSupportFacilitiesPage = queryHouseSupportFacilitiesDao.findHouseSupportFacilitiesPage(null, record, tag, search, orderBy, null, null);


//        page.setRecords(houseSupportFacilitiesPage);

        return SuccessTip.create(houseSupportFacilitiesPage);
    }
}
