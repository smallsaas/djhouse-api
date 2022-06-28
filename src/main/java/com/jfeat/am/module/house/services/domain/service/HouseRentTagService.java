package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentTagService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentTag;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseRentTagService extends CRUDHouseRentTagService {
    List<HouseRentTag> getHouseRentTags(String tagIds);
}