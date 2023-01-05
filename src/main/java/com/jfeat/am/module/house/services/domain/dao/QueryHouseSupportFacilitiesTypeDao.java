package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSupportFacilitiesTypeModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-05
 */
public interface QueryHouseSupportFacilitiesTypeDao extends QueryMasterDao<HouseSupportFacilitiesType> {
   /*
    * Query entity list by page
    */
    List<HouseSupportFacilitiesTypeRecord> findHouseSupportFacilitiesTypePage(Page<HouseSupportFacilitiesTypeRecord> page, @Param("record") HouseSupportFacilitiesTypeRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseSupportFacilitiesTypeModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseSupportFacilitiesTypeModel> queryMasterModelList(@Param("masterId") Object masterId);

 List<HouseSupportFacilitiesTypeRecord> findHouseSupportFacilitiesTypeItem(Page<HouseSupportFacilitiesTypeRecord> page, @Param("record") HouseSupportFacilitiesTypeRecord record,
                                                                           @Param("tag") String tag,
                                                                           @Param("search") String search, @Param("orderBy") String orderBy,
                                                                           @Param("startTime") Date startTime, @Param("endTime") Date endTime);


}