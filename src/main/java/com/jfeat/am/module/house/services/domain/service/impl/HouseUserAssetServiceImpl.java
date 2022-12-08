package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogHistoryDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserAssetServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserAssetService")
public class HouseUserAssetServiceImpl extends CRUDHouseUserAssetServiceImpl implements HouseUserAssetService {

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;



    @Resource
    HouseAssetComplaintMapper houseAssetComplaintMapper;

    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;


    @Resource
    HouseUserAssetHistoryMapper houseUserAssetHistoryMapper;

    @Resource
    QueryHouseAssetMatchLogHistoryDao queryHouseAssetMatchLogHistoryDao;


    @Override
    protected String entityName() {
        return "HouseUserAsset";
    }


    @Override
    public JSONObject parseMatchAssetData(List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecordList) {

//        将交换请求分类
        //        {"assetId":{"buildingId":{"unitId":["交换请求对象"]}}}

        JSONObject resultJson = new JSONObject();
        for (int i = 0; i < houseAssetExchangeRequestRecordList.size(); i++) {
            JSONObject jsonObject = new JSONObject();

            List<HouseAsset> targetList = houseAssetExchangeRequestRecordList.get(i).getTargetAssetList();
            if (targetList==null || targetList.size()<=0){
                continue;
            }


            for (HouseAsset matchHouseAsset : targetList) {

                if (matchHouseAsset != null) {
//                    查询是否存在楼栋
                    if (jsonObject.containsKey(matchHouseAsset.getBuildingCode())){
                        JSONObject buildingJson = (JSONObject) jsonObject.get(matchHouseAsset.getBuildingCode());
                        //                    查找是否存在单元
                        if (buildingJson.containsKey(matchHouseAsset.getUnitId().toString())){
                            JSONArray unit = (JSONArray) buildingJson.get(matchHouseAsset.getUnitId().toString());

//                            单元
                            unit.add(matchHouseAsset.getHouseNumber());
                        }else {
                            JSONArray unit = new JSONArray();
                            unit.add(matchHouseAsset.getHouseNumber());
                            buildingJson.put(matchHouseAsset.getUnitId().toString(),unit);
                        }
                    }else {
                        JSONObject buildingJson = new JSONObject();
                        JSONArray unit = new JSONArray();
                        unit.add(matchHouseAsset.getHouseNumber());
                        buildingJson.put(matchHouseAsset.getUnitId().toString(),unit);
                        jsonObject.put(matchHouseAsset.getBuildingCode(),buildingJson);
                    }

                }
            }
            resultJson.put(houseAssetExchangeRequestRecordList.get(i).getAssetId().toString(),jsonObject);
        }
        return resultJson;
    }

    public JSONArray formatAssetMatchResult(JSONObject jsonObject) {

        Iterator iterator = jsonObject.entrySet().iterator();
        JSONArray buildingJsonArray = new JSONArray();
        JSONObject resultJson = new JSONObject();
        while (iterator.hasNext()) {

            Map.Entry entry = (Map.Entry) iterator.next();
            JSONObject buildings = (JSONObject) entry.getValue();

            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(Long.parseLong((String) entry.getKey()));
            if (houseAssetModel==null){
                continue;
            }

//            查询匹配记录
            List<Integer> ids = new ArrayList<>();
            HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
            record.setOwnerUserId(JWTKit.getUserId());
            record.setOwnerAssetId(Long.parseLong((String) entry.getKey()));
            List<HouseAssetMatchLogRecord> houseAssetMatchLogPage = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(null, record, null, null, null, null, null);
            for (HouseAssetMatchLogRecord houseAssetMatchLogRecord:houseAssetMatchLogPage){
                HouseAssetModel matchHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogRecord.getMathchedAssetId());
                if (matchHouseAsset!=null){
                    ids.add(Integer.valueOf(matchHouseAsset.getHouseNumber()));
                }
            }

            Iterator buildingIterator = buildings.entrySet().iterator();

            while (buildingIterator.hasNext()) {

                Map.Entry uniteEntry = (Map.Entry) buildingIterator.next();

                JSONObject roomNumber = (JSONObject) uniteEntry.getValue();

                Iterator unitIterator = roomNumber.entrySet().iterator();
                while (unitIterator.hasNext()) {

                    Map.Entry roomEntry = (Map.Entry) unitIterator.next();
                    JSONArray jsonArray = (JSONArray) roomEntry.getValue();
                    List<Integer> room = jsonArray.toJavaList(Integer.class);
                    Collections.sort(room);
                    if (room.size() == 1) {
                        JSONObject item = new JSONObject();
                        item.put("buildingCode",houseAssetModel.getBuildingCode());
                        item.put("assetId",houseAssetModel.getId());
                        item.put("owner",houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                        item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(0))));
                        if (ids.contains(room.get(0))) {
                            item.put("contains", true);
                        }else {
                            item.put("contains", false);
                        }
                        item.put("end", "");
                        buildingJsonArray.add(item);
                    } else if (room.size() > 1) {
                        int count = 0;
                        int index = 0;

                        for (int i = 1; i < room.size(); i++) {
                            if (!room.get(i).equals(room.get(i - 1) + 100)) {
                                if (count >= 1) {
                                    JSONObject item = new JSONObject();
                                    item.put("buildingCode",houseAssetModel.getBuildingCode());
                                    item.put("assetId",houseAssetModel.getId());
                                    item.put("owner",houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                    item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                    item.put("end", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(i - 1))));
                                    List<Integer> tempList = room.subList(index, room.size());
                                    List<Integer> accountIdList = tempList.stream().filter(ids::contains).collect(Collectors.toList());
                                    if (accountIdList.size() > 0) {
                                        item.put("contains", true);
                                    }else {
                                        item.put("contains", false);
                                    }
                                    buildingJsonArray.add(item);
                                    index = i;
                                } else {
                                    JSONObject item = new JSONObject();
                                    item.put("buildingCode",houseAssetModel.getBuildingCode());
                                    item.put("assetId",houseAssetModel.getId());
                                    item.put("owner",houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                    item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                    item.put("end", "");
                                    if (ids.contains(room.get(0))) {
                                        item.put("contains", true);
                                    }else {
                                        item.put("contains", false);
                                    }
                                    buildingJsonArray.add(item);
                                    index = i;
                                }
                                count = 0;
                                continue;
                            }
                            count++;
                        }
                        if (index < room.size()) {
                            if (index >= 0 && index != room.size() - 1) {
                                JSONObject item = new JSONObject();
                                item.put("buildingCode",houseAssetModel.getBuildingCode());
                                item.put("assetId",houseAssetModel.getId());
                                item.put("owner",houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                item.put("end", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(room.size() - 1))));
                                List<Integer> tempList = room.subList(index, room.size());
                                List<Integer> accountIdList = tempList.stream().filter(ids::contains).collect(Collectors.toList());
                                if (accountIdList.size() > 0) {
                                    item.put("contains", true);
                                }else {
                                    item.put("contains", false);
                                }
                                buildingJsonArray.add(item);
                            } else {
                                JSONObject item = new JSONObject();
                                item.put("buildingCode",houseAssetModel.getBuildingCode());
                                item.put("assetId",houseAssetModel.getId());
                                item.put("owner",houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                if (ids.contains(room.get(0))) {
                                    item.put("contains", true);
                                }else {
                                    item.put("contains", false);
                                }
                                item.put("end", "");
                                buildingJsonArray.add(item);
                            }

                        }

                    }

                }

            }
        }
        return buildingJsonArray;
    }

    /**
     *
     * @param userId 用户id
     * @param assetId 房屋id
     * @return 删除记录数
     */
    @Transactional
    public int deleteUserAsset(Long userId,Long assetId){

        Integer effect = 0;
//        删除资产表记录
        QueryWrapper<HouseUserAsset> userAssetQueryWrapper = new QueryWrapper<>();
        userAssetQueryWrapper.eq(HouseUserAsset.USER_ID,userId).eq(HouseUserAsset.ASSET_ID,assetId);

        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(userAssetQueryWrapper);
        if (houseUserAsset!=null){
            JSONObject jsonObject = (JSONObject) JSON.toJSON(houseUserAsset);
            HouseUserAssetHistory houseUserAssetHistory = JSON.parseObject(jsonObject.toString(),HouseUserAssetHistory.class);
            houseUserAssetHistory.setId(null);
            houseUserAssetHistory.setDeleteTime(new Date());
            houseUserAssetHistoryMapper.insert(houseUserAssetHistory);
        }

        effect += houseUserAssetMapper.delete(userAssetQueryWrapper);



//        删除用户申诉
        QueryWrapper<HouseAssetComplaint> houseAssetComplaintQueryWrapper = new QueryWrapper<>();
        houseAssetComplaintQueryWrapper.eq(HouseAssetComplaint.USER_ID,userId).eq(HouseAssetComplaint.ASSET_ID,assetId);
        effect += houseAssetComplaintMapper.delete(houseAssetComplaintQueryWrapper);

//        删除交换请求
        QueryWrapper<HouseAssetExchangeRequest> houseAssetExchangeRequestQueryWrapper = new QueryWrapper<>();
        houseAssetExchangeRequestQueryWrapper.eq(HouseAssetExchangeRequest.USER_ID,userId).eq(HouseAssetExchangeRequest.ASSET_ID,assetId);
        effect += houseAssetExchangeRequestMapper.delete(houseAssetExchangeRequestQueryWrapper);


        List<HouseAssetMatchLogHistory> houseAssetMatchLogHistories = new ArrayList<>();

//        删除匹配成功记录
        QueryWrapper<HouseAssetMatchLog> houseAssetMatchLogQueryWrapper = new QueryWrapper<>();
        houseAssetMatchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID,userId).eq(HouseAssetMatchLog.OWNER_ASSET_ID,assetId);

        List<HouseAssetMatchLog> houseAssetMatchLogList = houseAssetMatchLogMapper.selectList(houseAssetMatchLogQueryWrapper);
        if (houseAssetMatchLogList!=null&&houseAssetMatchLogList.size()>0){
           for (HouseAssetMatchLog matchLog:houseAssetMatchLogList){
               JSONObject jsonObject = (JSONObject) JSON.toJSON(matchLog);
               HouseAssetMatchLogHistory houseAssetMatchLogHistory = JSON.parseObject(jsonObject.toString(),HouseAssetMatchLogHistory.class);
               houseAssetMatchLogHistory.setId(null);
               houseAssetMatchLogHistory.setDeleteTime(new Date());
               houseAssetMatchLogHistories.add(houseAssetMatchLogHistory);
           }
        }



//        删除别人匹配自己成功记录
        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.MATCHED_USER_ID,userId).eq(HouseAssetMatchLog.MATHCHED_ASSET_ID,assetId);

        List<HouseAssetMatchLog> houseAssetMatchLogs = houseAssetMatchLogMapper.selectList(houseAssetMatchLogQueryWrapper);
        if (houseAssetMatchLogs!=null&&houseAssetMatchLogs.size()>0){
            for (HouseAssetMatchLog matchLog:houseAssetMatchLogs){
                JSONObject jsonObject = (JSONObject) JSON.toJSON(matchLog);
                HouseAssetMatchLogHistory houseAssetMatchLogHistory = JSON.parseObject(jsonObject.toString(),HouseAssetMatchLogHistory.class);
                houseAssetMatchLogHistory.setId(null);
                houseAssetMatchLogHistory.setDeleteTime(new Date());
                houseAssetMatchLogHistories.add(houseAssetMatchLogHistory);
            }
        }

        if (houseAssetMatchLogHistories!=null && houseAssetMatchLogs.size()>0){
            queryHouseAssetMatchLogHistoryDao.batchAddHouseAssetMatchLogHistory(houseAssetMatchLogHistories);
        }

        effect += houseAssetMatchLogMapper.delete(houseAssetMatchLogQueryWrapper);
        effect += houseAssetMatchLogMapper.delete(matchLogQueryWrapper);

//        删除出租房子
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.LANDLORD_ID,userId).eq(HouseRentAsset.ASSET_ID,assetId);
        effect += houseRentAssetMapper.delete(houseRentAssetQueryWrapper);
        return effect;
    }
}
