package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUnlikeLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.domain.service.HouseTenantMenuService;
import com.jfeat.am.module.house.services.domain.service.HouseUnlikeLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetExchangeRequestServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetExchangeRequestService")
public class HouseAssetExchangeRequestServiceImpl extends CRUDHouseAssetExchangeRequestServiceImpl implements HouseAssetExchangeRequestService {


    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

    @Resource
    HouseAssetMatchLogService houseAssetMatchLogService;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    TenantUtility tenantUtility;

    @Resource
    QueryHouseUnlikeLogDao queryHouseUnlikeLogDao;

    @Resource
    HouseTenantMenuService houseTenantMenuService;


    @Override
    protected String entityName() {
        return "HouseAssetExchangeRequest";
    }


    @Override
    @Transactional
    public List<HouseAssetExchangeRequestRecord> assetMachResult(HouseAssetExchangeRequest assetExchangeRequest) {
         /*
        删除原有的 已经匹配成功记录
         */
        queryHouseAssetMatchLogDao.deleteHouseAssetMatchLogByUserIdAndAssetId(assetExchangeRequest.getAssetId(), assetExchangeRequest.getUserId());


        HouseAssetExchangeRequestRecord record = new HouseAssetExchangeRequestRecord();
        record.setAssetId(assetExchangeRequest.getAssetId());

        List<HouseAssetExchangeRequestRecord> exchangeRequests = queryHouseAssetExchangeRequestDao.queryMatchTargetAssetList(null, record, null);

        HouseMenuRecord record1 = new HouseMenuRecord();
        record1.setType("exchangeAsset");
        List<HouseMenuRecord> secondaryMenu = houseTenantMenuService.getSecondaryMenu(assetExchangeRequest.getUserId(), record1);

        Map<String,Integer> map = new HashMap<>();
        for (HouseMenuRecord houseMenuRecord:secondaryMenu){
            map.put(houseMenuRecord.getComponent(),houseMenuRecord.getEnabled());
        }


        List<HouseAssetMatchLog> matchLogList = new ArrayList<>();
        for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord : exchangeRequests) {
//            过滤条件

            if (!map.get("unlimited").equals(1)){

                if (map.get("unitId").equals(1) && ! houseAssetExchangeRequestRecord.getUnitId().equals(houseAssetExchangeRequestRecord.getTargetUnitId())){
                    continue;
                }

                if (map.get("houseType").equals(1) && ! houseAssetExchangeRequestRecord.getDesignModelId().equals(houseAssetExchangeRequestRecord.getTargetHouseTypeId())){
                    continue;
                }

                if (map.get("area").equals(1) && ! houseAssetExchangeRequestRecord.getArea().equals(houseAssetExchangeRequestRecord.getTargetArea())){
                    continue;
                }

                if (map.get("issue").equals(1) && ! houseAssetExchangeRequestRecord.getIssue().equals(houseAssetExchangeRequestRecord.getTargetIssue())){
                    continue;
                }
            }

            HouseAssetMatchLog matchLog = new HouseAssetMatchLog();
            matchLog.setOrgId(tenantUtility.getCurrentOrgId(assetExchangeRequest.getUserId()));
            matchLog.setOwnerAssetId(houseAssetExchangeRequestRecord.getAssetId());
            matchLog.setOwnerUserId(houseAssetExchangeRequestRecord.getUserId());
            matchLog.setMatchedUserId(houseAssetExchangeRequestRecord.getTargetUserId());
            matchLog.setMathchedAssetId(houseAssetExchangeRequestRecord.getTargetAsset());

            HouseAssetMatchLog ortherMatchLog = new HouseAssetMatchLog();
            ortherMatchLog.setOrgId(tenantUtility.getCurrentOrgId(assetExchangeRequest.getUserId()));
            ortherMatchLog.setMathchedAssetId(houseAssetExchangeRequestRecord.getAssetId());
            ortherMatchLog.setMatchedUserId(houseAssetExchangeRequestRecord.getUserId());
            ortherMatchLog.setOwnerUserId(houseAssetExchangeRequestRecord.getTargetUserId());
            ortherMatchLog.setOwnerAssetId(houseAssetExchangeRequestRecord.getTargetAsset());

            matchLogList.add(matchLog);
            matchLogList.add(ortherMatchLog);
        }

        if (matchLogList != null && matchLogList.size() > 0) {
            queryHouseAssetMatchLogDao.batchAddHouseAssetMatchLog(matchLogList);
        }

        return exchangeRequests;
    }

    @Override
    public int batchAddExchangeRequest(List<HouseAssetExchangeRequest> exchangeRequestList) {
        return queryHouseAssetExchangeRequestDao.batchAddExchangeRequest(exchangeRequestList);
    }

    @Override
    public int batchDeleteExchangeRequest(HouseAssetExchangeRequest exchangeRequest) {
        return queryHouseAssetExchangeRequestDao.batchDeleteExchangeRequest(exchangeRequest);
    }

    @Override
    public int addHouseAssetExchangeRequest(HouseAssetExchangeRequest entity) {

        Integer affect = 0;

        String[] targetAsset = entity.getTargetAssetRange().split(",");
        List<HouseAssetExchangeRequest> assetExchangeRequestList = new ArrayList<>();
        for (String targetAssetId : targetAsset) {
            Long targetAssetIdL = Long.parseLong(targetAssetId);
            HouseAssetExchangeRequest houseAssetExchangeRequest = new HouseAssetExchangeRequest();
            houseAssetExchangeRequest.setAssetId(entity.getAssetId());
            houseAssetExchangeRequest.setUserId(entity.getUserId());
            houseAssetExchangeRequest.setTargetAsset(targetAssetIdL);
            assetExchangeRequestList.add(houseAssetExchangeRequest);
        }

        QueryWrapper<HouseAssetExchangeRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAssetExchangeRequest.ASSET_ID, entity.getAssetId()).eq(HouseAssetExchangeRequest.USER_ID, entity.getUserId());
        List<HouseAssetExchangeRequest> exchangeRequestList = houseAssetExchangeRequestMapper.selectList(queryWrapper);

        if (exchangeRequestList != null && exchangeRequestList.size() > 0) {
            HouseAssetExchangeRequest deleteRecord = new HouseAssetExchangeRequest();
            deleteRecord.setUserId(entity.getUserId());
            deleteRecord.setAssetId(entity.getAssetId());
            affect += batchDeleteExchangeRequest(deleteRecord);
        }

        affect += batchAddExchangeRequest(assetExchangeRequestList);

        assetMachResult(entity);

        return affect;
    }

    @Override
    public int confirmExchangeAsset(List<HouseAssetExchangeRequest> exchangeRequestList) {
        List<HouseAssetExchangeRequest> assetExchangeRequestList = new ArrayList<>();
        Integer affect = 0;
        if (exchangeRequestList != null && exchangeRequestList.size() > 0) {
            affect += batchAddExchangeRequest(exchangeRequestList);
            for (HouseAssetExchangeRequest houseAssetExchangeRequest : exchangeRequestList) {
                assetMachResult(houseAssetExchangeRequest);

            }
        }
        return affect;
    }

    @Override
    public int addUnlikeAssetExchangeRequest(List<HouseAssetExchangeRequest> assetExchangeRequest, List<HouseUnlikeLog> houseUnlikeLogList) {
        int affect  = 0;
        affect+=batchAddExchangeRequest(assetExchangeRequest);
        affect+=queryHouseUnlikeLogDao.batchAddHouseUnLikeLog(houseUnlikeLogList);
        return affect;
    }
}
