package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseApplicationOperationsRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;
import com.jfeat.am.module.house.services.gen.crud.model.HouseApplicationOperationsModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-11
 */
public interface QueryHouseApplicationOperationsDao extends QueryMasterDao<HouseApplicationOperations> {
   /*
    * Query entity list by page
    */
    List<HouseApplicationOperationsRecord> findHouseApplicationOperationsPage(Page<HouseApplicationOperationsRecord> page, @Param("record") HouseApplicationOperationsRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseApplicationOperationsModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseApplicationOperationsModel> queryMasterModelList(@Param("masterId") Object masterId);
}