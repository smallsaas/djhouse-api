package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionIntentionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransactionIntention;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    EndpointUserService endpointUserService;

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

    @Override
    public List<EndpointUserModel> listUser(Long transactionId) {

        // 获取 transactionId下的所有userId
        List<HouseAssetTransactionIntentionRecord>  TransactionIntentionList = queryHouseAssetTransactionIntentionDao.listUser(transactionId);

        // 循环获取每个用户信息
        List<EndpointUserModel> userList = new ArrayList<>();
        for (int i = 0; i < TransactionIntentionList.size(); i++) {
            if (TransactionIntentionList.get(i).getUserId() != null) {
               userList.add(endpointUserService.listUser(TransactionIntentionList.get(i).getUserId()));
            }
        }

        return userList;
    }

    /**
     * 根据transactionId删除记录，会有多条记录
     *
     * @param transactionId
     * @return
     */
    @Override
    public int removeTransactionIntention(Long transactionId) {

        QueryWrapper<HouseAssetTransactionIntention> wrapper = new QueryWrapper<>();
        wrapper.eq("transaction_id",transactionId);
        int affected = queryHouseAssetTransactionIntentionDao.delete(wrapper);

        return affected;
    }
}
