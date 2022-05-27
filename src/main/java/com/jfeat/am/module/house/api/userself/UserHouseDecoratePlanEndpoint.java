package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.am.module.house.api.permission.HouseDecoratePlanPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDecoratePlanDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserDecoratePlanDao;
import com.jfeat.am.module.house.services.domain.dao.QueryProductDao;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/u//house/houseUserDecoratePlan/houseUserDecoratePlans")
public class UserHouseDecoratePlanEndpoint {

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    HouseDecoratePlanOverModelService houseDecoratePlanOverModelService;

    @Resource
    QueryProductDao queryProductDao;

    @GetMapping("/{userId}")
    public Tip getUserHouseDecoratePlan(@PathVariable Long userId){
//        if (JWTKit.getDomainUserId() == null) {
//            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
//        }
//        userId = JWTKit.getUserId();
        return SuccessTip.create(queryHouseUserDecoratePlanDao.queryHouseUserDecoratePlanByUserId(userId));
    }

    @BusinessLog(name = "HouseDecoratePlan", value = "查看 HouseDecoratePlanModel")
    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_VIEW)
//    @GetMapping("/funiture/{id}")
    @GetMapping("/funiture")
    @ApiOperation(value = "查看 HouseDecoratePlan", response = HouseDecoratePlanModel.class)
    public Tip getHouseDecoratePlan(Long decoratePlanId,Long userId) {

//        if (JWTKit.getDomainUserId() == null) {
//            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
//        }
//        Long userId = JWTKit.getUserId();

        CRUDObject<HouseDecoratePlanModel> entity = houseDecoratePlanOverModelService
                .registerQueryMasterDao(queryHouseDecoratePlanDao)
                .retrieveMaster(decoratePlanId, null, null, null);

        JSONObject jsonObject = entity.toJSONObject();


        JSONArray jsonArray = jsonObject.getJSONArray("items");
        String items = JSONArray.toJSONString(jsonArray);

//        装修计划原有的家居
        List<Product> products= JSON.parseArray(items, Product.class);

//        用户修改后装修计划的家居列表
        List<HouseUserDecoratePlanModel> productList = queryHouseUserDecoratePlanDao.queryHouseUserDecoratePlanFuniture(userId,decoratePlanId);
        for (int i=0;i<productList.size();i++){
            for (int j=0;j<products.size();j++){
                if (productList.get(i).getFunitureId()!=null){
//                    如果changefuniture 有值时替换 家居产品
                    if (productList.get(i).getFunitureId()==products.get(j).getId().longValue() && productList.get(i).getChangedFunitureId()!=null){
                        products.set(j,queryProductDao.queryMasterModel(productList.get(i).getChangedFunitureId()));
                    }else if (productList.get(i).getFunitureId()==products.get(j).getId().longValue() && productList.get(i).getChangedFunitureId()==null){
//                        如果changefuniture 移除原有 家居产品
                        products.remove(i);
                    }
                }

            }
        }
        System.out.println(products);
        jsonObject.put("items",products);
        if (entity != null) {
            return SuccessTip.create(jsonObject);
        } else {
            return SuccessTip.create();
        }
    }

}
