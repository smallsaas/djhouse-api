package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseAssetDao extends QueryMasterDao<HouseAsset> {
    /*
     * Query entity list by page
     */
    List<HouseAssetRecord> findHouseAssetPage(Page<HouseAssetRecord> page, @Param("record") HouseAssetRecord record,
                                              @Param("tag") String tag,
                                              @Param("search") String search, @Param("orderBy") String orderBy,
                                              @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetModel> queryMasterModelList(@Param("masterId") Object masterId);

    int deleteHouseRoomByBuildingId(@Param("buildingId") Long buildingId);

    List<HouseAsset> queryHouseAssetByBuildingId(@Param("buildingId") Long buildingId);

    HouseAssetRecord queryHouseAssetDetails(@Param("assetId") Long assetId);

    List<HouseAssetRecord> queryUserAssetRent(@Param("status") Integer status,@Param("username")String username,@Param("search") String search);

    HouseAssetRecord queryHouseAssetDetails1(@Param("assetId") Long assetId);
}