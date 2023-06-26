package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDEndpointUserService;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;

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

    /**
     * 获取置业顾问信息
     * 如果请求用户没有销售权限 或 置业顾问id实际参数为非置业顾问都会抛出异常
     *
     * @param id 置业顾问id
     * @return
     */
    EndpointUser salesGetIntermediaryInfo(Long id);

    /**
     * 判断是否存在指定id的用户
     * @param id
     * @return 存在返回true / 不存在返回false
     */
    Boolean existUser(Long id);
}