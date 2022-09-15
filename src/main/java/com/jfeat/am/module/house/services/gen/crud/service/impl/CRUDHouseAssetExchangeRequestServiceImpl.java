package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetExchangeRequestMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetExchangeRequestService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetExchangeRequestService
 * @author Code generator
 * @since 2022-09-13
 */

@Service
public class CRUDHouseAssetExchangeRequestServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetExchangeRequest> implements CRUDHouseAssetExchangeRequestService {





        @Resource
        protected HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

        @Override
        protected BaseMapper<HouseAssetExchangeRequest> getMasterMapper() {
                return houseAssetExchangeRequestMapper;
        }







}


