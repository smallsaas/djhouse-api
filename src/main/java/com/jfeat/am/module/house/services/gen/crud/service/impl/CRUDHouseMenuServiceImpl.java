package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseMenuMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseMenuService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseMenuService
 * @author Code generator
 * @since 2022-08-01
 */

@Service
public class CRUDHouseMenuServiceImpl  extends CRUDServiceOnlyImpl<HouseMenu> implements CRUDHouseMenuService {





        @Resource
        protected HouseMenuMapper houseMenuMapper;

        @Override
        protected BaseMapper<HouseMenu> getMasterMapper() {
                return houseMenuMapper;
        }







}


