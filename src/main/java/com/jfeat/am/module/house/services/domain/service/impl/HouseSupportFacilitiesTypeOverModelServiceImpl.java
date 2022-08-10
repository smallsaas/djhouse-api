package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSupportFacilitiesTypeDao;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesTypeOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    QueryHouseSupportFacilitiesTypeDao queryHouseSupportFacilitiesTypeDao;

    @Override
    protected String entityName() {
        return "HouseSupportFacilitiesType";
    }


    @Override
    public List<HouseSupportFacilitiesTypeRecord> getHouseSupportFacilitiesTypeItem() {
        HouseSupportFacilitiesTypeRecord houseSurroundFacilitiesTypeRecord = new HouseSupportFacilitiesTypeRecord();
        List<HouseSupportFacilitiesTypeRecord> list = queryHouseSupportFacilitiesTypeDao.findHouseSupportFacilitiesTypeItem(null,houseSurroundFacilitiesTypeRecord,null
        ,null,null,null,null);
        return list;
    }
}
