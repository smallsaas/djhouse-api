package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSupportFacilitiesTypeDao;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesTypeOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesTypeOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    HouseSupportFacilitiesMapper houseSupportFacilitiesMapper;

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

    @Override
    @Transactional
    public Integer deleteHouseSupportFacilitiesType(Long id) {
        Integer affect=0;
        affect += houseSupportFacilitiesTypeMapper.deleteById(id);

        QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseSupportFacilities.TYPE_ID,id);
        affect += houseSupportFacilitiesMapper.delete(queryWrapper);
        return affect;
    }
}
