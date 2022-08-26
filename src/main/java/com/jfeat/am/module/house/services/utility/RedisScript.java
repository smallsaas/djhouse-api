package com.jfeat.am.module.house.services.utility;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

@Component
public class RedisScript {

    @Resource
    StringRedisTemplate stringRedisTemplate;



    public int delRidesData(String pattern){
        Set<String> keys =  stringRedisTemplate.keys(pattern);
        System.out.println(pattern);
        System.out.println(keys.size());
        Iterator<String> keysIterator = keys.iterator();
        Integer affect = 0;
        while (keysIterator.hasNext()){
            String key = keysIterator.next();
            System.out.println(key);
            stringRedisTemplate.delete(key);
            affect+=1;
        }
        return affect;
    }

    public String getBuildingFloorsKey(Long communityId,Long buildingId,Integer floors){
        String key = "communityId".concat(String.valueOf(communityId)).concat(":").concat("buildingId").concat(String.valueOf(buildingId)).concat(":").concat("floor").concat(String.valueOf(floors));
        return key;
    }
}
