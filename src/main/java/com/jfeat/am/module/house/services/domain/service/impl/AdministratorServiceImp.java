package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.AdministratorService;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AdministratorServiceImp implements AdministratorService {


    //        删除置业顾问申请数据
    @Resource
    HouseApplicationIntermediaryMapper houseApplicationIntermediaryMapper;

    //        删除运维申请数据
    @Resource
    HouseApplicationOperationsMapper houseApplicationOperationsMapper;
    //        删除看房预约数据
    @Resource
    HouseAppointmentMapper houseAppointmentMapper;

    //        删除产权申诉数据
    @Resource
    HouseAssetComplaintMapper houseAssetComplaintMapper;

    //        删除房屋买卖数据
    @Resource
    HouseAssetDemandSupplyMapper houseAssetDemandSupplyMapper;

    //        删除交换请求数据
    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

    //        删除匹配成功数据
    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    //        删除装修计划数据
    @Resource
    HouseDecoratePlanMapper houseDecoratePlanMapper;

    //        删除家居数据
    @Resource
    HouseDecoratePlanFunitureMapper houseDecoratePlanFunitureMapper;

    //        删除方数买卖数据
    @Resource
    HouseEquityDemandSupplyMapper houseEquityDemandSupplyMapper;

    //        删除出租数据
    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    //        删除出租家居清单
    @Resource
    HouseRentSupportFacilitiesMapper houseRentSupportFacilitiesMapper;

    //        删除用户地址
    @Resource
    HouseUserAddressMapper houseUserAddressMapper;

    //        删除用户房屋
    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    //        删除用户小区状态
    @Resource
    HouseUserCommunityStatusMapper houseUserCommunityStatusMapper;

    @Resource
    HouseUserDecoratePlanMapper houseUserDecoratePlanMapper;

    //        删除用户装修家居
    @Resource
    HouseUserDecorateFunitureMapper houseUserDecorateFunitureMapper;

    @Override
    @Transactional
    public int deleteAllUserData() {
        Integer affect = 0;

//        删除置业顾问申请数据
        affect += houseApplicationIntermediaryMapper.delete(new QueryWrapper<HouseApplicationIntermediary>());

//        删除运维申请数据
        affect += houseApplicationOperationsMapper.delete(new QueryWrapper<HouseApplicationOperations>());

//        删除看房预约数据
        affect += houseAppointmentMapper.delete(new QueryWrapper<HouseAppointment>());

//        删除产权申诉数据
        affect += houseAssetComplaintMapper.delete(new QueryWrapper<HouseAssetComplaint>());

//        删除房屋买卖数据
        affect += houseAssetDemandSupplyMapper.delete(new QueryWrapper<HouseAssetDemandSupply>());

//        删除交换请求数据
        affect += houseAssetExchangeRequestMapper.delete(new QueryWrapper<HouseAssetExchangeRequest>());

//        删除匹配成功数据
        affect += houseAssetMatchLogMapper.delete(new QueryWrapper<HouseAssetMatchLog>());

//        删除装修计划数据
        affect += houseDecoratePlanMapper.delete(new QueryWrapper<HouseDecoratePlan>());

//        删除家居数据
        affect += houseDecoratePlanFunitureMapper.delete(new QueryWrapper<HouseDecoratePlanFuniture>());

//        删除方数买卖数据
        affect += houseEquityDemandSupplyMapper.delete(new QueryWrapper<HouseEquityDemandSupply>());

//        删除出租数据
        affect += houseRentAssetMapper.delete(new QueryWrapper<HouseRentAsset>());

//        删除出租家居清单
        affect += houseRentSupportFacilitiesMapper.delete(new QueryWrapper<HouseRentSupportFacilities>());

//        删除用户地址
        affect += houseUserAddressMapper.delete(new QueryWrapper<HouseUserAddress>());

//        删除用户房屋
        affect += houseUserAssetMapper.delete(new QueryWrapper<HouseUserAsset>());

//        删除用户小区状态
        affect += houseUserCommunityStatusMapper.delete(new QueryWrapper<HouseUserCommunityStatus>());

//        删除用户装修家居
        affect += houseUserDecoratePlanMapper.delete(new QueryWrapper<HouseUserDecoratePlan>());

//        删除用户装修计划
        affect += houseUserDecorateFunitureMapper.delete(new QueryWrapper<HouseUserDecorateFuniture>());
        return affect;
    }
}
