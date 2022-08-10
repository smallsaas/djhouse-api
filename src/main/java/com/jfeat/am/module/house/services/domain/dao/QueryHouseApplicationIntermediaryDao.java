package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseApplicationIntermediaryRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.am.module.house.services.gen.crud.model.HouseApplicationIntermediaryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-09
 */
public interface QueryHouseApplicationIntermediaryDao extends QueryMasterDao<HouseApplicationIntermediary> {
   /*
    * Query entity list by page
    */
    List<HouseApplicationIntermediaryRecord> findHouseApplicationIntermediaryPage(Page<HouseApplicationIntermediaryRecord> page, @Param("record") HouseApplicationIntermediaryRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseApplicationIntermediaryModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseApplicationIntermediaryModel> queryMasterModelList(@Param("masterId") Object masterId);
}