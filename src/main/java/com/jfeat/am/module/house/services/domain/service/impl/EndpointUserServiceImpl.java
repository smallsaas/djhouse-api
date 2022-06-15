package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDEndpointUserServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("endpointUserService")
public class EndpointUserServiceImpl extends CRUDEndpointUserServiceImpl implements EndpointUserService {

    @Override
    protected String entityName() {
        return "EndpointUser";
    }


                            }
