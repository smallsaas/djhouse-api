package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUnit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyUnitMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyUnitService
 * @author Code generator
 * @since 2022-05-23
 */

@Service
public class CRUDHousePropertyUnitServiceImpl  extends CRUDServiceOnlyImpl<HousePropertyUnit> implements CRUDHousePropertyUnitService {





        @Resource
        protected HousePropertyUnitMapper housePropertyUnitMapper;

        @Override
        protected BaseMapper<HousePropertyUnit> getMasterMapper() {
                return housePropertyUnitMapper;
        }







}


