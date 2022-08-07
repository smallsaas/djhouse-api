package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HousePropertyBuildingUnitService extends CRUDHousePropertyBuildingUnitService {
    int updateUnitBind(Long unitId,HousePropertyBuildingUnit unit);

    int updateUnitInfo();
}