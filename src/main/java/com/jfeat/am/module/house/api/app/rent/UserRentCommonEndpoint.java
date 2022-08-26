package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseBrowseLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSubscribeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/rent/rentCommon")
public class UserRentCommonEndpoint {

    /*
   用户出租列表
    */

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    HouseDesignModelMapper houseDesignModelMapper;

    @Resource
    StockTagMapper stockTagMapper;

    @Resource
    StockTagRelationMapper stockTagRelationMapper;

    @Resource
    QueryHouseSurroundFacilitiesTypeDao queryHouseSurroundFacilitiesTypeDao;

    @Resource
    HouseSurroundFacilitiesTypeOverModelService houseSurroundFacilitiesTypeOverModelService;

    @Resource
    HouseSupportFacilitiesTypeOverModelService houseSupportFacilitiesTypeOverModelService;

    @Resource
    HouseSupportFacilitiesService houseSupportFacilitiesService;

    @Resource
    HouseSubscribeMapper houseSubscribeMapper;

    @Resource
    HouseBrowseLogService houseBrowseLogService;

    @Resource
    HouseBrowseLogMapper houseBrowseLogMapper;



    @GetMapping("/getUserRentAsset")
    public Tip queryHouseRentAssetPage(Page<HouseRentAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "assetId", required = false) Long assetId,

                                       @RequestParam(name = "communityId", required = false) Long communityId,

                                       @RequestParam(name = "houseTypeId", required = false) Long houseTypeId,

                                       @RequestParam(name = "landlordId", required = false) Long landlordId,

                                       @RequestParam(name = "area", required = false) BigDecimal area,

                                       @RequestParam(name = "introducePicture", required = false) String introducePicture,

                                       @RequestParam(name = "serverId", required = false) Long serverId,

                                       @RequestParam(name = "cover", required = false) String cover,

                                       @RequestParam(name = "title", required = false) String title,

                                       @RequestParam(name = "price", required = false) BigDecimal price,

                                       @RequestParam(name = "slide", required = false) String slide,

                                       @RequestParam(name = "describe", required = false) String describe,

                                       @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                       @RequestParam(name = "note", required = false) String note,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "rentTime", required = false) Date rentTime,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "shelvesTime", required = false) Date shelvesTime,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setAssetId(assetId);
        record.setCommunityId(communityId);
        record.setHouseTypeId(houseTypeId);
        record.setLandlordId(landlordId);
        record.setArea(area);
        record.setIntroducePicture(introducePicture);
        record.setServerId(serverId);
        record.setCover(cover);
        record.setTitle(title);
        record.setPrice(price);
        record.setSlide(slide);
        record.setRentDescribe(describe);
        record.setRentStatus(HouseRentAsset.RENT_STATUS_SHELVES);
        record.setNote(note);
        record.setRentTime(rentTime);
        record.setShelvesTime(shelvesTime);


        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPageDetails(page, record, tag, search, orderBy, null, null);

//        添加tag标签 和房屋信息
        QueryWrapper<StockTag> stockTagQueryWrapper = new QueryWrapper<>();
        List<StockTag> stockTags = stockTagMapper.selectList(stockTagQueryWrapper);

        QueryWrapper<StockTagRelation> stockTagRelationQueryWrapper = new QueryWrapper<>();
        stockTagRelationQueryWrapper.eq(StockTagRelation.STOCK_TYPE,houseRentAssetService.getEntityName());
        List<StockTagRelation> stockTagRelations = stockTagRelationMapper.selectList(stockTagRelationQueryWrapper);

        for (HouseRentAssetRecord houseRentAssetRecord:houseRentAssetPage){
            JSONObject resultJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (StockTagRelation stockTagRelation:stockTagRelations){
                if (stockTagRelation.getStockId().equals(houseRentAssetRecord.getId())){

                    for (StockTag stockTag:stockTags){
                        if (stockTag.getId().equals(stockTagRelation.getTagId())){
                            JSONObject tagJson = new JSONObject();
                            tagJson.put("id",stockTag.getId());
                            tagJson.put("tagName",stockTag.getTagName());
                            jsonArray.add(tagJson);
                        }

                    }
                }
            }
            resultJson.put("tags",jsonArray);
            houseRentAssetRecord.setExtra(resultJson.toJSONString());
        }

//        查询用户是否有关注过房源
        if (JWTKit.getUserId()!=null){
            QueryWrapper<HouseSubscribe> houseSubscribeQueryWrapper = new QueryWrapper<>();
            houseSubscribeQueryWrapper.eq(HouseSubscribe.USER_ID,JWTKit.getUserId());
            List<HouseSubscribe> houseSubscribeList =  houseSubscribeMapper.selectList(houseSubscribeQueryWrapper);

//        遍历用户关注过的房源 如果有就添加状态
            for (HouseSubscribe houseSubscribe:houseSubscribeList){
                for (HouseRentAssetRecord rentAssetRecord:houseRentAssetPage){
                    if (houseSubscribe.getSubscribeId().equals(rentAssetRecord.getId())){
                        rentAssetRecord.setSubscribeStatus(true);
                    }
                }
            }
        }

        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }

    /*
    查看出租详情
     */
    @GetMapping("/userRentAssetDetails/{id}")
    public Tip userRentAssetDetails(@PathVariable("id") Long id) {

        HouseRentAssetModel houseRentAssetModel = queryHouseRentAssetDao.queryMasterModel(id);
        if (houseRentAssetModel!=null){

            if (JWTKit.getUserId()!=null){

                houseBrowseLogService.addBroseLog(JWTKit.getUserId());
                //            是否有关注
                QueryWrapper<HouseSubscribe> subscribeQueryWrapper = new QueryWrapper<>();
                subscribeQueryWrapper.eq(HouseSubscribe.USER_ID,JWTKit.getUserId()).eq(HouseSubscribe.SUBSCRIBE_ID,id);
                HouseSubscribe houseSubscribe =  houseSubscribeMapper.selectOne(subscribeQueryWrapper);
                if (houseSubscribe!=null){
                    houseRentAssetModel.setSubscribeStatus(true);
                }
            }

            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(houseRentAssetModel.getAssetId());
            if (houseAssetModel!=null){
                houseRentAssetModel.setHouseAssetModel(houseAssetModel);
            }
            if (houseRentAssetModel.getServerId()!=null){
                EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(houseRentAssetModel.getServerId());
                if (endpointUserModel!=null){
                    houseRentAssetModel.setServerAvatar(endpointUserModel.getAvatar());
                    houseRentAssetModel.setServerPhone(endpointUserModel.getPhone());
                    houseRentAssetModel.setServerName(endpointUserModel.getName());
                }
            }
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetModel);
            if (houseRentAssetModel!=null && houseRentAssetModel.getCommunityId()!=null){
                 jsonObject.put("facilities",houseSurroundFacilitiesTypeOverModelService.getCommunityFacilities(houseRentAssetModel.getCommunityId()));
            }
            jsonObject.put("supportFacilities",houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetModel.getAssetId(),houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem()));
            return SuccessTip.create(jsonObject);
        }
        return SuccessTip.create();
    }

//    获取全部面积类型
    @GetMapping("/houseAreaTypeList")
    public Tip getHouseAreaTypeList(){
        QueryWrapper<HouseDesignModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy(HouseDesignModel.AREA);
        List<HouseDesignModel> houseDesignModels = houseDesignModelMapper.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        for (HouseDesignModel houseDesignModel:houseDesignModels){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("area",houseDesignModel.getArea());
            jsonObject.put("areaName",houseDesignModel.getArea());
            jsonArray.add(jsonObject);
        }
        return SuccessTip.create(jsonArray);
    }


//    列出户型种类
    @GetMapping("/houseTypeList")
    public Tip getHouseTypeList(){

        HouseDesignModelRecord houseDesignModelRecord = new HouseDesignModelRecord();
        List<HouseDesignModelRecord> houseDesignModelRecordList = queryHouseDesignModelDao.findHouseDesignModelPage(null,houseDesignModelRecord,null,null
        ,null,null,null);
        return SuccessTip.create(houseDesignModelRecordList);
    }


//    获取关注数 和浏览数
    @GetMapping("/statistics")
    public Tip getStatisticsNumber(){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

//        浏览数
        QueryWrapper<HouseBrowseLog> browseLogQueryWrapper = new QueryWrapper<>();
        browseLogQueryWrapper.eq(HouseBrowseLog.USER_ID,JWTKit.getUserId());
        HouseBrowseLog houseBrowseLog = houseBrowseLogMapper.selectOne(browseLogQueryWrapper);

//        关注数
        QueryWrapper<HouseSubscribe> subscribeQueryWrapper = new QueryWrapper<>();
        subscribeQueryWrapper.eq(HouseSubscribe.USER_ID,JWTKit.getUserId());
        List<HouseSubscribe> houseSubscribeList = houseSubscribeMapper.selectList(subscribeQueryWrapper);

        JSONObject jsonObject = new JSONObject();

        if (houseBrowseLog!=null){
            jsonObject.put("browseNumber",houseBrowseLog.getBrowseNumber());
        }else {
            jsonObject.put("browseNumber",0);
        }

        if (houseSubscribeList!=null){
            jsonObject.put("subscribeNumber",houseSubscribeList.size());
        }else {
            jsonObject.put("subscribeNumber",0);
        }

        return SuccessTip.create(jsonObject);

    }


}
