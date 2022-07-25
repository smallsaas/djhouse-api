package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseVrPicturePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDesignModelDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrPictureDao;
import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;
import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;
import com.jfeat.am.module.house.services.domain.service.HouseVrPictureService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrPictureModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.supplier.services.domain.dao.QuerySupplierDao;
import com.jfeat.am.module.supplier.services.domain.model.SupplierRecord;
import com.jfeat.am.module.supplier.services.gen.persistence.model.Supplier;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.service.EavService;
import com.jfeat.eav.services.domain.service.DataServiceService;
import com.jfeat.eav.services.domain.service.EavEntityService;
import com.jfeat.eav.services.domain.service.Impl.DataServiceServiceImpl;
import com.jfeat.eav.services.gen.persistence.model.EavEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

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
    QueryHouseVrPictureDao queryHouseVrPictureDao;

    @Resource
    HouseVrPictureService houseVrPictureService;

    @Resource
    com.jfeat.eav.services.domain.service.Impl.DataServiceServiceImpl DataServiceServiceImpl;

    @GetMapping("/allVrCategory")
    public Tip getAllCategory() {
        EavEntity entity = eavEntityService.getByEntityName("vrType");
        //eavEntityService.getEavEntityAllValues(page, entity.getId())
        JSONObject page = new JSONObject();
        page.put("current", 1);
        page.put("size", 10);
        List<String> valueList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        JSONArray list = dataServiceService.getList(page, entity.getId(), DataServiceServiceImpl.KEY_ROW_ID);

//        获取分类名称
        for (int i = 0; i < list.size(); i++) {
            JSONObject item = (JSONObject) list.get(i);
            JSONObject extra = (JSONObject) item.get("extra");
            JSONArray items = extra.getJSONArray("items");
            JSONObject valueJson = (JSONObject) items.get(1);
            String value = valueJson.getString("value");
            valueList.add(value);
        }


        for (String value : valueList) {
            if ("supplier".equals(value)) {
                List<SupplierRecord> supplierRecords = querySupplierDao.queryAllSupplier();
                JSONArray jsonArray = new JSONArray();


                for (int i = 0; i < supplierRecords.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", supplierRecords.get(i).getId());
                    jsonObject.put("name", supplierRecords.get(i).getName());
                    jsonObject.put("vrUrl", supplierRecords.get(i).getLink());


                    String snapshot = supplierRecords.get(i).getSnapshot();
                    String pattern = "/attachments(.*)\\.jpg";
                    Pattern r = Pattern.compile(pattern);
                    if (snapshot != null) {
                        Matcher m = r.matcher(snapshot);
                        if (m.find()) {
                            jsonObject.put("snapshotUrl", m.group(0));
//                            supplierRecords.get(i).setSnapshotUrl();
                        }
                    }
                    jsonArray.add(jsonObject);

                }
                map.put(value, jsonArray);
            }
            if ("houseType".equals(value)) {
                List<HouseDesignModelRecord> houseDesignModelRecords = queryHouseDesignModelDao.queryAllHouseDesignModel();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < houseDesignModelRecords.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", houseDesignModelRecords.get(i).getId());
                    jsonObject.put("name", houseDesignModelRecords.get(i).getHouseType());
                    jsonObject.put("vrUrl", houseDesignModelRecords.get(i).getVrLink());

                    String snapshot = houseDesignModelRecords.get(i).getVrSnapshot();
                    String pattern = "/attachments(.*)\\.jpg";

                    Pattern r = Pattern.compile(pattern);
                    if (snapshot != null) {
                        Matcher m = r.matcher(snapshot);
                        if (m.find()) {
                            jsonObject.put("snapshotUrl", m.group(0));
//                            houseDesignModelRecords.get(i).setSnapshotUrl(m.group(0));
                        }
                    }
                    jsonArray.add(jsonObject);

                }
                map.put(value, jsonArray);
            }
        }
        return SuccessTip.create(map);
    }


    @GetMapping
    public Tip queryCategory(
            Page<HouseVrPictureRecord> page,
            @RequestParam("category") String category,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseVrPictureRecord record = new HouseVrPictureRecord();
        record.setTypeOption(category);
        record.setStatus(HouseVrPicture.STATUS_SHELVES);
        List<HouseVrPictureRecord> houseVrPictureRecordList = queryHouseVrPictureDao.findHouseVrPicturePage(page, record, null, null, null, null, null);
        page.setRecords(houseVrPictureRecordList);

        return SuccessTip.create(page);
    }


    /*
    增加vr图看过人数
     */
    @PutMapping("/addVrCount/{id}")
    public Tip vrClientCount(@PathVariable("id") Long id) {


        HouseVrPictureModel pictureModel = new HouseVrPictureModel();
        HouseVrPictureModel houseVrPictureModel = houseVrPictureService.queryMasterModel(queryHouseVrPictureDao, id);
        if (houseVrPictureModel != null) {
            pictureModel.setId(id);
            pictureModel.setVrPicture(houseVrPictureModel.getVrPicture());
            pictureModel.setName(houseVrPictureModel.getName());
            pictureModel.setSnapshot(houseVrPictureModel.getSnapshot());
            pictureModel.setLink(houseVrPictureModel.getLink());
            pictureModel.setStar(houseVrPictureModel.getStar() + 1);
            pictureModel.setBedrooms(houseVrPictureModel.getBedrooms());
            pictureModel.setStyle(houseVrPictureModel.getStyle());
            pictureModel.setTypeOption(houseVrPictureModel.getTypeOption());
            pictureModel.setNote(houseVrPictureModel.getNote());
            pictureModel.setStatus(houseVrPictureModel.getStatus());
        }
        return SuccessTip.create(houseVrPictureService.updateMaster(pictureModel));
    }



    /*
    新组件测试用api 后面删除
     */
    @GetMapping("/testImage")
    public Tip getTestImages() {
        List<Map<String, String>> images = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("img", "https://img0.baidu.com/it/u=3741814049,1596806643&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        Map<String, String> map2 = new HashMap<>();
        map2.put("img", "https://img1.baidu.com/it/u=742421947,2110405846&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        Map<String, String> map3 = new HashMap<>();
        map3.put("img", "https://img2.baidu.com/it/u=4253479904,3449894731&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800");
        Map<String, String> map4 = new HashMap<>();
        map4.put("img", "https://img0.baidu.com/it/u=282262975,2495783424&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800");

        images.add(map1);
        images.add(map2);
        images.add(map3);
        images.add(map4);
        return SuccessTip.create(images);
    }


}
