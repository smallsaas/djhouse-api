package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserDecoratePlanMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserDecoratePlanService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserDecoratePlanService
 * @author Code generator
 * @since 2022-07-01
 */

@Service
public class CRUDHouseUserDecoratePlanServiceImpl  extends CRUDServiceOnlyImpl<HouseUserDecoratePlan> implements CRUDHouseUserDecoratePlanService {





        @Resource
        protected HouseUserDecoratePlanMapper houseUserDecoratePlanMapper;

        @Override
        protected BaseMapper<HouseUserDecoratePlan> getMasterMapper() {
                return houseUserDecoratePlanMapper;
        }







}


