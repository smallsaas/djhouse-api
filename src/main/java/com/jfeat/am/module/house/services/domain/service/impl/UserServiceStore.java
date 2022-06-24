package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.users.account.services.domain.service.BusinessUserLoginInfo;
import com.jfeat.users.account.services.domain.service.impl.ServiceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
public class UserServiceStore implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceStore.class);

    @Resource
    private BusinessUserLoginInfo businessUserLoginInfo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (businessUserLoginInfo == null) {
            LOGGER.error("初始化服务失败");
        } else {
            ServiceStore.setBusinessUserLoginInfo(businessUserLoginInfo);
            LOGGER.info("初始化服务成功");
        }
    }
}
