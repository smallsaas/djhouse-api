package com.jfeat.am.module.house.api;


import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/house/test")
public class HouseTest {

    @Resource
    HouseEmailService houseEmailService;


    @GetMapping
    public Tip test(){
//        houseEmailService.sendAssetMatchLog("张三");
        return SuccessTip.create();
    }

}
