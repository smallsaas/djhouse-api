package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateAddress;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserDecorateAddressMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserDecorateAddressService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserDecorateAddressService
 * @author Code generator
 * @since 2022-05-30
 */

@Service
public class CRUDHouseUserDecorateAddressServiceImpl  extends CRUDServiceOnlyImpl<HouseUserDecorateAddress> implements CRUDHouseUserDecorateAddressService {





        @Resource
        protected HouseUserDecorateAddressMapper houseUserDecorateAddressMapper;

        @Override
        protected BaseMapper<HouseUserDecorateAddress> getMasterMapper() {
                return houseUserDecorateAddressMapper;
        }







}


