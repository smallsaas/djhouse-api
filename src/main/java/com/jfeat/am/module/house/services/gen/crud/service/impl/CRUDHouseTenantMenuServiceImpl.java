package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseTenantMenuMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseTenantMenuService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseTenantMenuService
 * @author Code generator
 * @since 2022-08-12
 */

@Service
public class CRUDHouseTenantMenuServiceImpl  extends CRUDServiceOnlyImpl<HouseTenantMenu> implements CRUDHouseTenantMenuService {





        @Resource
        protected HouseTenantMenuMapper houseTenantMenuMapper;

        @Override
        protected BaseMapper<HouseTenantMenu> getMasterMapper() {
                return houseTenantMenuMapper;
        }







}


