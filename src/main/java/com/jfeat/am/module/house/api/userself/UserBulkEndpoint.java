package com.jfeat.am.module.house.api.userself;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanFunitureRecord;
import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateFunitureRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserDecoratePlanRecord;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanOverModelService;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateFunitureService;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecoratePlanService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateFuniture;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
团购
 */
@RestController
@RequestMapping("/api/u/house/houseUserBulk")
public class UserBulkEndpoint {

    @Resource
    HouseDecoratePlanOverModelService houseDecoratePlanOverModelService;

    @Resource
    QueryHouseUserDecorateFunitureDao queryHouseUserDecorateFunitureDao;

    @Resource
    QueryHouseDecoratePlanFunitureDao queryHouseDecoratePlanFunitureDao;

    @Resource
    HouseUserDecorateFunitureService houseUserDecorateFunitureService;

    @Resource
    HouseUserDecoratePlanService houseUserDecoratePlanService;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryProductDao queryProductDao;


    @ApiOperation(value = "HouseDecoratePlan 列表信息", response = HouseDecoratePlanRecord.class)
    @GetMapping("/allBulk")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "decorateStyleName", dataType = "String"),
            @ApiImplicitParam(name = "allowChanged", dataType = "Integer"),
            @ApiImplicitParam(name = "colorStyle", dataType = "String"),
            @ApiImplicitParam(name = "totalBudget", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "star", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseDecoratePlanPage(Page<HouseDecoratePlanRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          // for tag feature query
                                          @RequestParam(name = "tag", required = false) String tag,
                                          // end tag
                                          @RequestParam(name = "search", required = false) String search,

                                          @RequestParam(name = "decorateStyleName", required = false) String decorateStyleName,

                                          @RequestParam(name = "allowChanged", required = false) Integer allowChanged,

                                          @RequestParam(name = "colorStyle", required = false) String colorStyle,

                                          @RequestParam(name = "totalBudget", required = false) BigDecimal totalBudget,

                                          @RequestParam(name = "star", required = false) Integer star,
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

        HouseDecoratePlanRecord record = new HouseDecoratePlanRecord();
        record.setDecorateStyleName(decorateStyleName);
        record.setAllowChanged(allowChanged);
        record.setColorStyle(colorStyle);
        record.setTotalBudget(totalBudget);
        record.setStar(star);
       record.setOptionType(2);


        List<HouseDecoratePlanRecord> houseDecoratePlanPage = queryHouseDecoratePlanDao.findHouseDecoratePlanPage(page, record, tag, search, orderBy, null, null);
        for (int i = 0; i < houseDecoratePlanPage.size(); i++) {
            Integer queryStar = queryHouseDecoratePlanDao.queryDecoratePlanStar(houseDecoratePlanPage.get(i).getId());
            if (queryStar != null && queryStar > 0) {
                houseDecoratePlanPage.get(i).setStar(queryStar);
            } else {
                houseDecoratePlanPage.get(i).setStar(0);
            }

            Double totalBudegt = queryHouseDecoratePlanDao.queryDecoratePlanTotalPrice(houseDecoratePlanPage.get(i).getId());
            if (totalBudegt != null && totalBudegt >= 0) {
                houseDecoratePlanPage.get(i).setTotalBudget(BigDecimal.valueOf(totalBudegt));
            }
        }
        page.setRecords(houseDecoratePlanPage);
        return SuccessTip.create(page);
    }

    @GetMapping("/bulk/{id}")
    public Tip getBulkProduct(@PathVariable("id") Long id){
        HouseDecoratePlanFunitureRecord record = new HouseDecoratePlanFunitureRecord();
        record.setDecoratePlanId(id);
        List<HouseDecoratePlanFunitureRecord> houseDecoratePlanRecordList = queryHouseDecoratePlanFunitureDao.findHouseDecoratePlanFuniturePage(null,record,null,null,null,null,null);

        List<Product> productList = new ArrayList<>();
        for (HouseDecoratePlanFunitureRecord funitureRecord:houseDecoratePlanRecordList){
            Product product = queryProductDao.queryMasterModel(funitureRecord.getFurnitureId());
            if (product!=null){
                productList.add(product);
            }
        }

        return SuccessTip.create(productList);
    }


    @PutMapping("/addUserBulkProduct")
    public Tip addUserBulkProduct(@RequestBody HouseUserDecorateFuniture decorateFuniture){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        decorateFuniture.setUserId(JWTKit.getUserId());
//        添加团购计划
        HouseUserDecoratePlan houseUserDecoratePlan = new HouseUserDecoratePlan();
        houseUserDecoratePlan.setUserId(JWTKit.getUserId());
        houseUserDecoratePlan.setDecoratePlanId(decorateFuniture.getDecoratePlanId());
        houseUserDecoratePlan.setAssetId(decorateFuniture.getAssetId());
        houseUserDecoratePlan.setOptionType(2);

        HouseUserDecoratePlanRecord houseUserDecoratePlanRecord = new HouseUserDecoratePlanRecord();
        houseUserDecoratePlanRecord.setUserId(JWTKit.getUserId());
        houseUserDecoratePlanRecord.setDecoratePlanId(decorateFuniture.getDecoratePlanId());
        houseUserDecoratePlanRecord.setAssetId(decorateFuniture.getAssetId());
        houseUserDecoratePlanRecord.setOptionType(2);

        List<HouseUserDecoratePlanRecord> houseUserDecoratePlanPage = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null, houseUserDecoratePlanRecord, null, null, null, null, null);
        houseUserDecoratePlan.setCreateTime(new Date());
        Integer affected = 0;
        if (houseUserDecoratePlanPage==null || houseUserDecoratePlanPage.size()!=1){
            try {
                affected = houseUserDecoratePlanService.createMaster(houseUserDecoratePlan);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        //        添加家居
        try {
            affected += houseUserDecorateFunitureService.createMaster(decorateFuniture);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @GetMapping("/userAllBulkProduct")
    public Tip getUserAllBulkProduct(){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        List<Product> productList = new ArrayList<>();
        HouseUserDecoratePlanRecord houseUserDecoratePlan = new HouseUserDecoratePlanRecord();
        houseUserDecoratePlan.setUserId(JWTKit.getUserId());
        houseUserDecoratePlan.setOptionType(2);
        List<HouseUserDecoratePlanRecord> houseDecoratePlanRecordList = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null,houseUserDecoratePlan,null,null,null,null,null);
        for (int i=0;i<houseDecoratePlanRecordList.size();i++){
            HouseUserDecorateFunitureRecord houseUserDecorateFunitureRecord = new HouseUserDecorateFunitureRecord();
            houseUserDecorateFunitureRecord.setUserId(JWTKit.getUserId());
            houseUserDecorateFunitureRecord.setDecoratePlanId(houseDecoratePlanRecordList.get(i).getDecoratePlanId());

            List<HouseUserDecorateFunitureRecord> houseUserDecorateFuniturePage = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null, houseUserDecorateFunitureRecord, null, null, null, null, null);
            for (HouseUserDecorateFunitureRecord funitureRecord : houseUserDecorateFuniturePage) {
                Product product = queryProductDao.queryMasterModel(funitureRecord.getFunitureId());
                productList.add(product);
            }
        }
        return SuccessTip.create(productList);
    }


}
