package com.jfeat.am.module.house.services.utility;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;

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

}
