package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetMatchLogService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetMatchLogService
 * @author Code generator
 * @since 2022-06-11
 */

@Service
public class CRUDHouseAssetMatchLogServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetMatchLog> implements CRUDHouseAssetMatchLogService {





        @Resource
        protected HouseAssetMatchLogMapper houseAssetMatchLogMapper;

        @Override
        protected BaseMapper<HouseAssetMatchLog> getMasterMapper() {
                return houseAssetMatchLogMapper;
        }







}


