package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetLog;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetLogMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetLogService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetLogService
 * @author Code generator
 * @since 2022-11-15
 */

@Service
public class CRUDHouseAssetLogServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetLog> implements CRUDHouseAssetLogService {





        @Resource
        protected HouseAssetLogMapper houseAssetLogMapper;

        @Override
        protected BaseMapper<HouseAssetLog> getMasterMapper() {
                return houseAssetLogMapper;
        }







}


