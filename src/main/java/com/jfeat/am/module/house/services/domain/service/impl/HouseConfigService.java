package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.dao.HouseConfigGroupMapper;
import com.jfeat.am.module.house.services.domain.dao.HouseConfigMapper;
import com.jfeat.am.module.house.services.domain.model.HouseConfigGroupDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 【匠城小程序】系统配置service，（衡量认为不需要接口化，直接调用两个表的dao）
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 15:32
 * @author: hhhhhtao
 */
@Service("houseConfigService")
public class HouseConfigService {

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
    public List<HouseConfigGroupDTO> listHouseConfigGroupDTO() {

        return houseConfigGroupMapper.listHouseConfigGroupDTO();
    }
}
