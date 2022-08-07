package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSurroundFacilitiesModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-05
 */
public interface QueryHouseSurroundFacilitiesDao extends QueryMasterDao<HouseSurroundFacilities> {
   /*
    * Query entity list by page
    */
    List<HouseSurroundFacilitiesRecord> findHouseSurroundFacilitiesPage(Page<HouseSurroundFacilitiesRecord> page, @Param("record") HouseSurroundFacilitiesRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseSurroundFacilitiesModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseSurroundFacilitiesModel> queryMasterModelList(@Param("masterId") Object masterId);
}