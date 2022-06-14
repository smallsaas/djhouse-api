package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserAssetService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserAssetService
 * @author Code generator
 * @since 2022-06-11
 */

@Service
public class CRUDHouseUserAssetServiceImpl  extends CRUDServiceOnlyImpl<HouseUserAsset> implements CRUDHouseUserAssetService {





        @Resource
        protected HouseUserAssetMapper houseUserAssetMapper;

        @Override
        protected BaseMapper<HouseUserAsset> getMasterMapper() {
                return houseUserAssetMapper;
        }







}


