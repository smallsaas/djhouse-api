package com.jfeat.am.module.house.api.app.administrators;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.model.HouseApplicationIntermediaryRecord;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.dev.devops.services.domain.service.DevopsServices;
import com.jfeat.users.account.services.domain.service.BusinessUserService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/house/administrators/userDataManage")
public class UserDataManageEndpoint {

    @Resource
    DevopsServices devopsServices;

    @Resource
    UserAccountMapper userAccountMapper;

    @PostMapping("/{sqlFile}")
    @EndUserPermission({EndUserTypeSetting.USER_TYPE_ADMIN_STRING})
    public Tip getResultList(@PathVariable("sqlFile") String sqlFile, HttpServletRequest request) {

        return SuccessTip.create(devopsServices.executeSql(request,sqlFile));
    }

    @GetMapping("/allUser")
    @EndUserPermission({EndUserTypeSetting.USER_TYPE_ADMIN_STRING})
    public Tip getAllUserAccount(Page<UserAccount> page,
                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(name = "search", required = false) String search) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        List<Integer> appids = Arrays.asList(1, 2);
        userAccountQueryWrapper.in("appid", appids);
        if (search != null) {
            userAccountQueryWrapper.and(e -> e.like(UserAccount.PHONE, search).or().like(UserAccount.NAME, search));
        }
        page = userAccountMapper.selectPage(page, userAccountQueryWrapper);
        return SuccessTip.create(page);
    }


}
