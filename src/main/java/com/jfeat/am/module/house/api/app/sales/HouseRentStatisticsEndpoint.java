package com.jfeat.am.module.house.api.app.sales;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/house/sales/houseRentStatistics")
public class HouseRentStatisticsEndpoint {

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @GetMapping
    public Tip getRentStatistics(@RequestParam(value = "rentStatus",required = false) Integer rentStatus){

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setRentStatus(rentStatus);

        List<HouseRentAssetRecord> houseRentAssetPageDetails = queryHouseRentAssetDao.findHouseRentAssetPageDetails(null, record, null, null, null, null, null);

        Map<String,Map<String,Integer>> map  = new HashMap<>();
        map.put("rentType",new HashMap<>());
        map.put("community",new HashMap<>());
        map.put("houseType",new HashMap<>());
        map.put("toward",new HashMap<>());

        if (houseRentAssetPageDetails!=null&&houseRentAssetPageDetails.size()>0){

            for (HouseRentAssetRecord rentAssetRecord:houseRentAssetPageDetails){
//            类型
                if (rentAssetRecord.getConfigurationStatus()!=null){
                    Map<String, Integer> rentType = map.get("rentType");

                    if (rentAssetRecord.getConfigurationStatus()){
                        if (rentType.containsKey("拎包入住")){
                            rentType.put("拎包入住",rentType.get("拎包入住")+1);
                        }else {
                            rentType.put("拎包入住",1);
                        }
                    }else {
                        if (rentType.containsKey("吉屋")){
                            rentType.put("吉屋",rentType.get("吉屋")+1);
                        }else {
                            rentType.put("吉屋",1);
                        }
                    }
                }

//            小区
                if (rentAssetRecord.getCommunityName()!=null){

                    Map<String, Integer> community = map.get("community");

                    if (community.containsKey(rentAssetRecord.getCommunityName())){
                        community.put(rentAssetRecord.getCommunityName(),community.get(rentAssetRecord.getCommunityName())+1);
                    }else {
                        community.put(rentAssetRecord.getCommunityName(),1);
                    }

                }

//            户型

                if (rentAssetRecord.getHouseTypeDescription()!=null){

                    Map<String, Integer> houseType = map.get("houseType");

                    if (houseType.containsKey(rentAssetRecord.getHouseTypeDescription())){
                        houseType.put(rentAssetRecord.getHouseTypeDescription(),houseType.get(rentAssetRecord.getHouseTypeDescription())+1);
                    }else {
                        houseType.put(rentAssetRecord.getHouseTypeDescription(),1);
                    }

                }

//            朝向
                if (rentAssetRecord.getToward()!=null){

                    Map<String, Integer> toward = map.get("toward");

                    if (toward.containsKey(rentAssetRecord.getToward())){
                        toward.put(rentAssetRecord.getToward(),toward.get(rentAssetRecord.getToward())+1);
                    }else {
                        toward.put(rentAssetRecord.getToward(),1);
                    }

                }
            }

        }

        JSONObject result = new JSONObject();

        for (String typeKey: map.keySet()){

            if (map.get(typeKey)!=null){

                JSONArray jsonArray = new JSONArray();

                for (String key:map.get(typeKey).keySet()){

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name",key);
                    jsonObject.put("num",map.get(typeKey).get(key));
                    jsonArray.add(jsonObject);
                }
                result.put(typeKey,jsonArray);
            }


        }


        return SuccessTip.create(result);
    }
}
