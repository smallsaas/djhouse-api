package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDesignModelModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-08
 */
public interface QueryHouseDesignModelDao extends QueryMasterDao<HouseDesignModel> {
   /*
    * Query entity list by page
    */
    List<HouseDesignModelRecord> findHouseDesignModelPage(Page<HouseDesignModelRecord> page, @Param("record") HouseDesignModelRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseDesignModelModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseDesignModelModel> queryMasterModelList(@Param("masterId") Object masterId);
}