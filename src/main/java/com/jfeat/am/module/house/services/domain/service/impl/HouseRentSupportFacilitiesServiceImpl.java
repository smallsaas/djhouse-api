package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseRentSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentSupportFacilitiesServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseRentSupportFacilitiesService")
public class HouseRentSupportFacilitiesServiceImpl extends CRUDHouseRentSupportFacilitiesServiceImpl implements HouseRentSupportFacilitiesService {

    @Override
    protected String entityName() {
        return "HouseRentSupportFacilities";
    }


                            }
