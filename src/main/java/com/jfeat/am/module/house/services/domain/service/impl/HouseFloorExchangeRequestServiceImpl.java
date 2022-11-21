package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseFloorExchangeRequestService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseFloorExchangeRequestServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseFloorExchangeRequestService")
public class HouseFloorExchangeRequestServiceImpl extends CRUDHouseFloorExchangeRequestServiceImpl implements HouseFloorExchangeRequestService {

    @Override
    protected String entityName() {
        return "HouseFloorExchangeRequest";
    }


                            }
