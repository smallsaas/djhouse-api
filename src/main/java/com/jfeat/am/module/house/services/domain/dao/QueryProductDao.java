package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.ProductRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.am.module.house.services.gen.crud.model.ProductModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-27
 */
public interface QueryProductDao extends QueryMasterDao<Product> {
   /*
    * Query entity list by page
    */
    List<ProductRecord> findProductPage(Page<ProductRecord> page, @Param("record") ProductRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    ProductModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<ProductModel> queryMasterModelList(@Param("masterId") Object masterId);
}