package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetExchangeRequestServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
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

    @Override
    protected String entityName() {
        return "HouseAssetExchangeRequest";
    }


    @Override
    public List<HouseAssetExchangeRequest> assetMachResult(HouseAssetExchangeRequest assetExchangeRequest,Boolean isSameHouseType) {
        /*
        删除原有的 已经匹配成功记录
         */
        queryHouseAssetMatchLogDao.deleteHouseAssetMatchLogByUserIdAndAssetId(assetExchangeRequest.getAssetId(),assetExchangeRequest.getUserId());

        /*
        查询交换的房子户型
         */
//        Long ownHouseTypeId = queryHouseAssetExchangeRequestDao.queryHouseAssetHouseType(assetExchangeRequest.getAssetId());
        List<HouseAssetExchangeRequest> matchedAsset = new ArrayList<>();
        List<String> targetAssetRange = Arrays.asList(assetExchangeRequest.getTargetAssetRange().split(","));
        List<Long> targetAssetRangeIds = targetAssetRange.stream().map(Long::valueOf).collect(Collectors.toList());


        for (int i=0;i<targetAssetRangeIds.size();i++){

            /*
            查询 需求房屋是否 添加到需求表中
            没有就跳过
             */
            HouseAssetExchangeRequest houseAssetExchangeRequest =  queryHouseAssetExchangeRequestDao.queryHouseAssetExchangeRequestByAssetId(targetAssetRangeIds.get(i));
            if (houseAssetExchangeRequest ==null){
                continue;
            }
//            if (isSameHouseType && (ownHouseTypeId==null|| ownHouseTypeId!=queryHouseAssetExchangeRequestDao.queryHouseAssetHouseType(houseAssetExchangeRequest.getAssetId()))){
//                continue;
//            }

            List<String>  matchedAssetRange = Arrays.asList(houseAssetExchangeRequest.getTargetAssetRange().split(","));
            List<Long> matchedAssetRangeIds = matchedAssetRange.stream().map(Long::valueOf).collect(Collectors.toList());

            /*
            如果 匹配到的人id 不等于自己id 而且 匹配到的人的需求范围正好有自己
            添加匹配成功表
             */
            if (houseAssetExchangeRequest.getUserId()!=assetExchangeRequest.getUserId() && matchedAssetRangeIds.contains(assetExchangeRequest.getAssetId())){
                matchedAsset.add(houseAssetExchangeRequest);
                HouseAssetMatchLog houseAssetMatchLog = new HouseAssetMatchLog();
                houseAssetMatchLog.setOwnerAssetId(assetExchangeRequest.getAssetId());
                houseAssetMatchLog.setOwnerUserId(assetExchangeRequest.getUserId());
                houseAssetMatchLog.setMathchedAssetId(houseAssetExchangeRequest.getAssetId());
                houseAssetMatchLog.setMatchedUserId(houseAssetExchangeRequest.getUserId());
                houseAssetMatchLog.setCreateTime(new Date());
                houseAssetMatchLog.setOrgId(JWTKit.getOrgId());
                houseAssetMatchLogService.createMaster(houseAssetMatchLog);

                HouseAssetMatchLog houseAssetMatchLog1 = new HouseAssetMatchLog();
                houseAssetMatchLog1.setOwnerAssetId(houseAssetExchangeRequest.getAssetId());
                houseAssetMatchLog1.setOwnerUserId(houseAssetExchangeRequest.getUserId());
                houseAssetMatchLog1.setMathchedAssetId(assetExchangeRequest.getAssetId());
                houseAssetMatchLog1.setMatchedUserId(assetExchangeRequest.getUserId());
                houseAssetMatchLog1.setCreateTime(new Date());
                houseAssetMatchLog.setOrgId(JWTKit.getOrgId());
                houseAssetMatchLogService.createMaster(houseAssetMatchLog1);
            }
        }
        return matchedAsset;
    }
}
