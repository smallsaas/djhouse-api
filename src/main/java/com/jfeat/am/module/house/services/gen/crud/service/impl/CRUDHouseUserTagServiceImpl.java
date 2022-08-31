package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTag;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserTagMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserTagService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserTagService
 * @author Code generator
 * @since 2022-08-29
 */

@Service
public class CRUDHouseUserTagServiceImpl  extends CRUDServiceOnlyImpl<HouseUserTag> implements CRUDHouseUserTagService {





        @Resource
        protected HouseUserTagMapper houseUserTagMapper;

        @Override
        protected BaseMapper<HouseUserTag> getMasterMapper() {
                return houseUserTagMapper;
        }







}


