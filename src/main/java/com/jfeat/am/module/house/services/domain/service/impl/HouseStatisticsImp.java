package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.domain.service.HouseStatistics;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.statistics.services.crud.StatisticsFieldService;
import com.jfeat.am.module.statistics.services.domain.dao.QueryStatisticsFieldDao;
import com.jfeat.am.module.statistics.services.domain.dao.QueryStatisticsRecordDao;
import com.jfeat.am.module.statistics.services.persistence.dao.StatisticsFieldMapper;
import com.jfeat.am.module.statistics.services.persistence.dao.StatisticsRecordMapper;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsField;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsRecord;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("houseStatistics")
@EnableScheduling
public class HouseStatisticsImp implements HouseStatistics {

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    HouseStatisticsDao houseStatisticsDao;

    @Resource
    StatisticsRecordMapper statisticsRecordMapper;

    @Resource
    StatisticsFieldMapper statisticsFieldMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    //    每天凌晨四点启动 更新数据
    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional
    public void timingHouseOverStatistics() {

        //获取一共有多少个小区
        HousePropertyCommunityRecord housePropertyCommunityRecord = new HousePropertyCommunityRecord();
        List<HousePropertyCommunityRecord> housePropertyCommunityRecordList = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(null, housePropertyCommunityRecord, null, null, null, null, null);

        //统计每一个小区数据
        for (HousePropertyCommunityRecord communityRecord : housePropertyCommunityRecordList) {
            Long communityId = communityRecord.getId();


//            查询统计表中是否已经有改小区数据
            QueryWrapper<StatisticsField> wrapper = new QueryWrapper<>();
            wrapper.eq(StatisticsField.FIELD, communityRecord.getCommunity());
            StatisticsField statisticsField = statisticsFieldMapper.selectOne(wrapper);
//            统计表中没有该数据时 添加一条小区数据
            if (statisticsField == null) {
                StatisticsField statisticsFieldRow = new StatisticsField();
                statisticsFieldRow.setField(communityRecord.getCommunity());
                statisticsFieldRow.setGroupName("小区数据统计");
                statisticsFieldRow.setName(communityRecord.getCommunity());
                statisticsFieldRow.setPattern("Count");
                statisticsFieldRow.setChart("".concat(communityRecord.getCommunity()).concat("数据表统计"));
                statisticsFieldMapper.insert(statisticsFieldRow);
            }

            //list 0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数
            List<Integer> houseStatistics = houseStatisticsDao.communityStatistics(communityId,null);

//            添加记录信息
            modifyStatisticsRecord(communityRecord.getCommunity(), "houseNumber", String.valueOf(houseStatistics.get(0)), 1);
            modifyStatisticsRecord(communityRecord.getCommunity(), "invalidHouse", String.valueOf(houseStatistics.get(1)), 2);
            modifyStatisticsRecord(communityRecord.getCommunity(), "bungalow", String.valueOf(houseStatistics.get(2)), 3);
            modifyStatisticsRecord(communityRecord.getCommunity(), "multipleNumber", String.valueOf(houseStatistics.get(3)), 4);
            modifyStatisticsRecord(communityRecord.getCommunity(), "buildingNumber", String.valueOf(houseStatistics.get(4)), 5);
            modifyStatisticsRecord(communityRecord.getCommunity(), "unitNumber", String.valueOf(houseStatistics.get(5)), 6);
            modifyStatisticsRecord(communityRecord.getCommunity(), "houseTypeNumber", String.valueOf(houseStatistics.get(6)), 7);
            modifyStatisticsRecord(communityRecord.getCommunity(), "houseExchangeNumber", String.valueOf(houseStatistics.get(7)), 8);
            modifyStatisticsRecord(communityRecord.getCommunity(), "houseExchangeMatchNumber", String.valueOf(houseStatistics.get(8)), 9);
            modifyStatisticsRecord(communityRecord.getCommunity(), "parkingNumber", String.valueOf(houseStatistics.get(9)), 10);
            System.out.println("方法执行完成=========================================");
        }
    }


//    @Transactional
//    @Scheduled(cron = "0 0 4 * * ?")
    public void timingCommunityBuildingStatistics() {

        //获取一共有多少个小区
        HousePropertyCommunityRecord housePropertyCommunityRecord = new HousePropertyCommunityRecord();
        List<HousePropertyCommunityRecord> housePropertyCommunityRecordList = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(null, housePropertyCommunityRecord, null, null, null, null, null);
        for (HousePropertyCommunityRecord communityRecord : housePropertyCommunityRecordList) {
            //        查询小区有多少栋楼
            QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
            buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID, communityRecord.getId());
            List<HousePropertyBuilding> housePropertyBuildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);

            HousePropertyCommunity community = housePropertyCommunityMapper.selectById(communityRecord.getId());
            String communityName = community.getCommunity();


            //统计每一个楼信息
            for (HousePropertyBuilding building : housePropertyBuildingList) {
                Long buildingId = building.getId();
                String key = communityName.concat(building.getCode());

//            查询统计表中是否已经有改楼栋数据
                QueryWrapper<StatisticsField> wrapper = new QueryWrapper<>();
                wrapper.eq(StatisticsField.FIELD, key);
                StatisticsField statisticsField = statisticsFieldMapper.selectOne(wrapper);
//            统计表中没有该数据时 添加一条小区数据
                if (statisticsField == null) {
                    StatisticsField statisticsFieldRow = new StatisticsField();
                    statisticsFieldRow.setField(key);
                    statisticsFieldRow.setGroupName("小区楼栋数据统计");
                    statisticsFieldRow.setName(key);
                    statisticsFieldRow.setPattern("Count");
                    statisticsFieldRow.setChart("".concat(key).concat("数据表统计"));
                    statisticsFieldMapper.insert(statisticsFieldRow);
                }

                //list0-房子数 1-单元数 2-户型数 3-已回迁数
                List<Integer> houseStatistics = houseStatisticsDao.buildingStatistics(buildingId);

//            添加记录信息
                modifyStatisticsRecord(key, "houseNumber", String.valueOf(houseStatistics.get(0)), 1);
                modifyStatisticsRecord(key, "unitNumber", String.valueOf(houseStatistics.get(1)), 2);
                modifyStatisticsRecord(key, "houseTypeNumber", String.valueOf(houseStatistics.get(2)), 3);
                modifyStatisticsRecord(key, "moveBackNumber", String.valueOf(houseStatistics.get(3)), 4);

                System.out.println("方法执行完成=========================================");
            }
        }
    }

    @Override
    public JSONArray getCommunityBuildingStatistics(Long communityId) {
        if (communityId==null){
            return null;
        }
        JSONArray result = new JSONArray();

        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID,communityId);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);
        for (HousePropertyBuilding building:buildingList){
            JSONObject jsonObject = new JSONObject();
            //list0-房子数 1-单元数 2-户型数 3-已回迁数
            List<Integer> houseStatistics = houseStatisticsDao.buildingStatistics(building.getId());

            jsonObject.put("buildingId",building.getId());
            jsonObject.put("buildingCode",building.getCode());
            jsonObject.put("houseNumber",houseStatistics.get(0));
            jsonObject.put("unitNumber",houseStatistics.get(1));
            jsonObject.put("houseTypeNumber",houseStatistics.get(2));
            jsonObject.put("removeBackNumber",houseStatistics.get(3));
            result.add(jsonObject);
        }
        return result;
    }

    //    获取小区楼栋统计
    private int modifyStatisticsRecord(String field, String recordName, String recodeValue, Integer seq) {

        QueryWrapper<StatisticsRecord> statisticsRecordQueryWrapper = new QueryWrapper<>();
        statisticsRecordQueryWrapper.eq(StatisticsRecord.FIELD, field).eq(StatisticsRecord.RECORD_NAME, recordName);
        StatisticsRecord statisticsRecord = statisticsRecordMapper.selectOne(statisticsRecordQueryWrapper);
        if (statisticsRecord == null) {
            StatisticsRecord record = new StatisticsRecord();
            record.setField(field);
            record.setRecordName(recordName);
            record.setRecordValue(recodeValue);
            record.setSeq(seq);
            return statisticsRecordMapper.insert(record);
        } else {
            statisticsRecord.setSeq(seq);
            statisticsRecord.setRecordValue(recodeValue);
            return statisticsRecordMapper.updateById(statisticsRecord);
        }
    }

}
