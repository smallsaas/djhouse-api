package com.jfeat.am.module.house.api.app.administrators;

import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.service.HouseConfigService;
import com.jfeat.am.module.house.services.domain.service.impl.HouseConfigServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取配置分组列表
     *
     * @return
     */
    @GetMapping("/house-config-group")
    public Tip ListHouseConfigGroupDTO() {

        // 用户权限判断
        if ( !(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_ADMIN)) )
             throw new BusinessException(BusinessCode.NoPermission,"没有权限");

        // 查询
        return SuccessTip.create(houseConfigService.listHouseConfigGroupDTO());
    }

    /**
     * 更新配置字段值
     * 该api只更新字段的`field_value`
     *
     * @param houseConfig 更新字段对象
     * @return
     */
    @PutMapping("/house-config")
    public Tip updateHouseConfig(@RequestBody HouseConfig houseConfig) {

        // 用户权限判断
        if ( !(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_ADMIN)) )
            throw new BusinessException(BusinessCode.NoPermission,"没有权限");

        // 获取参数
        Integer id = houseConfig.getId();
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed);
        String fieldValue = houseConfig.getFieldValue();
        if (StringUtils.isBlank(fieldValue)) throw new BusinessException(BusinessCode.EmptyNotAllowed);

        // 更新
        return SuccessTip.create(houseConfigService.updateFieldValueById(id,fieldValue));
    }
}
