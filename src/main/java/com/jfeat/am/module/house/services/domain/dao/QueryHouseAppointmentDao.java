package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAppointmentRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAppointmentModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-07-13
 */
public interface QueryHouseAppointmentDao extends QueryMasterDao<HouseAppointment> {
   /*
    * Query entity list by page
    */
    List<HouseAppointmentRecord> findHouseAppointmentPage(Page<HouseAppointmentRecord> page, @Param("record") HouseAppointmentRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseAppointmentModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseAppointmentModel> queryMasterModelList(@Param("masterId") Object masterId);
}