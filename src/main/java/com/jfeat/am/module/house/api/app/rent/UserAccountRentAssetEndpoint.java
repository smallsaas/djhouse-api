package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSubscribeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/rent/userRentAsset")
public class UserAccountRentAssetEndpoint {

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    StockTagMapper stockTagMapper;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    StockTagRelationMapper stockTagRelationMapper;

    @Resource
    HouseSupportFacilitiesTypeOverModelService houseSupportFacilitiesTypeOverModelService;

    @Resource
    HouseSupportFacilitiesService houseSupportFacilitiesService;

    @Resource
    HouseSurroundFacilitiesTypeOverModelService houseSurroundFacilitiesTypeOverModelService;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;


    @Resource
    UserCommunityAsset userCommunityAsset;


    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    /*
    用户出租自己的房子 填写照片 价格 描述信息
     */
    @PostMapping("/userRentAsset")
    public Tip userRentAsset(@RequestBody HouseRentAsset entity) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId() == null) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "assetId不能为空");
        }
         /*
        验证房子是否是用户的
         */
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        houseUserAssetRecord.setUserId(JWTKit.getUserId());
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord
                ,null,null,null,null,null);
        if (houseUserAssetRecordList==null || houseUserAssetRecordList.size()==0){
            throw new BusinessException(BusinessCode.NoPermission,"没有找到房子,请重试");
        }

        /*
        设置出租的 小区  户型 面积 房东
         */

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(entity.getAssetId());
        entity.setArea(houseAssetModel.getArea());
        entity.setCommunityId(houseAssetModel.getCommunityId());
        entity.setHouseTypeId(houseAssetModel.getDesignModelId());
        entity.setLandlordId(JWTKit.getUserId());
        entity.setCommunityName(houseAssetModel.getCommunityName());
        return SuccessTip.create(houseRentAssetService.createUserRentAssetNotAssetId(entity));
    }

    /*
    用户出租详情 根据房间号查询
     */
    @GetMapping("/userRentAssetDetails/{assetId}")
    public Tip userRentAssetDetailsByAssetId(@PathVariable("assetId") Long assetId) {
        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(assetId);
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null, houseRentAssetRecord
                , null, null, null, null, null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);

        /*
        判断是否出租
         */
        if (houseAssetModel == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有找到房子,请重试");
        }
        if (houseRentAssetRecordList == null || houseRentAssetRecordList.size() == 0) {
            houseRentAssetRecord.setHouseAssetModel(houseAssetModel);
            houseRentAssetRecordList.add(houseRentAssetRecord);
        } else {
            HouseRentAsset rentAsset = houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, houseRentAssetRecordList.get(0).getId());
            houseRentAssetRecordList.get(0).setHouseAssetModel(houseAssetModel);
            houseRentAssetRecordList.get(0).setExtra(rentAsset.getExtra());

        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetRecordList.get(0));
        if (houseAssetModel.getCommunityId() != null) {
            jsonObject.put("facilities", houseSurroundFacilitiesTypeOverModelService.getCommunityFacilities(houseAssetModel.getCommunityId()));
        }
        jsonObject.put("supportFacilities", houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetRecordList.get(0).getId(), houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem()));
        return SuccessTip.create(jsonObject);
    }


    /*
    用户出租详情 根据出租id
     */
    @GetMapping("/userRentAssetDetailsByRentId/{rentId}")
    public Tip userRentAssetDetailsByRentId(@PathVariable("rentId") Long rentId) {


        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setId(rentId);
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null, houseRentAssetRecord
                , null, null, null, null, null);

        if (houseRentAssetRecordList == null || houseRentAssetRecordList.size() <= 0) {
            return SuccessTip.create();
        }

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetRecordList.get(0));
        if (houseRentAssetRecordList.get(0).getAssetId() != null) {
            Long assetId = houseRentAssetRecordList.get(0).getAssetId();
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);
            if (houseRentAssetRecordList == null || houseRentAssetRecordList.size() == 0) {
                houseRentAssetRecord.setHouseAssetModel(houseAssetModel);
                houseRentAssetRecordList.add(houseRentAssetRecord);
            } else {
                HouseRentAsset rentAsset = houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, houseRentAssetRecordList.get(0).getId());
                houseRentAssetRecordList.get(0).setHouseAssetModel(houseAssetModel);
                houseRentAssetRecordList.get(0).setExtra(rentAsset.getExtra());
                jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetRecordList.get(0));
            }

            if (houseAssetModel.getCommunityId() != null) {
                jsonObject.put("facilities", houseSurroundFacilitiesTypeOverModelService.getCommunityFacilities(houseAssetModel.getCommunityId()));
            }
        }

        jsonObject.put("supportFacilities", houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetRecordList.get(0).getId(), houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem()));
        return SuccessTip.create(jsonObject);
    }


    /*
    用户下架
     */
    @DeleteMapping("/undercarriage/{assetId}")
    public Tip deleteRentAsset(@PathVariable("assetId") Long assetId) {
        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(assetId);
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null, houseRentAssetRecord
                , null, null, null, null, null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);

        /*
        判断是否出租
         */
        if (houseAssetModel == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有找到房子,请重试");
        }

        /*
        删除出租房子
         */
        if (houseRentAssetRecordList != null && houseRentAssetRecordList.size() == 1) {
            SuccessTip.create(houseRentAssetService.deleteMaster(houseRentAssetRecordList.get(0).getId()));
        }
        return SuccessTip.create();
    }


    //    房东出租列表
    @GetMapping("/UserRentAssetList")
    public Tip queryHouseRentAssetPage(Page<HouseRentAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        Long userId = JWTKit.getUserId();

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        String orderBy = "rentStatusDesc";

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setLandlordId(userId);


        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPageDetails(page, record, null, null, orderBy, null, null);

//        如果出租房屋时不存在的时候将 房屋信息设置为空
        for (HouseRentAssetRecord houseRentAssetRecord:houseRentAssetPage){
            if (houseRentAssetRecord.getAssetId()==null){
                houseRentAssetRecord.setHouseAssetModel(null);
            }
        }
//        添加tag标签 和房屋信息
        QueryWrapper<StockTag> stockTagQueryWrapper = new QueryWrapper<>();
        List<StockTag> stockTags = stockTagMapper.selectList(stockTagQueryWrapper);

        QueryWrapper<StockTagRelation> stockTagRelationQueryWrapper = new QueryWrapper<>();
        stockTagRelationQueryWrapper.eq(StockTagRelation.STOCK_TYPE, houseRentAssetService.getEntityName());
        List<StockTagRelation> stockTagRelations = stockTagRelationMapper.selectList(stockTagRelationQueryWrapper);

        for (HouseRentAssetRecord houseRentAssetRecord : houseRentAssetPage) {
            JSONObject resultJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (StockTagRelation stockTagRelation : stockTagRelations) {
                if (stockTagRelation.getStockId().equals(houseRentAssetRecord.getId())) {

                    for (StockTag stockTag : stockTags) {
                        if (stockTag.getId().equals(stockTagRelation.getTagId())) {
                            JSONObject tagJson = new JSONObject();
                            tagJson.put("id", stockTag.getId());
                            tagJson.put("tagName", stockTag.getTagName());
                            jsonArray.add(tagJson);
                        }

                    }
                }
            }
            resultJson.put("tags", jsonArray);
            houseRentAssetRecord.setExtra(resultJson.toJSONString());
        }
        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }


    //    添加出租房屋
    @PostMapping("/userRent")
    public Tip createRentAsset(@RequestParam(value = "appid",required = false,defaultValue = "1") String appid,@RequestBody HouseRentAsset entity) {


        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        entity.setLandlordId(userId);

        if (appid.equals("1")){
            HousePropertyCommunity housePropertyCommunity = userCommunityAsset.getUserCommunityInfo(userId);
            if (housePropertyCommunity==null){
                throw new BusinessException(BusinessCode.CodeBase,"没有小区信息");
            }
            entity.setCommunityName(housePropertyCommunity.getCommunity());
            entity.setCommunityId(housePropertyCommunity.getId());
        }

        return SuccessTip.create(houseRentAssetService.createUserRentAssetNotAssetId(entity));
    }


    //    修改出租房屋
    @PutMapping("/userRent/{id}")
    public Tip updateHouseRentAsset(@PathVariable Long id, @RequestBody HouseRentAsset entity) {
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        HouseRentAsset houseRentAsset =  houseRentAssetMapper.selectById(id);
//        判断房东是否是本人
        if (houseRentAsset==null || houseRentAsset.getLandlordId()==null || !houseRentAsset.getLandlordId().equals(userId)){
            throw new BusinessException(BusinessCode.CodeBase,"未找到改出租房屋");
        }


        entity.setId(id);
        entity.setLandlordId(userId);
        return SuccessTip.create(houseRentAssetService.updateUserRentAssetNotAssetId(entity));
    }

    @DeleteMapping("/userRent/{id}")
    public Tip deleteHouseRentAsset(@PathVariable Long id) {
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        HouseRentAsset houseRentAsset =  houseRentAssetMapper.selectById(id);
//        判断房东是否是本人
        if (houseRentAsset==null || houseRentAsset.getLandlordId()==null || !houseRentAsset.getLandlordId().equals(userId)){
            throw new BusinessException(BusinessCode.CodeBase,"未找到改出租房屋");
        }
        return SuccessTip.create(houseRentAssetService.deleteMaster(id));
    }


    @GetMapping("/userRent/{id}")
    public Tip getHouseRentAsset(@PathVariable Long id){
        return SuccessTip.create(houseRentAssetMapper.selectById(id));
    }


    @GetMapping("/rent/supportFacilities")
    public Tip getRentSupportFacilities(){
        return SuccessTip.create(houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem());
    }

}
