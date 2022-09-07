package com.jfeat.am.module.house.api.app.administrators;


import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.service.AdministratorService;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.DriverManager;

@RestController
@RequestMapping("/api/u/house/administrators/userDataClearEndpoint")
public class UserDataClearEndpoint {

    @Resource
    AdministratorService administratorService;



    @DeleteMapping("/deleteAllUserData")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_ADMIN_STRING})
    public Tip deleteAllUserData(){
        return SuccessTip.create(administratorService.deleteAllUserData());

    }
}
