package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSurroundFacilitiesTypeOverModelService;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseSurroundFacilitiesTypeOverModelService extends CRUDHouseSurroundFacilitiesTypeOverModelService {

    List<HouseSurroundFacilitiesTypeRecord> getCommunityFacilities(Long community);
}