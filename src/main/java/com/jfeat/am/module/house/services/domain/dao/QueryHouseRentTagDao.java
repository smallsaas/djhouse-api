package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentTagRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentTag;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentTagModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-27
 */
public interface QueryHouseRentTagDao extends QueryMasterDao<HouseRentTag> {
   /*
    * Query entity list by page
    */
    List<HouseRentTagRecord> findHouseRentTagPage(Page<HouseRentTagRecord> page, @Param("record") HouseRentTagRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseRentTagModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseRentTagModel> queryMasterModelList(@Param("masterId") Object masterId);
}