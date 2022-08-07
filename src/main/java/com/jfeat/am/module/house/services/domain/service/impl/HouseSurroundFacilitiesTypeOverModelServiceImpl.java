package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSurroundFacilitiesTypeDao;
import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSurroundFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSurroundFacilitiesTypeOverModelServiceImpl;
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

@Service("houseSurroundFacilitiesTypeService")
public class HouseSurroundFacilitiesTypeOverModelServiceImpl extends CRUDHouseSurroundFacilitiesTypeOverModelServiceImpl implements HouseSurroundFacilitiesTypeOverModelService {

    @Resource
    QueryHouseSurroundFacilitiesTypeDao queryHouseSurroundFacilitiesTypeDao;

    @Override
    protected String entityName() {
        return "HouseSurroundFacilitiesType";
    }


    @Override
    public List<HouseSurroundFacilitiesTypeRecord> getCommunityFacilities(Long community) {

        if (community==null){
            return null;
        }
        HouseSurroundFacilitiesTypeRecord houseSurroundFacilitiesTypeRecord = new HouseSurroundFacilitiesTypeRecord();
        houseSurroundFacilitiesTypeRecord.setCommunityId(community);

        return queryHouseSurroundFacilitiesTypeDao.findHouseSurroundFacilitiesTypeItem(null,houseSurroundFacilitiesTypeRecord,null,null,null,null,null);
    }
}
