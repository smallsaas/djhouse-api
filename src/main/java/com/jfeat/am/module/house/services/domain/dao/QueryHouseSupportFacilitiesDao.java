package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSupportFacilitiesModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-05
 */
public interface QueryHouseSupportFacilitiesDao extends QueryMasterDao<HouseSupportFacilities> {
    /*
     * Query entity list by page
     */
    List<HouseSupportFacilitiesRecord> findHouseSupportFacilitiesPage(Page<HouseSupportFacilitiesRecord> page, @Param("record") HouseSupportFacilitiesRecord record,
                                                                      @Param("tag") String tag,
                                                                      @Param("search") String search, @Param("orderBy") String orderBy,
                                                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseSupportFacilitiesModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseSupportFacilitiesModel> queryMasterModelList(@Param("masterId") Object masterId);


    List<HouseSupportFacilities> querySupportFacilitiesByTypeId(@Param("typeId") Long typeId);


    List<HouseSupportFacilities> querySupportFacilitiesByRentId(@Param("rentId")Long rentId);
}