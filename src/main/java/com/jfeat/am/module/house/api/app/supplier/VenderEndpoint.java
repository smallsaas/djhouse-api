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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 供应商api，该controller主要调用vms模块（供应商项目）的service
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/3/22 19:48
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/api/u/house/supplier")
public class VenderEndpoint {

    @Resource
    VenderOverModelService venderService;

    @Resource
    OrderService orderService;

    @Resource
    UserAccountUtility userAccountUtility;

    @GetMapping("/orders")
    public Tip listVenderOrders(@RequestParam(name = "pageNum",required = false,defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize) {

        if (!userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_SUPPLIER)) {
            throw new BusinessException(BusinessCode.NoPermission,"没有供应商权限");
        }

        Long userId = JWTKit.getUserId();

        Vender vender = venderService.getIdByUserId(userId);
        if (vender == null) throw new BusinessException(BusinessCode.NoPermission,"未加入到供应商");

        Page<TOrder> page = new Page<>(pageNum,pageSize);
        return SuccessTip.create(orderService.listOrdersBySupplier(page,vender.getId()));
    }
}
