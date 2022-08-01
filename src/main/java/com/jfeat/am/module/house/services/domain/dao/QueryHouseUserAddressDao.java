package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserAddressRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAddress;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAddressModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-01
 */
public interface QueryHouseUserAddressDao extends QueryMasterDao<HouseUserAddress> {
   /*
    * Query entity list by page
    */
    List<HouseUserAddressRecord> findHouseUserAddressPage(Page<HouseUserAddressRecord> page, @Param("record") HouseUserAddressRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserAddressModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserAddressModel> queryMasterModelList(@Param("masterId") Object masterId);
}