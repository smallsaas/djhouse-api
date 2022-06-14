package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetService
 * @author Code generator
 * @since 2022-06-11
 */

@Service
public class CRUDHouseAssetServiceImpl  extends CRUDServiceOnlyImpl<HouseAsset> implements CRUDHouseAssetService {





        @Resource
        protected HouseAssetMapper houseAssetMapper;

        @Override
        protected BaseMapper<HouseAsset> getMasterMapper() {
                return houseAssetMapper;
        }







}


