package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTagRelate;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserTagRelateMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserTagRelateService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserTagRelateService
 * @author Code generator
 * @since 2022-08-29
 */

@Service
public class CRUDHouseUserTagRelateServiceImpl  extends CRUDServiceOnlyImpl<HouseUserTagRelate> implements CRUDHouseUserTagRelateService {





        @Resource
        protected HouseUserTagRelateMapper houseUserTagRelateMapper;

        @Override
        protected BaseMapper<HouseUserTagRelate> getMasterMapper() {
                return houseUserTagRelateMapper;
        }







}


