package com.jfeat.am.module.house.services.utility;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class UserAccountUtility {

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    public List<Integer> getUserTypeList(Long userId){
        UserAccount userAccount =  userAccountMapper.selectById(userId);

        if (userAccount!=null && userAccount.getType()!=null){
            return userAccountService.getUserTypeList(userAccount.getType());
        }
        return null;
    }
}
