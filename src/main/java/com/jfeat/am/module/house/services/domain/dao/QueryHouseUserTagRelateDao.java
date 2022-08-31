package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserTagRelateRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTagRelate;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserTagRelateModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-29
 */
public interface QueryHouseUserTagRelateDao extends QueryMasterDao<HouseUserTagRelate> {
   /*
    * Query entity list by page
    */
    List<HouseUserTagRelateRecord> findHouseUserTagRelatePage(Page<HouseUserTagRelateRecord> page, @Param("record") HouseUserTagRelateRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserTagRelateModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserTagRelateModel> queryMasterModelList(@Param("masterId") Object masterId);
}