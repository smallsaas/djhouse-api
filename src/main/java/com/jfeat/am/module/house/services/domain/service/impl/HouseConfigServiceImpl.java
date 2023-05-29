package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.dao.HouseConfigGroupMapper;
import com.jfeat.am.module.house.services.domain.dao.HouseConfigMapper;
import com.jfeat.am.module.house.services.domain.model.HouseConfigGroupDTO;
import com.jfeat.am.module.house.services.domain.service.HouseConfigService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 【匠城小程序】系统配置service实现类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 15:32
 * @author: hhhhhtao
 */
@Service("houseConfigService")
public class HouseConfigServiceImpl implements HouseConfigService {

    @Resource
    HouseConfigGroupMapper houseConfigGroupMapper;

    @Resource
    HouseConfigMapper houseConfigMapper;

    /**
     * 获取系统配置分组列表
     * 列表内类型为：HouseConfigGroupDTO，该DTO中包含有子表`t_house_config`的列表
     *
     * @return
     */
    @Override
    public List<HouseConfigGroupDTO> listHouseConfigGroupDTO() {

        return houseConfigGroupMapper.listHouseConfigGroupDTO();
    }

    /**
     * 根据id更新配置字段值
     *
     * @param id 配置字段id
     * @param fieldValue 配置字段值
     * @return 影响行数
     */
    @Override
    @Transactional
    public HouseConfig updateFieldValueById(Integer id,String fieldValue) {

        // 更新
        int affected = 0;
        affected += houseConfigMapper.updateFieldValueById(id,fieldValue);
        if (affected == 0) throw new BusinessException(BusinessCode.DatabaseUpdateError);

        // 更新成功返回更新的对象
        return houseConfigMapper.getById(id);
    }

    /**
     * 根据字段分组名和字段名获取字段值
     *
     * @param fieldGroupName 字段分组名
     * @param fieldName      字段名
     * @return 字段值
     */
    @Override
    public String getFieldValueByFieldGroupNameAndFieldName(String fieldGroupName, String fieldName) {

        return houseConfigMapper.getFieldValueByFieldGroupNameAndFieldName(fieldGroupName,fieldName);
    }
}
