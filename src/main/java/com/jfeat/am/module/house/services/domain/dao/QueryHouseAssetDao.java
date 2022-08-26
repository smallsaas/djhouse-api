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



    /*
    批量插入房子
     */
    int insertAssets(@Param("houseAssetList") List<HouseAsset> houseAssetList);

    /*
    更改楼栋 删除多余的
     */
    int deleteHouseAssetByBuildingIdAndFloors(@Param("buildingId") Long buildingId,@Param("floor") Integer floor);

    /*
    批量删除房子
     */
    int deleteHouseAssets(@Param("houseAssetList") List<Long> houseAssetList);


//    户型管理资产表
    List<HouseAssetRecord> getHouseAssetPage(Page<HouseAssetRecord> page, @Param("record") HouseAssetRecord record,
                                              @Param("tag") String tag,
                                              @Param("search") String search, @Param("orderBy") String orderBy,
                                              @Param("startTime") Date startTime, @Param("endTime") Date endTime);

//    查询多个房产地址

    List<HouseAssetRecord> getHouseAssetList(@Param("ids") List<Long> ids);

}