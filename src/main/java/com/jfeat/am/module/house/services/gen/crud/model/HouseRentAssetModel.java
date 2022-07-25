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


    private HouseAssetModel houseAssetModel;

    private String serverPhone;
    private String serverName;
    private String serverAvatar;

    public String getServerPhone() {
        return serverPhone;
    }

    public void setServerPhone(String serverPhone) {
        this.serverPhone = serverPhone;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAvatar() {
        return serverAvatar;
    }

    public void setServerAvatar(String serverAvatar) {
        this.serverAvatar = serverAvatar;
    }

    public HouseAssetModel getHouseAssetModel() {
        return houseAssetModel;
    }

    public void setHouseAssetModel(HouseAssetModel houseAssetModel) {
        this.houseAssetModel = houseAssetModel;
    }



}
