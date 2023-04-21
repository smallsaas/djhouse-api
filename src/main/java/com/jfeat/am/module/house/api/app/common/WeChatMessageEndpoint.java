package com.jfeat.am.module.house.api.app.common;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.common.WeChatMiniProgramMessage;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 微信消息controller
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/21 14:00
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/wechat-message-test")
public class WeChatMessageEndpoint {

    // 微信消息发送类
    @Resource
    WeChatMiniProgramMessage weChatMiniProgramMessage;

    /**
     * 用来测试微信一次性订阅消息的api，因为还没有业务，所以采用直接调用的方式测试
     * @return
     */
    @PostMapping()
    public Tip weChatMessageTest(@RequestBody JSONObject params) {
        // 获取用户openid
        String openid = params.getString("openid");
        if (openid == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"缺少openid");
        weChatMiniProgramMessage.sendOrderProgressMessage(openid);
        return SuccessTip.create();
    }
}
