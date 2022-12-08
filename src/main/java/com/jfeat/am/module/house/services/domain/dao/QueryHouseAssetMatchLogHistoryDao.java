package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogHistoryRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLogHistory;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetMatchLogHistoryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-12-08
 */
public interface QueryHouseAssetMatchLogHistoryDao extends QueryMasterDao<HouseAssetMatchLogHistory> {
   /*
    * Query entity list by page
    */
    List<HouseAssetMatchLogHistoryRecord> findHouseAssetMatchLogHistoryPage(Page<HouseAssetMatchLogHistoryRecord> page, @Param("record") HouseAssetMatchLogHistoryRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetMatchLogHistoryModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetMatchLogHistoryModel> queryMasterModelList(@Param("masterId") Object masterId);

 int batchAddHouseAssetMatchLogHistory(@Param("matchLogList") List<HouseAssetMatchLogHistory> matchLogList);
}