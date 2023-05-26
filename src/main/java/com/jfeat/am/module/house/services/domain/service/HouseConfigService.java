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

    List<HouseConfigGroupDTO> listHouseConfigGroupDTO();

    HouseConfig updateFieldValueById(Integer id, String fieldValue);
}
