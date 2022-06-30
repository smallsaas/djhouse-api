
package com.jfeat.am.module.house.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.crud.plus.util.QueryParamUtils;
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseEquityDemandSupplyDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseEquityDemandSupplyRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@RestController
@Api("HouseEquityDemandSupply")
@RequestMapping("/api/crud/house/houseEquityDemandSupply/houseEquityDemandSupplyies")
public class HouseEquityDemandSupplyEndpoint {

    @Resource
    HouseEquityDemandSupplyService houseEquityDemandSupplyService;

    @Resource
    QueryHouseEquityDemandSupplyDao queryHouseEquityDemandSupplyDao;


    @BusinessLog(name = "HouseEquityDemandSupply", value = "create HouseEquityDemandSupply")
    @Permission(HouseEquityDemandSupplyPermission.HOUSEEQUITYDEMANDSUPPLY_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseEquityDemandSupply", response = HouseEquityDemandSupply.class)
    public Tip createHouseEquityDemandSupply(@RequestBody HouseEquityDemandSupply entity) {
        Integer affected = 0;
        try {
            affected = houseEquityDemandSupplyService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseEquityDemandSupplyPermission.HOUSEEQUITYDEMANDSUPPLY_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseEquityDemandSupply", response = HouseEquityDemandSupply.class)
    public Tip getHouseEquityDemandSupply(@PathVariable Long id) {
        return SuccessTip.create(houseEquityDemandSupplyService.queryMasterModel(queryHouseEquityDemandSupplyDao, id));
    }

    @BusinessLog(name = "HouseEquityDemandSupply", value = "update HouseEquityDemandSupply")
    @Permission(HouseEquityDemandSupplyPermission.HOUSEEQUITYDEMANDSUPPLY_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseEquityDemandSupply", response = HouseEquityDemandSupply.class)
    public Tip updateHouseEquityDemandSupply(@PathVariable Long id, @RequestBody HouseEquityDemandSupply entity) {
        entity.setId(id);
        return SuccessTip.create(houseEquityDemandSupplyService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseEquityDemandSupply", value = "delete HouseEquityDemandSupply")
    @Permission(HouseEquityDemandSupplyPermission.HOUSEEQUITYDEMANDSUPPLY_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseEquityDemandSupply")
    public Tip deleteHouseEquityDemandSupply(@PathVariable Long id) {
        return SuccessTip.create(houseEquityDemandSupplyService.deleteMaster(id));
    }

    @Permission(HouseEquityDemandSupplyPermission.HOUSEEQUITYDEMANDSUPPLY_VIEW)
    @ApiOperation(value = "HouseEquityDemandSupply 列表信息", response = HouseEquityDemandSupplyRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "equityOption", dataType = "Integer"),
            @ApiImplicitParam(name = "area", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseEquityDemandSupplyPage(Page<HouseEquityDemandSupplyRecord> page,
                                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                // for tag feature query
                                                @RequestParam(name = "tag", required = false) String tag,
                                                // end tag
                                                @RequestParam(name = "search", required = false) String search,

                                                @RequestParam(name = "userId", required = false) Long userId,

                                                @RequestParam(name = "equityOption", required = false) Integer equityOption,

                                                @RequestParam(name = "area", required = false) BigDecimal area,
                                                @RequestParam(name = "orderBy", required = false) String orderBy,
                                                @RequestParam(name = "sort", required = false) String sort,
                                                @RequestParam(name = "areaRange",required = false) Double[] areaRange) {

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

        HouseEquityDemandSupplyRecord record = new HouseEquityDemandSupplyRecord();
        record.setUserId(userId);
        record.setEquityOption(equityOption);
        record.setArea(area);


//        QueryWrapper multiEntityWrapper = QueryParamUtils.getMultiEntityWrapper(params, HouseEquityDemandSupplyRecord.class,"t_house_equity_demand_supply.");
//        System.out.println(areaRange[1]);
        Double leftRange =null;
        Double rightRange = null;

        if (areaRange!=null && areaRange.length==1){
            leftRange =areaRange[0];
        }
        if (areaRange!=null && areaRange.length>0 && areaRange.length<3){
            leftRange =areaRange[0];
           rightRange = areaRange[1];
        }

        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(page, record, tag, search, orderBy, null, null,leftRange,rightRange);

        page.setRecords(houseEquityDemandSupplyPage);

        return SuccessTip.create(page);
    }
}

