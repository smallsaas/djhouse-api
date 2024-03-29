package com.jfeat.am.module.house.services.task;

import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 定时任务类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/3/17 10:39
 * @author: hhhhhtao
 */
@Component
public class TimedTask {

    private final static Logger logger = LoggerFactory.getLogger(TimedTask.class);

    @Resource
    HouseAssetTransactionService transactionService; // 房屋转让服务类

    /**
     * 房屋转让功能模块 - 每天凌晨一点执行，查询全表判断是否已达到下架要求
     * 下架要求：updateTime已经超过"下架时间"配置的记录，如果updateTime为null,则判断createTime
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void carryOutPulledOffShelvesTransaction() {
        int affected = transactionService.pulledOffShelvesTransaction();
        logger.info("执行房屋转让记录的超时下架任务，共下架：" + affected );
    }

}
