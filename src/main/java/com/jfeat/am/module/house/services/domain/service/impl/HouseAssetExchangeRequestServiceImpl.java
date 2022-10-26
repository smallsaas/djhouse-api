package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.domain.service.HouseTenantMenuService;
import com.jfeat.am.module.house.services.domain.service.HouseUnlikeLogService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetExchangeRequestServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.weChatMiniprogram.services.domain.service.SysThirdPartyUserService;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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


//   private static final Logger log = LoggerFactory.getLogger(this.getClass())


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HouseAssetExchangeRequestService.class);

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

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


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

//        去除自己匹配自己的情况
        List<HouseAssetExchangeRequestRecord> temp = new ArrayList<>();
        for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord:exchangeRequests){
            if (!houseAssetExchangeRequestRecord.getUserId().equals(houseAssetExchangeRequestRecord.getTargetUserId())){
                temp.add(houseAssetExchangeRequestRecord);
            }
        }
        exchangeRequests = temp;




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
        if (exchangeRequestList!=null && exchangeRequestList.size()>0){
            return queryHouseAssetExchangeRequestDao.batchAddExchangeRequest(exchangeRequestList);
        }
        return 0;

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

    @Override
    @Async
    public int addSameFloorExchangeRequest(Long userId) {
//        获取用户当前小区信息
        Long communityId = userCommunityAsset.getUserCommunityStatus(userId);
        if (communityId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有找到小区信息");
        }

//        获取用当前小区房屋
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setCommunityId(communityId);
        houseUserAssetRecord.setUserId(userId);
        List<HouseUserAssetRecord> userAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord,null,null,null,null,null);

        List<HouseAssetRecord> houseAssetRecordList = new ArrayList<>();
        for (HouseUserAssetRecord userAssetRecord:userAssetRecordList){
            HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
            houseAssetRecord.setBuildingId(userAssetRecord.getBuildingId());
            houseAssetRecord.setFloor(userAssetRecord.getFloor());

            houseAssetRecordList.add(houseAssetRecord);
        }


//        获取当前小区同楼栋同楼层房屋
        List<HouseAssetRecord> sameBuildingAssetList =  queryHouseAssetDao.querySameBuildingAndFloor(houseAssetRecordList);

        List<HouseAssetRecord> temp = new ArrayList<>();
        for (HouseAssetRecord sameBuildingAsset:sameBuildingAssetList){
            Boolean flag = true;
            for (HouseUserAssetRecord ownHouseAsset:userAssetRecordList){

                if (ownHouseAsset.getAssetId().equals(sameBuildingAsset.getId())){
                    flag=false;
                }

            }
            if (flag){
                temp.add(sameBuildingAsset);
            }
        }
        sameBuildingAssetList = temp;



//        判断条件匹配条件是否合适
        //        获取换房功能条件
        HouseMenuRecord record1 = new HouseMenuRecord();
        record1.setType("exchangeAsset");
        List<HouseMenuRecord> secondaryMenu = houseTenantMenuService.getSecondaryMenu(userId, record1);

        Map<String,Integer> map = new HashMap<>();
        for (HouseMenuRecord houseMenuRecord:secondaryMenu){
            map.put(houseMenuRecord.getComponent(),houseMenuRecord.getEnabled());
        }

        List<HouseAssetExchangeRequest> houseAssetExchangeRequestList = new ArrayList<>();

//        自己的房子
        for (HouseUserAssetRecord userAssetRecord:userAssetRecordList){

//            同城的房子
            for (HouseAssetRecord houseAssetRecord: sameBuildingAssetList){
                if (!userAssetRecord.getFloor().equals(houseAssetRecord.getFloor())){

                    HouseAssetExchangeRequest houseAssetExchangeRequest = new HouseAssetExchangeRequest();

                    if (userAssetRecord.getBuildingId().equals(houseAssetRecord.getBuildingId())){


                        if (map.get("unlimited")!=null &&!map.get("unlimited").equals(1)){

                            if (map.get("unitId").equals(1) &&  userAssetRecord.getUnitId().equals(houseAssetRecord.getUnitId())){
                                houseAssetExchangeRequest.setUserId(userId);
                                houseAssetExchangeRequest.setAssetId(userAssetRecord.getAssetId());
                                houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                                houseAssetExchangeRequestList.add(houseAssetExchangeRequest);
                            }

                            if (map.get("houseType").equals(1) &&  userAssetRecord.getHouseTypeId().equals(houseAssetRecord.getDesignModelId())){
                                houseAssetExchangeRequest.setUserId(userId);
                                houseAssetExchangeRequest.setAssetId(userAssetRecord.getAssetId());
                                houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                                houseAssetExchangeRequestList.add(houseAssetExchangeRequest);
                            }

                            if (map.get("area").equals(1) &&  userAssetRecord.getUnitArea().equals(houseAssetRecord.getArea())){
                                houseAssetExchangeRequest.setUserId(userId);
                                houseAssetExchangeRequest.setAssetId(userAssetRecord.getAssetId());
                                houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                                houseAssetExchangeRequestList.add(houseAssetExchangeRequest);
                            }

                            if (map.get("issue").equals(1) &&  userAssetRecord.getIssue().equals(houseAssetRecord.getIssue())){
                                houseAssetExchangeRequest.setUserId(userId);
                                houseAssetExchangeRequest.setAssetId(userAssetRecord.getAssetId());
                                houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                                houseAssetExchangeRequestList.add(houseAssetExchangeRequest);
                            }
                        }else {
                            houseAssetExchangeRequest.setUserId(userId);
                            houseAssetExchangeRequest.setAssetId(userAssetRecord.getAssetId());
                            houseAssetExchangeRequest.setTargetAsset(houseAssetRecord.getId());
                            houseAssetExchangeRequestList.add(houseAssetExchangeRequest);
                        }


                    }

                }
            }
        }

        logger.info("自动交换记录",userId,houseAssetExchangeRequestList);

        System.out.println(houseAssetExchangeRequestList);

        if (houseAssetExchangeRequestList!=null){
            for (HouseAssetExchangeRequest houseAssetExchangeRequest:houseAssetExchangeRequestList){
                houseAssetExchangeRequest.setAutoGenerateStatus(HouseAssetExchangeRequest.AUTO_GENERATE_STATUS_YES);
            }
        }

        Integer affect  =  batchAddExchangeRequest(houseAssetExchangeRequestList);

        for (HouseAssetExchangeRequest houseAssetExchangeRequest:houseAssetExchangeRequestList){
             assetMachResult(houseAssetExchangeRequest);
        }


        return affect;
    }
}
