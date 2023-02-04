package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDEndpointUserServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    QueryEndpointUserDao endpointUserDao;

    @Override
    protected String entityName() {
        return "EndpointUser";
    }


    @Override
    public EndpointUserModel listUser(Long id) {
        return endpointUserDao.queryMasterModel(id);
    }
}
