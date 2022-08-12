package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseApplicationOperationsService;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseApplicationOperationsService extends CRUDHouseApplicationOperationsService {

    int passApplicationOperations(Long id);
}