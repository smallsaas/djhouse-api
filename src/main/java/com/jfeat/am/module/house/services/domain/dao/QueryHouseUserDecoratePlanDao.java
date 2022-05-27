package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserDecoratePlanRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecoratePlanModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryHouseUserDecoratePlanDao extends QueryMasterDao<HouseUserDecoratePlan> {
   /*
    * Query entity list by page
    */
    List<HouseUserDecoratePlanRecord> findHouseUserDecoratePlanPage(Page<HouseUserDecoratePlanRecord> page, @Param("record") HouseUserDecoratePlanRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserDecoratePlanModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserDecoratePlanModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HouseUserDecoratePlanModel> queryHouseUserDecoratePlanByUserId(@Param("userId") long userId);

    List<HouseUserDecoratePlanModel> queryHouseUserDecoratePlanFuniture(@Param("userId") Long userId,@Param("decoratePlanId") Long decoratePlanId);

    String queryHouseUserDecoratePlanAddress(@Param("userId") Long userId,@Param("decoratePlanId") Long decoratePlanId);
}