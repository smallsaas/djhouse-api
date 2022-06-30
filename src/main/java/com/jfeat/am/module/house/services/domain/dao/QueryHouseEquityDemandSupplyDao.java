package com.jfeat.am.module.house.services.domain.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.model.HouseEquityDemandSupplyRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import com.jfeat.am.module.house.services.gen.crud.model.HouseEquityDemandSupplyModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseEquityDemandSupplyDao extends QueryMasterDao<HouseEquityDemandSupply> {
    /*
     * Query entity list by page
     */
    List<HouseEquityDemandSupplyRecord> findHouseEquityDemandSupplyPage(Page<HouseEquityDemandSupplyRecord> page, @Param("record") HouseEquityDemandSupplyRecord record,
                                                                        @Param("tag") String tag,
                                                                        @Param("search") String search, @Param("orderBy") String orderBy,
                                                                        @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                                        @Param("leftRange") Double leftRange,
                                                                        @Param("rightRange") Double rightRange
    );

    /*
     * Query entity model for details
     */
    HouseEquityDemandSupplyModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseEquityDemandSupplyModel> queryMasterModelList(@Param("masterId") Object masterId);


}