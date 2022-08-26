package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseBrowseLogMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseBrowseLogService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseBrowseLogService
 * @author Code generator
 * @since 2022-08-25
 */

@Service
public class CRUDHouseBrowseLogServiceImpl  extends CRUDServiceOnlyImpl<HouseBrowseLog> implements CRUDHouseBrowseLogService {





        @Resource
        protected HouseBrowseLogMapper houseBrowseLogMapper;

        @Override
        protected BaseMapper<HouseBrowseLog> getMasterMapper() {
                return houseBrowseLogMapper;
        }







}


