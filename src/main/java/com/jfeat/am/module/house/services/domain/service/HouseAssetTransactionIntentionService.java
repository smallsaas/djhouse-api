package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;

import java.util.List;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 15:03
 * @author: hhhhhtao
 */
public interface HouseAssetTransactionIntentionService {

    Integer saveTransactionIntention(HouseAssetTransactionIntentionRecord transactionIntention);

    Boolean existsTransactionIntention(HouseAssetTransactionIntentionRecord transactionIntention);

    /**
     * 根据transactionId查询同transactionId下的所有用户
     * @param transactionId
     * @return
     */
    List<HouseAssetTransactionIntentionRecord> listUser(Long transactionId);
}
