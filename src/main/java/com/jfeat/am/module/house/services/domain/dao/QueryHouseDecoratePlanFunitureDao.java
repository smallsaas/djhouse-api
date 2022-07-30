package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanFunitureRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlanFuniture;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanFunitureModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryHouseDecoratePlanFunitureDao extends QueryMasterDao<HouseDecoratePlanFuniture> {
   /*
    * Query entity list by page
    */
    List<HouseDecoratePlanFunitureRecord> findHouseDecoratePlanFuniturePage(Page<HouseDecoratePlanFunitureRecord> page, @Param("record") HouseDecoratePlanFunitureRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseDecoratePlanFunitureModel queryMasterModel(@Param("id") Long id);

    /*
     * Query entity model list for slave items
     */
    List<HouseDecoratePlanFunitureModel> queryMasterModelList(@Param("masterId") Object masterId);

    HouseDecoratePlanFunitureModel queryHouseDecoratePlanFunitureExists(@Param("decoratePlanId") Long decoratePlanId,
                                                                        @Param("furnitureId") Long furnitureId);
    int updateDecoratePlanFuniture(@Param("id") Long id,@Param("entity") HouseDecoratePlanFuniture entity);

    int createDecoratePlanFuniture(@Param("entity") HouseDecoratePlanFuniture entity);


    int insertDecoratePlanFunitures(@Param("furnitureList") List<HouseDecoratePlanFuniture> funitureList);
}