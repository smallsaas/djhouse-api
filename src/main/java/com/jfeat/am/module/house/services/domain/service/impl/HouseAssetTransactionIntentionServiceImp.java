package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionIntentionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 房屋买卖-用户"有意向"记录表，`t_house_asset_transaction_intention`service
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 15:06
 * @author: hhhhhtao
 */
@Service
public class HouseAssetTransactionIntentionServiceImp implements HouseAssetTransactionIntentionService {

    @Resource
    QueryHouseAssetTransactionIntentionDao queryHouseAssetTransactionIntentionDao;

    @Override
    public Integer saveTransactionIntention(HouseAssetTransactionIntentionRecord transactionIntention) {
        return queryHouseAssetTransactionIntentionDao.saveTransactionIntention(transactionIntention);
    }

    @Override
    public Boolean existsTransactionIntention(HouseAssetTransactionIntentionRecord transactionIntention) {
        if (transactionIntention.getId() == null &&
            transactionIntention.getTransactionId() == null &&
            transactionIntention.getUserId() == null
        ) throw new BusinessException(BusinessCode.InvalidKey,"无意义查询，请检查是否缺失必要参数：id、TransactionId、UserId");
        return queryHouseAssetTransactionIntentionDao.existsTransactionIntention(transactionIntention);
    }
}
