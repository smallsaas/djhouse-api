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

    /**
     * 指定便民服务的拨打次数加 1
     * @param id 被指定的便民服务id
     * @return
     */
    int facilitatePeoPleDialFrequencyAddOne(@Param("id") Integer id);

    Integer getFrequencyById(@Param("id") Integer id);

    /**
     * 统计指定id的记录条目数，用作判断指定id的记录是否存在
     * @param id
     * @return
     */
    Integer countFacilitatePeopleById(@Param("id") Integer id);
}
