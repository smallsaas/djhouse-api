
package com.jfeat.am.module.house.api;


import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDemandSupplyDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseAssetDemandSupplyRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetDemandSupply;

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
 * @since 2022-06-29
 */
@RestController
@Api("HouseAssetDemandSupply")
@RequestMapping("/api/crud/house/houseAssetDemandSupply/houseAssetDemandSupplyies")
public class HouseAssetDemandSupplyEndpoint {

    @Resource
    HouseAssetDemandSupplyService houseAssetDemandSupplyService;

    @Resource
    QueryHouseAssetDemandSupplyDao queryHouseAssetDemandSupplyDao;


    @BusinessLog(name = "HouseAssetDemandSupply", value = "create HouseAssetDemandSupply")
    @Permission(HouseAssetDemandSupplyPermission.HOUSEASSETDEMANDSUPPLY_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetDemandSupply", response = HouseAssetDemandSupply.class)
    public Tip createHouseAssetDemandSupply(@RequestBody HouseAssetDemandSupply entity) {
        Integer affected = 0;
        try {
            entity.setCreateTime(new Date());
            affected = houseAssetDemandSupplyService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetDemandSupplyPermission.HOUSEASSETDEMANDSUPPLY_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetDemandSupply", response = HouseAssetDemandSupply.class)
    public Tip getHouseAssetDemandSupply(@PathVariable Long id) {
        return SuccessTip.create(houseAssetDemandSupplyService.queryMasterModel(queryHouseAssetDemandSupplyDao, id));
    }

    @BusinessLog(name = "HouseAssetDemandSupply", value = "update HouseAssetDemandSupply")
    @Permission(HouseAssetDemandSupplyPermission.HOUSEASSETDEMANDSUPPLY_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetDemandSupply", response = HouseAssetDemandSupply.class)
    public Tip updateHouseAssetDemandSupply(@PathVariable Long id, @RequestBody HouseAssetDemandSupply entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetDemandSupplyService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetDemandSupply", value = "delete HouseAssetDemandSupply")
    @Permission(HouseAssetDemandSupplyPermission.HOUSEASSETDEMANDSUPPLY_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetDemandSupply")
    public Tip deleteHouseAssetDemandSupply(@PathVariable Long id) {
        return SuccessTip.create(houseAssetDemandSupplyService.deleteMaster(id));
    }

    @Permission(HouseAssetDemandSupplyPermission.HOUSEASSETDEMANDSUPPLY_VIEW)
    @ApiOperation(value = "HouseAssetDemandSupply 列表信息", response = HouseAssetDemandSupplyRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "assetOption", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetDemandSupplyPage(Page<HouseAssetDemandSupplyRecord> page,
                                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                               // for tag feature query
                                               @RequestParam(name = "tag", required = false) String tag,
                                               // end tag
                                               @RequestParam(name = "search", required = false) String search,

                                               @RequestParam(name = "userId", required = false) Long userId,

                                               @RequestParam(name = "assetId", required = false) Long assetId,

                                               @RequestParam(name = "assetOption", required = false) Integer assetOption,
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

        HouseAssetDemandSupplyRecord record = new HouseAssetDemandSupplyRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setAssetOption(assetOption);
        List<HouseAssetDemandSupplyRecord> houseAssetDemandSupplyPage = queryHouseAssetDemandSupplyDao.findHouseAssetDemandSupplyPage(page, record, tag, search, orderBy, null, null);
        for (int i=0;i<houseAssetDemandSupplyPage.size();i++){
            String buildingCode = houseAssetDemandSupplyPage.get(i).getBuildingCode();
            String number = houseAssetDemandSupplyPage.get(i).getNumber();
            if (buildingCode!=null && number!=null){
                houseAssetDemandSupplyPage.get(i).setCombinationNumber(buildingCode.concat("-").concat(number));
            }

            if (houseAssetDemandSupplyPage.get(i).getAssetOption().equals(1)){
                houseAssetDemandSupplyPage.get(i).setOption("sell");
            }else {
                houseAssetDemandSupplyPage.get(i).setOption("buy");
            }
        }
        page.setRecords(houseAssetDemandSupplyPage);
        return SuccessTip.create(page);
    }
}

