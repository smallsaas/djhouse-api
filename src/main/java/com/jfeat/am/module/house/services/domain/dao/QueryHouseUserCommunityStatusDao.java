package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserCommunityStatusModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-07
 */
public interface QueryHouseUserCommunityStatusDao extends QueryMasterDao<HouseUserCommunityStatus> {
   /*
    * Query entity list by page
    */
    List<HouseUserCommunityStatusRecord> findHouseUserCommunityStatusPage(Page<HouseUserCommunityStatusRecord> page, @Param("record") HouseUserCommunityStatusRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserCommunityStatusModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserCommunityStatusModel> queryMasterModelList(@Param("masterId") Object masterId);

    HouseUserCommunityStatus queryUserCommunityStatusByUserId(@Param("userId") Long userId);

    int updateUserCommunityStatusByUserId(@Param("entity") HouseUserCommunityStatus entity);

}