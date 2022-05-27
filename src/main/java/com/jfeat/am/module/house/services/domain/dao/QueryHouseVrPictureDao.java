package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrPictureModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryHouseVrPictureDao extends QueryMasterDao<HouseVrPicture> {
   /*
    * Query entity list by page
    */
    List<HouseVrPictureRecord> findHouseVrPicturePage(Page<HouseVrPictureRecord> page, @Param("record") HouseVrPictureRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseVrPictureModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseVrPictureModel> queryMasterModelList(@Param("masterId") Object masterId);
}