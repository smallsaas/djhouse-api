package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlanFuniture;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDecoratePlanFunitureMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseDecoratePlanFunitureService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseDecoratePlanFunitureService
 * @author Code generator
 * @since 2022-05-27
 */

@Service
public class CRUDHouseDecoratePlanFunitureServiceImpl  extends CRUDServiceOnlyImpl<HouseDecoratePlanFuniture> implements CRUDHouseDecoratePlanFunitureService {





        @Resource
        protected HouseDecoratePlanFunitureMapper houseDecoratePlanFunitureMapper;

        @Override
        protected BaseMapper<HouseDecoratePlanFuniture> getMasterMapper() {
                return houseDecoratePlanFunitureMapper;
        }







}


