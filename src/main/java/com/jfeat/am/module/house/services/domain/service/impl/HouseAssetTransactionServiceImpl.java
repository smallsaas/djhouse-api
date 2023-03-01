package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.definition.HouseAssetTransactionStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetTransactionServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetTransactionService")
public class HouseAssetTransactionServiceImpl extends CRUDHouseAssetTransactionServiceImpl implements HouseAssetTransactionService {

    @Resource
    UserAccountUtility userAccountUtility;

    @Resource
    QueryHouseAssetTransactionDao queryHouseAssetTransactionDao;

    @Resource
    HouseAssetTransactionIntentionService houseAssetTransactionIntentionService;

    @Override
    protected String entityName() {
        return "HouseAssetTransaction";
    }


    @Override
    public void setStatus(List<HouseAssetTransactionRecord> houseAssetTransactionRecordList) {
        if (houseAssetTransactionRecordList!=null && houseAssetTransactionRecordList.size()>0){

            for (HouseAssetTransactionRecord record:houseAssetTransactionRecordList){

                if (record.getState()!=null && HouseAssetTransactionStatus.containerState(record.getState())){

                    String cnStatus = HouseAssetTransactionStatus.getStatusByState(record.getState());

                    String enStatus = HouseAssetTransactionStatus.getNameByState(record.getState());

                    record.setEnStatus(enStatus);
                    record.setCnStatus(cnStatus);
                }

            }
        }
    }

    @Override
    public void setStatus(HouseAssetTransaction houseAssetTransaction) {

        if (houseAssetTransaction!=null){

            if (houseAssetTransaction.getState()!=null && HouseAssetTransactionStatus.containerState(houseAssetTransaction.getState())){

                String cnStatus = HouseAssetTransactionStatus.getStatusByState(houseAssetTransaction.getState());

                String enStatus = HouseAssetTransactionStatus.getNameByState(houseAssetTransaction.getState());

                houseAssetTransaction.setEnStatus(enStatus);
                houseAssetTransaction.setCnStatus(cnStatus);
            }
        }
    }

    @Override
    public List<BigDecimal> listPriceOfTransaction() {
        return queryHouseAssetTransactionDao.listPriceOfTransaction();
    }

    @Override
    public List<HouseAssetTransactionRecord> listTransaction(Long userId) {
        return queryHouseAssetTransactionDao.listTransaction(userId);
    }

    @Override
    public Integer updateDisplay(HouseAssetTransactionRecord transaction) {
        return queryHouseAssetTransactionDao.updateDisplay(transaction);
    }

    /**
     * 删除转让记录
     *
     * @param id 记录id
     * @return
     */
    @Override
    public int removeTransaction(Long id) {

        // 判断是否拥有销售权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_SALES))) throw new BusinessException(BusinessCode.NoPermission,"没有销售权限");

        // 执行删除
        int affected = queryHouseAssetTransactionDao.deleteById(id);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseDeleteError,"没有id为" + id + "的记录");
        // 删除 关注记录
        affected += houseAssetTransactionIntentionService.removeTransactionIntention(id);

        return affected;
    }
}
