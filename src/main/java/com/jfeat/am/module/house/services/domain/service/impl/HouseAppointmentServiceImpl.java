package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAppointmentService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAppointmentServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAppointmentService")
public class HouseAppointmentServiceImpl extends CRUDHouseAppointmentServiceImpl implements HouseAppointmentService {

    @Override
    protected String entityName() {
        return "HouseAppointment";
    }


                            }
