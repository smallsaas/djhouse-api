package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.dao.EndpointUserMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDEndpointUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDEndUserService
 * @author Code generator
 * @since 2022-06-14
 */

@Service
public class CRUDEndpointUserServiceImpl extends CRUDServiceOnlyImpl<EndpointUser> implements CRUDEndpointUserService {





        @Resource
        protected EndpointUserMapper endUserMapper;

        @Override
        protected BaseMapper<EndpointUser> getMasterMapper() {
                return endUserMapper;
        }







}


