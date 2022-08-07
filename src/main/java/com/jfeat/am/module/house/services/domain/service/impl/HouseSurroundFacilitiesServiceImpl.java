package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseSurroundFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSurroundFacilitiesServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseSurroundFacilitiesService")
public class HouseSurroundFacilitiesServiceImpl extends CRUDHouseSurroundFacilitiesServiceImpl implements HouseSurroundFacilitiesService {

    @Override
    protected String entityName() {
        return "HouseSurroundFacilities";
    }


                            }
