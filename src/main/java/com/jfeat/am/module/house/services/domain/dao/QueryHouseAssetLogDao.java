package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetLogRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetLog;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetLogModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-11-15
 */
public interface QueryHouseAssetLogDao extends QueryMasterDao<HouseAssetLog> {
   /*
    * Query entity list by page
    */
    List<HouseAssetLogRecord> findHouseAssetLogPage(Page<HouseAssetLogRecord> page, @Param("record") HouseAssetLogRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetLogModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetLogModel> queryMasterModelList(@Param("masterId") Object masterId);
}