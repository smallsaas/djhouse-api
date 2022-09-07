package com.jfeat.am.module.house.api.app.administrators;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.definition.Develops;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.core.util.RedisKit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/house/administrators/develops")
public class DevelopsManageEndpoint {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    JdbcTemplate jdbcTemplate;

    @GetMapping
    public Tip getDevelopsStatus(){

        Develops develops = new Develops();
        if (RedisKit.isSanity()){
            if (stringRedisTemplate.hasKey(Develops.REDIS_KEY_TENANT_DATA)){
                String tenant =  stringRedisTemplate.opsForValue().get(Develops.REDIS_KEY_TENANT_DATA);
                develops.setTenantData(Boolean.parseBoolean(tenant));
            }
            if (stringRedisTemplate.hasKey(Develops.REDIS_KEY_USER_DATA)){
                String userData =  stringRedisTemplate.opsForValue().get(Develops.REDIS_KEY_USER_DATA);
                develops.setUserData(Boolean.parseBoolean(userData));
            }
            if (stringRedisTemplate.hasKey(Develops.REDIS_KEY_CONFIGURATION_DATA)){
                String configData =  stringRedisTemplate.opsForValue().get(Develops.REDIS_KEY_CONFIGURATION_DATA);
                develops.setConfigurationData(Boolean.parseBoolean(configData));
            }

        }
        String sql = "select version()";
        String version = jdbcTemplate.queryForObject(sql,String.class);
        develops.setDataBaseVersion(version);
        return SuccessTip.create(develops);

    }

    @PostMapping
    public Tip setDevelopsStatus(@RequestBody Develops entity){
        Integer affect = 0;
        if (entity.getTenantData()!=null){
            affect+=1;
            stringRedisTemplate.opsForValue().set(Develops.REDIS_KEY_TENANT_DATA,String.valueOf(entity.getTenantData()));
        }
        if (entity.getUserData()!=null){
            affect+=1;
            stringRedisTemplate.opsForValue().set(Develops.REDIS_KEY_USER_DATA,String.valueOf(entity.getUserData()));
        }
        if (entity.getConfigurationData()!=null){
            affect+=1;
            stringRedisTemplate.opsForValue().set(Develops.REDIS_KEY_CONFIGURATION_DATA,String.valueOf(entity.getConfigurationData()));
        }
        return SuccessTip.create(affect);
    }
}
