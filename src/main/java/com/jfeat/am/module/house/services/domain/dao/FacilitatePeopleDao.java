package com.jfeat.am.module.house.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;
import org.apache.ibatis.annotations.Param;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/23 18:21
 * @author: hhhhhtao
 */
public interface FacilitatePeopleDao extends BaseMapper<FacilitatePeople> {

    FacilitatePeople getFacilitatePeople(Integer id);

    Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeopleRecord> page, @Param("search") String search);
}
