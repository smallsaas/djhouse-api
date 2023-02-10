package com.jfeat.am.module.house.api.app.sales;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jfeat.crud.base.tips.Tip;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/property")
public class PropertyConsultantEndpoint {

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    UserAccountService userAccountService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    StockTagMapper stockTagMapper;

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    StockTagRelationMapper stockTagRelationMapper;

    /*
    * 获取置业顾问列表
    * */
    @GetMapping
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_OPERATION_STRING})
    public Tip getPropertyConsultantList(
            Page<EndpointUserRecord> page,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(value = "search",required = false) String search
    ){

        //获取用户id
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

//        //获取社区id
//        Long orgId = tenantUtility.getCurrentOrgId(userId);
//        if (orgId==null){
//            throw new BusinessException(BusinessCode.NoPermission,"没有社区信息");
//        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        EndpointUserRecord record = new EndpointUserRecord();
        record.setPhone(phone);
        record.setType(32);

        List<EndpointUserRecord> userRecordList = queryEndpointUserDao.findEndUserPage(page, record, null, search, null, null, null);

        for (int i = 0; i < userRecordList.size(); i++) {
            if (userRecordList.get(i).getType() == null) {
                continue;
            }

//            将用户类型 转为list 返回个前端
            List<Integer> userTypeList = userAccountService.getUserTypeList(userRecordList.get(i).getType());
            if (userRecordList != null && userRecordList.size() > 0) {
                userRecordList.get(i).setTypeList(userTypeList);
            }

        }
        page.setRecords(userRecordList);

        return SuccessTip.create(page);

    }

    /*
     * 获取置业顾问房源
     * */
    @GetMapping("/houseRentAsset")
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
                                       @RequestParam(name = "sort", required = false) String sort) {


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
        record.setServerId(serverId);

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

}
