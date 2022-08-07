package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesTypeOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseSupportFacilitiesTypeService")
public class HouseSupportFacilitiesTypeOverModelServiceImpl extends CRUDHouseSupportFacilitiesTypeOverModelServiceImpl implements HouseSupportFacilitiesTypeOverModelService {


    @Override
    protected String entityName() {
        return "HouseSupportFacilitiesType";
    }


    @Override
    public List<HouseSupportFacilitiesType> getHouseSupportFacilitiesTypeItem() {

        return null;
    }
}
