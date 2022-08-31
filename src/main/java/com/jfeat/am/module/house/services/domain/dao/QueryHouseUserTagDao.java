package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserTagRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTag;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserTagModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-29
 */
public interface QueryHouseUserTagDao extends QueryMasterDao<HouseUserTag> {
   /*
    * Query entity list by page
    */
    List<HouseUserTagRecord> findHouseUserTagPage(Page<HouseUserTagRecord> page, @Param("record") HouseUserTagRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserTagModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserTagModel> queryMasterModelList(@Param("masterId") Object masterId);
}