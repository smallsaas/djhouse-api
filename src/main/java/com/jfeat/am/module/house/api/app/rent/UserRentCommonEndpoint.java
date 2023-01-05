package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.appointment.services.gen.persistence.dao.AppointmentTimeMapper;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAppointmentMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseBrowseLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSubscribeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Resource
    HouseAppointmentMapper houseAppointmentMapper;

    protected final Log logger = LogFactory.getLog(getClass());

    @Resource
    AppointmentTimeMapper appointmentTimeMapper;

    @Resource
    UserAccountMapper userAccountMapper;


    @Resource
    UserAccountService userAccountService;

    @Resource
    QueryHouseAppointmentDao queryHouseAppointmentDao;

    @Resource
    QueryHouseSubscribeDao queryHouseSubscribeDao;


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
                                       @RequestParam(name = "houseType", required = false) String houseType,

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
        record.setHouseType(houseType);


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


        houseRentAssetService.setRentTitle(houseRentAssetPage);

        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }

    /*
    查看出租详情
     */
    @GetMapping("/userRentAssetDetails/{id}")
    public Tip userRentAssetDetails(@PathVariable("id") Long id,@RequestParam(value = "userId",required = false) Long userId) {

        HouseRentAssetModel houseRentAssetModel = queryHouseRentAssetDao.queryMasterModel(id);

        userId = JWTKit.getUserId()!=null?JWTKit.getUserId():userId;


        if (houseRentAssetModel!=null){

            if (userId!=null){

                houseBrowseLogService.addBroseLog(userId);
                //            是否有关注
                QueryWrapper<HouseSubscribe> subscribeQueryWrapper = new QueryWrapper<>();
                subscribeQueryWrapper.eq(HouseSubscribe.USER_ID,userId).eq(HouseSubscribe.SUBSCRIBE_ID,id);
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


    @GetMapping("/houseType")
    public Tip getHouseType(){

        QueryWrapper<HouseDesignModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy(HouseDesignModel.DESCRIPTION);
        List<HouseDesignModel> houseDesignModels = houseDesignModelMapper.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        for (HouseDesignModel houseDesignModel:houseDesignModels){
            if (houseDesignModel.getDescription()==null || houseDesignModel.getDescription().equals("")){
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description",houseDesignModel.getDescription());
            jsonObject.put("houseType",houseDesignModel.getDescription());
            jsonArray.add(jsonObject);
        }
        return SuccessTip.create(jsonArray);
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

//        预约数量
        QueryWrapper<HouseAppointment> appointmentQueryWrapper = new QueryWrapper<>();
        appointmentQueryWrapper.eq(HouseAppointment.USER_ID,JWTKit.getUserId());
        List<HouseAppointment> houseAppointmentList =  houseAppointmentMapper.selectList(appointmentQueryWrapper);

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

        if (houseAppointmentList!=null){
            jsonObject.put("visitNumber",houseAppointmentList.size());
        }else {
            jsonObject.put("visitNumber",0);
        }

        return SuccessTip.create(jsonObject);

    }



//    查询中介时间段
    @GetMapping("/intermediaryTime")
    public Tip getAppointmentTimeList(
            @RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "category",required = false) String category) {
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        QueryWrapper<AppointmentTime> appointmentTimeQueryWrapper = new QueryWrapper<>();
        appointmentTimeQueryWrapper.eq(AppointmentTime.USER_ID, userId);
        appointmentTimeQueryWrapper.eq(AppointmentTime.STATUS,AppointmentTime.STATUS_OPEN);
        List<AppointmentTime> appointmentTimeList = appointmentTimeMapper.selectList(appointmentTimeQueryWrapper);



        List<AppointmentTime> result = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String mid = "12:00:00";

        Date midDate = null;
        try {
            midDate = simpleDateFormat.parse(mid);
        } catch (ParseException e) {
            logger.error(e);
        }

        for (AppointmentTime appointmentTime : appointmentTimeList) {

            String s = simpleDateFormat.format(appointmentTime.getStartTime());
            String endTime = simpleDateFormat.format(appointmentTime.getEndTime());


            appointmentTime.setStartTimeStr(s);
            appointmentTime.setEndTimeStr(endTime);

            Date startTime = null;
            try {
                startTime = simpleDateFormat.parse(s);

            } catch (ParseException e) {
                logger.error(e);
            }

            if (startTime.before(midDate) && category!=null &&"am".equals(category)) {
                result.add(appointmentTime);
            } else if (startTime.after(midDate) && category!=null && "pm".equals(category)){
                result.add(appointmentTime);
            }
        }

        if (category==null||category.equals("")){
            return SuccessTip.create(appointmentTimeList);
        }
        return SuccessTip.create(result);
    }

//    根据前端要求返回 指定几个api 数据总数
    @GetMapping("/infoNumber")
    public Tip infoNumber(){
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        JSONObject jsonObject = new JSONObject();
       if (1==1){
           Page<HouseAppointmentRecord> page = new Page<>();
           page.setCurrent(1);
           page.setSize(1);

           HouseAppointmentRecord record = new HouseAppointmentRecord();


           record.setConfirmStatus(HouseAppointment.CONFIRM_STATUS_WAIT);


//        设置身份
           UserAccount userAccount =  userAccountMapper.selectById(userId);
           List<Integer> typeList =  null;
           if (userAccount.getType()!=null){
               typeList = userAccountService.getUserTypeList(userAccount.getType());
           }
           if (typeList!=null && typeList.contains(EndUserTypeSetting.USER_TYPE_INTERMEDIARY)){
               record.setServerId(userId);
           }else {
               record.setUserId(userId);
           }

           Date date = new Date();
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String dateStr = format.format(date);
           record.setNowDate(dateStr);
           List<HouseAppointmentRecord> houseAppointmentPage = queryHouseAppointmentDao.findHouseAppointmentPageDetail(page, record, null, null, null, null, null);
           jsonObject.put("unconfirmedAppointment",page.getTotal());
       }


       if (1==1){
           Page<HouseRentAssetRecord> page = new Page<>();
           page.setCurrent(1);
           page.setSize(1);

           HouseSubscribeRecord record = new HouseSubscribeRecord();
           record.setUserId(JWTKit.getUserId());
           List<HouseRentAssetRecord> houseRentAssetPage = queryHouseSubscribeDao.userSubscribe(page, record);
           page.getTotal();

           jsonObject.put("subscribe",page.getTotal());
       }

       if (1==1){

           Page<HouseRentAssetRecord> page = new Page<>();
           page.setCurrent(1);
           page.setSize(1);

           HouseRentAssetRecord record = new HouseRentAssetRecord();


           UserAccount userAccount =  userAccountMapper.selectById(JWTKit.getUserId());
           List<Integer> typeList = null;
           if (userAccount.getType()!=null){
               typeList = userAccountService.getUserTypeList(userAccount.getType());
           }
           if (typeList!=null && typeList.contains(EndUserTypeSetting.USER_TYPE_SALES)){
               record.setServerId(null);
           }else {
               record.setServerId(JWTKit.getUserId());
           }

           List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPageDetails(page, record, null, null, null, null, null);

           jsonObject.put("rent",page.getTotal());
       }

        return SuccessTip.create(jsonObject);
    }


}
