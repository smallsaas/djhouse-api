package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSurroundFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSurroundFacilitiesService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseSurroundFacilitiesService
 * @author Code generator
 * @since 2022-08-05
 */

@Service
public class CRUDHouseSurroundFacilitiesServiceImpl  extends CRUDServiceOnlyImpl<HouseSurroundFacilities> implements CRUDHouseSurroundFacilitiesService {





        @Resource
        protected HouseSurroundFacilitiesMapper houseSurroundFacilitiesMapper;

        @Override
        protected BaseMapper<HouseSurroundFacilities> getMasterMapper() {
                return houseSurroundFacilitiesMapper;
        }







}


