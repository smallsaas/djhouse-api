package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingUnitModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryHousePropertyBuildingUnitDao extends QueryMasterDao<HousePropertyBuildingUnit> {
    /*
     * Query entity list by page
     */
    List<HousePropertyBuildingUnitRecord> findHousePropertyBuildingUnitPage(Page<HousePropertyBuildingUnitRecord> page, @Param("record") HousePropertyBuildingUnitRecord record,
                                                                            @Param("tag") String tag,
                                                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HousePropertyBuildingUnitModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HousePropertyBuildingUnitModel> queryMasterModelList(@Param("masterId") Object masterId);


    List<HousePropertyBuilding> queryHouseCommunity();



//    <!--    查询户型列表-->
    List<HousePropertyBuildingUnitModel> queryHouseType(@Param("community")String community);
//
//
//<!--    以户型查询楼栋-->
    List<HousePropertyBuilding> queryHouseBuildingByHouseType(@Param("houseType") String houseType,
                                                              @Param("community")String community);
//
//<!--    户型和楼栋联合查询房屋信息-->
    List<HousePropertyBuildingUnit> queryHouseBuildingUnit(@Param("buildingId") Long buildingId,@Param("houseType") String houseType,
                                                           @Param("community")String community);
}