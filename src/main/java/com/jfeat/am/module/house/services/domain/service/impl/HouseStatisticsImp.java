package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.domain.service.HouseStatistics;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
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

import javax.annotation.Resource;
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

//    每天凌晨四点启动 更新数据
//    @Scheduled(cron = "0 0 4 * * ?")
    public Tip getHouseOverStatistics() {

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
            if (statisticsField==null){
                StatisticsField statisticsFieldRow = new StatisticsField();
                statisticsFieldRow.setField(communityRecord.getCommunity());
                statisticsFieldRow.setGroupName("小区数据统计");
                statisticsFieldRow.setName(communityRecord.getCommunity());
                statisticsFieldRow.setPattern("Count");
                statisticsFieldRow.setChart("".concat(communityRecord.getCommunity()).concat("数据表统计"));
                statisticsFieldMapper.insert(statisticsFieldRow);
            }

            //list 0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数
            List<Integer> houseStatistics =   houseStatisticsDao.communityStatistics(communityId);

//            添加记录信息
            modifyStatisticsRecord(communityRecord.getCommunity(),"houseNumber",String.valueOf(houseStatistics.get(0)),1);
            modifyStatisticsRecord(communityRecord.getCommunity(),"invalidHouse",String.valueOf(houseStatistics.get(1)),2);
            modifyStatisticsRecord(communityRecord.getCommunity(),"bungalow",String.valueOf(houseStatistics.get(2)),3);
            modifyStatisticsRecord(communityRecord.getCommunity(),"multipleNumber",String.valueOf(houseStatistics.get(3)),4);
            modifyStatisticsRecord(communityRecord.getCommunity(),"buildingNumber",String.valueOf(houseStatistics.get(4)),5);
            modifyStatisticsRecord(communityRecord.getCommunity(),"unitNumber",String.valueOf(houseStatistics.get(5)),6);
            modifyStatisticsRecord(communityRecord.getCommunity(),"houseTypeNumber",String.valueOf(houseStatistics.get(6)),7);
            modifyStatisticsRecord(communityRecord.getCommunity(),"houseExchangeNumber",String.valueOf(houseStatistics.get(7)),8);
            modifyStatisticsRecord(communityRecord.getCommunity(),"houseExchangeMatchNumber",String.valueOf(houseStatistics.get(8)),9);
            modifyStatisticsRecord(communityRecord.getCommunity(),"parkingNumber",String.valueOf(houseStatistics.get(9)),10);
            System.out.println("方法执行完成=========================================");
        }

        return SuccessTip.create();
    }

    private int modifyStatisticsRecord(String field,String recordName,String recodeValue,Integer seq){

        QueryWrapper<StatisticsRecord> statisticsRecordQueryWrapper  = new QueryWrapper<>();
        statisticsRecordQueryWrapper.eq(StatisticsRecord.FIELD,field).eq(StatisticsRecord.RECORD_NAME,recordName);
        StatisticsRecord statisticsRecord =  statisticsRecordMapper.selectOne(statisticsRecordQueryWrapper);
        if (statisticsRecord==null){
            StatisticsRecord record = new StatisticsRecord();
            record.setField(field);
            record.setRecordName(recordName);
            record.setRecordValue(recodeValue);
            record.setSeq(seq);
            return statisticsRecordMapper.insert(record);
        }else {
            statisticsRecord.setSeq(seq);
            statisticsRecord.setRecordValue(recodeValue);
            return statisticsRecordMapper.updateById(statisticsRecord);
        }
    }

}
