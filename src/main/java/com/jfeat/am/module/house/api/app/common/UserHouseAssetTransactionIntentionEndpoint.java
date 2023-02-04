package com.jfeat.am.module.house.api.app.common;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.ErrorTip;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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

    @PostMapping("/{transactionId}")
    public Tip saveTransactionIntention(@PathVariable Long transactionId, @RequestBody HouseAssetTransactionIntentionRecord transactionIntention) {

        // 必须有transactionId和userId
        if (transactionIntention.getUserId() == null) throw new BusinessException(BusinessCode.BadRequest, "userId don't null");
        transactionIntention.setTransactionId(transactionId);

        // 判断插入是否成功
        Integer affected = transactionIntentionService.saveTransactionIntention(transactionIntention);
        if (affected == 0) throw new BusinessException(BusinessCode.DatabaseInsertError, "database insert error");

        return SuccessTip.create(affected);
    }

    @GetMapping("/{transactionId}")
    public Tip queryUser(@PathVariable Long transactionId) {
        return SuccessTip.create(transactionIntentionService.listUser(transactionId));
    }
}
