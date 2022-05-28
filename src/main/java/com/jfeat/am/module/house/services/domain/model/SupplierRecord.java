package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;

/**
 * Created by Code generator on 2022-05-19
 */
public class SupplierRecord extends Supplier{
    String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
