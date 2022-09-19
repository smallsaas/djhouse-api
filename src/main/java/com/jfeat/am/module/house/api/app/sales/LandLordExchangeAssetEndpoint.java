package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetExchangeRequestDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/sales/landLordExchangeAsset")
public class LandLordExchangeAssetEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    TenantUtility tenantUtility;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;

//    获取指定小区不喜欢房产列表
    @GetMapping("/unlikeList")
    public Tip getUnlikeAssetList(Page<HouseUserAssetRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(name = "search", required = false) String search,
                                  @RequestParam("communityId") Long communityId){

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setCommunityId(communityId);
        record.setUnlike(HouseUserAsset.UNLIKE_STATUS_UNLIKE);
        record.setLocked(HouseUserAsset.LOCKED_STATUS_UNLOCKED);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(page,record,null,null,null,null,null);

        page.setRecords(houseUserAssetRecordList);
        return SuccessTip.create(page);
    }

//    获取换房需求列表
    @GetMapping("/exchangeList")
    public Tip getExchangeRequestList(Page<HouseAssetExchangeRequestRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      @RequestParam(name = "search", required = false) String search){
        Long userId = JWTKit.getUserId();

        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long orgId = tenantUtility.getCurrentOrgId(userId);

        HouseAssetExchangeRequestRecord record  = new HouseAssetExchangeRequestRecord();
        record.setOrgId(orgId);

        List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestList = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(page,record,null,search,null,null,null);

        page.setRecords(houseAssetExchangeRequestList);

        return SuccessTip.create(page);
    }




}
