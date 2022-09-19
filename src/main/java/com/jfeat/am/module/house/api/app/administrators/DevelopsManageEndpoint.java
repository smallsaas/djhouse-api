package com.jfeat.am.module.house.api.app.administrators;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.definition.Develops;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.core.util.RedisKit;
import com.jfeat.dev.devops.services.domain.dao.QueryDevVersionDao;
import com.jfeat.dev.devops.services.domain.model.DevVersionRecord;
import com.jfeat.dev.devops.services.domain.service.DevopsServices;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/administrators/develops")
public class DevelopsManageEndpoint {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    QueryDevVersionDao queryDevVersionDao;

    @Resource
    DevopsServices devopsServices;

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

    //    获取当前app数据 运维操作
    @GetMapping("/appidOperationList")
    public Tip getAppidOperationList(@RequestParam("appid") String appid) {

        DevVersionRecord record = new DevVersionRecord();
        record.setAppid(appid);
        List<DevVersionRecord> devVersionRecordList = queryDevVersionDao.queryVersionDetail(null,record,null);
//        System.out.println(devVersionRecordList);
        if (devVersionRecordList!=null && devVersionRecordList.size()==1){
            return SuccessTip.create(devVersionRecordList.get(0));
        }
        return SuccessTip.create();
    }

    @GetMapping("/{sqlFile}")
    public Tip getResultList(@PathVariable("sqlFile") String sqlFile, HttpServletRequest request) {
        return SuccessTip.create(devopsServices.querySql(request, sqlFile));
    }


    @GetMapping("/getRowCount/{sqlFile}")
    public Tip getQueryCount(@PathVariable("sqlFile") String sqlFile, HttpServletRequest request){
       JSONArray jsonArray =  devopsServices.querySql(request, sqlFile);
       int countRow = 0;
       if (jsonArray!=null){
           for (int i =0;i<jsonArray.size();i++){
               JSONArray item = (JSONArray) jsonArray.get(i);
               if (item!=null && item.size()>0){
                   countRow+=item.size();
               }
           }
       }
       return SuccessTip.create(countRow);

    }

    @PostMapping("/{sqlFile}")
    public Tip executeSql(@PathVariable("sqlFile") String sqlFile, HttpServletRequest request) {
        return SuccessTip.create(devopsServices.executeSql(request, sqlFile));
    }




}
