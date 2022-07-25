package com.jfeat.am.module.house.services.utility;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/*
身份验证类
 */
@Configuration
public class Authentication {

    @Resource
    private QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    UserAccountService userAccountService;

    /*
    验证运维身份
     */
    public Boolean verifyOperation(Long userId){
        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(userId);
        if (endpointUserModel!=null && endpointUserModel.getType()!=null){
            List<Integer> userTypeList = userAccountService.getUserTypeList(endpointUserModel.getType());
            if (userTypeList.contains(SecurityConstants.USER_TYPE_OPERATION)){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

}
