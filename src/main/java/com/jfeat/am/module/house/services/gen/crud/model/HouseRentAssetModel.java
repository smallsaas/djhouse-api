package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

/**
 * Created by Code generator on 2022-07-16
 *  * slaves.size() : 0
 *  * modelpack : $modelpack
 */
public class HouseRentAssetModel extends HouseRentAsset{

//    用户是否预约
    private Boolean userAppointment;

    // 置业顾问联系电话
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getUserAppointment() {
        return userAppointment;
    }

    public void setUserAppointment(Boolean userAppointment) {
        this.userAppointment = userAppointment;
    }
}
