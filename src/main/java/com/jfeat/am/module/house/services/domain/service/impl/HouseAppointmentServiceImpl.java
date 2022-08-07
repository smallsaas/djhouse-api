package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.jfeat.am.module.house.services.domain.model.HouseAppointmentRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAppointmentService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAppointmentServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
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

    public List<Map<String,Object>> formatAppointmentList(List<HouseAppointmentRecord> appointmentRecordList) {

        Date current = new Date();
        List<Map<String,Object>> result = new ArrayList<>(100);
        for (int i=0;i<100;i++) result.add(null);
//        Map<String, Map<String,Object>> map = new HashMap<>();
        for (int i=0;i<appointmentRecordList.size();i++){
            Map<String,Object> item = null;
            Date appointmentTime =  appointmentRecordList.get(i).getAppointmentTime();
            Long house = (appointmentTime.getTime()-current.getTime())/(60*60*1000);
//            小于一个小时
            if (house==0){
                Long minutes = (appointmentTime.getTime()-current.getTime())/(60*1000);

//                一分钟以内
                if (minutes>0 && minutes<=1){
                    if (result.get(1)!=null){
                        Map<String,Object> map = result.get(1);
                        List<HouseAppointmentRecord> list = (List<HouseAppointmentRecord>) map.get("list");
                        list.add(appointmentRecordList.get(i));
                        result.add(1,item);
                    }else {
                        item = new HashMap<>();
                        item.put("countdown","还有1分钟");
                        List<HouseAppointmentRecord> list = new ArrayList<>();
                        list.add(appointmentRecordList.get(i));
                        item.put("list",list);
                        result.add(1,item);
                    }
                }else if (minutes>1 && minutes<60){
                    String key = "minute".concat(String.valueOf(minutes));
                    int index = Math.toIntExact(minutes);
                    if (result.get(index)!=null){
                        Map<String,Object> map = result.get(index);
                        List<HouseAppointmentRecord> list = (List<HouseAppointmentRecord>) map.get("list");
                        list.add(appointmentRecordList.get(i));
                        result.add(index,item);
                    }else {
                        item = new HashMap<>();
                        item.put("countdown","还有".concat(String.valueOf(minutes)).concat("分钟"));
                        List<HouseAppointmentRecord> list = new ArrayList<>();
                        list.add(appointmentRecordList.get(i));
                        item.put("list",list);
                        result.add(index,item);
                    }
                }
            }
//            大于一个小时
            if (house>0 && house<24){
                String key = "house".concat(String.valueOf(house));
                int index = Math.toIntExact(house)+60;
                if (result.get(index)!=null){
                    Map<String,Object> map = result.get(index);
                    List<HouseAppointmentRecord> list = (List<HouseAppointmentRecord>) map.get("list");
                    list.add(appointmentRecordList.get(i));
                    result.add(index,item);
                }else {
                    item = new HashMap<>();
                    item.put("countdown","还有".concat(String.valueOf(house)).concat("小时"));
                    List<HouseAppointmentRecord> list = new ArrayList<>();
                    list.add(appointmentRecordList.get(i));
                    item.put("list",list);
                    result.add(index,item);
                }
            }

        }


        return result;
    }
}
