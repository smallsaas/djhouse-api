package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyCommunityOverModelService;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HousePropertyCommunityOverModelService extends CRUDHousePropertyCommunityOverModelService {
//    删除小区 更新社区是否可以删除
    int deleteCommunity(Long id);

    int createCommunity(HousePropertyCommunityModel entity);
}