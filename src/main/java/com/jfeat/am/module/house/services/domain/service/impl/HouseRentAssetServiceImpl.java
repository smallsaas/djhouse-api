package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentAssetServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseRentAssetService")
public class HouseRentAssetServiceImpl extends CRUDHouseRentAssetServiceImpl implements HouseRentAssetService {

    @Override
    protected String entityName() {
        return "HouseRentAsset";
    }


                            }
