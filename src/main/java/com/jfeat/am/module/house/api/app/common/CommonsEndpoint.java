package com.jfeat.am.module.house.api.app.common;

import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 通用controller，这里会存放通用的api
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/3/15 19:51
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/cron")
public class CommonsEndpoint {

    @Resource
    HouseAssetTransactionService transactionService;

    /**
     * 房屋买卖 - 下架距离最新更新时间已经过了一个月的记录
     *
     * @return 下架的记录数
     */
    @PutMapping("/close-transaction")
    public Tip pulledOffShelvesTransaction() {

        return SuccessTip.create(transactionService.pulledOffShelvesTransaction());
    }
}
