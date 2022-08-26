package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseBrowseLogRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;
import com.jfeat.am.module.house.services.gen.crud.model.HouseBrowseLogModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-25
 */
public interface QueryHouseBrowseLogDao extends QueryMasterDao<HouseBrowseLog> {
   /*
    * Query entity list by page
    */
    List<HouseBrowseLogRecord> findHouseBrowseLogPage(Page<HouseBrowseLogRecord> page, @Param("record") HouseBrowseLogRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseBrowseLogModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseBrowseLogModel> queryMasterModelList(@Param("masterId") Object masterId);
}