package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDesignModelDao;
import com.jfeat.am.module.house.services.domain.dao.QuerySupplierDao;
import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;
import com.jfeat.am.module.house.services.domain.model.SupplierRecord;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.service.EavService;
import com.jfeat.eav.services.domain.service.DataServiceService;
import com.jfeat.eav.services.domain.service.EavEntityService;
import com.jfeat.eav.services.domain.service.Impl.DataServiceServiceImpl;
import com.jfeat.eav.services.gen.persistence.model.EavEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Api("UserVrCategory")
@RequestMapping("/api/u/vr")
public class UserVrCategoryEndpoint {

    @Resource
    QuerySupplierDao querySupplierDao;


    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    EavEntityService eavEntityService;

    @Resource
    DataServiceService dataServiceService;

    @Resource
    com.jfeat.eav.services.domain.service.Impl.DataServiceServiceImpl DataServiceServiceImpl;

    @GetMapping("/allVrCategory")
    public Tip getAllCategory(){
        EavEntity entity = eavEntityService.getByEntityName("vrType");
        //eavEntityService.getEavEntityAllValues(page, entity.getId())
        JSONObject page = new JSONObject();
        page.put("current",1);
        page.put("size",10);
        List<String> valueList = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        JSONArray list = dataServiceService.getList(page, entity.getId(), DataServiceServiceImpl.KEY_ROW_ID);

        for (int i=0;i<list.size();i++){
            JSONObject item = (JSONObject) list.get(i);
            JSONObject extra = (JSONObject) item.get("extra");
            JSONArray items = extra.getJSONArray("items");
            JSONObject valueJson = (JSONObject) items.get(1);
            String value = valueJson.getString("value");
            valueList.add(value);
        }
        for (String value:valueList){
            if ("supplier".equals(value)){
                List<SupplierRecord> supplierRecords = querySupplierDao.queryAllSupplier();

                for (int i=0;i<supplierRecords.size();i++){
                    String snapshot =  supplierRecords.get(i).getSnapshot();
                    String pattern = "/attachments(.*)\\.jpg";
                    Pattern r = Pattern.compile(pattern);
                    if (snapshot!=null){
                        Matcher m = r.matcher(snapshot);
                        if (m.find()) {
                            supplierRecords.get(i).setSnapshotUrl(m.group(0));
                        }
                    }

                }
                map.put(value,supplierRecords);
            }
            if ("houseType".equals(value)){
                List<HouseDesignModelRecord> houseDesignModelRecords = queryHouseDesignModelDao.queryAllHouseDesignModel();
                for (int i=0;i<houseDesignModelRecords.size();i++){
                    String snapshot =  houseDesignModelRecords.get(i).getVrSnapshot();
                    String pattern = "/attachments(.*)\\.jpg";

                    Pattern r = Pattern.compile(pattern);
                    if (snapshot!=null){
                        Matcher m = r.matcher(snapshot);
                        if (m.find()) {
                            houseDesignModelRecords.get(i).setSnapshotUrl(m.group(0));
                        }
                    }

                }
                map.put(value,houseDesignModelRecords);
            }
        }
        return SuccessTip.create(map);
    }


    @GetMapping
    public Tip queryCategory(@RequestParam("category") String category,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        if ("supplier".equals(category)){
            Page<SupplierRecord> page = new Page<>();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            List<SupplierRecord> supplierRecords = querySupplierDao.queryAllSupplier();
            page.setRecords(supplierRecords);
            return SuccessTip.create(page);
        }else if ("houseType".equals(category)){
            Page<HouseDesignModelRecord> page = new Page<>();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            List<HouseDesignModelRecord> houseDesignModelRecords = queryHouseDesignModelDao.queryAllHouseDesignModel();
            page.setRecords(houseDesignModelRecords);
            return SuccessTip.create(page);
        }

        return SuccessTip.create();
    }

}
