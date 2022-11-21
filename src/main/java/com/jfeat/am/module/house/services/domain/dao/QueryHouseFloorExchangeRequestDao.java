package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseFloorExchangeRequestRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseFloorExchangeRequest;
import com.jfeat.am.module.house.services.gen.crud.model.HouseFloorExchangeRequestModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-11-19
 */
public interface QueryHouseFloorExchangeRequestDao extends QueryMasterDao<HouseFloorExchangeRequest> {
   /*
    * Query entity list by page
    */
    List<HouseFloorExchangeRequestRecord> findHouseFloorExchangeRequestPage(Page<HouseFloorExchangeRequestRecord> page, @Param("record") HouseFloorExchangeRequestRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseFloorExchangeRequestModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseFloorExchangeRequestModel> queryMasterModelList(@Param("masterId") Object masterId);
}