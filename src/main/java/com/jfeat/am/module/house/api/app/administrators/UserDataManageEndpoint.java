package com.jfeat.am.module.house.api.app.administrators;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
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

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @PostMapping("/{sqlFile}")
    @EndUserPermission({EndUserTypeSetting.USER_TYPE_ADMIN_STRING,EndUserTypeSetting.USER_TYPE_DEVELOPER_STRING})
    public Tip getResultList(@PathVariable("sqlFile") String sqlFile, HttpServletRequest request) {

        return SuccessTip.create(devopsServices.executeSql(request,sqlFile));
    }

    @GetMapping("/allUser")
    @EndUserPermission({EndUserTypeSetting.USER_TYPE_ADMIN_STRING,EndUserTypeSetting.USER_TYPE_DEVELOPER_STRING})
    public Tip getAllUserAccount(Page<UserAccount> page,
                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(name = "search", required = false) String search) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        UserAccount record = new UserAccount();
        List<UserAccount> userAccounts =  queryEndpointUserDao.getAllUserList(page,record,null,search,null,null,null);
       page.setRecords(userAccounts);
        return SuccessTip.create(page);
    }


}
