package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAssetHistory;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetHistoryMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserAssetHistoryService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserAssetHistoryService
 * @author Code generator
 * @since 2022-12-08
 */

@Service
public class CRUDHouseUserAssetHistoryServiceImpl  extends CRUDServiceOnlyImpl<HouseUserAssetHistory> implements CRUDHouseUserAssetHistoryService {





        @Resource
        protected HouseUserAssetHistoryMapper houseUserAssetHistoryMapper;

        @Override
        protected BaseMapper<HouseUserAssetHistory> getMasterMapper() {
                return houseUserAssetHistoryMapper;
        }







}


