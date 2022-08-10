package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentSupportFacilitiesService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseRentSupportFacilitiesService
 * @author Code generator
 * @since 2022-08-08
 */

@Service
public class CRUDHouseRentSupportFacilitiesServiceImpl  extends CRUDServiceOnlyImpl<HouseRentSupportFacilities> implements CRUDHouseRentSupportFacilitiesService {





        @Resource
        protected HouseRentSupportFacilitiesMapper houseRentSupportFacilitiesMapper;

        @Override
        protected BaseMapper<HouseRentSupportFacilities> getMasterMapper() {
                return houseRentSupportFacilitiesMapper;
        }







}


