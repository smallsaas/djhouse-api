package com.jfeat.am.module.house.api.app.common;


import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationOperationsService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api("HouseEquityDemandSupply")
@RequestMapping("/api/u/house/uerApplicationOperationsEndpoint")
public class UserApplicationOperationsEndpoint {

    @Resource
    HouseApplicationOperationsService houseApplicationOperationsService;

    //    申请社区管理
    @PostMapping
    public Tip createHouseApplicationOperations(@RequestBody HouseApplicationOperations entity) {
        if (JWTKit.getUserId()==null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Integer affected = 0;
        entity.setUserId(JWTKit.getUserId());
        try {
            affected = houseApplicationOperationsService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }
        return SuccessTip.create(affected);
    }


}
