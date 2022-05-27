package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyUnitRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUnit;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyUnitModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-23
 */
public interface QueryHousePropertyUnitDao extends QueryMasterDao<HousePropertyUnit> {
   /*
    * Query entity list by page
    */
    List<HousePropertyUnitRecord> findHousePropertyUnitPage(Page<HousePropertyUnitRecord> page, @Param("record") HousePropertyUnitRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyUnitModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyUnitModel> queryMasterModelList(@Param("masterId") Object masterId);
}