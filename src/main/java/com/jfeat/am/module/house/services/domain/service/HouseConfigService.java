package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseConfigGroupDTO;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;

import java.util.List;

/**
 * @description:
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/26 18:06
 * @author: hhhhhtao
 */
public interface HouseConfigService {

    /**
     * 获取系统配置分组列表
     * 列表内类型为：HouseConfigGroupDTO，该DTO中包含有子表`t_house_config`的列表
     *
     * @return
     */
    List<HouseConfigGroupDTO> listHouseConfigGroupDTO();

    /**
     * 根据id更新配置字段值
     *
     * @param id 配置字段id
     * @param fieldValue 配置字段值
     * @return 影响行数
     */
    HouseConfig updateFieldValueById(Integer id, String fieldValue);

    /**
     * 根据字段分组名和字段名获取字段值
     * @param fieldGroupName 字段分组名
     * @param fieldName 字段名
     * @return 字段值
     */
    String getFieldValueByFieldGroupNameAndFieldName(String fieldGroupName,String fieldName);
}
