package com.jfeat.am.module.house.api.app.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 公共api控制器
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/2 14:19
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/common-api")
public class CommonController {

    @Resource
    HouseEmailService houseEmailService;

    /**
     * 用于自定义发送邮件的测试接口
     * @return
     */
    @PostMapping("/send-email-test")
    public Tip sendEmailTestAPI(@RequestBody JSONObject emailInfo) {

        // 参数判断
        String emailTitle = emailInfo.getString("emailTitle");
        String emailContent = emailInfo.getString("emailContent");
        List<String> toEmailAddressList = null;
        if (emailInfo.getJSONArray("toEmailAddressList") != null) {
            toEmailAddressList = emailInfo.getJSONArray("toEmailAddressList").toJavaList(String.class);
        } else {
            toEmailAddressList = new ArrayList<String>();
        }

        // 发送邮件，异步发送，直接返回，不等待
        houseEmailService.sendEmailByCustomization(emailTitle, emailContent,toEmailAddressList);
        return SuccessTip.create();
    }
}
