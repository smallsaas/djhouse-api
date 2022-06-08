package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyRoomRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyRoom;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyRoomModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-06
 */
public interface QueryHousePropertyRoomDao extends QueryMasterDao<HousePropertyRoom> {
   /*
    * Query entity list by page
    */
    List<HousePropertyRoomRecord> findHousePropertyRoomPage(Page<HousePropertyRoomRecord> page, @Param("record") HousePropertyRoomRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyRoomModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyRoomModel> queryMasterModelList(@Param("masterId") Object masterId);

 int deleteHouseRoomByBuildingId(@Param("buildingId")Long buildingId);
}