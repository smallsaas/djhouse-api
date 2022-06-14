package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseEquityDemandSupplyMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseEquityDemandSupplyService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseEquityDemandSupplyService
 * @author Code generator
 * @since 2022-06-11
 */

@Service
public class CRUDHouseEquityDemandSupplyServiceImpl  extends CRUDServiceOnlyImpl<HouseEquityDemandSupply> implements CRUDHouseEquityDemandSupplyService {





        @Resource
        protected HouseEquityDemandSupplyMapper houseEquityDemandSupplyMapper;

        @Override
        protected BaseMapper<HouseEquityDemandSupply> getMasterMapper() {
                return houseEquityDemandSupplyMapper;
        }







}


