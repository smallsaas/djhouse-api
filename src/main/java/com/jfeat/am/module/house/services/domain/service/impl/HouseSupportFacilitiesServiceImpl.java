package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseSupportFacilitiesService")
public class HouseSupportFacilitiesServiceImpl extends CRUDHouseSupportFacilitiesServiceImpl implements HouseSupportFacilitiesService {

    @Override
    protected String entityName() {
        return "HouseSupportFacilities";
    }


                            }
