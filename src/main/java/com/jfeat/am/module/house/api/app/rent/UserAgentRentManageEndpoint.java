package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.appointment.services.gen.persistence.dao.AppointmentTimeMapper;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import com.jfeat.am.module.house.services.definition.HouseRentLogStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.house.services.utility.DateTimeUtil;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/u/house/rent/agentRentManage")
public class UserAgentRentManageEndpoint {

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;


    @Resource
    AppointmentTimeMapper appointmentTimeMapper;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    Authentication authentication;

    @Resource
    StockTagMapper stockTagMapper;

    @Resource
    StockTagRelationMapper stockTagRelationMapper;

    @Resource
    HouseSupportFacilitiesTypeOverModelService houseSupportFacilitiesTypeOverModelService;

    @Resource
    HouseSupportFacilitiesService houseSupportFacilitiesService;


    @Resource
    HouseSurroundFacilitiesTypeOverModelService houseSurroundFacilitiesTypeOverModelService;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;


    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;


    @Resource
    HouseRentLogService houseRentLogService;





    /*
    用户出租列表
     */

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

                                       @RequestParam(name = "landlordName", required = false) String landlordName,
                                       @RequestParam(name = "landlordRealName", required = false) String landlordRealName,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "rentTime", required = false) Date rentTime,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "shelvesTime", required = false) Date shelvesTime,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort,
                                       @RequestParam(name = "additionalQuery", required = false, defaultValue = "false") Boolean additionalQuery) {


        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        orderBy = "rentStatusAsc";

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setAssetId(assetId);
        record.setCommunityId(communityId);
        record.setHouseTypeId(houseTypeId);
        record.setLandlordId(landlordId);
        record.setArea(area);
        record.setHouseType(houseType);
        record.setIntroducePicture(introducePicture);
        record.setLandlordName(landlordName);
        record.setLandlordRealName(landlordRealName);

        // 查询当前访问用户信息
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
        List<Integer> typeList = null;
        // 获取用户类型列表
        if (userAccount.getType() != null) {
            typeList = userAccountService.getUserTypeList(userAccount.getType());
        }
        // 判断类型是否有销售
        // 是销售则查询所有房源
        if (typeList != null && typeList.contains(EndUserTypeSetting.USER_TYPE_SALES)) {
            record.setServerId(null);
        } else {
        // 不是销售，只能查看自己的房源
            record.setServerId(JWTKit.getUserId());
        }

        /**
         * 判断是否使用精准查询
         *
         * 精准查询是从前端设置，并缓存在redis中的字段值，如果使用则从redis中取出使用
         */
        // 判断additionalQuery是否为true
        if (additionalQuery) {
            Map<Object,Object> accurateFields =  houseRentAssetService.listAccurateField();
            if (accurateFields.size() > 0) {
                Iterator<Map.Entry<Object,Object>> entries = accurateFields.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<Object,Object> entry = entries.next();
                    // 如果值为""空串，无需再做判断，结束此次循环
                    if ("".equals(entry.getValue())) continue;
                    // 判断 楼栋，单元，朝向，楼层
                    if ("buildings".equals(entry.getKey())) {
                        record.setBuildings(String.valueOf(entry.getValue()).split(","));
                    } else if ("units".equals(entry.getKey())) {
                        record.setUnits(String.valueOf(entry.getValue()).split(","));
                    } else if ("towards".equals(entry.getKey())) {
                        record.setTowards(String.valueOf(entry.getValue()).split(","));
                    } else if ("startFloor".equals(entry.getKey())) {
                        record.setStartFloor(Integer.valueOf(String.valueOf(entry.getValue())));
                    } else if ("endFloor".equals(entry.getKey())) {
                        record.setEndFloor(Integer.valueOf(String.valueOf(entry.getValue())));
                    }
                }
            }
        }

        record.setCover(cover);
        record.setTitle(title);
        record.setPrice(price);
        record.setSlide(slide);
        record.setRentDescribe(describe);
        record.setRentStatus(rentStatus);
        record.setNote(note);
        record.setRentTime(rentTime);
        record.setShelvesTime(shelvesTime);


        Long start = System.currentTimeMillis();
        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPageDetails(page, record, tag, search, orderBy, null, null);

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

//        houseRentAssetService.setRentDescribe(houseRentAssetPage);

        page.setRecords(houseRentAssetPage);

        System.out.println("统计时间" + (System.currentTimeMillis() - start));
        return SuccessTip.create(page);
    }

    /**
     * 保存精准查询的参数到redis[0]
     *
     * @param accurateField: 字符串键值对map集合，要求key和value都为字符串
     * @return  null
     */
    @PostMapping
    public Tip updateAccurateFields(@RequestBody Map<String,String> accurateField) {
        houseRentAssetService.saveAccurateQueryField(accurateField);
        return SuccessTip.create();
    }

    /**
     * 从redis中获取精准查询字段
     *
     * @return
     */
    @GetMapping("/getAccurateFields")
    public Tip queryAccurateFields() {
        return SuccessTip.create(houseRentAssetService.listAccurateField());
    }


    /*
    中介补充 用户出租信息
     */
    @PutMapping("/agentModifyRentInfo/{id}")
    public Tip AgentModifyRentInfo(@PathVariable Long id, @RequestBody HouseRentAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
          /*
        判断是否是中介身份
         */
        if (!authentication.verifyIntermediary(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }

        entity.setId(id);
        return SuccessTip.create(houseRentAssetService.updateUserRentAsset(entity));
    }


    /*
    修改出租状态 上架或者下架
     */
    @PutMapping("/modifyRentStatus")
    public Tip updateHouseRentAsset(@RequestBody HouseRentAsset entity) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();


//        appointmentMapper

        if (entity.getId() == null || "".equals(entity.getId())) {
            throw new BusinessException(BusinessCode.BadRequest, "id为必填");
        }
        if (entity.getRentStatus() == null || "".equals(entity.getRentStatus())) {
            throw new BusinessException(BusinessCode.BadRequest, "rentStatus");
        }


        if (entity.getRentStatus().equals(HouseRentAsset.RENT_STATUS_SHELVES)) {
            QueryWrapper<AppointmentTime> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Appointment.USERID, userId);
            List<AppointmentTime> list = appointmentTimeMapper.selectList(queryWrapper);
            if (list == null || list.size() <= 0) {
                throw new BusinessException(BusinessCode.CodeBase, "必须设置个人预约时间");
            }
        }

        HouseRentAssetModel houseRentAssetModel = queryHouseRentAssetDao.queryMasterModel(entity.getId());
        if (houseRentAssetModel == null) {
            throw new BusinessException(BusinessCode.BadRequest, "参数异常,请检查参数是否正确");
        }


        if (entity.getRentStatus() == 1) {

//            添加下架日志
            houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.pullOffShelves.name());

        } else if (entity.getRentStatus() == 2) {
            //            添加上架日志
            houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.putOnShelves.name());
        }


        /*
        设置中介身份和状态
         */
        houseRentAssetModel.setServerId(JWTKit.getUserId());
        houseRentAssetModel.setRentStatus(entity.getRentStatus());
        houseRentAssetModel.setShelvesTime(new Date());
        return SuccessTip.create(houseRentAssetService.updateMaster(houseRentAssetModel));
    }

    /*
    查看出租详情
     */
    @GetMapping("/userRentDetails/{id}")
    public Tip getHouseRentAsset(@PathVariable Long id) {
        HouseRentAsset houseRentAssetModel = houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, id);


        if (houseRentAssetModel != null) {

//            设置状态
            List<HouseSupportFacilitiesTypeRecord> rentHouseSupportFacilitiesStatus = houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetModel.getId(), houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem());
//            houseRentAssetService.setRentDescribe(houseRentAssetModel,rentHouseSupportFacilitiesStatus);

            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(houseRentAssetModel.getAssetId());
            if (houseAssetModel != null) {
                houseRentAssetModel.setHouseAssetModel(houseAssetModel);
            }
//            添加中介信息
            if (houseRentAssetModel.getServerId() != null) {
                EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(houseRentAssetModel.getServerId());
                if (endpointUserModel != null) {
                    houseRentAssetModel.setServerAvatar(endpointUserModel.getAvatar());
                    houseRentAssetModel.setServerPhone(endpointUserModel.getPhone());
                    houseRentAssetModel.setServerName(endpointUserModel.getName());
                }
            }
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetModel);
            if (houseAssetModel!=null&&houseAssetModel.getCommunityId() != null) {
                jsonObject.put("facilities", houseSurroundFacilitiesTypeOverModelService.getCommunityFacilities(houseAssetModel.getCommunityId()));
            }
            jsonObject.put("supportFacilities",rentHouseSupportFacilitiesStatus);
            return SuccessTip.create(jsonObject);
        }
        return SuccessTip.create(houseRentAssetModel);
    }


    //    查询楼栋
    @GetMapping("/community/{id}")
    public Tip getBuildingList(@PathVariable("id") Long communityId) {
        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID, communityId);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);
        return SuccessTip.create(buildingList);
    }


    //    查询房号
    @GetMapping("/building/{id}")
    public Tip getAssetList(@PathVariable("id") Long buildingId,
                            Page<HouseAsset> page,
                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        QueryWrapper<HouseAsset> assetQueryWrapper = new QueryWrapper<>();
        assetQueryWrapper.eq(HouseAsset.BUILDING_ID, buildingId);
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = houseAssetMapper.selectPage(page, assetQueryWrapper);

        List<HouseAsset> houseAssetList = page.getRecords();
        List<Long> assetIds = houseAssetList.stream().map(HouseAsset::getId).collect(Collectors.toList());


        if (assetIds != null) {
            QueryWrapper<HouseRentAsset> rentAssetQueryWrapper = new QueryWrapper<>();
            rentAssetQueryWrapper.in(HouseRentAsset.ASSET_ID, assetIds);
            List<HouseRentAsset> rentAssetList = houseRentAssetMapper.selectList(rentAssetQueryWrapper);

            for (HouseAsset houseAsset : houseAssetList) {

                for (HouseRentAsset houseRentAsset : rentAssetList) {
                    if (houseAsset.getId().equals(houseRentAsset.getAssetId())) {
                        houseAsset.setExistRent(true);
                    }

                }
            }
        }


        return SuccessTip.create(page);
    }

    /*
用户出租详情
 */
    @GetMapping("/rentAssetDetails/{assetId}")
    public Tip userRentAssetDetails(@PathVariable("assetId") Long assetId) {

        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(assetId);
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
        jsonObject.put("supportFacilities", houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetRecordList.get(0).getAssetId(), houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem()));
        return SuccessTip.create(jsonObject);
    }

    //    添加出租
    @PostMapping("/createRentAsset")
    public Tip createRentAsset(@RequestBody HouseRentAsset entity) {
        if (entity.getAssetId() == null) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为必填值");
        }

//        查看是否存产权
        QueryWrapper<HouseUserAsset> houseUserAssetQueryWrapper = new QueryWrapper<>();
        houseUserAssetQueryWrapper.eq(HouseUserAsset.ASSET_ID, entity.getAssetId());
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(houseUserAssetQueryWrapper);
        if (houseUserAsset == null) {
            throw new BusinessException(BusinessCode.CodeBase, "房屋没有产权不能创盘");
        }

//        查看是否出租
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.ASSET_ID, entity.getAssetId());
        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectOne(houseRentAssetQueryWrapper);
        if (houseRentAsset != null) {
            throw new BusinessException(BusinessCode.CodeBase, "房屋已出租,不能重复创盘");
        }

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(entity.getAssetId());
        entity.setLandlordId(houseUserAsset.getUserId());
        entity.setCommunityId(houseAssetModel.getCommunityId());
        entity.setHouseTypeId(houseAssetModel.getDesignModelId());
        entity.setArea(houseAssetModel.getArea());
        entity.setServerId(null);

        Integer affect =  houseRentAssetMapper.insert(entity);
        //            添加发布日志
        houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.createRentInfo.name());
        return SuccessTip.create(affect);
    }


//    删除出租屋
    @DeleteMapping("/{id}")
    public Tip deleteHouseRentAsset(@PathVariable Long id) {
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        HouseRentAsset houseRentAsset =  houseRentAssetMapper.selectById(id);
//        判断房东是否是本人
        if (houseRentAsset==null){
            throw new BusinessException(BusinessCode.CodeBase,"未找到改出租房屋");
        }

        //            记录删除日志
        houseRentLogService.addHouseRentLog(id, HouseRentLogStatus.deleteRentInfo.name());

        return SuccessTip.create(houseRentAssetService.deleteMaster(id));
    }


//    出租房屋

    @PutMapping("/rentOut/{id}")
    public Tip rentOut(@PathVariable("id")Long id,@RequestBody HouseRentAsset entity){

        if (entity.getContractStartTime()==null||entity.getContractEndTime()==null){
            throw new BusinessException(BusinessCode.BadRequest,"出租时间必填");
        }
        if (entity.getContractEndTime().before(entity.getContractStartTime())){
            throw new BusinessException(BusinessCode.BadRequest,"出租结束时间不能大于出租开始时间");
        }

        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectById(id);
        houseRentAsset.setState(1);
        houseRentAsset.setContractEndTime(entity.getContractEndTime());
        houseRentAsset.setContractStartTime(entity.getContractStartTime());
        houseRentAsset.setRentStatus(HouseRentAsset.RENT_STATUS_SOLD_OUT);
        houseRentAsset.setContractTimeLimit(DateTimeUtil.getMonthDiff(entity.getContractStartTime(),entity.getContractEndTime()));
        return SuccessTip.create(houseRentAssetMapper.updateById(houseRentAsset));

    }

//    收回出租屋
    @PutMapping("/stopRent/{id}")
    public Tip stopRent(@PathVariable("id")Long id){
        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectById(id);
        houseRentAsset.setState(0);
        houseRentAsset.setContractEndTime(null);
        houseRentAsset.setContractStartTime(null);
        houseRentAsset.setContractTimeLimit(null);
        return SuccessTip.create(houseRentAssetMapper.updateById(houseRentAsset));
    }


//    已出租列表

}
