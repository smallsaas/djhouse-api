package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetTransactionModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2023-01-05
 */
public interface QueryHouseAssetTransactionDao extends QueryMasterDao<HouseAssetTransaction> {
    /*
     * Query entity list by page
     */
    List<HouseAssetTransactionRecord> findHouseAssetTransactionPage(Page<HouseAssetTransactionRecord> page, @Param("record") HouseAssetTransactionRecord record,
                                                                    @Param("tag") String tag,
                                                                    @Param("search") String search, @Param("orderBy") String orderBy,
                                                                    @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetTransactionModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetTransactionModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HouseAssetTransactionRecord> findHouseAssetTransactionPageDetail(Page<HouseAssetTransactionRecord> page, @Param("record") HouseAssetTransactionRecord record,
                                                                          @Param("tag") String tag,
                                                                          @Param("search") String search, @Param("orderBy") String orderBy,
                                                                          @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * 查询所有价格（price） > 0.00的记录
     */
    List<BigDecimal> listPriceOfTransaction();

    List<HouseAssetTransactionRecord> listTransaction(@Param("userId") Long userId);

    Integer updateDisplay(@Param("transaction") HouseAssetTransactionRecord transaction);
}