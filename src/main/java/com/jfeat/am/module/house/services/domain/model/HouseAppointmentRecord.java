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

    private String userAvatar;

    private String serverAvatar;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getServerAvatar() {
        return serverAvatar;
    }

    public void setServerAvatar(String serverAvatar) {
        this.serverAvatar = serverAvatar;
    }

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
