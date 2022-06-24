package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.*;

import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public class HouseAssetRecord extends HouseAsset {

    private List<Product> decoratePlanProductList;

    private String username;

    private String phone;

    private String userAvatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Product> getDecoratePlanProductList() {
        return decoratePlanProductList;
    }

    public void setDecoratePlanProductList(List<Product> decoratePlanProductList) {
        this.decoratePlanProductList = decoratePlanProductList;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
