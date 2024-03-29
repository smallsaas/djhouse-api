package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseTenantMenuRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.am.module.house.services.gen.crud.model.HouseTenantMenuModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-12
 */
public interface QueryHouseTenantMenuDao extends QueryMasterDao<HouseTenantMenu> {
   /*
    * Query entity list by page
    */
    List<HouseTenantMenuRecord> findHouseTenantMenuPage(Page<HouseTenantMenuRecord> page, @Param("record") HouseTenantMenuRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseTenantMenuModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseTenantMenuModel> queryMasterModelList(@Param("masterId") Object masterId);

    int updateTenantMenus(@Param("houseTenantMenuList") List<HouseTenantMenu> houseTenantMenuList);
}