package com.jfeat.am.module.house.api.userself;

import com.jfeat.am.module.house.services.domain.model.SupplierBindModel;
import com.jfeat.am.module.house.services.domain.service.SupplierService;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("UserSupplierEndpoint")
@RequestMapping("/api/u/supplier")
public class UserSupplierEndpoint {


    @Resource
    SupplierService supplierService;


    //发起绑定申请
/*    @PostMapping("/bindUser")
    public Tip BindSupplier(@RequestBody SupplierBindModel supplierBindModel){

        Integer integer = supplierService.bindSupplier(supplierBindModel);

        return SuccessTip.create(integer);
    }*/
}
