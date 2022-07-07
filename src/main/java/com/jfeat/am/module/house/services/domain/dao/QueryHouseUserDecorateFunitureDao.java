package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateFunitureRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateFuniture;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecorateFunitureModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-07-01
 */
public interface QueryHouseUserDecorateFunitureDao extends QueryMasterDao<HouseUserDecorateFuniture> {
   /*
    * Query entity list by page
    */
    List<HouseUserDecorateFunitureRecord> findHouseUserDecorateFuniturePage(Page<HouseUserDecorateFunitureRecord> page, @Param("record") HouseUserDecorateFunitureRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserDecorateFunitureModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserDecorateFunitureModel> queryMasterModelList(@Param("masterId") Object masterId);
}