package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrType;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrTypeMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseVrTypeService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseVrTypeService
 * @author Code generator
 * @since 2022-08-16
 */

@Service
public class CRUDHouseVrTypeServiceImpl  extends CRUDServiceOnlyImpl<HouseVrType> implements CRUDHouseVrTypeService {





        @Resource
        protected HouseVrTypeMapper houseVrTypeMapper;

        @Override
        protected BaseMapper<HouseVrType> getMasterMapper() {
                return houseVrTypeMapper;
        }







}


