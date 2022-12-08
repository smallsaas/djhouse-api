package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserAssetHistoryRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAssetHistory;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetHistoryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-12-08
 */
public interface QueryHouseUserAssetHistoryDao extends QueryMasterDao<HouseUserAssetHistory> {
   /*
    * Query entity list by page
    */
    List<HouseUserAssetHistoryRecord> findHouseUserAssetHistoryPage(Page<HouseUserAssetHistoryRecord> page, @Param("record") HouseUserAssetHistoryRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserAssetHistoryModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserAssetHistoryModel> queryMasterModelList(@Param("masterId") Object masterId);
}