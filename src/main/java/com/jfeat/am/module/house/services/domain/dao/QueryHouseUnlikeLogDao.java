package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUnlikeLogRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUnlikeLogModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-09-19
 */
public interface QueryHouseUnlikeLogDao extends QueryMasterDao<HouseUnlikeLog> {
   /*
    * Query entity list by page
    */
    List<HouseUnlikeLogRecord> findHouseUnlikeLogPage(Page<HouseUnlikeLogRecord> page, @Param("record") HouseUnlikeLogRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUnlikeLogModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUnlikeLogModel> queryMasterModelList(@Param("masterId") Object masterId);

//    批量添加

     int batchAddHouseUnLikeLog(@Param("houseUnlikeLogList") List<HouseUnlikeLog> houseUnlikeLogList);
}