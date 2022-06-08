package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyCommunityRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-06
 */
public interface QueryHousePropertyCommunityDao extends QueryMasterDao<HousePropertyCommunity> {
   /*
    * Query entity list by page
    */
    List<HousePropertyCommunityRecord> findHousePropertyCommunityPage(Page<HousePropertyCommunityRecord> page, @Param("record") HousePropertyCommunityRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyCommunityModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyCommunityModel> queryMasterModelList(@Param("masterId") Object masterId);
}