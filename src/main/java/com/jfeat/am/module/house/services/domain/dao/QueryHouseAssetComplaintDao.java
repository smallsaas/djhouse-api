package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetComplaintModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-07-22
 */
public interface QueryHouseAssetComplaintDao extends QueryMasterDao<HouseAssetComplaint> {
    /*
     * Query entity list by page
     */
    List<HouseAssetComplaintRecord> findHouseAssetComplaintPage(Page<HouseAssetComplaintRecord> page, @Param("record") HouseAssetComplaintRecord record,
                                                                @Param("tag") String tag,
                                                                @Param("search") String search, @Param("orderBy") String orderBy,
                                                                @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAssetComplaintModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAssetComplaintModel> queryMasterModelList(@Param("masterId") Object masterId);

    /*
    申诉列表 添加houseAssetModel类型 和添加 用户信息
     */
    List<HouseAssetComplaintRecord> findHouseAssetComplaintPageDetails(Page<HouseAssetComplaintRecord> page, @Param("record") HouseAssetComplaintRecord record,
                                                                       @Param("tag") String tag,
                                                                       @Param("search") String search, @Param("orderBy") String orderBy,
                                                                       @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}