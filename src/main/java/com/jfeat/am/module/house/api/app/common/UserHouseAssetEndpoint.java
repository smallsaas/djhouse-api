package com.jfeat.am.module.house.api.app.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.constant.MyHouseAssetConst;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.am.uaas.tenant.services.gen.persistence.dao.TenantMapper;
import com.jfeat.am.uaas.tenant.services.gen.persistence.model.Tenant;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.module.blacklist.services.domain.service.EndUserBlacklistService;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api("UserHouseAsset")
@RequestMapping("/api/u/asset")
@EnableAsync
public class UserHouseAssetEndpoint {
    protected final static Logger logger = LoggerFactory.getLogger(UserHouseAssetEndpoint.class);

    public static Integer CHANGE_ASSET_LIMIT = 1;

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    HouseUserCommunityStatusService houseUserCommunityStatusService;

    @Resource
    QueryHousePropertyBuildingDao housePropertyBuildingDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseUserAssetService houseUserAssetService;


    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;


    @Resource
    HouseAssetLogService houseAssetLogService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    HouseEmailService houseEmailService;

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Resource
    TenantUtility tenantUtility;

    @Resource
    TenantMapper tenantMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    HouseAssetTransactionMapper houseAssetTransactionMapper;

    @Resource
    UserAccountUtility userAccountUtility;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;


    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;


    @Resource
    EndUserBlacklistService endUserBlacklistService;

    @Resource
    HouseAssetLogMapper houseAssetLogMapper;

    @Resource
    HouseConfigService houseConfigService;


    @GetMapping("/tenant")
    public Tip getTenantList(Page<Tenant> page,
                             @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam("appid") String appid) {
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        QueryWrapper<HousePropertyCommunity> communityQueryWrapper = new QueryWrapper<>();
        List<HousePropertyCommunity> communityList = housePropertyCommunityMapper.selectList(communityQueryWrapper);
        List<Long> communityIds = communityList.stream().map(HousePropertyCommunity::getTenantId).collect(Collectors.toList());


        QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
        tenantQueryWrapper.lambda().select(Tenant.class, i -> !i.getColumn().equals("domain"));
        if (communityIds != null && communityIds.size() > 0) {
            tenantQueryWrapper.in(Tenant.ORG_ID, communityIds);
        }
        tenantQueryWrapper.eq("appid", appid);
        tenantMapper.selectPage(page, tenantQueryWrapper);
        return SuccessTip.create(page);
    }


    //    获取小区
    @GetMapping("/community")
    public Tip getHousePropertyCommunityByTenantId(@RequestParam(value = "tenantId", required = false) Long tenantId) {

        if (tenantId == null) {
            if (JWTKit.getUserId() == null) {
                throw new BusinessException(BusinessCode.NoPermission, "没有登录");
            }
            tenantId = tenantUtility.getCurrentOrgId(JWTKit.getUserId());
        }

        if (tenantId == null) {
            throw new BusinessException(BusinessCode.CodeBase, "没有社区");
        }

        return SuccessTip.create(queryHousePropertyCommunityDao.queryHouseCommunityByTenantId(tenantId));
    }

    //    全部获取小区 不需要社区验证
    @GetMapping("/common/community")
    public Tip getCommonCommunity() {
        HousePropertyCommunityRecord record = new HousePropertyCommunityRecord();
        List<HousePropertyCommunityRecord> communityRecordList = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(null, record, null, null, null, null, null);
        return SuccessTip.create(communityRecordList);
    }


    //    修改用户小区状态
    @PostMapping("/community/userCommunityStatus")
    public Tip updateUserCommunityStatusByUserId(@RequestParam("communityId") Long communityId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        System.out.println(JWTKit.getTenantOrgId());
        HouseUserCommunityStatus entity = new HouseUserCommunityStatus();

        entity.setUserId(JWTKit.getUserId());
        entity.setTenantId(JWTKit.getOrgId());
        entity.setCommunityId(communityId);

        HouseUserCommunityStatus houseUserCommunityStatus = queryHouseUserCommunityStatusDao.queryUserCommunityStatusByUserId(JWTKit.getUserId());
        if (houseUserCommunityStatus != null) {
            return SuccessTip.create(queryHouseUserCommunityStatusDao.updateUserCommunityStatusByUserId(entity));
        } else {
            Integer affected = 0;
            try {
                affected = houseUserCommunityStatusService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
            return SuccessTip.create(affected);
        }
    }


    //    获取楼栋信息
    @GetMapping("/building")
    public Tip getBuildingInfo() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        List<HousePropertyBuilding> housePropertyBuildingList = housePropertyBuildingDao.queryHousePropertyBuildingByUserId(JWTKit.getUserId());

        return SuccessTip.create(housePropertyBuildingList);
    }

    //    查询楼栋下面的房子
    @GetMapping("/asset/{buildingId}")
    public Tip getAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

//        获取整栋房屋
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord> houseAssetList = queryHouseAssetDao.findHouseAssetPage(null, houseAssetRecord, null, null, null, null, null);

//        获取用户房屋
        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);


        List<Map<String, Object>> headerListMap = new ArrayList<>();

        for (int i = 0; i < houseAssetList.size(); i++) {

//            获取第十层单元号和面积
            if (houseAssetList.get(i).getFloor() == 10) {

                Map<String, Object> map = new HashMap<>();
                String houseNumber = houseAssetList.get(i).getHouseNumber();
                BigDecimal area = houseAssetList.get(i).getRealArea();
                String unitNumber = houseNumber.substring(houseNumber.length() - 2);

                map.put("unitNumber", unitNumber);
                map.put("area", area);

                headerListMap.add(map);

            }


//            判断是否有人居住
            for (int j = 0; j < recordList.size(); j++) {

                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())) {
                    houseAssetList.get(i).setExistUser(true);
                    houseAssetList.get(i).setUserId(recordList.get(j).getUserId());

                      /*
                是否是最终确认
                 */
                    if (recordList.get(j).getFinalFlag() != null && recordList.get(j).getFinalFlag().equals(HouseUserAsset.FINAL_FLAG_CONFIRM)) {
                        houseAssetList.get(i).setFinalFlag(HouseUserAsset.FINAL_FLAG_CONFIRM);
                    } else {
                        houseAssetList.get(i).setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
                    }

                     /*
                是否是自己的
                 */
                    if (recordList.get(j).getUserId().equals(JWTKit.getUserId())) {
                        houseAssetList.get(i).setMyselfAsset(true);
                    }

                    /*
                    是否二房东
                     */
                    if (recordList.get(j).getUserType().equals(HouseUserAsset.USER_TYPE_PRINCIPAL)) {
                        houseAssetList.get(i).setPrincipal(true);
                    }

                    if (recordList.get(j).getUnlike().equals(HouseUserAsset.UNLIKE_STATUS_UNLIKE)) {
                        houseAssetList.get(i).setUnlikeStatus(true);
                    }
                }
            }
        }

        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(buildingId);
        if (housePropertyBuilding.getUnits() <= 0) {
            throw new BusinessException(BusinessCode.CodeBase, "此小区没有房子");
        }

//        List<BigDecimal> unitAreas = new ArrayList<>();
//        for (int i = 0; i < housePropertyBuilding.getUnits(); i++) {
//            if (houseAssetList.get(i).getArea() != null) {
//                unitAreas.add(houseAssetList.get(i).getRealArea());
//            }
//        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("header", headerListMap);
//        jsonObject.put("header", unitAreas);
        jsonObject.put("data", houseAssetList);


        return SuccessTip.create(jsonObject);
    }


    //    查询楼栋下面的房子 带用户信息
    @GetMapping("/userInfoAsset/{buildingId}")
    public Tip getUserInfoAssetsByBuildingId(@PathVariable("buildingId") Long buildingId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setBuildingId(buildingId);
        List<HouseAssetRecord> houseAssetList = queryHouseAssetDao.findHouseAssetPage(null, houseAssetRecord, null, null, null, null, null);

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);

        Long uStart = System.currentTimeMillis();
        for (int i = 0; i < houseAssetList.size(); i++) {
//            判断是否有人居住
            for (int j = 0; j < recordList.size(); j++) {
                if (recordList.get(j).getAssetId().equals(houseAssetList.get(i).getId())) {

//                    添加用户信息
                    EndpointUserModel endpointUser = queryEndpointUserDao.queryMasterModel(recordList.get(j).getUserId());
                    if (endpointUser != null) {
                        if (endpointUser.getPhone() != null) {
                            houseAssetList.get(i).setUserPhone(endpointUser.getPhone());
                        }
                        if (endpointUser.getAvatar() != null) {
                            houseAssetList.get(i).setUserAvatar(endpointUser.getAvatar());
                        }
                        if (endpointUser.getName() != null) {
                            houseAssetList.get(i).setUsername(endpointUser.getName());
                        }
                    }
                    houseAssetList.get(i).setExistUser(true);
                }
                if (recordList.get(j).getUserId().equals(JWTKit.getUserId())) {
                    houseAssetList.get(i).setMyselfAsset(true);
                }
            }
        }

        return SuccessTip.create(houseAssetList);
    }

    //    获取房屋详情
    @GetMapping("/asset/details/{assetId}")
    public Tip getAssetDetails(@PathVariable("assetId") Long assetId) {

        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setId(assetId);
        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.findHouseAssetPage(null, houseAssetRecord, null, null, null, null, null);
        if (houseAssetRecordList != null && houseAssetRecordList.size() > 0) {
            SuccessTip.create(houseAssetRecordList.get(0));
        }
        return SuccessTip.create();
    }

    //    获取用户房子信息
    @GetMapping("/user/userAsset")
    public Tip getUserUnite() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        Long star = System.currentTimeMillis();

        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId == null) {
            return SuccessTip.create();
        }

        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
        userAssetRecord.setUserId(JWTKit.getUserId());
        userAssetRecord.setCommunityId(communityId);
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);

        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null, houseRentAssetRecord, null, null, null, null, null);

        //            是否存在置换房屋
        HouseAssetExchangeRequestRecord exchangeRequestRecord = new HouseAssetExchangeRequestRecord();
        exchangeRequestRecord.setUserId(JWTKit.getUserId());
        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null, exchangeRequestRecord, null, null, null, null, null);


        /**
         * 查询房屋买卖情况
         */
        QueryWrapper<HouseAssetTransaction> transactionQueryWrapper = new QueryWrapper<>();
        transactionQueryWrapper.eq(HouseAssetTransaction.USER_ID,JWTKit.getUserId());
        List<HouseAssetTransaction> houseAssetTransactions = houseAssetTransactionMapper.selectList(transactionQueryWrapper);


        for (int i = 0; i < houseUserAssets.size(); i++) {
            //        连接地址信息
            String address = houseUserAssets.get(i).getAddress();
            String buildingCode = houseUserAssets.get(i).getBuildingCode();
            String number = houseUserAssets.get(i).getRoomNumber();
            houseUserAssets.get(i).setAddressDetail("".concat(address).concat(" ").concat(buildingCode).concat(" ").concat(number));

//            设置 出租 托管 是否有置换
            if (houseUserAssets.get(i).getTrust() != null && houseUserAssets.get(i).getTrust() > 0) {
                houseUserAssets.get(i).setExistTrust(true);
            }

//            出租状态
            for (HouseRentAssetRecord record : houseRentAssetRecordList) {
                if (record.getAssetId() != null && record.getAssetId().equals(houseUserAssets.get(i).getAssetId())) {
                    houseUserAssets.get(i).setExistRent(true);
                    houseUserAssets.get(i).setRentPrice(record.getPrice());
                    houseUserAssets.get(i).setRentStatus(record.getStatus());
                }
            }

//            是否有换房记录
            for (HouseAssetExchangeRequest houseAssetExchangeRequest : houseAssetExchangeRequestList) {
                if (houseUserAssets.get(i).getAssetId().equals(houseAssetExchangeRequest.getAssetId())) {
                    houseUserAssets.get(i).setExistExchange(true);
                }
            }

            if (houseAssetTransactions!=null && houseAssetTransactions.size()>0){

                for (HouseAssetTransaction houseAssetTransaction:houseAssetTransactions){
                    if (houseAssetTransaction.getAssetId()!=null && houseUserAssets.get(i).getAssetId().equals(houseAssetTransaction.getAssetId())) {
                        houseUserAssets.get(i).setTransaction(true);
                    }
                }

            }


        }

        return SuccessTip.create(houseUserAssets);
    }


    //    添加房子
    @PostMapping("/user/userAsset")
    public Tip createHousePropertyUserUnit(@RequestBody HouseUserAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (entity.getAssetId() == null || "".equals(entity.getAssetId())) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为必填项");
        }

        if (endUserBlacklistService.isUserShield(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.CodeBase, "已被拉黑");
        }

        Integer affected = 0;


//        产权记录
        HouseAssetLog houseAssetLog = new HouseAssetLog();
        houseAssetLog.setUserId(JWTKit.getUserId());


//        判断用户是是房东 还是二房东
        List<Integer> typeList = userAccountUtility.getUserTypeList(JWTKit.getUserId());
        if (typeList == null || typeList.size() <= 0) {
            throw new BusinessException(BusinessCode.NoPermission, "无权限");
        }

        Integer userType = 2;

        if (typeList.contains(EndUserTypeSetting.USER_TYPE_SECOND_LANDLORD)) {
            userType = 2;
        } else {
            userType = 1;
        }


        entity.setUserType(userType);
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        houseUserAssetRecord.setUserType(userType);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, houseUserAssetRecord, null, null, null, null, null);

        /*
        判断这个房子是否有人，没人直接进行增加 有人判断是否是最终用户
         */

        if (houseUserAssetRecordList == null || houseUserAssetRecordList.size() == 0) {
            entity.setUserId(JWTKit.getUserId());
            try {
                affected = houseUserAssetService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }


            houseAssetLog.setAssetId(entity.getAssetId());
            affected += houseAssetLogMapper.insert(houseAssetLog);


            houseAssetExchangeRequestService.addSameFloorExchangeRequest(JWTKit.getUserId());


            return SuccessTip.create(affected);
        } else {
            /*
            判读房子是否是自己的
             */
            if (!(houseUserAssetRecordList.get(0).getUserId().equals(JWTKit.getUserId()))) {
                /*
                判断是否是最终用户 不是可以进行修改
                 */
                if (!(HouseUserAsset.FINAL_FLAG_CONFIRM.equals(houseUserAssetRecordList.get(0).getFinalFlag()))) {

                    //        超过换房限制次数 发送邮件
                    QueryWrapper<HouseAssetLog> houseAssetLogQueryWrapper = new QueryWrapper<>();
                    houseAssetLogQueryWrapper.eq(HouseAssetLog.ASSET_ID, entity.getAssetId());
                    List<HouseAssetLog> houseAssetLogList = houseAssetLogMapper.selectList(houseAssetLogQueryWrapper);
                    if (houseAssetLogList != null && houseAssetLogList.size() >= CHANGE_ASSET_LIMIT) {
                        houseEmailService.sendMoreThanAssetLimit(houseUserAssetRecordList.get(0).getUserId(), JWTKit.getUserId(), queryHouseAssetDao.queryMasterModel(entity.getAssetId()));
                    }

                    entity.setId(houseUserAssetRecordList.get(0).getId());
                    entity.setUserId(JWTKit.getUserId());
                    entity.setUnlike(HouseUserAsset.UNLIKE_STATUS_LIKE);
                    entity.setLocked(HouseUserAsset.LOCKED_STATUS_UNLOCKED);

                    HouseAssetComplaint complaint = new HouseAssetComplaint();
                    complaint.setUserId(JWTKit.getUserId());
                    complaint.setOldUserId(houseUserAssetRecordList.get(0).getUserId());
                    complaint.setAssetId(houseUserAssetRecordList.get(0).getAssetId());


//                    添加记录
                    houseAssetLog.setOldUserId(houseUserAssetRecordList.get(0).getUserId());
                    houseAssetLog.setAssetId(houseUserAssetRecordList.get(0).getAssetId());
                    affected += houseAssetLogMapper.insert(houseAssetLog);


                    affected = houseUserAssetService.updateMaster(entity);
                    if (affected > 0) {
                        /*
                        添加产权申述
                         */
                        try {
                            affected += houseAssetComplaintService.createMaster(complaint);

//                            发送申诉消息
                            if (affected > 0) {
                                houseEmailService.sendComplaintAssetInfo(complaint);
                            }

                        } catch (DuplicateKeyException e) {
                            throw new BusinessException(BusinessCode.DuplicateKey);
                        }
                    }


                    //            进行同层添加
                    houseAssetExchangeRequestService.addSameFloorExchangeRequest(JWTKit.getUserId());

                    return SuccessTip.create(affected);
                } else {
                    throw new BusinessException(BusinessCode.CodeBase, "该资产已被确认");
                }

            }
        }
        return SuccessTip.create();

    }

    //    修改用户的资产
    @BusinessLog(name = "HousePropertyUserUnit", value = "update HousePropertyUserUnit")
    @PutMapping("/user/userAsset/{id}")
    @ApiOperation(value = "修改 HousePropertyUserUnit", response = HouseUserAsset.class)
    public Tip updateHousePropertyUserUnit(@PathVariable Long id, @RequestBody HouseUserAsset entity) {
        entity.setId(id);
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        return SuccessTip.create(houseUserAssetService.updateMaster(entity));
    }

    //    删除用户资产
    @BusinessLog(name = "HousePropertyUserUnit", value = "delete HousePropertyUserUnit")
    @DeleteMapping("/user/userAsset/{id}")
    @ApiOperation("删除 HousePropertyUserUnit")
    public Tip deleteHousePropertyUserUnit(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectById(id);
        if (houseUserAsset != null && houseUserAsset.getUserId().equals(JWTKit.getUserId())) {
            return SuccessTip.create(houseUserAssetService.deleteUserAsset(JWTKit.getUserId(), houseUserAsset.getAssetId()));
        }
        return SuccessTip.create();

    }


    //    我的回迁房--附带换房需求
    @GetMapping("/getMyReturnRoom")
    public Tip getMyReturnRoom() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

//        获取当前小区
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId == null) {
            return SuccessTip.create();
        }

//        HouseUserAssetRecord userAssetRecord = new HouseUserAssetRecord();
//        userAssetRecord.setUserId(JWTKit.getUserId());
//        userAssetRecord.setCommunityId(communityId);
//        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.findHouseUserAssetPage(null, userAssetRecord, null, null, null, null, null);
        Long userId = JWTKit.getUserId();
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.pageMyHouse(userId,communityId);

        // 查询匹配到的房屋
        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID, JWTKit.getUserId());
        List<HouseAssetMatchLog> matchLogList = houseAssetMatchLogMapper.selectList(matchLogQueryWrapper);

        // 获取每平方物业管理费配置值
        String fieldValue = houseConfigService.getFieldValueByFieldGroupNameAndFieldName(
                MyHouseAssetConst.My_HOUSE_ASSET_CONFIG_GROUP_NAME,
                MyHouseAssetConst.PROPERTY_MANAGEMENT_FEE_CONFIG_FIELD_NAME
        );
        if (fieldValue == null) {
            logger.info(
                    "物业管理费配置获取失败，获取参数：配置分组=" + "'" + MyHouseAssetConst.My_HOUSE_ASSET_CONFIG_GROUP_NAME
                    + "'，配置字段名=" + "'" + MyHouseAssetConst.PROPERTY_MANAGEMENT_FEE_CONFIG_FIELD_NAME + "'"
                    );
            throw new BusinessException(BusinessCode.SYSTEM_GENERAL_ERROR);
        }
        // 因为从配置中取出来的类型都是String所以需要进行类型转换
        BigDecimal perSquarePropertyManagementFee = new BigDecimal(fieldValue).setScale(2,RoundingMode.HALF_UP);
        // 计算物业管理费
        for (HouseUserAssetRecord record : houseUserAssets) {
            BigDecimal realArea = record.getRealArea().setScale(2,RoundingMode.HALF_UP);
            BigDecimal propertyManagementFee = realArea.multiply(perSquarePropertyManagementFee).setScale(2,RoundingMode.HALF_UP);
            record.setPropertyManagementFee(propertyManagementFee);
        }

        for (int i = 0; i < houseUserAssets.size(); i++) {

//            匹配的的房屋id列表
            List<Long> assetIds = new ArrayList<>();

            for (HouseAssetMatchLog houseAssetMatchLog : matchLogList) {
                if (houseUserAssets.get(i).getAssetId().equals(houseAssetMatchLog.getOwnerAssetId())) {
                    assetIds.add(houseAssetMatchLog.getMathchedAssetId());
                }
            }

            if (assetIds != null && assetIds.size() > 0) {
                houseUserAssets.get(i).setExistExchange(true);
                houseUserAssets.get(i).setExchangeRequestRecordList(queryHouseAssetDao.getHouseAssetList(assetIds));
            }
        }

        houseUserAssetService.setUserAssetArea(houseUserAssets);

        return SuccessTip.create(houseUserAssets);
    }

    //    锁定房子
    @PutMapping("/locked/{id}")
    public Tip lockedAsset(@PathVariable("id") Long assetId) {
        Integer affect = 0;
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.ASSET_ID, assetId);
        List<HouseUserAsset> houseUserAssetList = houseUserAssetMapper.selectList(queryWrapper);
        if (houseUserAssetList != null && houseUserAssetList.size() > 0) {
            for (HouseUserAsset houseUserAsset : houseUserAssetList) {
                houseUserAsset.setLocked(HouseUserAsset.LOCKED_STATUS_LOCKED);

                affect += houseUserAssetMapper.updateById(houseUserAsset);
            }

            return SuccessTip.create(affect);

        }
        return SuccessTip.create(affect);
    }

    //    解除锁定
    @PutMapping("/unlocked/{id}")
    public Tip unlockedAsset(@PathVariable("id") Long assetId) {
        Integer affect = 0;
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.ASSET_ID, assetId);
        List<HouseUserAsset> houseUserAssetList = houseUserAssetMapper.selectList(queryWrapper);
        if (houseUserAssetList != null && houseUserAssetList.size() > 0) {
            for (HouseUserAsset houseUserAsset : houseUserAssetList) {
                houseUserAsset.setLocked(HouseUserAsset.LOCKED_STATUS_UNLOCKED);

                affect += houseUserAssetMapper.updateById(houseUserAsset);
            }

            return SuccessTip.create(affect);

        }
        return SuccessTip.create(affect);
    }

    //    设置不喜欢房子
    @PutMapping("/unlike/{id}")
    public Tip unlikeAsset(@PathVariable("id") Long assetId, @RequestBody(required = false) HouseUserAsset entity) {
        Integer affect = 0;
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.ASSET_ID, assetId);
        List<HouseUserAsset> houseUserAssetList = houseUserAssetMapper.selectList(queryWrapper);
        if (houseUserAssetList != null && houseUserAssetList.size() > 0) {
            for (HouseUserAsset houseUserAsset : houseUserAssetList) {
                houseUserAsset.setUnlike(HouseUserAsset.UNLIKE_STATUS_UNLIKE);
                if (entity != null) {
                    houseUserAsset.setNote(entity.getNote());
                }


                affect += houseUserAssetMapper.updateById(houseUserAsset);
            }

            return SuccessTip.create(affect);

        }
        return SuccessTip.create(affect);
    }

    //    设置房子为喜欢
    @PutMapping("/like/{id}")
    public Tip likeAsset(@PathVariable("id") Long assetId) {
        Integer affect = 0;
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.ASSET_ID, assetId);
        List<HouseUserAsset> houseUserAssetList = houseUserAssetMapper.selectList(queryWrapper);
        if (houseUserAssetList != null && houseUserAssetList.size() > 0) {
            for (HouseUserAsset houseUserAsset : houseUserAssetList) {
                houseUserAsset.setUnlike(HouseUserAsset.UNLIKE_STATUS_LIKE);
                houseUserAsset.setNote(null);
                affect += houseUserAssetMapper.updateById(houseUserAsset);
            }

            return SuccessTip.create(affect);

        }
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "上下排序我的回迁房",httpMethod = "PUT")
    @PutMapping("/moveHouseAsset")
    public Tip userMoveHouseAssetSequence(@RequestBody JSONObject params) {
        // 参数判断
        Long id = params.getLong("id");
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");
        Integer direction = params.getInteger("direction");
        if (direction == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"direction cannot null");

        // 获取用户信息
        Long userId = JWTKit.getUserId();
        HouseUserAssetModel userAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (userAssetModel == null) {
            logger.error("没有找到该房产记录,用户id：" + userId + "，房产id：" + id);
            throw new BusinessException(BusinessCode.CodeBase);
        }
        // 获取社区信息
        Long communityId = null;
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(userId);
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
        }
        if (communityId == null) {
            logger.error("社区信息获取失败，用户id：" + userId);
            throw new BusinessException(BusinessCode.CodeBase);
        }

        // 执行变更排序
        return SuccessTip.create(houseUserAssetService.updateMyHouseSequence(userId,communityId,id,direction));
    }


    @ApiOperation(value = "更新我的回迁房的水电编号", httpMethod = "PUT")
    @PutMapping("house-water-electricity")
    public Tip updateMyHouseWaterElectricityNumber(@RequestBody HouseUserAsset houseUserAsset) {
        // 获取参数，只获取必要参数即可
        Long userId = JWTKit.getUserId();
        if (userId == null) throw new BusinessException(BusinessCode.UserNotExisted,"用户不存在");
        // 用户房产id
        Long id = houseUserAsset.getId();
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");
        // 水编号
        String waterNumber = houseUserAsset.getWaterNumber();
        // 电编号
        String electricityNumber = houseUserAsset.getElectricityNumber();
        // 燃气编号
        String gasNumber = houseUserAsset.getGasNumber();

        return SuccessTip.create(houseUserAssetService.updateMyHouseWaterElectricity(id,userId,waterNumber,electricityNumber,gasNumber));
    }
}
