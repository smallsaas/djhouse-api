package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyBuildingUnitService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyBuildingUnitService
 * @author Code generator
 * @since 2022-05-27
 */

@Service
public class CRUDHousePropertyBuildingUnitServiceImpl  extends CRUDServiceOnlyImpl<HousePropertyBuildingUnit> implements CRUDHousePropertyBuildingUnitService {





        @Resource
        protected HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

        @Override
        protected BaseMapper<HousePropertyBuildingUnit> getMasterMapper() {
                return housePropertyBuildingUnitMapper;
        }







}


