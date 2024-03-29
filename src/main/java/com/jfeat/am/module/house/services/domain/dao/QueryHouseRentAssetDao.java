package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-07-16
 */
public interface QueryHouseRentAssetDao extends QueryMasterDao<HouseRentAsset> {
    /*
     * Query entity list by page
     */
    List<HouseRentAssetRecord> findHouseRentAssetPage(Page<HouseRentAssetRecord> page, @Param("record") HouseRentAssetRecord record,
                                                      @Param("tag") String tag,
                                                      @Param("search") String search, @Param("orderBy") String orderBy,
                                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity list by page
     */
    List<HouseRentAssetRecord> findHouseRentAssetPageToWeb(Page<HouseRentAssetRecord> page, @Param("record") HouseRentAssetRecord record,
                                                      @Param("tag") String tag,
                                                      @Param("search") String search, @Param("orderBy") String orderBy,
                                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                           @Param("landlordSearch") String landlordSearch,
                                                           @Param("serverSearch") String serverSearch);

    /*
     * Query entity model for details
     */
    HouseRentAssetModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseRentAssetModel> queryMasterModelList(@Param("masterId") Object masterId);


    List<HouseRentAssetRecord> findHouseRentAssetPageDetails(Page<HouseRentAssetRecord> page, @Param("record") HouseRentAssetRecord record,
                                                             @Param("tag") String tag,
                                                             @Param("search") String search, @Param("orderBy") String orderBy,
                                                             @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<HouseRentAssetRecord> findHouseRentAssetList(List<Long> assetIds);
}