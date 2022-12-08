package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLogHistory;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogHistoryMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetMatchLogHistoryService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetMatchLogHistoryService
 * @author Code generator
 * @since 2022-12-08
 */

@Service
public class CRUDHouseAssetMatchLogHistoryServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetMatchLogHistory> implements CRUDHouseAssetMatchLogHistoryService {





        @Resource
        protected HouseAssetMatchLogHistoryMapper houseAssetMatchLogHistoryMapper;

        @Override
        protected BaseMapper<HouseAssetMatchLogHistory> getMasterMapper() {
                return houseAssetMatchLogHistoryMapper;
        }







}


