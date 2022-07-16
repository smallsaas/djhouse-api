package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentAssetService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseRentAssetService
 * @author Code generator
 * @since 2022-07-16
 */

@Service
public class CRUDHouseRentAssetServiceImpl  extends CRUDServiceOnlyImpl<HouseRentAsset> implements CRUDHouseRentAssetService {





        @Resource
        protected HouseRentAssetMapper houseRentAssetMapper;

        @Override
        protected BaseMapper<HouseRentAsset> getMasterMapper() {
                return houseRentAssetMapper;
        }







}


