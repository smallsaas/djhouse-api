package com.jfeat.am.module.house.services.utility;

import com.jfeat.am.module.house.services.domain.dao.HouseStatisticsDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class HouseStatisticsTools {


    @Resource
    HouseStatisticsDao houseStatisticsDao;


    public List<Map<String,Object>> getCommunitStatistics(Long communityId){
//        0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数,10房东数 11-供应商数
        List<String> statisticsFiled = Arrays.asList("houseNumber","invalidHouse","bungalow","multipleNumber","buildingNumber","unitNumber","houseTypeNumber","houseExchangeNumber","houseExchangeMatchNumber","parkingNumber"
        ,"landlord","supplier");
        List<Map<String,Object>> result = new ArrayList<>();
        List<Integer> houseStatistics =   houseStatisticsDao.communityStatistics(communityId,null);
        if (houseStatistics.size()<=statisticsFiled.size()){
            for (int i=0;i<houseStatistics.size();i++){
                Map<String,Object> map = new HashMap<>();
                map.put(statisticsFiled.get(i),houseStatistics.get(i));
                result.add(map);
            }
        }
        return result;
    }
}
