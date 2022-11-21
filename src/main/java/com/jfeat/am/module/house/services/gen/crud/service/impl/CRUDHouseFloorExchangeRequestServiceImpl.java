package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseFloorExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseFloorExchangeRequestMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseFloorExchangeRequestService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseFloorExchangeRequestService
 * @author Code generator
 * @since 2022-11-19
 */

@Service
public class CRUDHouseFloorExchangeRequestServiceImpl  extends CRUDServiceOnlyImpl<HouseFloorExchangeRequest> implements CRUDHouseFloorExchangeRequestService {





        @Resource
        protected HouseFloorExchangeRequestMapper houseFloorExchangeRequestMapper;

        @Override
        protected BaseMapper<HouseFloorExchangeRequest> getMasterMapper() {
                return houseFloorExchangeRequestMapper;
        }







}


