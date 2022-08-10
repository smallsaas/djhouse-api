package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentSupportFacilities;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentSupportFacilitiesModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-08
 */
public interface QueryHouseRentSupportFacilitiesDao extends QueryMasterDao<HouseRentSupportFacilities> {
   /*
    * Query entity list by page
    */
    List<HouseRentSupportFacilitiesRecord> findHouseRentSupportFacilitiesPage(Page<HouseRentSupportFacilitiesRecord> page, @Param("record") HouseRentSupportFacilitiesRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseRentSupportFacilitiesModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseRentSupportFacilitiesModel> queryMasterModelList(@Param("masterId") Object masterId);

//    批量添加出租房屋设施家居
     int batchInsertHouseRentSupportFacilities(@Param("rentSupportFacilities") List<HouseRentSupportFacilitiesRecord> rentSupportFacilities);
}