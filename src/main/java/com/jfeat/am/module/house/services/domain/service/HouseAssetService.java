package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetService extends CRUDHouseAssetService {


    Integer addAsset(HouseUserAsset entity);

}