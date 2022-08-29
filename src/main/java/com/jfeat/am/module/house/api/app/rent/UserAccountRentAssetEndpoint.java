package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/rent/userRentAsset")
public class UserAccountRentAssetEndpoint {

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseUserAssetService houseUserAssetService;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseAssetService houseAssetService;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @Resource
    HouseSupportFacilitiesTypeOverModelService houseSupportFacilitiesTypeOverModelService;

    @Resource
    HouseSupportFacilitiesService houseSupportFacilitiesService;


    @Resource
    HouseSurroundFacilitiesTypeOverModelService houseSurroundFacilitiesTypeOverModelService;

    /*
    用户出租自己的房子 填写照片 价格 描述信息
     */
    @PostMapping("/userRentAsset")
    public Tip userRentAsset(@RequestBody HouseRentAsset entity){

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId()==null){
            throw new BusinessException(BusinessCode.EmptyNotAllowed,"assetId不能为空");
        }
        return SuccessTip.create(houseRentAssetService.createUserRentAsset(entity));
    }

    /*
    用户出租详情
     */
    @GetMapping("/userRentAssetDetails/{assetId}")
    public Tip userRentAssetDetails(@PathVariable("assetId") Long assetId){

        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(assetId);
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null,houseRentAssetRecord
        ,null,null,null,null,null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);

        /*
        判断是否出租
         */
        if (houseAssetModel==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有找到房子,请重试");
        }
        if (houseRentAssetRecordList==null || houseRentAssetRecordList.size()==0){
           houseRentAssetRecord.setHouseAssetModel(houseAssetModel);
           houseRentAssetRecordList.add(houseRentAssetRecord);
        }else {
            HouseRentAsset rentAsset = houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, houseRentAssetRecordList.get(0).getId());
            houseRentAssetRecordList.get(0).setHouseAssetModel(houseAssetModel);
            houseRentAssetRecordList.get(0).setExtra(rentAsset.getExtra());



        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(houseRentAssetRecordList.get(0));
        if (houseAssetModel.getCommunityId()!=null){
            jsonObject.put("facilities",houseSurroundFacilitiesTypeOverModelService.getCommunityFacilities(houseAssetModel.getCommunityId()));
        }
        jsonObject.put("supportFacilities",houseSupportFacilitiesService.getRentHouseSupportFacilitiesStatus(houseRentAssetRecordList.get(0).getAssetId(),houseSupportFacilitiesTypeOverModelService.getHouseSupportFacilitiesTypeItem()));
        return SuccessTip.create(jsonObject);
    }

    /*
    用户下架
     */
    @DeleteMapping("/undercarriage/{assetId}")
    public Tip deleteRentAsset(@PathVariable("assetId") Long assetId){
        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(assetId);
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> houseRentAssetRecordList = queryHouseRentAssetDao.findHouseRentAssetPage(null,houseRentAssetRecord
                ,null,null,null,null,null);

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);

        /*
        判断是否出租
         */
        if (houseAssetModel==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有找到房子,请重试");
        }

        /*
        删除出租房子
         */
        if (houseRentAssetRecordList!=null && houseRentAssetRecordList.size()==1){
            SuccessTip.create(houseRentAssetService.deleteMaster(houseRentAssetRecordList.get(0).getId()));
        }
        return SuccessTip.create();

    }
}
