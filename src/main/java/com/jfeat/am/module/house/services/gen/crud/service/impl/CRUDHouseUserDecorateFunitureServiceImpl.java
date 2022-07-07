package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecorateFuniture;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserDecorateFunitureMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserDecorateFunitureService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserDecorateFunitureService
 * @author Code generator
 * @since 2022-07-01
 */

@Service
public class CRUDHouseUserDecorateFunitureServiceImpl  extends CRUDServiceOnlyImpl<HouseUserDecorateFuniture> implements CRUDHouseUserDecorateFunitureService {





        @Resource
        protected HouseUserDecorateFunitureMapper houseUserDecorateFunitureMapper;

        @Override
        protected BaseMapper<HouseUserDecorateFuniture> getMasterMapper() {
                return houseUserDecorateFunitureMapper;
        }







}


