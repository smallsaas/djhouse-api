package com.jfeat.am.module.house.services.utility;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/*
身份验证类
 */
@Configuration
public class Authentication {

    @Resource
    private QueryEndpointUserDao queryEndpointUserDao;

    /*
    验证运维身份
     */
    public Boolean verifyOperation(Long userId){
        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(userId);
        if (endpointUserModel==null || !(endpointUserModel.getType().equals(SecurityConstants.USER_TYPE_OPERATION))){
            return false;
        }
        return true;
    }

}
