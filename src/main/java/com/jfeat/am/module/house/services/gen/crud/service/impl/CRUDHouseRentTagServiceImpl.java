package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentTag;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentTagMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentTagService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseRentTagService
 * @author Code generator
 * @since 2022-06-27
 */

@Service
public class CRUDHouseRentTagServiceImpl  extends CRUDServiceOnlyImpl<HouseRentTag> implements CRUDHouseRentTagService {





        @Resource
        protected HouseRentTagMapper houseRentTagMapper;

        @Override
        protected BaseMapper<HouseRentTag> getMasterMapper() {
                return houseRentTagMapper;
        }







}


