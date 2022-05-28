package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyUserUnitRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserUnit;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyUserUnitModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-28
 */
public interface QueryHousePropertyUserUnitDao extends QueryMasterDao<HousePropertyUserUnit> {
   /*
    * Query entity list by page
    */
    List<HousePropertyUserUnitRecord> findHousePropertyUserUnitPage(Page<HousePropertyUserUnitRecord> page, @Param("record") HousePropertyUserUnitRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyUserUnitModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyUserUnitModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HousePropertyUserUnitRecord> queryUserUnitList(@Param("userId") Long userId);
}