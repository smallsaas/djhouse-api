package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSupportFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseSupportFacilitiesTypeOverModelService extends CRUDHouseSupportFacilitiesTypeOverModelService {
    List<HouseSupportFacilitiesTypeRecord> getHouseSupportFacilitiesTypeItem();

    Integer deleteHouseSupportFacilitiesType(Long id);
}