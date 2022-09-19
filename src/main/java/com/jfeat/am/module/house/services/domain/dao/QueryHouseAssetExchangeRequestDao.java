package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetExchangeRequestModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-09-13
 */
public interface QueryHouseAssetExchangeRequestDao extends QueryMasterDao<HouseAssetExchangeRequest> {
   /*
    * Query entity list by page
    */
    List<HouseAssetExchangeRequestRecord> findHouseAssetExchangeRequestPage(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetExchangeRequestModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetExchangeRequestModel> queryMasterModelList(@Param("masterId") Object masterId);

//    查询匹配目标
   List<HouseAssetExchangeRequestRecord> queryMatchTargetAssetList(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record,
                                                                   @Param("search") String search);

    int batchAddExchangeRequest(@Param("exchangeRequestList") List<HouseAssetExchangeRequest> exchangeRequestList);

    int batchDeleteExchangeRequest(@Param("record") HouseAssetExchangeRequest exchangeRequest);

    List<HouseAsset> queryExchangeTargetList(Page<HouseAssetExchangeRequestRecord> page,@Param("record") HouseAssetExchangeRequest record);

    List<HouseAssetExchangeRequestRecord> queryExchangeRequestGroupByAssetId(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record);

    List<HouseAssetExchangeRequestRecord> queryOptionExchangeRequestList(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record,
                                                                         @Param("search") String search);
}