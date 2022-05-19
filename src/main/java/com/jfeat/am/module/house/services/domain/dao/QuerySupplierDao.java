package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.SupplierRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;
import com.jfeat.am.module.house.services.gen.crud.model.SupplierModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-19
 */
public interface QuerySupplierDao extends QueryMasterDao<Supplier> {
   /*
    * Query entity list by page
    */
    List<SupplierRecord> findSupplierPage(Page<SupplierRecord> page, @Param("record") SupplierRecord record,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    SupplierModel queryMasterModel(@Param("id") Long id);
}