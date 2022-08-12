package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationOperationsService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseApplicationOperationsServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseApplicationOperationsMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;
import com.jfeat.am.uaas.tenant.services.domain.service.TenantService;
import com.jfeat.am.uaas.tenant.services.gen.persistence.model.Tenant;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseApplicationOperationsService")
public class HouseApplicationOperationsServiceImpl extends CRUDHouseApplicationOperationsServiceImpl implements HouseApplicationOperationsService {

    @Resource
    TenantService tenantService;

    @Resource
    HouseApplicationOperationsMapper houseApplicationOperationsMapper;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    @Override
    protected String entityName() {
        return "HouseApplicationOperations";
    }


    @Override
    @Transactional
    public int passApplicationOperations(Long id) {

        HouseApplicationOperations houseApplicationOperations = houseApplicationOperationsMapper.selectById(id);
        Integer affect = 0;
        if (houseApplicationOperations != null) {
            houseApplicationOperations.setStatus(HouseApplicationOperations.STATUS_PASS);
            houseApplicationOperationsMapper.updateById(houseApplicationOperations);


//            插入社区信息
            Tenant tenant = new Tenant();
            tenant.setName(houseApplicationOperations.getTenantName());
            tenant.setPhone(houseApplicationOperations.getPhone());
            tenant.setAppid("1");
            affect += tenantService.createTenant(tenant, false);

//            更新用户orgId
            UserAccount userAccount = userAccountMapper.selectById(houseApplicationOperations.getUserId());
            userAccount.setOrgId(tenant.getOrgId());


//            添加社区管理员身份
            List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
            if (!userTypeList.contains(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) {
                userTypeList.add(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER);
                Integer userType = userAccountService.getUserTypeByList(userTypeList);
                userAccount.setType(userType);
            }
            affect += userAccountMapper.updateById(userAccount);


        }

        return affect;
    }
}
