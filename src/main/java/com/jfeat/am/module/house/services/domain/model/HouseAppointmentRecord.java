package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

import java.util.Date;

/**
 * Created by Code generator on 2022-07-13
 */
public class HouseAppointmentRecord extends HouseAppointment {
    private Long appointmentTimeStamp;

    private HouseAsset houseAsset;

    private String simpleTime;

    public HouseAsset getHouseAsset() {
        return houseAsset;
    }

    public void setHouseAsset(HouseAsset houseAsset) {
        this.houseAsset = houseAsset;
    }

    public Long getAppointmentTimeStamp() {
        return appointmentTimeStamp;
    }

    public void setAppointmentTimeStamp(Long appointmentTimeStamp) {
        this.appointmentTimeStamp = appointmentTimeStamp;
    }

    public String getSimpleTime() {
        return simpleTime;
    }

    public void setSimpleTime(String simpleTime) {
        this.simpleTime = simpleTime;
    }
}
