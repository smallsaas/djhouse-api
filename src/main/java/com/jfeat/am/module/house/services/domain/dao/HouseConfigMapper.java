package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 【匠城小程序】系统配置字段项mapper
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 16:32
 * @author: hhhhhtao
 */
public interface HouseConfigMapper {

    List<HouseConfig> listHouseConfig(@Param("fieldGroupName") String fieldGroupName);
}
