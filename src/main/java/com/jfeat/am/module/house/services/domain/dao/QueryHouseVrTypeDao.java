package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseVrTypeRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrType;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrTypeModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-16
 */
public interface QueryHouseVrTypeDao extends QueryMasterDao<HouseVrType> {
   /*
    * Query entity list by page
    */
    List<HouseVrTypeRecord> findHouseVrTypePage(Page<HouseVrTypeRecord> page, @Param("record") HouseVrTypeRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseVrTypeModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseVrTypeModel> queryMasterModelList(@Param("masterId") Object masterId);
}