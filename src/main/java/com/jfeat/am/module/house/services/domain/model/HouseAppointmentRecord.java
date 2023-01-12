package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

import java.util.Date;

/**
 * Created by Code generator on 2022-07-13
 */
public class HouseAppointmentRecord extends HouseAppointment {
    private Long appointmentTimeStamp;

    private String nowDate;

    private Long appointmentCreateTimeStamp;

    private HouseRentAssetModel houseAssetModel;

    private String simpleTime;

    private String appointmentStrTime;

    private String appointmentEndTimeStr;

    private String userRealName;

    private String serverRealName;

    private String userAvatar;

    private String serverAvatar;


    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getServerRealName() {
        return serverRealName;
    }

    public void setServerRealName(String serverRealName) {
        this.serverRealName = serverRealName;
    }

    public String getAppointmentEndTimeStr() {
        return appointmentEndTimeStr;
    }

    public void setAppointmentEndTimeStr(String appointmentEndTimeStr) {
        this.appointmentEndTimeStr = appointmentEndTimeStr;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public Long getAppointmentCreateTimeStamp() {
        return appointmentCreateTimeStamp;
    }

    public void setAppointmentCreateTimeStamp(Long appointmentCreateTimeStamp) {
        this.appointmentCreateTimeStamp = appointmentCreateTimeStamp;
    }

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

    public HouseRentAssetModel getHouseAssetModel() {
        return houseAssetModel;
    }

    public void setHouseAssetModel(HouseRentAssetModel houseAssetModel) {
        this.houseAssetModel = houseAssetModel;
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

    public String getAppointmentStrTime() {
        return appointmentStrTime;
    }

    public void setAppointmentStrTime(String appointmentStrTime) {
        this.appointmentStrTime = appointmentStrTime;
    }
}
