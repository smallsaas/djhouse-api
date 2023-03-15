package com.jfeat.am.module.house.api.app.common;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.ErrorTip;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: HouseAssetTransactionIntention controller
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 15:21
 * @author: hhhhhtao
 */

@RestController
@RequestMapping("/api/u/house/houseAssetTransactionIntention")
public class UserHouseAssetTransactionIntentionEndpoint {

    @Resource
    HouseAssetTransactionIntentionService transactionIntentionService;

    @Resource
    UserAccountMapper userAccountMapper;

    @PostMapping("/{transactionId}")
    public Tip saveTransactionIntention(@PathVariable Long transactionId) {

        // 从请求用户的token中获取数据
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
        if (userAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"无效用户");
        HouseAssetTransactionIntentionRecord transactionIntention = new HouseAssetTransactionIntentionRecord();
        transactionIntention.setTransactionId(transactionId);
        transactionIntention.setUserId(userAccount.getId());

        // 判断插入是否成功
        Integer affected = transactionIntentionService.saveTransactionIntention(transactionIntention);
        if (affected == 0) throw new BusinessException(BusinessCode.DatabaseInsertError, "database insert error");

        return SuccessTip.create(affected);
    }

    /**
     *
     * @param transactionId
     * @return
     */
    @DeleteMapping("/{transactionId}")
    public Tip cancelIntention(@PathVariable(name = "transactionId") Long transactionId){

        return SuccessTip.create(transactionIntentionService.cancelIntention(transactionId));
    }

    /**
     * 返回指定订单的关注用户
     *
     * @param transactionId 要查询的订单id
     * @return 用户列表
     */
    @GetMapping("/{transactionId}")
    public Tip queryUser(@PathVariable Long transactionId) {
        return SuccessTip.create(transactionIntentionService.listUser(transactionId));
    }
}
