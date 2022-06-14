package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseUserAssetDao extends QueryMasterDao<HouseUserAsset> {
   /*
    * Query entity list by page
    */
    List<HouseUserAssetRecord> findHouseUserAssetPage(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserAssetModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserAssetModel> queryMasterModelList(@Param("masterId") Object masterId);

 List<HouseUserAsset> queryUserRoomByUserId(@Param("userId") Long userId);
}