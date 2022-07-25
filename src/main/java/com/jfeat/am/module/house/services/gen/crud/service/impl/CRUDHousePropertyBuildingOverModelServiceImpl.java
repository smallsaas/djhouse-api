package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyBuildingOverModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;

import javax.annotation.Resource;

import com.jfeat.crud.plus.impl.CRUDServiceOverModelImpl;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;

/**
 * <p>
 * implementation
 * </p>
 * CRUDHousePropertyBuildingOverModelService
 *
 * @author Code generator
 * @since 2022-06-11
 */

@Service
public class CRUDHousePropertyBuildingOverModelServiceImpl extends CRUDServiceOverModelImpl<HousePropertyBuilding, HousePropertyBuildingModel> implements CRUDHousePropertyBuildingOverModelService {


    @Resource
    protected HousePropertyBuildingMapper housePropertyBuildingMapper;


    @Override
    protected BaseMapper<HousePropertyBuilding> getMasterMapper() {
        return housePropertyBuildingMapper;
    }

    @Override
    protected Class<HousePropertyBuilding> masterClassName() {
        return HousePropertyBuilding.class;
    }

    @Override
    protected Class<HousePropertyBuildingModel> modelClassName() {
        return HousePropertyBuildingModel.class;
    }


    @Resource
    private HouseAssetMapper houseAssetMapper;

    private final static String houseAssetFieldName = "building_id";

    private final static String houseAssetKeyName = "items";


    @Override
    protected String[] slaveFieldNames() {
        return new String[]{
                houseAssetKeyName
        };
    }

    @Override
    protected FIELD onSlaveFieldItem(String field) {


        if (field.compareTo(houseAssetKeyName) == 0) {
            FIELD _field = new FIELD();
            _field.setItemKeyName(field);
            _field.setItemFieldName(houseAssetFieldName);
            _field.setItemClassName(HouseAsset.class);
            _field.setItemMapper(houseAssetMapper);

            return _field;
        }


        throw new BusinessException(BusinessCode.BadRequest);
    }


    @Override
    protected String[] childFieldNames() {
        return new String[]{
        };
    }

    @Override
    protected FIELD onChildFieldItem(String field) {

        throw new BusinessException(BusinessCode.BadRequest);
    }


}


