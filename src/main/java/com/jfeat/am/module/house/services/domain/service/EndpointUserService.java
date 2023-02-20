package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDEndpointUserService;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface EndpointUserService extends CRUDEndpointUserService {

    EndpointUserModel listUser(Long id);

    /**
     * 销售 修改 置业顾问的联系电话
     *
     * @param id 置业顾问的id
     * @param contact 修改后的联系电话
     * @return
     */
    int salesUpdateIntermediaryContact(Long id,String contact);
}