package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseUserDecorateAddressRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateAddress;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecorateAddressModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-05-30
 */
public interface QueryHouseUserDecorateAddressDao extends QueryMasterDao<HouseUserDecorateAddress> {
    /*
     * Query entity list by page
     */
    List<HouseUserDecorateAddressRecord> findHouseUserDecorateAddressPage(Page<HouseUserDecorateAddressRecord> page, @Param("record") HouseUserDecorateAddressRecord record,
                                                                          @Param("tag") String tag,
                                                                          @Param("search") String search, @Param("orderBy") String orderBy,
                                                                          @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserDecorateAddressModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserDecorateAddressModel> queryMasterModelList(@Param("masterId") Object masterId);

    List<HouseUserDecorateAddress> queryUserDecoratePlanAddress(@Param("userId") Long userId,@Param("decoratePlan") Long decoratePlan);

    int updateUserDecorateAddress(@Param("userId") Long userId,@Param("decoratePlanId") Long decoratePlanId,@Param("userDecorateAddress") HouseUserDecorateAddress userDecorateAddress);
}