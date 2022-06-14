package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseEquityDemandSupplyService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseEquityDemandSupplyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseEquityDemandSupplyService")
public class HouseEquityDemandSupplyServiceImpl extends CRUDHouseEquityDemandSupplyServiceImpl implements HouseEquityDemandSupplyService {

    @Override
    protected String entityName() {
        return "HouseEquityDemandSupply";
    }


                            }
