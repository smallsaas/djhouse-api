package com.jfeat.am.module.house.api.app.common;

import com.jfeat.am.module.house.services.domain.service.UserRegisterService;
import com.jfeat.am.module.house.services.gen.persistence.model.OAuthTokenRequestExtend;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.weChatMiniprogram.services.domain.model.wx.OAuthTokenRequest;
import com.jfeat.users.weChatMiniprogram.services.domain.service.SysThirdPartyUserService;
import org.apache.poi.hssf.record.TabIdRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.plaf.SeparatorUI;


@RestController
@RequestMapping("/api/u/login")
public class UserLoginEndpoint {


    @Resource
    UserRegisterService userRegisterService;


//    注册用户
    @PostMapping
    public Tip createUserAccount(@RequestBody OAuthTokenRequestExtend token){
        return SuccessTip.create(userRegisterService.register(token));
    }

}
