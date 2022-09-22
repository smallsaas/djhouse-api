package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseMenuService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseMenuService extends CRUDHouseMenuService {
    int updateMenuStatus(HouseMenu entity);


}