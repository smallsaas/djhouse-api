package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyUserRoomRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserRoom;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyUserRoomModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-06
 */
public interface QueryHousePropertyUserRoomDao extends QueryMasterDao<HousePropertyUserRoom> {
   /*
    * Query entity list by page
    */
    List<HousePropertyUserRoomRecord> findHousePropertyUserRoomPage(Page<HousePropertyUserRoomRecord> page, @Param("record") HousePropertyUserRoomRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyUserRoomModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyUserRoomModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HousePropertyUserRoom> queryUserRoomByUserId(@Param("userId") Long userId);

}