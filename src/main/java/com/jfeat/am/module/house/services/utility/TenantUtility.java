package com.jfeat.am.module.house.services.utility;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TenantUtility {

    @Resource
    UserAccountMapper userAccountMapper;

    public Long getCurrentOrgId(Long userId){

        if (userId==null){
            throw new BusinessException(BusinessCode.BadRequest,"userId不能为空");
        }
        

        UserAccount userAccount =  userAccountMapper.selectById(userId);
        if (userAccount==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有找到该用户信息");
        }
        if (userAccount.getCurrentOrgId()!=null){
            return userAccount.getCurrentOrgId();
        }
        if (userAccount.getOrgId()!=null){
            return userAccount.getOrgId();
        }
        return null;
    }
}
