package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseAssetMatchLogPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/*
置换记录
 */

@RestController
@RequestMapping("/api/u/house/operations/userAssetExchangeManage")
public class UserAssetExchangeManageEndpoint {

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    Authentication authentication;


    /*
    获取房屋交换成功信息
     */
    @GetMapping("/getUserMatchResult")
    public Tip queryHouseAssetMatchLogPage(Page<HouseAssetMatchLogRecord> page,
                                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                           // for tag feature query
                                           @RequestParam(name = "tag", required = false) String tag,
                                           // end tag
                                           @RequestParam(name = "search", required = false) String search,

                                           @RequestParam(name = "ownerUserId", required = false) Long ownerUserId,

                                           @RequestParam(name = "ownerAssetId", required = false) Long ownerAssetId,

                                           @RequestParam(name = "matchedUserId", required = false) Long matchedUserId,

                                           @RequestParam(name = "mathchedAssetId", required = false) Long mathchedAssetId,

                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam(name = "createTime", required = false) Date createTime,
                                           @RequestParam(name = "orderBy", required = false) String orderBy,
                                           @RequestParam(name = "sort", required = false) String sort) {
        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }



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

        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setOwnerUserId(ownerUserId);
        record.setOwnerAssetId(ownerAssetId);
        record.setMatchedUserId(matchedUserId);
        record.setMathchedAssetId(mathchedAssetId);
        record.setCreateTime(createTime);
        List<HouseAssetMatchLogRecord> houseAssetMatchLogPage = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(page, record, tag, search, orderBy, null, null);

        /*
        添加匹配双方个人信息和房产信息
         */
        for (int i=0;i<houseAssetMatchLogPage.size();i++){
            HouseAsset ownerHouseAsset=  queryHouseAssetDao.queryMasterModel(houseAssetMatchLogPage.get(i).getOwnerAssetId());
            EndpointUser ownerEndUser = queryEndpointUserDao.queryMasterModel(houseAssetMatchLogPage.get(i).getOwnerUserId());
            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogPage.get(i).getMathchedAssetId());
            EndpointUser matchedEndUser = queryEndpointUserDao.queryMasterModel(houseAssetMatchLogPage.get(i).getMatchedUserId());
            if (ownerHouseAsset!=null && ownerEndUser!=null && matchedHouseAsset!=null &&matchedEndUser!=null){
                houseAssetMatchLogPage.get(i).setOwnerBuilding(ownerHouseAsset.getBuildingCode());
                houseAssetMatchLogPage.get(i).setOwnerCommunity(ownerHouseAsset.getCommunityName());
                houseAssetMatchLogPage.get(i).setOwnerNumber(ownerHouseAsset.getNumber());

                houseAssetMatchLogPage.get(i).setMatchedBuilding(matchedHouseAsset.getBuildingCode());
                houseAssetMatchLogPage.get(i).setMatchedCommunity(matchedHouseAsset.getCommunityName());
                houseAssetMatchLogPage.get(i).setMatchedNumber(matchedHouseAsset.getNumber());

            }

        }

        page.setRecords(houseAssetMatchLogPage);

        return SuccessTip.create(page);
    }
}
