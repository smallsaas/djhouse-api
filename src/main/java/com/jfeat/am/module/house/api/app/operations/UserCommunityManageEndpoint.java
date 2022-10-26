package com.jfeat.am.module.house.api.app.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyCommunityRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserCommunityStatusRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyCommunityOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.house.services.utility.RedisScript;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.am.uaas.tenant.services.gen.persistence.dao.TenantMapper;
import com.jfeat.am.uaas.tenant.services.gen.persistence.model.Tenant;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.META;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/userCommunityManage")
public class UserCommunityManageEndpoint {

    @Resource
    HousePropertyCommunityOverModelService housePropertyCommunityOverModelService;

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    Authentication authentication;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    TenantMapper tenantMapper;

    @Resource
    RedisScript redisScript;

    @Resource
    TenantUtility tenantUtility;


//    小区
    /*
     添加小区
     */

    @PostMapping
    public Tip createHousePropertyCommunity(@RequestBody HousePropertyCommunityModel entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }
        entity.setTenantId(JWTKit.getTenantOrgId());
        return SuccessTip.create(housePropertyCommunityOverModelService.createCommunity(entity));
    }


    /*
       修改小区
        */
    @PutMapping("/{id}")
    public Tip updateHousePropertyCommunity(@PathVariable Long id, @RequestBody HousePropertyCommunityModel entity) {

        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }


        entity.setId(id);
//        entity.setTenantId(JWTKit.getTenantOrgId());
        // use update flags
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items
        // newOptions = FlagUtil.setFlag(newOptions, META.UPDATE_ALL_COLUMNS_FLAG);

//        删除缓存
        Integer affect  = housePropertyCommunityOverModelService.updateMaster(entity, null, null, null, newOptions);
        if (affect>0){
            redisScript.delRidesData("communityId".concat(String.valueOf(id).concat(":*")));
        }

        return SuccessTip.create(affect);
    }

    /*
   删除小区
    */
    @DeleteMapping("/{id}")
    public Tip deleteHousePropertyCommunity(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }
        Integer affect  = housePropertyCommunityOverModelService.deleteCommunity(id);
        if (affect>0){
            redisScript.delRidesData("communityId".concat(String.valueOf(id).concat(":*")));
        }
        return SuccessTip.create(affect);
    }


    /*
    查询小区
     */
    @GetMapping
    public Tip queryHousePropertyCommunityPage(Page<HousePropertyCommunityRecord> page,
                                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                               // for tag feature query
                                               @RequestParam(name = "tag", required = false) String tag,
                                               // end tag
                                               @RequestParam(name = "search", required = false) String search,

                                               @RequestParam(name = "community", required = false) String community,

                                               @RequestParam(name = "tenantId", required = false) Long tenantId,

                                               @RequestParam(name = "communityCode", required = false) String communityCode,

                                               @RequestParam(name = "address", required = false) String address,
                                               @RequestParam(name = "orderBy", required = false) String orderBy,
                                               @RequestParam(name = "sort", required = false) String sort) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
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

        HousePropertyCommunityRecord record = new HousePropertyCommunityRecord();
        record.setCommunity(community);
        record.setTenantId(tenantId);
        record.setCommunityCode(communityCode);
        record.setAddress(address);


        List<HousePropertyCommunityRecord> housePropertyCommunityPage = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(housePropertyCommunityPage);

        return SuccessTip.create(page);
    }


    @GetMapping("/{id}")
    public Tip getHousePropertyCommunity(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }

        CRUDObject<HousePropertyCommunityModel> entity = housePropertyCommunityOverModelService
                .registerQueryMasterDao(queryHousePropertyCommunityDao)
                // 要查询[从表]关联数据，取消下行注释
                //.registerQuerySlaveModelListDao(HousePropertyBuilding.class, queryHousePropertyBuildingDao)
                .retrieveMaster(id, null, null, null);
        if (entity != null) {
            return SuccessTip.create(entity.toJSONObject());
        } else {
            return SuccessTip.create();
        }

    }

    //    当前小区
    @GetMapping("/getCurrentCommunity")
    public Tip getCurrentCoummunity() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        Long communityId = null;
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
            if (communityId != null) {
                return SuccessTip.create(housePropertyCommunityMapper.selectById(communityId));
            }
        } else {
            throw new BusinessException(BusinessCode.CodeBase, "未设置小区");
        }

        return SuccessTip.create();
    }

    //    当前社区api
    @GetMapping("/getCurrentTenant")
    public Tip getCurrentTenant() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long tenantId = tenantUtility.getCurrentOrgId(JWTKit.getUserId());
       if (tenantId!=null){
           QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
           queryWrapper.eq(Tenant.ORG_ID,tenantId);
           return SuccessTip.create(tenantMapper.selectOne(queryWrapper));
       }
        return SuccessTip.create();
    }

//    小区信息配置
    @PutMapping("/updateCommunityInfo")
    public Tip updateCommunityInfo(@RequestBody HousePropertyCommunity entity){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getParkingNumber()==null || entity.getParkingNumber()<0){
            throw new BusinessException(BusinessCode.CodeBase,"停车位不能为空或者小于0");
        }

        HouseUserCommunityStatusRecord communityStatusRecord = new HouseUserCommunityStatusRecord();
        communityStatusRecord.setUserId(JWTKit.getUserId());
        Long communityId = null;
        List<HouseUserCommunityStatusRecord> communityStatusRecordList = queryHouseUserCommunityStatusDao.findHouseUserCommunityStatusPage(null, communityStatusRecord, null, null, null, null, null);
        if (communityStatusRecordList != null && communityStatusRecordList.size() == 1) {
            communityId = communityStatusRecordList.get(0).getCommunityId();
            if (communityId != null) {
                HousePropertyCommunity community =  housePropertyCommunityMapper.selectById(communityId);
                entity.setId(community.getId());
                entity.setCommunity(community.getCommunity());
                entity.setTenantId(community.getTenantId());
                entity.setCommunityCode(community.getCommunityCode());
                entity.setAddress(community.getAddress());
                entity.setPicture(community.getPicture());
                entity.setPostcode(community.getPostcode());
                return SuccessTip.create(housePropertyCommunityMapper.updateById(entity));
            }
        } else {
            throw new BusinessException(BusinessCode.CodeBase, "未设置小区");
        }
        return SuccessTip.create();
    }
}
