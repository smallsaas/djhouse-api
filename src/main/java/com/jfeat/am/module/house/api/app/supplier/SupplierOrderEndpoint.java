package com.jfeat.am.module.house.api.app.supplier;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.am.module.kehai.services.domain.service.VenderOverModelService;
import com.jfeat.am.module.kehai.services.gen.persistence.model.Vender;
import com.jfeat.am.module.order.services.domain.service.OrderService;
import com.jfeat.am.module.order.services.gen.persistence.model.TOrder;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 供应商订单api,此controller主要是关联 商城(saas-mall-api) 和 供应商(vms) 两个模块，涉及到供应商订单操作的api都会在此controller
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/3/22 19:48
 * @author: hhhhhtao
 */
@Api(value = "供应商订单")
@RestController
@RequestMapping("/api/u/house/supplier")
public class SupplierOrderEndpoint {

    @Resource
    VenderOverModelService venderService;

    @Resource
    OrderService orderService;

    @Resource
    UserAccountUtility userAccountUtility;

    @ApiOperation(httpMethod = "GET",value="获取供应商订单列表",notes = "拥有供应商身份的用户请求才能获取到对应的订单列表，只能获取到自己（供应商）的订单列表")
    @GetMapping("/orders")
    public Tip listVenderOrders(@RequestParam(name = "pageNum",required = false,defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize) {

        // 判断用户类型是否拥有供应商
        if (!userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_SUPPLIER)) {
            throw new BusinessException(BusinessCode.NoPermission,"没有供应商权限");
        }

        // 因为遗留的问题，即使拥有了供应商类型，也有可能没有加入到供应商表，所以这里再做一个判断
        Long userId = JWTKit.getUserId();
        Vender vender = venderService.getIdByUserId(userId);
        if (vender == null) throw new BusinessException(BusinessCode.NoPermission,"未加入到供应商");

        Page<TOrder> page = new Page<>(pageNum,pageSize);
        return SuccessTip.create(orderService.listOrdersBySupplier(page,vender.getId()));
    }

}
