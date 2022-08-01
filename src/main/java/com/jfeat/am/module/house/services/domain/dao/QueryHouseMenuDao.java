package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.crud.model.HouseMenuModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-01
 */
public interface QueryHouseMenuDao extends QueryMasterDao<HouseMenu> {
   /*
    * Query entity list by page
    */
    List<HouseMenuRecord> findHouseMenuPage(Page<HouseMenuRecord> page, @Param("record") HouseMenuRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseMenuModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseMenuModel> queryMasterModelList(@Param("masterId") Object masterId);

//    批量修改
    int updateHouseMenus(@Param("houseMenuList") List<HouseMenu> houseMenuList);
}