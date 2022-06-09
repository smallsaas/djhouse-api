package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseUserDecorateAddressPermission;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateAddressRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateAddressService;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecoratePlanService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateAddress;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.am.module.house.api.permission.HouseDecoratePlanPermission;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/houseUserDecoratePlan/houseUserDecoratePlans")
public class UserHouseDecoratePlanEndpoint {

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    HouseDecoratePlanOverModelService houseDecoratePlanOverModelService;

    @Resource
    QueryHouseUserDecorateAddressDao queryHouseUserDecorateAddressDao;

    @Resource
    QueryProductDao queryProductDao;

    @Resource
    HouseUserDecorateAddressService houseUserDecorateAddressService;

    @Resource
    QueryHouseDecoratePlanFunitureDao queryHouseDecoratePlanFunitureDao;

    @Resource
    HouseUserDecoratePlanService houseUserDecoratePlanService;


    @GetMapping()
    public Tip getUserHouseDecoratePlan(
            Page<HouseDecoratePlan> page,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        List<HouseUserDecoratePlanModel> houseUserDecoratePlan = queryHouseUserDecoratePlanDao.queryHouseUserDecoratePlanByUserId(page, userId);
        JSONArray json = (JSONArray) JSONArray.toJSON(houseUserDecoratePlan);
        String items = JSONArray.toJSONString(json);
//        添加装地址
        List<HouseDecoratePlan> houseDecoratePlanList = JSON.parseArray(items, HouseDecoratePlan.class);



//        for (HouseDecoratePlan houseDecoratePlan : houseDecoratePlanList) {
//
//
//
//            HouseUserDecorateAddressRecord houseUserDecorateAddressRecord = queryHouseUserDecorateAddressDao.queryUserDecoratePlanAddress(userId, houseDecoratePlan.getId());
//
//
//            if (houseUserDecorateAddressRecord != null) {
//                String community = houseUserDecorateAddressRecord.getHousePropertyCommunity().getCommunity();
//                String building = houseUserDecorateAddressRecord.getHousePropertyBuilding().getCode();
//                String unit = houseUserDecorateAddressRecord.getHousePropertyBuildingUnit().getUnitCode();
//                String address = "".concat(community).concat(building).concat("栋").concat(unit);
//                houseDecoratePlan.setDecorateAddress(address);
//            }
//        }

        page.setRecords(houseDecoratePlanList);
        return SuccessTip.create(page);
    }

    @BusinessLog(name = "HouseDecoratePlan", value = "查看 HouseDecoratePlanModel")
    @GetMapping("/funiture")
    @ApiOperation(value = "查看 HouseDecoratePlan", response = HouseDecoratePlanModel.class)
    public Tip getHouseDecoratePlan(@RequestParam("decoratePlanId") Long decoratePlanId) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();


        CRUDObject<HouseDecoratePlanModel> entity = houseDecoratePlanOverModelService
                .registerQueryMasterDao(queryHouseDecoratePlanDao)
                .retrieveMaster(decoratePlanId, null, null, null);

        JSONObject jsonObject = entity.toJSONObject();


        JSONArray jsonArray = jsonObject.getJSONArray("items");
        String items = JSONArray.toJSONString(jsonArray);

//        装修计划原有的家居
        List<Product> products = JSON.parseArray(items, Product.class);

//        用户修改后装修计划的家居列表
        List<HouseUserDecoratePlanModel> productList = queryHouseUserDecoratePlanDao.queryHouseUserDecoratePlanFuniture(userId, decoratePlanId);


        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                if (productList.get(i).getFunitureId() != null) {
//                    如果changefuniture 有值时替换 家居产品
                    if (productList.get(i).getFunitureId() == products.get(j).getId().longValue() && productList.get(i).getChangedFunitureId() != null) {
                        products.set(j, queryProductDao.queryMasterModel(productList.get(i).getChangedFunitureId()));
                    } else if (productList.get(i).getFunitureId() == products.get(j).getId().longValue() && productList.get(i).getChangedFunitureId() == null) {
//                        如果changefuniture 移除原有 家居产品
//                        System.out.println(products.get(i));
                        products.remove(j);
                    }
                }

            }
        }
        jsonObject.put("items", products);

//        增加显示装修地址
//        HouseUserDecorateAddressRecord houseUserDecorateAddressRecord = queryHouseUserDecorateAddressDao.queryUserDecoratePlanAddress(userId, decoratePlanId);
//        if (houseUserDecorateAddressRecord != null) {
//            String community = houseUserDecorateAddressRecord.getHousePropertyCommunity().getCommunity();
//            String building = houseUserDecorateAddressRecord.getHousePropertyBuilding().getCode();
//            String unit = houseUserDecorateAddressRecord.getHousePropertyBuildingUnit().getUnitCode();
//            String address = "".concat(community).concat(building).concat("栋").concat(unit);
//            jsonObject.put("decorateAddress", address);
//        }
        if (entity != null) {
            return SuccessTip.create(jsonObject);
        } else {
            return SuccessTip.create();
        }
    }

//    用户替换装修计划里面的家居
    @PutMapping("/funiture")
    public Tip changeFuniture(
                              @RequestParam("decoratePlanId") Long decoratePlanId,
                              @RequestParam("funitureId") Long funitureId,
                              @RequestBody HouseUserDecoratePlan houseUserDecoratePlan){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();

        Integer affected = 0;

        if (queryHouseDecoratePlanFunitureDao.queryHouseDecoratePlanFunitureExists(decoratePlanId,funitureId) ==null){
            throw new BusinessException(BusinessCode.valueOf("计划暂无该商品"));
        }else if (queryHouseUserDecoratePlanDao.queryUserDecoratePlanFunitureExists(userId,decoratePlanId,funitureId)!=null){
            return SuccessTip.create(queryHouseUserDecoratePlanDao.updateUserDecoratePlanFuniture(userId,decoratePlanId,funitureId,houseUserDecoratePlan));
        }else {
            try {
                houseUserDecoratePlan.setUserId(userId);
                houseUserDecoratePlan.setDecoratePlanId(decoratePlanId);
                houseUserDecoratePlan.setFunitureId(funitureId);
                affected = houseUserDecoratePlanService.createMaster(houseUserDecoratePlan);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        return SuccessTip.create(affected);
    }


//    用户移除装修计划里面的家居
    @DeleteMapping("/funiture")
    public Tip removeFuniture(
                              @RequestParam("decoratePlanId") Long decoratePlanId,
                              @RequestParam("funitureId") Long funitureId,
    @RequestBody HouseUserDecoratePlan entity){

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();

        Integer affected = 0;
        if (queryHouseUserDecoratePlanDao.queryUserDecoratePlanFunitureExists(userId,decoratePlanId,funitureId)!=null){
            return SuccessTip.create(queryHouseUserDecoratePlanDao.removeExistsUserDecoratePlanFuniture(userId,decoratePlanId,funitureId,entity));
        }else {
            try {
                HouseUserDecoratePlan houseUserDecoratePlan = new HouseUserDecoratePlan();
                houseUserDecoratePlan.setUserId(userId);
                houseUserDecoratePlan.setDecoratePlanId(decoratePlanId);
                houseUserDecoratePlan.setFunitureId(funitureId);
                affected = houseUserDecoratePlanService.createMaster(houseUserDecoratePlan);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        return SuccessTip.create(affected);
    }



    @BusinessLog(name = "HouseUserDecorateAddress", value = "create HouseUserDecorateAddress")
    @PostMapping("/userDecoratePlanAddress")
    @ApiOperation(value = "新建 HouseUserDecorateAddress", response = HouseUserDecorateAddress.class)
    public Tip createHouseUserDecorateAddress(@RequestBody HouseUserDecorateAddress entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        Integer affected = 0;
        try {
            affected = houseUserDecorateAddressService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @BusinessLog(name = "HouseUserDecorateAddress", value = "update HouseUserDecorateAddress")
    @PutMapping("/userDecoratePlanAddress")
    @ApiOperation(value = "修改 HouseUserDecorateAddress", response = HouseUserDecorateAddress.class)
    public Tip updateHouseUserDecorateAddress(
                                              @RequestParam("decoratePlanId") Long decoratePlanId,
                                              @RequestBody HouseUserDecorateAddress entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        return SuccessTip.create(queryHouseUserDecorateAddressDao.updateUserDecorateAddress(userId,decoratePlanId,entity));
    }

}
