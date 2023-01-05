package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetTransactionMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetTransactionService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetTransactionService
 * @author Code generator
 * @since 2023-01-05
 */

@Service
public class CRUDHouseAssetTransactionServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetTransaction> implements CRUDHouseAssetTransactionService {





        @Resource
        protected HouseAssetTransactionMapper houseAssetTransactionMapper;

        @Override
        protected BaseMapper<HouseAssetTransaction> getMasterMapper() {
                return houseAssetTransactionMapper;
        }







}


