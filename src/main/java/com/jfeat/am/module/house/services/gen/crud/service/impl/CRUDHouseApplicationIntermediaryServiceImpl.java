package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseApplicationIntermediaryMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseApplicationIntermediaryService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;

import javax.annotation.Resource;

import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 * implementation
 * </p>
 * CRUDHouseApplicationIntermediaryService
 *
 * @author Code generator
 * @since 2022-08-09
 */

@Service
public class CRUDHouseApplicationIntermediaryServiceImpl extends CRUDServiceOnlyImpl<HouseApplicationIntermediary> implements CRUDHouseApplicationIntermediaryService {


    @Resource
    protected HouseApplicationIntermediaryMapper houseApplicationIntermediaryMapper;

    @Override
    protected BaseMapper<HouseApplicationIntermediary> getMasterMapper() {
        return houseApplicationIntermediaryMapper;
    }


}


