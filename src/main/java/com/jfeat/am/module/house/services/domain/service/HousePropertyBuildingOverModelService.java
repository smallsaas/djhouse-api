package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyBuildingOverModelService;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HousePropertyBuildingOverModelService extends CRUDHousePropertyBuildingOverModelService {
    int initHouseProperty(HousePropertyBuildingModel entity);

    int modifyHouseBuilding(HousePropertyBuildingModel housePropertyBuildingModel);
}