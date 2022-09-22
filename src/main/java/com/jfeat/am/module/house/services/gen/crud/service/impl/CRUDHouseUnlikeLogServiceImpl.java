package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUnlikeLogMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUnlikeLogService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUnlikeLogService
 * @author Code generator
 * @since 2022-09-19
 */

@Service
public class CRUDHouseUnlikeLogServiceImpl  extends CRUDServiceOnlyImpl<HouseUnlikeLog> implements CRUDHouseUnlikeLogService {





        @Resource
        protected HouseUnlikeLogMapper houseUnlikeLogMapper;

        @Override
        protected BaseMapper<HouseUnlikeLog> getMasterMapper() {
                return houseUnlikeLogMapper;
        }







}


