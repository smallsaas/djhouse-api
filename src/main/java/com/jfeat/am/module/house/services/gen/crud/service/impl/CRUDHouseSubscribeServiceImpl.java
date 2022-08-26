package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSubscribeMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSubscribeService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseSubscribeService
 * @author Code generator
 * @since 2022-08-24
 */

@Service
public class CRUDHouseSubscribeServiceImpl  extends CRUDServiceOnlyImpl<HouseSubscribe> implements CRUDHouseSubscribeService {





        @Resource
        protected HouseSubscribeMapper houseSubscribeMapper;

        @Override
        protected BaseMapper<HouseSubscribe> getMasterMapper() {
                return houseSubscribeMapper;
        }







}


