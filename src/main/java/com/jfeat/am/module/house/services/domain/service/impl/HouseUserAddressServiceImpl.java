package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserAddressService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserAddressServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserAddressService")
public class HouseUserAddressServiceImpl extends CRUDHouseUserAddressServiceImpl implements HouseUserAddressService {

    @Override
    protected String entityName() {
        return "HouseUserAddress";
    }


                            }
