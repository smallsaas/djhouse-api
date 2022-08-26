package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentAssetService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseRentAssetService extends CRUDHouseRentAssetService {

//    用户上架出租自己房子
    int createUserRentAsset(HouseRentAsset entity);

    int updateUserRentAsset(HouseRentAsset entity);

}