package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetExchangeRequestModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseAssetExchangeRequestDao extends QueryMasterDao<HouseAssetExchangeRequest> {
    /*
     * Query entity list by page
     */
    List<HouseAssetExchangeRequestRecord> findHouseAssetExchangeRequestPage(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record,
                                                                            @Param("tag") String tag,
                                                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    /**
     * @param page
     * @param record
     * @param communityId 需要过滤出来的小区id
     * @return
     */
    List<HouseAssetExchangeRequestRecord> findHouseAssetExchangeRequestPageFilterCommunity(Page<HouseAssetExchangeRequestRecord> page, @Param("record") HouseAssetExchangeRequestRecord record, @Param("communityId") Long communityId);

    /*
     * Query entity model for details
     */
    HouseAssetExchangeRequestModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetExchangeRequestModel> queryMasterModelList(@Param("masterId") Object masterId);

    HouseAssetExchangeRequest queryHouseAssetExchangeRequestByAssetId(@Param("assetId") Long assetId);

    Long queryHouseAssetHouseType(@Param("assetId") Long assetId);

    List<HouseAssetExchangeRequest> queryHouseAssetExchangeRequestByUserId(@Param("userId") Long userId);

    HouseAssetExchangeRequest queryHouseAssetExchangeRequestByAssetIdAndUserId(@Param("assetId") Long assetId, @Param("userId") Long userId);

    List<HouseAssetExchangeRequest> queryAllHouseAssetExchangeRequest();

}