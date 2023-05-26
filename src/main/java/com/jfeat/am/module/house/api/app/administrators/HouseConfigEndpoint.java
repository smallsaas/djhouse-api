package com.jfeat.am.module.house.api.app.administrators;

import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.service.impl.HouseConfigService;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.ErrorTip;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.data.redis.core.index.GeoIndexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/26 09:58
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/api/u/house/house-config")
public class HouseConfigEndpoint {

    @Resource
    HouseConfigService houseConfigService;

    @Resource
    UserAccountUtility userAccountUtility; // 用户权限判断工具类

    @GetMapping("/house-config-group")
    public Tip ListHouseConfigGroupDTO() {

        // 用户权限判断
        if ( !(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_ADMIN)) )
             throw new BusinessException(BusinessCode.NoPermission,"没有权限");

        // 查询
        return SuccessTip.create(houseConfigService.listHouseConfigGroupDTO());
    }
}
