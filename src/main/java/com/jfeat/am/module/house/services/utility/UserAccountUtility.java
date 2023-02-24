package com.jfeat.am.module.house.services.utility;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
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


    /**
     * 判断用户权限
     *
     * @param userType 权限类型
     * @return 是否符合
     */
    public Boolean judgementJurisdiction(Integer userType) {

        // 获取用户信息
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
        if (userAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"无效用户");

        // 获取用户类型列表
        if (userAccount.getType() != null) {
            List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
            // 判断用户是否拥有指定权限
            if (userTypeList != null && userTypeList.contains(userType)) return true;
        }

        return false;
    }
}
