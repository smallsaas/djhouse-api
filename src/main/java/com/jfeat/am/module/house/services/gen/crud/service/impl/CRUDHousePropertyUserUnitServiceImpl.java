package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserUnit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyUserUnitMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyUserUnitService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyUserUnitService
 * @author Code generator
 * @since 2022-05-28
 */

@Service
public class CRUDHousePropertyUserUnitServiceImpl  extends CRUDServiceOnlyImpl<HousePropertyUserUnit> implements CRUDHousePropertyUserUnitService {





        @Resource
        protected HousePropertyUserUnitMapper housePropertyUserUnitMapper;

        @Override
        protected BaseMapper<HousePropertyUserUnit> getMasterMapper() {
                return housePropertyUserUnitMapper;
        }







}


