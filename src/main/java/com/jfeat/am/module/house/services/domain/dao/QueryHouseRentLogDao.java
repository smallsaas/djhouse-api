package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentLogRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentLogModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2023-01-06
 */
public interface QueryHouseRentLogDao extends QueryMasterDao<HouseRentLog> {
   /*
    * Query entity list by page
    */
    List<HouseRentLogRecord> findHouseRentLogPage(Page<HouseRentLogRecord> page, @Param("record") HouseRentLogRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseRentLogModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseRentLogModel> queryMasterModelList(@Param("masterId") Object masterId);



}