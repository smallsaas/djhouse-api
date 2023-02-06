package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransactionIntention;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 房屋买卖-用户"有意向"记录表，`t_house_asset_transaction_intention`mapper
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 11:47
 * @author: hhhhhtao
 */
public interface QueryHouseAssetTransactionIntentionDao extends QueryMasterDao<HouseAssetTransactionIntention> {

    Integer saveTransactionIntention(@Param("transactionIntention") HouseAssetTransactionIntentionRecord transactionIntention);

    Boolean existsTransactionIntention(@Param("transactionIntention") HouseAssetTransactionIntentionRecord transactionIntention);

    /**
     * 根据 transactionId 获取用户列表
     * @param transactionId
     * @return
     */
    List<HouseAssetTransactionIntentionRecord> listUser(@Param("transactionId") Long transactionId);

    Integer countUser(@Param("transactionId") Long transactionId);
}
