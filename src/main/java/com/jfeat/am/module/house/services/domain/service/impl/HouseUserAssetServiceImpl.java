package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogHistoryDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserAssetServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.beans.Transient;
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

    @Resource
    QueryHouseUserAssetDao houseUserAssetDao;


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
            if (targetList == null || targetList.size() <= 0) {
                continue;
            }


            for (HouseAsset matchHouseAsset : targetList) {

                if (matchHouseAsset != null) {
//                    查询是否存在楼栋
                    if (jsonObject.containsKey(matchHouseAsset.getBuildingCode())) {
                        JSONObject buildingJson = (JSONObject) jsonObject.get(matchHouseAsset.getBuildingCode());
                        //                    查找是否存在单元
                        if (buildingJson.containsKey(matchHouseAsset.getUnitId().toString())) {
                            JSONArray unit = (JSONArray) buildingJson.get(matchHouseAsset.getUnitId().toString());

//                            单元
                            unit.add(matchHouseAsset.getHouseNumber());
                        } else {
                            JSONArray unit = new JSONArray();
                            unit.add(matchHouseAsset.getHouseNumber());
                            buildingJson.put(matchHouseAsset.getUnitId().toString(), unit);
                        }
                    } else {
                        JSONObject buildingJson = new JSONObject();
                        JSONArray unit = new JSONArray();
                        unit.add(matchHouseAsset.getHouseNumber());
                        buildingJson.put(matchHouseAsset.getUnitId().toString(), unit);
                        jsonObject.put(matchHouseAsset.getBuildingCode(), buildingJson);
                    }

                }
            }
            resultJson.put(houseAssetExchangeRequestRecordList.get(i).getAssetId().toString(), jsonObject);
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
            if (houseAssetModel == null) {
                continue;
            }

//            查询匹配记录
            List<Integer> ids = new ArrayList<>();
            HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
            record.setOwnerUserId(JWTKit.getUserId());
            record.setOwnerAssetId(Long.parseLong((String) entry.getKey()));
            List<HouseAssetMatchLogRecord> houseAssetMatchLogPage = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(null, record, null, null, null, null, null);
            for (HouseAssetMatchLogRecord houseAssetMatchLogRecord : houseAssetMatchLogPage) {
                HouseAssetModel matchHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogRecord.getMathchedAssetId());
                if (matchHouseAsset != null) {
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
                        item.put("buildingCode", houseAssetModel.getBuildingCode());
                        item.put("assetId", houseAssetModel.getId());
                        item.put("owner", houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                        item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(0))));
                        if (ids.contains(room.get(0))) {
                            item.put("contains", true);
                        } else {
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
                                    item.put("buildingCode", houseAssetModel.getBuildingCode());
                                    item.put("assetId", houseAssetModel.getId());
                                    item.put("owner", houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                    item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                    item.put("end", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(i - 1))));
                                    List<Integer> tempList = room.subList(index, room.size());
                                    List<Integer> accountIdList = tempList.stream().filter(ids::contains).collect(Collectors.toList());
                                    if (accountIdList.size() > 0) {
                                        item.put("contains", true);
                                    } else {
                                        item.put("contains", false);
                                    }
                                    buildingJsonArray.add(item);
                                    index = i;
                                } else {
                                    JSONObject item = new JSONObject();
                                    item.put("buildingCode", houseAssetModel.getBuildingCode());
                                    item.put("assetId", houseAssetModel.getId());
                                    item.put("owner", houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                    item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                    item.put("end", "");
                                    if (ids.contains(room.get(0))) {
                                        item.put("contains", true);
                                    } else {
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
                                item.put("buildingCode", houseAssetModel.getBuildingCode());
                                item.put("assetId", houseAssetModel.getId());
                                item.put("owner", houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                item.put("end", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(room.size() - 1))));
                                List<Integer> tempList = room.subList(index, room.size());
                                List<Integer> accountIdList = tempList.stream().filter(ids::contains).collect(Collectors.toList());
                                if (accountIdList.size() > 0) {
                                    item.put("contains", true);
                                } else {
                                    item.put("contains", false);
                                }
                                buildingJsonArray.add(item);
                            } else {
                                JSONObject item = new JSONObject();
                                item.put("buildingCode", houseAssetModel.getBuildingCode());
                                item.put("assetId", houseAssetModel.getId());
                                item.put("owner", houseAssetModel.getBuildingCode().concat("-").concat(houseAssetModel.getHouseNumber()));
                                item.put("start", "".concat((String) uniteEntry.getKey()).concat("-").concat(String.valueOf(room.get(index))));
                                if (ids.contains(room.get(0))) {
                                    item.put("contains", true);
                                } else {
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
     * @param userId  用户id
     * @param assetId 房屋id
     * @return 删除记录数
     */
    @Transactional
    public int deleteUserAsset(Long userId, Long assetId) {

        Integer effect = 0;
//        删除资产表记录
        QueryWrapper<HouseUserAsset> userAssetQueryWrapper = new QueryWrapper<>();
        userAssetQueryWrapper.eq(HouseUserAsset.USER_ID, userId).eq(HouseUserAsset.ASSET_ID, assetId);

        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(userAssetQueryWrapper);
        if (houseUserAsset != null) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(houseUserAsset);
            HouseUserAssetHistory houseUserAssetHistory = JSON.parseObject(jsonObject.toString(), HouseUserAssetHistory.class);
            houseUserAssetHistory.setId(null);
            houseUserAssetHistory.setDeleteTime(new Date());
            houseUserAssetHistoryMapper.insert(houseUserAssetHistory);
        }

        effect += houseUserAssetMapper.delete(userAssetQueryWrapper);


//        删除用户申诉
        QueryWrapper<HouseAssetComplaint> houseAssetComplaintQueryWrapper = new QueryWrapper<>();
        houseAssetComplaintQueryWrapper.eq(HouseAssetComplaint.USER_ID, userId).eq(HouseAssetComplaint.ASSET_ID, assetId);
        effect += houseAssetComplaintMapper.delete(houseAssetComplaintQueryWrapper);

//        删除交换请求
        QueryWrapper<HouseAssetExchangeRequest> houseAssetExchangeRequestQueryWrapper = new QueryWrapper<>();
        houseAssetExchangeRequestQueryWrapper.eq(HouseAssetExchangeRequest.USER_ID, userId).eq(HouseAssetExchangeRequest.ASSET_ID, assetId);
        effect += houseAssetExchangeRequestMapper.delete(houseAssetExchangeRequestQueryWrapper);


        List<HouseAssetMatchLogHistory> houseAssetMatchLogHistories = new ArrayList<>();

//        删除匹配成功记录
        QueryWrapper<HouseAssetMatchLog> houseAssetMatchLogQueryWrapper = new QueryWrapper<>();
        houseAssetMatchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID, userId).eq(HouseAssetMatchLog.OWNER_ASSET_ID, assetId);

        List<HouseAssetMatchLog> houseAssetMatchLogList = houseAssetMatchLogMapper.selectList(houseAssetMatchLogQueryWrapper);
        if (houseAssetMatchLogList != null && houseAssetMatchLogList.size() > 0) {
            for (HouseAssetMatchLog matchLog : houseAssetMatchLogList) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(matchLog);
                HouseAssetMatchLogHistory houseAssetMatchLogHistory = JSON.parseObject(jsonObject.toString(), HouseAssetMatchLogHistory.class);
                houseAssetMatchLogHistory.setId(null);
                houseAssetMatchLogHistory.setDeleteTime(new Date());
                houseAssetMatchLogHistories.add(houseAssetMatchLogHistory);
            }
        }


//        删除别人匹配自己成功记录
        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.MATCHED_USER_ID, userId).eq(HouseAssetMatchLog.MATHCHED_ASSET_ID, assetId);

        List<HouseAssetMatchLog> houseAssetMatchLogs = houseAssetMatchLogMapper.selectList(houseAssetMatchLogQueryWrapper);
        if (houseAssetMatchLogs != null && houseAssetMatchLogs.size() > 0) {
            for (HouseAssetMatchLog matchLog : houseAssetMatchLogs) {
                JSONObject jsonObject = (JSONObject) JSON.toJSON(matchLog);
                HouseAssetMatchLogHistory houseAssetMatchLogHistory = JSON.parseObject(jsonObject.toString(), HouseAssetMatchLogHistory.class);
                houseAssetMatchLogHistory.setId(null);
                houseAssetMatchLogHistory.setDeleteTime(new Date());
                houseAssetMatchLogHistories.add(houseAssetMatchLogHistory);
            }
        }

        if (houseAssetMatchLogHistories != null && houseAssetMatchLogs.size() > 0) {
            queryHouseAssetMatchLogHistoryDao.batchAddHouseAssetMatchLogHistory(houseAssetMatchLogHistories);
        }

        effect += houseAssetMatchLogMapper.delete(houseAssetMatchLogQueryWrapper);
        effect += houseAssetMatchLogMapper.delete(matchLogQueryWrapper);

//        删除出租房子
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.LANDLORD_ID, userId).eq(HouseRentAsset.ASSET_ID, assetId);
        effect += houseRentAssetMapper.delete(houseRentAssetQueryWrapper);
        return effect;
    }

    @Override
    public void setUserAssetArea(List<HouseUserAssetRecord> recordList) {

        if (recordList != null && recordList.size() > 0) {

            for (HouseUserAssetRecord record : recordList) {

                if (record.getAssetFlag() != null && record.getAssetFlag().equals(HouseAsset.ASSET_FLAG_MULTIPLE)) {
                    if (record.getMultiArea() != null) {
                        record.setUnitArea(record.getMultiArea());

                    }

                    if (record.getMultiRealArea() != null) {
                        record.setRealArea(record.getMultiRealArea());
                    }
                }

            }

        }

    }

    /**
     * 设定用户"我的回迁房"的默认排序
     *
     * @return
     */
    @Transactional
    @Override
    public int setDefaultSequenceNumber(Long userId, Long communityId) {

        // 受影响的行数
        int affected = 0;

        // 1、获取该用户所有的回迁房
        List<HouseUserAsset> userAssets = houseUserAssetDao.listUserAssetByUserId(userId, communityId);
        // 2、根据列表的下标进行排序，设定sequence_num字段
        for (int i = 0; i < userAssets.size(); i++) {
            HouseUserAsset userAsset = userAssets.get(i);
            // 封装所需要更新的数据
            HouseUserAsset param = new HouseUserAsset();
            param.setId(userAsset.getId());
            param.setSequenceNum(i);
            affected += houseUserAssetDao.updateById(param);
        }
        // 3、设定成功返回条目数
        return affected;
    }

    /**
     * 变更"我的回迁房"的排序
     *
     * @param userId      用户id
     * @param communityId 用户社区id
     * @param id          要移动的房产记录的id
     * @param direction   上移/下移 1=上移，0=下移
     * @return
     */
    @Transactional
    @Override
    public List<HouseUserAssetRecord> updateMyHouseSequence(Long userId, Long communityId, Long id, Integer direction) {

        final Integer UP = 1; // 上升
        final Integer DOWN = 0; // 下降

        // direction只允许1和0两个参数
        if (direction != 1 && direction != 0) {
            logger.error("参数direction超出范围，只能为1或者0，目前的值是：" + direction);
            throw new BusinessException(BusinessCode.BadRequest, "参数有误");
        }

        // 因为这是新加的功能，以前的sequence_num数据为空，为了兼容性，每次都进行重新赋值sequence_num
        setDefaultSequenceNumber(userId, communityId);

        // 要移动的房子
        HouseUserAsset moveHouse = new HouseUserAsset();
        moveHouse.setId(id);
        // 被移动的房子
        HouseUserAsset beForcedMoveHouse = new HouseUserAsset();

        // 获取该用户所有的回迁房
        List<HouseUserAsset> userAssets = houseUserAssetDao.listUserAssetByUserId(userId, communityId);
        // 受影响行数
        int affected = 0;
        // 获取要移动的房子的排序号
        for (int i = 0; i < userAssets.size(); i++) {
            if (id.equals(userAssets.get(i).getId())) {
                // 并且判断是上移还是下移，然后获取对应的需要被迫移动的房产
                if (direction == UP) {
                    // 判断移动的id是否是第一个，如果是，则不允许上移
                    if (userAssets.get(i).getSequenceNum() == 0) {
                        logger.error("不允许上移，移动的房产id：" + id + "排序号：" + userAssets.get(i).getSequenceNum());
                        throw new BusinessException(BusinessCode.CodeBase);
                    }
                    // 获取被移动的房产id
                    beForcedMoveHouse.setId(userAssets.get(i - 1).getId());
                    // 获取移动和被移动房产的新排序号
                    moveHouse.setSequenceNum(userAssets.get(i).getSequenceNum() - 1);
                    beForcedMoveHouse.setSequenceNum(userAssets.get(i - 1).getSequenceNum() + 1);
                } else if (direction == DOWN) {
                    // 判断移动的id是否是最后一个，如果是，则不允许下移
                    if (userAssets.get(i).getSequenceNum() == userAssets.size() - 1) {
                        logger.error("不允许下移，移动的房产id：" + id + "排序号：" + userAssets.get(i).getSequenceNum());
                        throw new BusinessException(BusinessCode.CodeBase);
                    }
                    // 获取被移动的房产id
                    beForcedMoveHouse.setId(userAssets.get(i + 1).getId());
                    // 获取移动和被移动房产的新排序号
                    moveHouse.setSequenceNum(userAssets.get(i).getSequenceNum() + 1);
                    beForcedMoveHouse.setSequenceNum(userAssets.get(i + 1).getSequenceNum() - 1);
                }

                // 执行更新
                UpdateWrapper<HouseUserAsset> updateWrapper = new UpdateWrapper();
                affected += houseUserAssetDao.updateById(moveHouse);
                affected += houseUserAssetDao.updateById(beForcedMoveHouse);
                break;
            }
        }

        if (affected != 2) throw new BusinessException(BusinessCode.DatabaseUpdateError, "排序失败");
        logger.error("用户：" + userId + (direction == 1 ? "上移" : "下移") + "我的回迁房失败，排序房产的id：" + id);

        // 获取"我的回迁房"并返回
        List<HouseUserAssetRecord> myHouses = houseUserAssetDao.pageMyHouse(userId, communityId);
        return myHouses;
    }

    /**
     * 更新"我的回迁房"的水编号，电编号，燃气编号
     *
     * @param id                用户房产id
     * @param userId            用户id
     * @param waterNumber       水编号
     * @param electricityNumber 电编号
     * @param gasNumber         燃气编号
     * @return
     */
    @Transactional
    @Override
    public HouseUserAssetRecord updateMyHouseWaterElectricity(Long id, Long userId, String waterNumber, String electricityNumber, String gasNumber) {
        if (id == null) {
            logger.error("id 不能为 null");
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        }

        // 判断是否是本人修改
        HouseUserAsset originalUserAsset = houseUserAssetDao.getUserAssetById(id);
        if (originalUserAsset == null) {
            logger.error("没有找到该用户房产信息：用户房产id=" + id);
            throw new BusinessException(BusinessCode.NoPermission);
        } else if (!(userId.equals(originalUserAsset.getUserId()))) {
            logger.error("没有权限修改该房屋的水电编号: 用户房产id=" + id + ", 该房产所属用户id:" + originalUserAsset.getUserId() + ", 请求用户id=" + userId);
            throw new BusinessException(BusinessCode.NoPermission);
        }

        // 参数判断，允许参数为空串和null，如果是空串统一将参数设为null，避免数据库出现空串难以管理
        if (StringUtils.isBlank(waterNumber)) waterNumber = null;
        if (StringUtils.isBlank(electricityNumber)) electricityNumber = null;
        if (StringUtils.isBlank(gasNumber)) gasNumber = null;

        // 执行更新
        UpdateWrapper<HouseUserAsset> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("water_number", waterNumber).set("electricity_number", electricityNumber).set("gas_number", gasNumber).eq("id", id);
        int affected = houseUserAssetDao.update(new HouseUserAsset(),updateWrapper);
        if (affected == 0) {
            logger.error("数据库更新失败: id=" + id + ", waterNumber=" + waterNumber + ", electricityNumber=" + electricityNumber + ", gasNumber=" + gasNumber);
            throw new BusinessException(BusinessCode.DatabaseUpdateError);
        }

        // 获取被更新的记录
        HouseUserAssetRecord result = houseUserAssetDao.getUserAssetRecordById(id);
        if (result == null) {
            logger.error("获取更新的记录失败，id=" + id);
            throw new BusinessException(BusinessCode.CodeBase);
        }

        return result;
    }
}
