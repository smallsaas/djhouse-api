package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseApplicationOperationsMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseApplicationOperationsService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseApplicationOperationsService
 * @author Code generator
 * @since 2022-08-11
 */

@Service
public class CRUDHouseApplicationOperationsServiceImpl  extends CRUDServiceOnlyImpl<HouseApplicationOperations> implements CRUDHouseApplicationOperationsService {





        @Resource
        protected HouseApplicationOperationsMapper houseApplicationOperationsMapper;

        @Override
        protected BaseMapper<HouseApplicationOperations> getMasterMapper() {
                return houseApplicationOperationsMapper;
        }







}


