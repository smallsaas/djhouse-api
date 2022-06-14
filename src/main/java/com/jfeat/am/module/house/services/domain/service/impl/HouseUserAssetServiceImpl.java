package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserAssetServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserAssetService")
public class HouseUserAssetServiceImpl extends CRUDHouseUserAssetServiceImpl implements HouseUserAssetService {

    @Override
    protected String entityName() {
        return "HouseUserAsset";
    }


                            }
