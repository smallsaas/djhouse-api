package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseConfigGroupDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 【匠城小程序】系统配置字段分组mapper
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 16:33
 * @author: hhhhhtao
 */
public interface HouseConfigGroupMapper {

    List<HouseConfigGroupDTO> listHouseConfigGroupDTO();
}
