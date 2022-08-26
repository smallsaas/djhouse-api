package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSubscribeRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSubscribeModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-24
 */
public interface QueryHouseSubscribeDao extends QueryMasterDao<HouseSubscribe> {
   /*
    * Query entity list by page
    */
    List<HouseSubscribeRecord> findHouseSubscribePage(Page<HouseSubscribeRecord> page, @Param("record") HouseSubscribeRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseSubscribeModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseSubscribeModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HouseRentAssetRecord> userSubscribe(Page<HouseRentAssetRecord> page, @Param("record") HouseSubscribeRecord record);
}