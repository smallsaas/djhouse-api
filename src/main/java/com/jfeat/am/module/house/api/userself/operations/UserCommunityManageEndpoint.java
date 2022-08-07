package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HousePropertyCommunityPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyCommunityDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyCommunityRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyCommunityOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.crud.plus.META;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
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


//    小区
    /*
     添加小区
     */

    @PostMapping
    public Tip createHousePropertyCommunity(@RequestBody HousePropertyCommunityModel entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }
//        entity.setTenantId(JWTKit.getTenantOrgId());


        Integer affected = 0;
        try {
            DefaultFilterResult filterResult = new DefaultFilterResult();
            affected = housePropertyCommunityOverModelService.createMaster(entity, filterResult, null, null);
            if (affected > 0) {
                return SuccessTip.create(filterResult.result());
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
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

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }


        entity.setId(id);
//        entity.setTenantId(JWTKit.getTenantOrgId());
        // use update flags
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items
        // newOptions = FlagUtil.setFlag(newOptions, META.UPDATE_ALL_COLUMNS_FLAG);

        return SuccessTip.create(housePropertyCommunityOverModelService.updateMaster(entity, null, null, null, newOptions));
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

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        HousePropertyBuildingRecord housePropertyBuildingRecord = new HousePropertyBuildingRecord();
        housePropertyBuildingRecord.setCommunityId(id);
        List<HousePropertyBuildingRecord> housePropertyBuildingRecords = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(null,housePropertyBuildingRecord,null,
                null,null,null,null);
        if (housePropertyBuildingRecords!=null && housePropertyBuildingRecords.size()>0){
            throw new BusinessException(BusinessCode.CodeBase,"已经配置房屋，不可以删除");
        }

        return SuccessTip.create(housePropertyCommunityOverModelService.deleteMaster(id));
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

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
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


}
