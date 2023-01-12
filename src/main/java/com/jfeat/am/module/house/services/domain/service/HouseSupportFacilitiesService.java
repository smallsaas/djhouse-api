package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseSupportFacilitiesService extends CRUDHouseSupportFacilitiesService {

//    判断出租房屋家居设施清单勾选情况

    List<HouseSupportFacilitiesTypeRecord> getRentHouseSupportFacilitiesStatus(Long assetId, List<HouseSupportFacilitiesTypeRecord> list);
}