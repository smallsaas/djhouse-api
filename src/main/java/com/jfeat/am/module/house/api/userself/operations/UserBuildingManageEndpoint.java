package com.jfeat.am.module.house.api.userself.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HousePropertyBuildingPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
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
@RequestMapping("/api/u/house/operations/userBuildingManage")
public class UserBuildingManageEndpoint {
    @Resource
    HousePropertyBuildingOverModelService housePropertyBuildingOverModelService;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    QueryHouseAssetDao queryHousePropertyRoomDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    Authentication authentication;

    //    楼栋

    /*
    添加楼栋
     */
    @PostMapping
    public Tip createHousePropertyBuilding(@RequestBody HousePropertyBuildingModel entity) {

        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        Integer affected = 0;
        try {

            DefaultFilterResult filterResult = new DefaultFilterResult();
//            entity.setOrgId(JWTKit.getTenantOrgId());
            // int insert = housePropertyBuildingMapper.insert(entity);
            affected = housePropertyBuildingOverModelService.createMaster(entity, filterResult, null, null);
            if (affected > 0) {
                housePropertyBuildingOverModelService.initHouseProperty(entity);
                return SuccessTip.create(filterResult.result());
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }





    /*
    修改楼栋层数
     */
    @PutMapping("/{id}")
    public Tip updateHousePropertyBuilding(@PathVariable Long id, @RequestBody HousePropertyBuildingModel entity) {
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
        HousePropertyBuildingModel housePropertyBuildingModel =  queryHousePropertyBuildingDao.queryMasterModel(id);
        // use update flags
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items
        // newOptions = FlagUtil.setFlag(newOptions, META.UPDATE_ALL_COLUMNS_FLAG);
        Integer effect = housePropertyBuildingOverModelService.updateMaster(entity, null, null, null, newOptions);
        if (effect>0 && housePropertyBuildingModel!=null){

            if (entity.getFloors()!=null&&entity.getFloors()!=housePropertyBuildingModel.getFloors()){
                queryHousePropertyRoomDao.deleteHouseRoomByBuildingId(id);
                queryHousePropertyBuildingUnitDao.deleteHouseBuildingUnitByBuildingId(id);
                housePropertyBuildingModel.setFloors(entity.getFloors());
                housePropertyBuildingOverModelService.initHouseProperty(housePropertyBuildingModel);
            }
            if (entity.getUnits()!=null&&entity.getUnits()!=housePropertyBuildingModel.getUnits()){
                queryHousePropertyRoomDao.deleteHouseRoomByBuildingId(id);
                queryHousePropertyBuildingUnitDao.deleteHouseBuildingUnitByBuildingId(id);
                housePropertyBuildingModel.setUnits(entity.getUnits());
                housePropertyBuildingOverModelService.initHouseProperty(housePropertyBuildingModel);
            }
        }
        return SuccessTip.create(effect);
    }


    /*
    删除楼栋
     */
    @DeleteMapping("/{id}")
    public Tip deleteHousePropertyBuilding(@PathVariable Long id) {
        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        Integer affect = housePropertyBuildingOverModelService.deleteMaster(id);
        if (affect>0){
            queryHousePropertyRoomDao.deleteHouseRoomByBuildingId(id);
            queryHousePropertyBuildingUnitDao.deleteHouseBuildingUnitByBuildingId(id);
        }
        return SuccessTip.create(affect);
    }

    /*
       查询楼栋
        */

    @GetMapping
    public Tip queryHousePropertyBuildingPage(Page<HousePropertyBuildingRecord> page,
                                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              // for tag feature query
                                              @RequestParam(name = "tag", required = false) String tag,
                                              // end tag
                                              @RequestParam(name = "search", required = false) String search,

                                              @RequestParam(name = "orgId", required = false) Long orgId,

                                              @RequestParam(name = "communityId", required = false) Long communityId,

                                              @RequestParam(name = "area", required = false) String area,

                                              @RequestParam(name = "code", required = false) String code,

                                              @RequestParam(name = "floors", required = false) Integer floors,

                                              @RequestParam(name = "units", required = false) Integer units,
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

        HousePropertyBuildingRecord record = new HousePropertyBuildingRecord();
//        if (META.enabledSaas()) {
//            record.setOrgId(JWTKit.getOrgId());
//        }
        record.setCommunityId(communityId);
        record.setArea(area);
        record.setCode(code);
        record.setFloors(floors);
        record.setUnits(units);


        List<HousePropertyBuildingRecord> housePropertyBuildingPage = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(housePropertyBuildingPage);

        return SuccessTip.create(page);
    }


    @GetMapping("/{id}")
    public Tip getHousePropertyBuilding(@PathVariable Long id) {
        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        CRUDObject<HousePropertyBuildingModel> entity = housePropertyBuildingOverModelService
                .registerQueryMasterDao(queryHousePropertyBuildingDao)
                // 要查询[从表]关联数据，取消下行注释
                //.registerQuerySlaveModelListDao(HouseAsset.class, queryHouseAssetDao)
                .retrieveMaster(id, null, null, null);

        if (entity != null) {
            return SuccessTip.create(entity.toJSONObject());
        } else {
            return SuccessTip.create();
        }

    }



}
