package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentLogMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentLogService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseRentLogService
 * @author Code generator
 * @since 2023-01-06
 */

@Service
public class CRUDHouseRentLogServiceImpl  extends CRUDServiceOnlyImpl<HouseRentLog> implements CRUDHouseRentLogService {





        @Resource
        protected HouseRentLogMapper houseRentLogMapper;

        @Override
        protected BaseMapper<HouseRentLog> getMasterMapper() {
                return houseRentLogMapper;
        }







}


