package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseApplicationIntermediaryService;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseApplicationIntermediaryService extends CRUDHouseApplicationIntermediaryService {

    /**
     * 通过中介申请 添加中介身份
     * @param id
     * @return
     */
    int passHouseApplicationIntermediary(Long id);
}