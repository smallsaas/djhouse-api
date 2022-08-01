package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAddress;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAddressMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserAddressService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserAddressService
 * @author Code generator
 * @since 2022-08-01
 */

@Service
public class CRUDHouseUserAddressServiceImpl  extends CRUDServiceOnlyImpl<HouseUserAddress> implements CRUDHouseUserAddressService {





        @Resource
        protected HouseUserAddressMapper houseUserAddressMapper;

        @Override
        protected BaseMapper<HouseUserAddress> getMasterMapper() {
                return houseUserAddressMapper;
        }







}


