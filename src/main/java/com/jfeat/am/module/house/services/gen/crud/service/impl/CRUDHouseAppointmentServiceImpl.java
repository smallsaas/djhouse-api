package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAppointmentMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAppointmentService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAppointmentService
 * @author Code generator
 * @since 2022-07-13
 */

@Service
public class CRUDHouseAppointmentServiceImpl  extends CRUDServiceOnlyImpl<HouseAppointment> implements CRUDHouseAppointmentService {





        @Resource
        protected HouseAppointmentMapper houseAppointmentMapper;

        @Override
        protected BaseMapper<HouseAppointment> getMasterMapper() {
                return houseAppointmentMapper;
        }







}


