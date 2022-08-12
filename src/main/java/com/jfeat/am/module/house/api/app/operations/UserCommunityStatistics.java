package com.jfeat.am.module.house.api.app.operations;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.HouseStatisticsDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyCommunityDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.house.services.utility.HouseStatisticsTools;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/api/u/house/operations/UserCommunityStatistics")
public class UserCommunityStatistics {

    @Resource
    Authentication authentication;

    @Resource
    HouseStatisticsDao houseStatisticsDao;

    @Resource
    HouseStatisticsTools houseStatisticsTools;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @GetMapping
    public Tip getCommunityStatistics(@RequestParam(value = "communityId",required = false) Long communityId){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

//        判读是否带了小区id 当没带小区id是 取当前用户的小区状态
        if (communityId==null || "".equals(communityId)){
            HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
            communityStatusRecord.setUserId(JWTKit.getUserId());
            List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null,communityStatusRecord,null,null,null,null,null);
            if (communityStatusRecordList!=null && communityStatusRecordList.size()==1){
                communityId = communityStatusRecordList.get(0).getCommunityId();
            }
            if (communityId==null){
                return SuccessTip.create();
            }
        }

        HousePropertyCommunityModel housePropertyCommunityModel = queryHousePropertyCommunityDao.queryMasterModel(communityId);

        //list 0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数 10房东数 11-供应商数
        List<Integer> houseStatistics =   houseStatisticsDao.communityStatistics(communityId);


        JSONObject result = new JSONObject();

        result.put("communityName",housePropertyCommunityModel.getCommunity());

        JSONObject houseNumber = new JSONObject();
        houseNumber.put("房产数",houseStatistics.get(0));
        result.put("houseNumber",houseNumber);

        JSONObject invalidHouse = new JSONObject();
        invalidHouse.put("无效房子数",houseStatistics.get(1));
        result.put("invalidHouse",invalidHouse);

        JSONObject bungalow = new JSONObject();
        bungalow.put("平房数",houseStatistics.get(2));
        result.put("bungalow",bungalow);

        JSONObject multipleNumber = new JSONObject();
        multipleNumber.put("复式数",houseStatistics.get(3));
        result.put("multipleNumber",multipleNumber);

        JSONObject buildingNumber = new JSONObject();
        buildingNumber.put("楼栋数",houseStatistics.get(4));
        result.put("buildingNumber",buildingNumber);

        JSONObject unitNumber = new JSONObject();
        unitNumber.put("单元数",houseStatistics.get(5));
        result.put("unitNumber",unitNumber);

        JSONObject houseTypeNumber = new JSONObject();
        houseTypeNumber.put("户型数",houseStatistics.get(6));
        result.put("houseTypeNumber",houseTypeNumber);

        JSONObject houseExchangeNumber = new JSONObject();
        houseExchangeNumber.put("换房需求数",houseStatistics.get(7));
        result.put("houseExchangeNumber",houseExchangeNumber);

        JSONObject houseExchangeMatchNumber = new JSONObject();
        houseExchangeMatchNumber.put("换房记录数",houseStatistics.get(8));
        result.put("houseExchangeMatchNumber",houseExchangeMatchNumber);

        JSONObject parkingNumber = new JSONObject();
        parkingNumber.put("停车位数",houseStatistics.get(9));
        result.put("parkingNumber",parkingNumber);

        JSONObject landlord = new JSONObject();
        landlord.put("房东数",houseStatistics.get(10));
        result.put("landlord",landlord);

        JSONObject supplier = new JSONObject();
        supplier.put("供应商数",houseStatistics.get(11));
        result.put("supplier",supplier);


        return SuccessTip.create(result);
    }
}
