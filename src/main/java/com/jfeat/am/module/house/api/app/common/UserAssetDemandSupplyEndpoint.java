package com.jfeat.am.module.house.api.app.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDemandSupplyDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetDemandSupplyRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetDemandSupplyService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetDemandSupply;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("HouseEquityDemandSupply")
@RequestMapping("/api/u/house/houseAssetDemandSupply/houseAssetDemandSupplyies")
public class UserAssetDemandSupplyEndpoint {


    @Resource
    HouseAssetDemandSupplyService houseAssetDemandSupplyService;

    @Resource
    QueryHouseAssetDemandSupplyDao queryHouseAssetDemandSupplyDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    HouseUserAssetService houseUserAssetService;


    @BusinessLog(name = "HouseAssetDemandSupply", value = "create HouseAssetDemandSupply")
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetDemandSupply", response = HouseAssetDemandSupply.class)
    public Tip createHouseAssetDemandSupply(@RequestBody HouseAssetDemandSupply entity) {
        System.out.println(JWTKit.getUserId());
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }


        if (entity.getAssetOption()==1){
            HouseUserAsset houseUserAsset =  queryHouseUserAssetDao.queryHouseUserAssetByAssetId(entity.getAssetId());
            if (houseUserAsset==null || !(houseUserAsset.getUserId().equals(JWTKit.getUserId()))){
                throw new BusinessException(BusinessCode.CodeBase, "该用户没有房产");
            }
            HouseAsset houseAsset =  queryHouseAssetDao.queryMasterModel(entity.getAssetId());
            Long unitId = houseAsset.getUnitId();
            HousePropertyBuildingUnit housePropertyBuildingUnit = queryHousePropertyBuildingUnitDao.queryMasterModel(unitId);
            entity.setDesignModelId(housePropertyBuildingUnit.getDesignModelId());

        }
        Long userId = JWTKit.getUserId();
        entity.setUserId(userId);
        Integer affected = 0;
        try {
            affected = houseAssetDemandSupplyService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


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
        for (HouseAssetDemandSupplyRecord houseAssetDemandSupplyRecord:houseAssetDemandSupplyPage){
            houseAssetDemandSupplyRecord.setUserId(null);
            houseAssetDemandSupplyRecord.setPhone(null);
            houseAssetDemandSupplyRecord.setUsername(null);
            houseAssetDemandSupplyRecord.setUserAvatar(null);
        }
        page.setRecords(houseAssetDemandSupplyPage);
        return SuccessTip.create(page);
    }


//    我的房屋买卖

    @GetMapping("/userDemandSupply")
    public Tip getHouseAssetDemandSupply(Page<HouseAssetDemandSupplyRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(name = "option",required = false) String option){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseAssetDemandSupplyRecord record = new HouseAssetDemandSupplyRecord();
        if (option.equals("demand")){
            record.setAssetOption(1);
        }
        if (option.equals("supply")){
            record.setAssetOption(2);
        }

        List<HouseAssetDemandSupplyRecord> resultHouseAssetDemandSupplyPage=new ArrayList<>();
        record.setUserId(JWTKit.getUserId());
        List<HouseAssetDemandSupplyRecord> houseAssetDemandSupplyPage = queryHouseAssetDemandSupplyDao.findHouseAssetDemandSupplyPage(page, record, null, null, null, null, null);

        for (HouseAssetDemandSupplyRecord houseAssetDemandSupplyRecord:houseAssetDemandSupplyPage){
            HouseAssetDemandSupplyRecord temp = new HouseAssetDemandSupplyRecord();
            if (option.equals("demand")){
                temp.setAssetOption(2);
            }
            if (option.equals("supply")){
                record.setAssetOption(1);
            }
            temp.setDesignModelId(houseAssetDemandSupplyRecord.getDesignModelId());
            List<HouseAssetDemandSupplyRecord> houseAssetDemandSupplyRecords = queryHouseAssetDemandSupplyDao.findHouseAssetDemandSupplyPage(page, temp, null, null, null, null, null);
            for (HouseAssetDemandSupplyRecord assetDemandSupplyRecord:houseAssetDemandSupplyRecords){
                String number = assetDemandSupplyRecord.getPhone();
                assetDemandSupplyRecord.setUserId(null);
                assetDemandSupplyRecord.setUsername(null);
                assetDemandSupplyRecord.setUserAvatar(null);
                if (number!=null){
                    assetDemandSupplyRecord.setPhone(number.substring(0, 1).concat("****").concat(number.substring(number.length() - 1, number.length())));
                }
                resultHouseAssetDemandSupplyPage.add(assetDemandSupplyRecord);
            }
        }
        page.setRecords(resultHouseAssetDemandSupplyPage);
        return SuccessTip.create(page);
    }


}
