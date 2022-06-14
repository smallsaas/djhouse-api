package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetMatchLogModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseAssetMatchLogDao extends QueryMasterDao<HouseAssetMatchLog> {
   /*
    * Query entity list by page
    */
    List<HouseAssetMatchLogRecord> findHouseAssetMatchLogPage(Page<HouseAssetMatchLogRecord> page, @Param("record") HouseAssetMatchLogRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetMatchLogModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetMatchLogModel> queryMasterModelList(@Param("masterId") Object masterId);

    int deleteHouseAssetMatchLogByUserIdAndAssetId(@Param("assetId") Long assetId,@Param("userId") Long userId);

    List<HouseAssetMatchLog> queryHouseAssetMatchLogByUserId(@Param("assetId") Long assetId,@Param("userId") Long userId);
}