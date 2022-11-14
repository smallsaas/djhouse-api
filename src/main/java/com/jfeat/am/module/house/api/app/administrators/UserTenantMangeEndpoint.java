package com.jfeat.am.module.house.api.app.administrators;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.uaas.tenant.services.gen.persistence.dao.TenantMapper;
import com.jfeat.am.uaas.tenant.services.gen.persistence.model.Tenant;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.dao.QueryUserAccountDao;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/administrators/userTenantMangeEndpoint")
public class UserTenantMangeEndpoint {

    @Resource
    UserAccountMapper userAccountMapper;


    @Resource
    UserAccountService userAccountService;

    @Resource
    TenantMapper tenantMapper;


    //    查看管理员
    @GetMapping("/getAdministratorList")
    public Tip getAdministratorList(@RequestParam(value = "orgId", required = true) Long orgId) {

        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserAccount.ORG_ID, orgId);
        List<UserAccount> userAccountList = userAccountMapper.selectList(queryWrapper);

        List<UserAccount> result = new ArrayList<>();
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.getType() != null) {
                List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
//                判断是否是社区管理员或者运维人员
                if (userTypeList.contains(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER) || userTypeList.contains(EndUserTypeSetting.USER_TYPE_OPERATION)) {
                    userAccount.setUserTypeList(userTypeList);
                    result.add(userAccount);
                }
            }

        }

        return SuccessTip.create(result);
    }


    @PutMapping("/markProductionData")
    public Tip markProductionData(@RequestBody Tenant entity) {
        if (entity.getOrgId() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "orgId为必填项");
        }
        if (entity.getLocked() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "locked为必填项");
        }
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Tenant.ORG_ID, entity.getOrgId());
        Tenant tenant = tenantMapper.selectOne(queryWrapper);
        if (tenant != null) {
            tenant.setLocked(entity.getLocked());
            return SuccessTip.create(tenantMapper.updateById(tenant));
        }
        return SuccessTip.create();
    }
}
