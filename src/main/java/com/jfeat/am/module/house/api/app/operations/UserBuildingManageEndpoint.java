package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.house.services.utility.RedisScript;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.crud.plus.META;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
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

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    RedisScript redisScript;



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
////        查当前用户的的小区状态
//        Long userCommunityStatus =  userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
//        if (userCommunityStatus==null){
//            throw new BusinessException(BusinessCode.NoPermission,"没有选择小区");
//        }
//        entity.setCommunityId(userCommunityStatus);

        Integer affected = 0;

        try {

            DefaultFilterResult filterResult = new DefaultFilterResult();
            affected = housePropertyBuildingOverModelService.createMaster(entity, filterResult, null, null);
            if (affected > 0 &&(entity.getFloors()!=null && entity.getFloors()>=0) && (entity.getUnits()!=null && entity.getUnits()>=0)) {
//                初始化楼栋 创建 单元 房屋等信息
                housePropertyBuildingOverModelService.initHouseProperty(entity);
                return SuccessTip.create(filterResult.result());
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey,"已有该编号，请重试");
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

        if (entity.getFloors()==null || entity.getUnits()==null || entity.getFloors()<0 || entity.getUnits()<0){
            throw new BusinessException(BusinessCode.BadRequest,"楼陈数或者单元数设置有误");
        }


        entity.setId(id);
        entity.setItems(null);
        HousePropertyBuildingModel housePropertyBuildingModel =  queryHousePropertyBuildingDao.queryMasterModel(id);
        // use update flags
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items
        // newOptions = FlagUtil.setFlag(newOptions, META.UPDATE_ALL_COLUMNS_FLAG);
        Integer effect = housePropertyBuildingOverModelService.updateMaster(entity, null, null, null, newOptions);
//        当楼栋没有被设置时 先初始化楼栋
        if (housePropertyBuildingModel.getUnits()==0 && housePropertyBuildingModel.getFloors()==0){
            housePropertyBuildingOverModelService.initHouseProperty(entity);
        }
//        当楼栋已经初始化后 修改楼栋信息 如单元数和房屋数
        if (effect>0 && housePropertyBuildingModel!=null &&(housePropertyBuildingModel.getFloors()!=null && housePropertyBuildingModel.getFloors()>0) && (housePropertyBuildingModel.getUnits()!=null && housePropertyBuildingModel.getUnits()>0)){
            effect+=housePropertyBuildingOverModelService.modifyHouseBuilding(housePropertyBuildingModel);

        }
        if (effect>0){
            //  清除缓存
            redisScript.delRidesData("*".concat("buildingId").concat(String.valueOf(id)).concat(":*"));
        }
        return SuccessTip.create(effect);
    }


    /*
    删除楼栋
     */
    @DeleteMapping("/{id}")
    public Tip deleteHousePropertyBuilding(@PathVariable("id") Long id) {
        /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        QueryWrapper<HouseAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAsset.BUILDING_ID,id);
        List<HouseAsset> houseAssetList = houseAssetMapper.selectList(queryWrapper);
        if (houseAssetList!=null && houseAssetList.size()>0){
            throw new BusinessException(BusinessCode.DeleteNotEmptyOne,"该楼栋下有房屋,不能删除");
        }

        Integer affect = housePropertyBuildingOverModelService.deleteMaster(id);
        if (affect>0){
//            删除房屋和单元
            queryHousePropertyRoomDao.deleteHouseRoomByBuildingId(id);
            queryHousePropertyBuildingUnitDao.deleteHouseBuildingUnitByBuildingId(id);

//            清除缓存
            redisScript.delRidesData("*".concat("buildingId").concat(String.valueOf(id)).concat(":*"));
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
        Collections.sort(housePropertyBuildingPage);

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
