package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserNoteRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserNoteModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-29
 */
public interface QueryHouseUserNoteDao extends QueryMasterDao<HouseUserNote> {
   /*
    * Query entity list by page
    */
    List<HouseUserNoteRecord> findHouseUserNotePage(Page<HouseUserNoteRecord> page, @Param("record") HouseUserNoteRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserNoteModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserNoteModel> queryMasterModelList(@Param("masterId") Object masterId);
}