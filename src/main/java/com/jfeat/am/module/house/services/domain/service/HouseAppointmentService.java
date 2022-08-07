package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.jfeat.am.module.house.services.domain.model.HouseAppointmentRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAppointmentService;

import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAppointmentService extends CRUDHouseAppointmentService {
    public List<Map<String,Object>> formatAppointmentList(List<HouseAppointmentRecord> appointmentRecordList);
}