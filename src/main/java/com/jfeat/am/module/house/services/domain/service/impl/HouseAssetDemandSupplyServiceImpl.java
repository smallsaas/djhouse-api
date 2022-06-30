package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAssetDemandSupplyService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetDemandSupplyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetDemandSupplyService")
public class HouseAssetDemandSupplyServiceImpl extends CRUDHouseAssetDemandSupplyServiceImpl implements HouseAssetDemandSupplyService {

    @Override
    protected String entityName() {
        return "HouseAssetDemandSupply";
    }


                            }
