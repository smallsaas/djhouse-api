package com.jfeat.am.module.house.api.app.administrators;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.dao.QueryUserAccountDao;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



//    查看管理员
    @GetMapping("/getAdministratorList")
    public Tip getAdministratorList(@RequestParam(value = "orgId",required = true) Long orgId){

        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserAccount.ORG_ID,orgId);
        List<UserAccount> userAccountList = userAccountMapper.selectList(queryWrapper);

        List<UserAccount> result = new ArrayList<>();
        for (UserAccount userAccount:userAccountList){
            if (userAccount.getType()!=null){
                List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
//                判断是否是社区管理员或者运维人员
                if (userTypeList.contains(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER) || userTypeList.contains(EndUserTypeSetting.USER_TYPE_OPERATION)){
                    userAccount.setUserTypeList(userTypeList);
                    result.add(userAccount);
                }
            }
        }

        return SuccessTip.create(result);
    }
}
