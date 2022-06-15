package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHousePropertyBuildingDao extends QueryMasterDao<HousePropertyBuilding> {
   /*
    * Query entity list by page
    */
    List<HousePropertyBuildingRecord> findHousePropertyBuildingPage(Page<HousePropertyBuildingRecord> page, @Param("record") HousePropertyBuildingRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyBuildingModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyBuildingModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HousePropertyBuilding> queryHousePropertyBuildingByUserId(@Param("userId") Long userId);
}