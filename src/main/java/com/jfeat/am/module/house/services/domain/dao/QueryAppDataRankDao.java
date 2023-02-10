package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.crud.plus.QueryMasterDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


import java.util.List;
public interface QueryAppDataRankDao extends QueryMasterDao<HouseRentAssetRecord> {

    /*
    * 获取置业顾问排名列表
    * */
    List<HouseRentAssetRecord> findHouseRentAssetPage(
            Page<HouseRentAssetRecord> page,
            @Param("orgId") Long orgId,
            @Param("search") String search
            );

}
