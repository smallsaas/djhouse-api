package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateAddressService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserDecorateAddressServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserDecorateAddressService")
public class HouseUserDecorateAddressServiceImpl extends CRUDHouseUserDecorateAddressServiceImpl implements HouseUserDecorateAddressService {

    @Override
    protected String entityName() {
        return "HouseUserDecorateAddress";
    }


                            }
