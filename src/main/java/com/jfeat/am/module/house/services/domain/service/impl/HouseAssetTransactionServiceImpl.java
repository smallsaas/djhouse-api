package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.constant.HouseAssetTransactionConst;
import com.jfeat.am.module.house.services.definition.HouseAssetTransactionStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.domain.service.HouseConfigService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetTransactionServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
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

    @Resource
    HouseConfigService houseConfigService; // 【匠城小程序】的配置服务类

    @Override
    protected String entityName() {
        return "HouseAssetTransaction";
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseAssetTransactionServiceImpl.class);


    @Override
    public void setStatus(List<HouseAssetTransactionRecord> houseAssetTransactionRecordList) {
        if (houseAssetTransactionRecordList != null && houseAssetTransactionRecordList.size() > 0) {

            for (HouseAssetTransactionRecord record : houseAssetTransactionRecordList) {

                if (record.getState() != null && HouseAssetTransactionStatus.containerState(record.getState())) {

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

        if (houseAssetTransaction != null) {

            if (houseAssetTransaction.getState() != null && HouseAssetTransactionStatus.containerState(houseAssetTransaction.getState())) {

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
        return queryHouseAssetTransactionDao.listTransactionByUserId(userId);
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
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_SALES)))
            throw new BusinessException(BusinessCode.NoPermission, "没有销售权限");

        // 执行删除
        int affected = queryHouseAssetTransactionDao.deleteById(id);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseDeleteError, "没有id为" + id + "的记录");
        // 删除 关注记录
        affected += houseAssetTransactionIntentionService.removeTransactionIntentions(id);

        return affected;
    }

    @Override
    public int updateTransaction(HouseAssetTransaction transaction) {

        // 更新条件构造器
        UpdateWrapper<HouseAssetTransaction> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", transaction.getId());

        // 如果更新的是SELL的记录
        if (StringUtils.isNotBlank(transaction.getEnStatus()) && "SELL".equals(transaction.getEnStatus())) {
            if (transaction.getHide() == null) {
                updateWrapper.set("hide", 0);
                updateWrapper.set("custom_floor", null);
            }
        }

        // 执行更新
        int affected = queryHouseAssetTransactionDao.update(transaction, updateWrapper);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError, "update fail");
        return affected;
    }

    /**
     * 刷新转让记录
     *
     * @param id 要刷新的记录id
     * @return
     */
    @Override
    public int renovateTransaction(Long id) {

        if (id == null) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "id cannot null");
        }

        // 刷新默认将display设为1（上架状态）
        HouseAssetTransaction transaction = new HouseAssetTransaction();
        transaction.setId(id);
        transaction.setDisplay(1);
        transaction.setUpdateTime(new Date());

        int affected = queryHouseAssetTransactionDao.updateById(transaction);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError, "刷新失败");

        return affected;
    }

    /**
     * 下架距离最新更新时间已经过"下架周期"的记录
     *
     * @return 下架条目数
     */
    @Override
    @Transactional
    public int pulledOffShelvesTransaction() {

        int affected = 0;

        // 获取全表数据
        List<HouseAssetTransaction> transactions = queryHouseAssetTransactionDao.listTransaction();

        // 从配置表中获取下架时间（配置中的值定义的是天数）
        String configValue = houseConfigService.getFieldValueByFieldGroupNameAndFieldName(
                HouseAssetTransactionConst.HOUSE_BUY_SELL_CONFIG_GROUP_NAME,
                HouseAssetTransactionConst.DOWN_SHELF_TIME_CONFIG_FIELD_NAME
        );
        if (configValue == null) {
            logger.error("从配置中获取下架时间为null，自动下架房屋转让记录方法执行失败");
            return affected;
        }
        // 因为配置值统一是String类型的，所以需要做一次转换
        int downShelfTime = Integer.parseInt(configValue);

        // 当前时间戳
        long nowTimeStamp = new Date().getTime();
        long day = 1000 * 60 * 60 * 24;
        long cycle = day * downShelfTime;

        // 循环判断更新的时间是否已经超过一个月
        for (int i = 0; i < transactions.size(); i++) {
            HouseAssetTransaction transaction = transactions.get(i);
            Date updateDateTime = transaction.getUpdateTime();
            // 如果没有更新时间则判断创建时间
            if (updateDateTime != null) {
                long updateTimeStamp = updateDateTime.getTime();
                if (nowTimeStamp - updateTimeStamp > cycle) {
                    affected += queryHouseAssetTransactionDao.updateDisplayById(transaction.getId());
                }
            } else {
                Date createDateTime = transaction.getCreateTime();
                if (createDateTime == null) continue;
                long createTimeStamp = createDateTime.getTime();
                if (nowTimeStamp - createTimeStamp > cycle) {
                    affected += queryHouseAssetTransactionDao.updateDisplayById(transaction.getId());
                }
            }
        }

        return affected;
    }
}
