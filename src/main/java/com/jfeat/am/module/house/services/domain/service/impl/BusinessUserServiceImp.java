package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.users.account.services.domain.service.BusinessUserService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import com.jfeat.users.weChatMiniprogram.services.domain.dao.SysThirdPartyUserMapper;
import com.jfeat.users.weChatMiniprogram.services.domain.model.wx.SysThirdPartyUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BusinessUserServiceImp implements BusinessUserService {

    SysThirdPartyUserMapper sysThirdPartyUserMapper;

    UserAccountMapper userAccountMapper;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    HouseUserDecoratePlanMapper houseUserDecoratePlanMapper;

    @Resource
    HouseUserDecorateFunitureMapper houseUserDecorateFunitureMapper;

    @Resource
    HouseAssetComplaintMapper houseAssetComplaintMapper;

    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Override
    @Transactional
    public Integer delEndUser(Long aLong) {



        Integer effect = 0;

        QueryWrapper<SysThirdPartyUser> sysThirdPartyUserMapperQueryWrapper = new QueryWrapper<>();
        sysThirdPartyUserMapperQueryWrapper.eq("user_id", aLong);
        effect += sysThirdPartyUserMapper.delete(sysThirdPartyUserMapperQueryWrapper);

        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", aLong);
        effect += sysThirdPartyUserMapper.delete(sysThirdPartyUserMapperQueryWrapper);

//        删除资产表记录
        QueryWrapper<HouseUserAsset> userAssetQueryWrapper = new QueryWrapper<>();
        userAssetQueryWrapper.eq(HouseUserAsset.USER_ID, aLong);
        effect += houseUserAssetMapper.delete(userAssetQueryWrapper);

//        删除用户装修计划地址
        QueryWrapper<HouseUserDecoratePlan> houseUserDecoratePlanQueryWrapper = new QueryWrapper<>();
        houseUserDecoratePlanQueryWrapper.eq(HouseUserDecoratePlan.USER_ID, aLong);
        effect += houseUserDecoratePlanMapper.delete(houseUserDecoratePlanQueryWrapper);

//        删除用户装修计划家居
        QueryWrapper<HouseUserDecorateFuniture> houseUserDecorateFunitureQueryWrapper = new QueryWrapper<>();
        houseUserDecorateFunitureQueryWrapper.eq(HouseUserDecorateFuniture.USER_ID, aLong);
        effect += houseUserDecorateFunitureMapper.delete(houseUserDecorateFunitureQueryWrapper);

//        删除用户申诉
        QueryWrapper<HouseAssetComplaint> houseAssetComplaintQueryWrapper = new QueryWrapper<>();
        houseAssetComplaintQueryWrapper.eq(HouseAssetComplaint.USER_ID, aLong);
        effect += houseAssetComplaintMapper.delete(houseAssetComplaintQueryWrapper);

//        删除交换请求
        QueryWrapper<HouseAssetExchangeRequest> houseAssetExchangeRequestQueryWrapper = new QueryWrapper<>();
        houseAssetExchangeRequestQueryWrapper.eq(HouseAssetExchangeRequest.USER_ID, aLong);
        effect += houseAssetExchangeRequestMapper.delete(houseAssetExchangeRequestQueryWrapper);

//        删除匹配成功记录
        QueryWrapper<HouseAssetMatchLog> houseAssetMatchLogQueryWrapper = new QueryWrapper<>();
        houseAssetMatchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID, aLong);
        effect += houseAssetMatchLogMapper.delete(houseAssetMatchLogQueryWrapper);

//        删除别人匹配自己成功记录
        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.MATCHED_USER_ID, aLong);
        effect += houseAssetMatchLogMapper.delete(matchLogQueryWrapper);

//        删除出租房子
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.LANDLORD_ID, aLong);
        effect += houseRentAssetMapper.delete(houseRentAssetQueryWrapper);

//        删除预约信息


        return effect;
    }
}
