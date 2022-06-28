package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryHouseDecoratePlanDao extends QueryMasterDao<HouseDecoratePlan> {
   /*
    * Query entity list by page
    */
    List<HouseDecoratePlanRecord> findHouseDecoratePlanPage(Page<HouseDecoratePlanRecord> page, @Param("record") HouseDecoratePlanRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseDecoratePlanModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseDecoratePlanModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<Product> queryProductListByDesignModel(@Param("designModelId") Long designModelId);

    Integer queryDecoratePlanStar(@Param("decoratePlanId") Long decoratePlanId);

    Double queryDecoratePlanTotalPrice(@Param("decoratePlanId") Long decoratePlanId);

}