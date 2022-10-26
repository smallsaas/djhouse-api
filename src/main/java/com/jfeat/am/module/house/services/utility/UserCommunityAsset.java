package com.jfeat.am.module.house.services.utility;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;

import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserCommunityStatusMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserCommunityAsset {

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HouseUserCommunityStatusMapper houseUserCommunityStatusMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

//    过滤不是当前小区的房屋
    public List<HouseUserAssetRecord> getCommunityAsset(Long userId, List<HouseUserAssetRecord> list){
        //        查询用户小区状态
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(userId);
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null,buildingRecord,null,null,null,null,null);

        List<HouseUserAssetRecord> t = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            for (int j=0;j<buildingRecordList.size();j++)
            if (list.get(i).getBuildingId().equals(buildingRecordList.get(j).getId())){
                t.add(list.get(i));
            }
        }
        return t;
    }

//    获取当前小区id
    public Long getUserCommunityStatus(Long userId){
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(userId);
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        return communityId;
    }


    public HousePropertyCommunity getUserCommunityInfo(Long userId){
        QueryWrapper<HouseUserCommunityStatus> houseUserCommunityStatusQueryWrapper = new QueryWrapper<>();
        houseUserCommunityStatusQueryWrapper.eq(HouseUserCommunityStatus.USER_ID,userId);
        HouseUserCommunityStatus houseUserCommunityStatus = houseUserCommunityStatusMapper.selectOne(houseUserCommunityStatusQueryWrapper);

        if (houseUserCommunityStatus==null){
            throw new BusinessException(BusinessCode.CodeBase,"未找到小区信息");
        }
        Long communityId = houseUserCommunityStatus.getCommunityId();

        HousePropertyCommunity housePropertyCommunity =  housePropertyCommunityMapper.selectById(communityId);
        return housePropertyCommunity;
    }

//    返回当前小区的assetId 列表
    public List<Long> getCurrentCommunityAssetIds(Long userId,List<Long> assetIds){

        if (userId==null || assetIds==null){
            throw new BusinessException(BusinessCode.BadRequest,"参数错误");
        }

//        获取当前小区
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(userId);
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
        if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }

//        查询小区有多少楼栋
        HousePropertyBuildingRecord buildingRecord = new HousePropertyBuildingRecord();
        buildingRecord.setCommunityId(communityId);
        List<HousePropertyBuildingRecord> buildingRecordList = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null,buildingRecord,null,null,null,null,null);

//        获取整个小区的asset
        List<HouseAssetRecord> overCommunityAsset = new ArrayList<>();
        for (int i=0;i<buildingRecordList.size();i++){
            HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
            houseAssetRecord.setBuildingId(buildingRecordList.get(i).getId());
            List<HouseAssetRecord> overBuildingAsset = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);
            if (overBuildingAsset!=null && overBuildingAsset.size()>0){
                overCommunityAsset.addAll(overBuildingAsset);
            }
        }

//        AssetIds 是否存在当前小区
        List<Long> result = new ArrayList<>();
        for (HouseAssetRecord record:overCommunityAsset){
            if (assetIds.contains(record.getId())){
                result.add(record.getId());
            }
        }

        return result;
    }
}
