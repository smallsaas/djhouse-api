package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetService")
public class HouseAssetServiceImpl extends CRUDHouseAssetServiceImpl implements HouseAssetService {

    @Override
    protected String entityName() {
        return "HouseAsset";
    }


                            }
