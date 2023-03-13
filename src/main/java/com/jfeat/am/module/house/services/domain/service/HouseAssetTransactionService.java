package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseAssetTransactionService extends CRUDHouseAssetTransactionService {
    void setStatus(List<HouseAssetTransactionRecord> houseAssetTransactionRecordList);

    void setStatus(HouseAssetTransaction houseAssetTransaction);

    /**
     * 获取所有的出售价格
     * @return 价格列表
     */
    List<BigDecimal> listPriceOfTransaction();

    /**
     * 获取我的所有房屋转让记录
     * @param userId
     * @return 我的记录列表
     */
    List<HouseAssetTransactionRecord> listTransaction(Long userId);

    /**
     * 修改transaction.display(是否显示字段)，1=显示，0=不显示
     * @return 修改条目数
     */
    Integer updateDisplay(HouseAssetTransactionRecord transaction);

    /**
     * 删除转让记录
     * @param id 记录id
     * @return
     */
    int removeTransaction(Long id);

    int updateTransaction(HouseAssetTransaction transaction);

    /**
     * 刷新转让记录
     * @param id 要刷新的记录id
     * @return
     */
    int renovateTransaction(Long id);
}