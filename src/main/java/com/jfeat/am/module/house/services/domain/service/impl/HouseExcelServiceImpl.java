package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetExchangeRequestService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseExcelService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
public class HouseExcelServiceImpl implements HouseExcelService {


//    将列表解析成 {
//        "username":{
//            "userId":
//            "house":{
//                "communityName":{
//                    "buildingCode"：[
//                    {"assetId":,"houseNumber":},
//                            ]
//                }
//            }
//        }
//    }

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;


    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HouseAssetService houseAssetService;


    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Override
    public JSONObject parseExcelData(List<UserHouseExcel> excelList) {
        if (excelList == null || excelList.size() <= 0) {
            return null;
        }


        JSONObject json = new JSONObject();

        for (UserHouseExcel userHouseExcel : excelList) {

            Long userId = null;

//            判断名字是否为空 如果名字为空就跳过
            if (userHouseExcel.getUserName() == null || userHouseExcel.getUserName().equals("")) {
                continue;
            }

            QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
            userAccountQueryWrapper.eq(UserAccount.NAME, userHouseExcel.getUserName());
            userAccountQueryWrapper.eq(UserAccount.APPID, "1");

            List<UserAccount> userAccounts = userAccountMapper.selectList(userAccountQueryWrapper);


            if (userAccounts == null || userAccounts.size() <= 0) {
                UserAccount userAccount = new UserAccount();
                userAccount.setName(userHouseExcel.getUserName());
                userAccount.setRealName(userHouseExcel.getUserName());
                userAccount.setAppid("1");
                userAccount.setType(16);
                userAccountService.createMaster(userAccount);
                userId = userAccount.getId();

            } else {
                UserAccount userAccount = userAccounts.get(0);
                userId = userAccount.getId();

            }

            if (userId != null) {

                if (!json.containsKey(userHouseExcel.getUserName())) {

                    JSONObject userMap = new JSONObject();
                    userMap.put("userId", userId);


                    JSONArray houseNumberList = new JSONArray();

                    JSONObject houseNumberJson = new JSONObject();
                    houseNumberJson.put("houseNumber", userHouseExcel.getHouseNumber());
                    houseNumberList.add(houseNumberJson);

                    JSONObject buildingCodeMap = new JSONObject();
                    buildingCodeMap.put(userHouseExcel.getBuildingCode(), houseNumberList);


                    JSONObject userHouseMap = new JSONObject();
                    userHouseMap.put(userHouseExcel.getCommunityName(), buildingCodeMap);

                    userMap.put("house", userHouseMap);

                    json.put(userHouseExcel.getUserName(), userMap);


                } else {


                    JSONObject userMap = json.getJSONObject(userHouseExcel.getUserName());


                    JSONObject userHouseMap = userMap.getJSONObject("house");


                    if (userHouseMap.containsKey(userHouseExcel.getCommunityName())) {

                        JSONObject communityMap = userHouseMap.getJSONObject(userHouseExcel.getCommunityName());


                        if (communityMap.containsKey(userHouseExcel.getBuildingCode())) {

                            JSONArray houseNumberList = communityMap.getJSONArray(userHouseExcel.getBuildingCode());

                            JSONObject houseNumberJson = new JSONObject();
                            houseNumberJson.put("houseNumber", userHouseExcel.getHouseNumber());
                            houseNumberList.add(houseNumberJson);

                        } else {

                            JSONArray houseNumberList = new JSONArray();

                            JSONObject houseNumberJson = new JSONObject();
                            houseNumberJson.put("houseNumber", userHouseExcel.getHouseNumber());
                            houseNumberList.add(houseNumberJson);
                            communityMap.put(userHouseExcel.getBuildingCode(), houseNumberList);
                        }


                    } else {


                        JSONArray houseNumberList = new JSONArray();

                        JSONObject houseNumberJson = new JSONObject();
                        houseNumberJson.put("houseNumber", userHouseExcel.getHouseNumber());
                        houseNumberList.add(houseNumberJson);

                        JSONObject buildingCodeMap = new JSONObject();
                        buildingCodeMap.put(userHouseExcel.getBuildingCode(), houseNumberList);


                        userHouseMap.put(userHouseExcel.getCommunityName(), buildingCodeMap);

                    }

                }


            }


        }

        return json;
    }

    @Override
    public JSONObject setAssetId(JSONObject json) {

//        获取Excel里面有哪些小区
        Set<String> communityNameSet = new HashSet<>();
        Set<String> userNameSet = json.keySet();
        for (String userName : userNameSet) {
            JSONObject communityNameJson = json.getJSONObject(userName).getJSONObject("house");
            Set<String> communityNames = communityNameJson.keySet();
            communityNameSet.addAll(communityNames);
        }

        List<String> communityNameList = new ArrayList<>(communityNameSet);

        if (communityNameList == null || communityNameList.size() <= 0) {
            return null;
        }

//        获取全部小区信息
        QueryWrapper<HousePropertyCommunity> communityQueryWrapper = new QueryWrapper<>();
        communityQueryWrapper.in(HousePropertyCommunity.COMMUNITY, communityNameList);
        List<HousePropertyCommunity> housePropertyCommunities = housePropertyCommunityMapper.selectList(communityQueryWrapper);

        if (housePropertyCommunities == null || housePropertyCommunities.size() <= 0) {
            return null;
        }

//        获取小区楼栋信息
        for (HousePropertyCommunity housePropertyCommunity : housePropertyCommunities) {

            QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
            buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID, housePropertyCommunity.getId());
            List<HousePropertyBuilding> housePropertyBuildings = housePropertyBuildingMapper.selectList(buildingQueryWrapper);

            //        获取房屋信息

            for (HousePropertyBuilding housePropertyBuilding : housePropertyBuildings) {

                QueryWrapper<HouseAsset> houseAssetQueryWrapper = new QueryWrapper<>();
                houseAssetQueryWrapper.eq(HouseAsset.BUILDING_ID, housePropertyBuilding.getId());

                List<HouseAsset> houseAssetList = houseAssetMapper.selectList(houseAssetQueryWrapper);
                addAssetId(json, housePropertyCommunity.getCommunity(), housePropertyBuilding.getCode(), houseAssetList);

            }

        }


        return json;
    }

    @Override
    @Transactional
    public Integer addAsset(JSONObject json) {
        Set<String> userNames = json.keySet();

        Integer affect = 0;

        for (String userName : userNames) {

            JSONObject userJson = json.getJSONObject(userName);
            if (!userJson.containsKey("userId") || !userJson.containsKey("house")) {
                continue;
            }

            Long userId = userJson.getLong("userId");

            JSONObject houseJson = userJson.getJSONObject("house");

            Set<String> communityKeys = houseJson.keySet();

            for (String communityName : communityKeys) {

                JSONObject buildJson = houseJson.getJSONObject(communityName);

                Set<String> buildingCodes = buildJson.keySet();

                for (String buildingCode : buildingCodes) {

                    JSONArray assetJsonArray = buildJson.getJSONArray(buildingCode);

                    if (assetJsonArray != null && assetJsonArray.size() > 0) {

                        for (int i = 0; i < assetJsonArray.size(); i++) {

                            JSONObject assetJson = assetJsonArray.getJSONObject(i);

                            if (assetJson.containsKey("assetId")) {
                                HouseUserAsset houseUserAsset = new HouseUserAsset();
                                houseUserAsset.setUserId(userId);
                                houseUserAsset.setAssetId(assetJson.getLong("assetId"));
                                affect+=houseAssetService.addAsset(houseUserAsset);
                            }

                        }

                    }

                }


            }


        }

        return affect;
    }

    @Override
    @Transactional
    public List<HouseAssetMatchLog> addSameFloorExchange(JSONObject json) {
        Set<String> userNames = json.keySet();


        List<HouseAssetMatchLog> result= new ArrayList<>();

        for (String userName : userNames) {

            JSONObject userJson = json.getJSONObject(userName);
            if (!userJson.containsKey("userId") || !userJson.containsKey("house")) {
                continue;
            }

            Long userId = userJson.getLong("userId");

            JSONObject houseJson = userJson.getJSONObject("house");

            Set<String> communityKeys = houseJson.keySet();

            for (String communityName : communityKeys) {

                List<HouseAssetMatchLog> item =  houseAssetExchangeRequestService.addSameFloorExchangeRequest(communityName,userId);
                if (item!=null&&item.size()>0){
                    result.addAll(item);
                }

            }


        }
        return result;
    }


    private void addAssetId(JSONObject json, String communityName, String buildingCode, List<HouseAsset> houseAssetList) {

        Set<String> userNames = json.keySet();

        for (String userName : userNames) {

            JSONObject houseJson = json.getJSONObject(userName).getJSONObject("house");

            if (houseJson.containsKey(communityName)) {

                JSONObject communityJson = houseJson.getJSONObject(communityName);

                if (communityJson.containsKey(buildingCode)) {

                    JSONArray buildingJsonArray = communityJson.getJSONArray(buildingCode);

                    for (HouseAsset houseAsset : houseAssetList) {

                        for (int i = 0; i < buildingJsonArray.size(); i++) {

                            JSONObject assetJson = buildingJsonArray.getJSONObject(i);
                            if (houseAsset.getHouseNumber().equals(assetJson.getString("houseNumber"))) {
                                assetJson.put("assetId", houseAsset.getId());
                            }

                        }

                    }

                }


            }

        }

    }


}
