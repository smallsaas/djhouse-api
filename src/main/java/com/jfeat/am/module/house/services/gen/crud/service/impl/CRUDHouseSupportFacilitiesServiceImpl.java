package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSupportFacilitiesService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseSupportFacilitiesService
 * @author Code generator
 * @since 2022-08-05
 */

@Service
public class CRUDHouseSupportFacilitiesServiceImpl  extends CRUDServiceOnlyImpl<HouseSupportFacilities> implements CRUDHouseSupportFacilitiesService {





        @Resource
        protected HouseSupportFacilitiesMapper houseSupportFacilitiesMapper;

        @Override
        protected BaseMapper<HouseSupportFacilities> getMasterMapper() {
                return houseSupportFacilitiesMapper;
        }







}


