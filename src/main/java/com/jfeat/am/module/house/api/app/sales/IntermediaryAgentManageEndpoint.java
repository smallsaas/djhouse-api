package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/sales/intermediaryAgent")
public class IntermediaryAgentManageEndpoint {

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    TenantUtility tenantUtility;

    @GetMapping
    public Tip getIntermediaryAgent(Page<UserAccount> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "search", required = false) String search) {

        Long userId = JWTKit.getUserId();

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long orgId = tenantUtility.getCurrentOrgId(userId);
        if (orgId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "未设置社区");
        }

        List<String> appids = Arrays.asList("1", "2");
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq(UserAccount.ORG_ID, orgId);
        userAccountQueryWrapper.in("appid", appids);

        userAccountQueryWrapper.last("and type & ".concat(EndUserTypeSetting.USER_TYPE_INTERMEDIARY_STRING));
        if (search != null && !search.equals("")) {
            userAccountQueryWrapper.and(wrapper -> wrapper.lambda().like(UserAccount::getName, search).or().like(UserAccount::getPhone, search));
        }
        userAccountMapper.selectPage(page, userAccountQueryWrapper);
        return SuccessTip.create(page);
    }
}
