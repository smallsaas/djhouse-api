package com.jfeat.am.module.house.services.domain.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.definition.HouseAssetTransactionStatus;
import com.jfeat.am.module.house.services.definition.HouseRentLogStatus;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseRentLogService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentLogServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseRentLogService")
public class HouseRentLogServiceImpl extends CRUDHouseRentLogServiceImpl implements HouseRentLogService {

    @Override
    protected String entityName() {
        return "HouseRentLog";
    }

    @Resource
    HouseRentLogMapper houseRentLogMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;


    @Override
    public HouseRentLog houseRentToLog(HouseRentAsset houseRentAsset) {

        if (houseRentAsset==null){
            return null;
        }

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAsset);

        HouseRentLog houseRentLog = JSONObject.parseObject(jsonObject.toJSONString(),HouseRentLog.class);

        Long rentId = houseRentLog.getId();

        Date createTime = houseRentLog.getCreateTime();

        Date updateTime = houseRentLog.getUpdateTime();

        houseRentLog.setRentId(rentId);
        houseRentLog.setId(null);
        houseRentLog.setRentCreateTime(createTime);
        houseRentLog.setCreateTime(null);
        houseRentLog.setRentUpdateTime(updateTime);
        houseRentLog.setUpdateTime(null);
        return houseRentLog;
    }

    @Override
    public Integer addHouseRentLog(HouseRentAsset houseRentAsset,String status) {

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        Integer affect = 0;
        if (houseRentAsset==null||status==null||!HouseRentLogStatus.containName(status)){
            return affect;
        }
        HouseRentLog houseRentLog = houseRentToLog(houseRentAsset);
        Integer state = HouseRentLogStatus.getStateByName(status);
        houseRentLog.setState(state);
        houseRentLog.setOperator(userId);
        affect = houseRentLogMapper.insert(houseRentLog);
        return affect;
    }

    @Override
    public Integer addHouseRentLog(HouseRentAssetModel houseRentAssetModel,String status) {
        HouseRentAsset houseRentAsset = CRUD.castObject(houseRentAssetModel);
        return addHouseRentLog(houseRentAsset,status);
    }

    @Override
    public Integer addHouseRentLog(HouseRentAssetRecord houseRentAssetRecord,String status) {
        HouseRentAsset houseRentAsset = CRUD.castObject(houseRentAssetRecord);
        return addHouseRentLog(houseRentAsset,status);
    }

    @Override
    public Integer addHouseRentLog(Long id,String status) {

        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectById(id);
        if (houseRentAsset==null){
            return 0;
        }
        return addHouseRentLog(houseRentAsset,status);
    }

    @Override
    public void setStateStr(List<HouseRentLogRecord> recordList) {
        if (recordList!=null && recordList.size()>0){

            for (HouseRentLogRecord record:recordList){

                if (record.getState()!=null && HouseRentLogStatus.containerState(record.getState())){

                    String cnStatus = HouseRentLogStatus.getStatusByState(record.getState());

                    String enStatus = HouseRentLogStatus.getNameByState(record.getState());

                    record.setEnStatus(enStatus);
                    record.setCnStatus(cnStatus);
                }

            }
        }
    }


}
