package com.jfeat.am.module.house.api.app.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.fs.service.LoadFileCodeService;
import com.jfeat.fs.util.FileInfo;
import com.jfeat.module.kafka.services.domain.model.KafkaEmailProperties;
import com.jfeat.module.kafka.services.domain.service.KafkaEmailProductService;
import com.jfeat.module.kafka.services.domain.util.FileUtility;
import com.jfeat.module.kafka.services.domain.util.TemplateUtil;
import com.jfeat.users.weChatMiniprogram.services.domain.dao.SysThirdPartyUserMapper;
import com.jfeat.users.weChatMiniprogram.services.domain.model.wx.SysThirdPartyUser;
import com.jfeat.users.weChatMiniprogram.services.domain.model.wx.WxAvatarAndNickName;
import com.jfeat.users.weChatMiniprogram.services.domain.service.SysThirdPartyUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 在user-account中移动过来，模仿oauth认证，颁发token等功能
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/11 11:16
 * @author: hhhhhtao
 */
@Api(value = "登录认证")
@RestController
@RequestMapping("/api/oauth")
public class HouseAuthEndpoint {

    // 获取日志对象
    private static final Logger logger = LoggerFactory.getLogger(HouseAuthEndpoint.class);

    // 邮件模版
    private static final String USER_REGISTER = "【新用户注册】{userName}在{createTime}注册了{appid};大匠注册人数：{houseCount},小匠注册人数:{housingCount}，团购注册人数:{buyCount},总注册数:{total}";

    // 邮件收件人列表文件路径
    private static final String EMAIL_LIST_PATH = "./email/email-list.txt";

    @Resource
    LoadFileCodeService loadFileCodeService;

    @Resource
    KafkaEmailProductService kafkaEmailProductService;

    @Resource
    SysThirdPartyUserMapper sysThirdPartyUserMapper;

    @Autowired
    SysThirdPartyUserService sysThirdPartyUserService;

    /**
     * 更新微信头像和昵称
     *
     * @param entity
     * @return
     */
    @PostMapping("/avatarAndName")
    public Tip updateAvatarAndName(WxAvatarAndNickName entity){

        FileInfo fileInfo = null;
        if (entity.getImageUrl()!=null){
            fileInfo = loadFileCodeService.uploadFile(entity.getImageUrl(),"/avatar","./avatar",null);
            if (fileInfo.getUrl()==null || fileInfo.getUrl().equals("")){
                throw new BusinessException(BusinessCode.FileNotFound,"文件保存失败");
            }
        }

        SysThirdPartyUser sysThirdPartyUser = new SysThirdPartyUser();
        sysThirdPartyUser.setFrom(2);
        if (fileInfo!=null){
            sysThirdPartyUser.setImageUrl(fileInfo.getUrl());
        }
        sysThirdPartyUser.setNickname(entity.getNickName());
        sysThirdPartyUser.setOpenId(entity.getOpenId());
        sysThirdPartyUser.setApp(entity.getApp());

//        发邮件
        KafkaEmailProperties kafkaEmailProperties = new KafkaEmailProperties();
        kafkaEmailProperties.setSubject("新用户注册");
        List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
        kafkaEmailProperties.setToAddressList(emailList);

        SysThirdPartyUser thirdPartyUser =  sysThirdPartyUserService.updateAvatarAndName(sysThirdPartyUser);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName",thirdPartyUser.getNickname());
        jsonObject.put("avatar",thirdPartyUser.getImageUrl());

//        String template = kafkaEmailTemplateProperties.getTemplateContent("userRegister");
        String template = USER_REGISTER;

        Map<String,String> tempalteMap = new HashMap<>();
        tempalteMap.put("userName",thirdPartyUser.getNickname()==null?"":thirdPartyUser.getNickname());
        tempalteMap.put("createTime",thirdPartyUser.getCreateTime()==null?"":thirdPartyUser.getCreateTime().toString());

        String app = thirdPartyUser.getApp()==null?"":thirdPartyUser.getApp();
        if (app.equals("1")){
            app="大匠小程序";
        }else if (app.equals("2")){
            app="小匠小程序";
        }else if (app.equals("3")){
            app="团购小程序";
        }

        QueryWrapper<SysThirdPartyUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(SysThirdPartyUser.APP, Arrays.asList("1","2","3"));
        List<SysThirdPartyUser> appUserCount = sysThirdPartyUserMapper.selectList(queryWrapper);

        Integer house = 0;
        Integer housing=0;
        Integer buy = 0;
        if (appUserCount!=null && appUserCount.size()>0){
            for (SysThirdPartyUser partyUser:appUserCount){
                if (partyUser.getApp().equals("1")){
                    house+=1;
                }else if (partyUser.getApp().equals("2")){
                    housing+=1;
                }else if (partyUser.getApp().equals("3")){
                    buy+=1;
                }
            }
        }

        tempalteMap.put("appid",app);
        tempalteMap.put("houseCount",String.valueOf(house));
        tempalteMap.put("housingCount",String.valueOf(housing));
        tempalteMap.put("buyCount",String.valueOf(buy));
        tempalteMap.put("total",String.valueOf((house+housing+buy)));
        String content =  TemplateUtil.getContent(template,tempalteMap);
        kafkaEmailProperties.setContent(content);
        kafkaEmailProductService.sendTextEmail(kafkaEmailProperties);

        return SuccessTip.create(jsonObject);
    }
}
