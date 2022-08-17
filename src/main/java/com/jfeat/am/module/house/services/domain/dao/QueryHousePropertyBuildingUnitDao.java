package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingUnitModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-09
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

    int deleteHouseBuildingUnitByBuildingId(@Param("buildingId") Long buildingId);

    HousePropertyBuildingUnit queryExtraHouseBuildingUnitByEntity(@Param("entity") HousePropertyBuildingUnit entity);

    int insertUnits(@Param("unitList") List<HousePropertyBuildingUnit> unitList);

    List<HousePropertyBuildingUnitRecord> queryUnitListByCommunityId(Page<HousePropertyBuildingUnitRecord> page,@RequestParam("communityId")Long communityId);

}