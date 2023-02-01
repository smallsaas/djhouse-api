package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 15:03
 * @author: hhhhhtao
 */
public interface HouseAssetTransactionIntentionService {

    Integer saveTransactionIntention(HouseAssetTransactionIntentionRecord transactionIntention);
}
