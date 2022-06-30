package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetDemandSupply;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetDemandSupplyMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetDemandSupplyService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetDemandSupplyService
 * @author Code generator
 * @since 2022-06-29
 */

@Service
public class CRUDHouseAssetDemandSupplyServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetDemandSupply> implements CRUDHouseAssetDemandSupplyService {





        @Resource
        protected HouseAssetDemandSupplyMapper houseAssetDemandSupplyMapper;

        @Override
        protected BaseMapper<HouseAssetDemandSupply> getMasterMapper() {
                return houseAssetDemandSupplyMapper;
        }







}


