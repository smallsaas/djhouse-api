package com.jfeat.am.module.house.api.userself;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HousePropertyUserRoomPermission;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyCommunityRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyCommunityOverModelService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyUserRoomService;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserRoom;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.META;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("用户房屋api")
@RequestMapping("/api/u/house/house/housePropertyUnit")
public class UserHousePropertyUnitEndpoint {

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    HousePropertyBuildingOverModelService housePropertyBuildingOverModelService;

    @Resource
    HousePropertyUserRoomService housePropertyUserRoomService;


    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;

    @Resource
    QueryHousePropertyUserRoomDao queryHousePropertyUserRoomDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    HouseUserCommunityStatusService houseUserCommunityStatusService;

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;


    //    通过楼栋id获取整栋楼的 unit
    @BusinessLog(name = "HousePropertyBuilding", value = "查看 HousePropertyBuildingModel")
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyBuilding", response = HousePropertyBuildingModel.class)
    public Tip getHousePropertyBuilding(@PathVariable Long id) {
        CRUDObject<HousePropertyBuildingModel> entity = housePropertyBuildingOverModelService
                .registerQueryMasterDao(queryHousePropertyBuildingDao)
                .retrieveMaster(id, null, null, null);
        if (entity != null) {
            return SuccessTip.create(entity.toJSONObject());
        } else {
            return SuccessTip.create();
        }
    }


    //    获取全部楼栋信息
    @ApiOperation(value = "HousePropertyBuilding 列表信息", response = HousePropertyBuildingRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "communityId", dataType = "Long"),
            @ApiImplicitParam(name = "area", dataType = "String"),
            @ApiImplicitParam(name = "code", dataType = "String"),
            @ApiImplicitParam(name = "floors", dataType = "Integer"),
            @ApiImplicitParam(name = "units", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
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
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setCommunityId(communityId);
        record.setArea(area);
        record.setCode(code);
        record.setFloors(floors);
        record.setUnits(units);


        List<HousePropertyBuildingRecord> housePropertyBuildingPage = queryHousePropertyBuildingDao.findHousePropertyBuildingPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(housePropertyBuildingPage);

        return SuccessTip.create(page);
    }


    @ApiOperation(value = "获取用户的unit", response = QueryHousePropertyUserRoomDao.class)
    @GetMapping("/userUnit")
    public Tip getUserUnite() {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        return SuccessTip.create(queryHousePropertyUserRoomDao.queryUserRoomByUserId(userId));
    }


    //    新增安装unit位置
    @BusinessLog(name = "HousePropertyUserUnit", value = "create HousePropertyUserUnit")
    @PostMapping("/userUnit")
    @ApiOperation(value = "新建 HousePropertyUserUnit", response = HousePropertyUserRoom.class)
    public Tip createHousePropertyUserUnit(@RequestBody HousePropertyUserRoom entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        Integer affected = 0;
        try {
            affected = housePropertyUserRoomService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    //    修改用户的unit位置
    @BusinessLog(name = "HousePropertyUserUnit", value = "update HousePropertyUserUnit")
    @PutMapping("/userUnit/{id}")
    @ApiOperation(value = "修改 HousePropertyUserUnit", response = HousePropertyUserRoom.class)
    public Tip updateHousePropertyUserUnit(@PathVariable Long id, @RequestBody HousePropertyUserRoom entity) {
        entity.setId(id);
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId(JWTKit.getUserId());
        return SuccessTip.create(housePropertyUserRoomService.updateMaster(entity));
    }

    //    删除用户unit
    @BusinessLog(name = "HousePropertyUserUnit", value = "delete HousePropertyUserUnit")
    @DeleteMapping("/userUnit/{id}")
    @ApiOperation("删除 HousePropertyUserUnit")
    public Tip deleteHousePropertyUserUnit(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(housePropertyUserRoomService.deleteMaster(id));
    }


    @ApiOperation(value = "HousePropertyCommunity 列表信息", response = HousePropertyCommunityRecord.class)
    @GetMapping("/community")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "community", dataType = "String"),
            @ApiImplicitParam(name = "communityCode", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHousePropertyCommunityPage(Page<HousePropertyCommunityRecord> page,
                                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                               // for tag feature query
                                               @RequestParam(name = "tag", required = false) String tag,
                                               // end tag
                                               @RequestParam(name = "search", required = false) String search,

                                               @RequestParam(name = "community", required = false) String community,

                                               @RequestParam(name = "communityCode", required = false) String communityCode,
                                               @RequestParam(name = "orderBy", required = false) String orderBy,
                                               @RequestParam(name = "sort", required = false) String sort) {

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
        record.setCommunityCode(communityCode);


        List<HousePropertyCommunityRecord> housePropertyCommunityPage = queryHousePropertyCommunityDao.findHousePropertyCommunityPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(housePropertyCommunityPage);

        return SuccessTip.create(page);
    }


//   获取用户社区状态
    @GetMapping("/userCommunityStatus")
    public Tip queryUserCommunityStatusByUserId(){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        return SuccessTip.create(queryHouseUserCommunityStatusDao.queryUserCommunityStatusByUserId(userId));

    }

//    修改用户社区状态
    @PostMapping("/userCommunityStatus")
    public Tip updateUserCommunityStatusByUserId(@RequestBody HouseUserCommunityStatus entity){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        entity.setUserId( JWTKit.getUserId());
       HouseUserCommunityStatus houseUserCommunityStatus =  queryHouseUserCommunityStatusDao.queryUserCommunityStatusByUserId(JWTKit.getUserId());
       if (houseUserCommunityStatus!=null){
           return SuccessTip.create(queryHouseUserCommunityStatusDao.updateUserCommunityStatusByUserId(entity));
       }else {
           Integer affected = 0;
           try {
               affected = houseUserCommunityStatusService.createMaster(entity);
           } catch (DuplicateKeyException e) {
               throw new BusinessException(BusinessCode.DuplicateKey);
           }

           return SuccessTip.create(affected);
       }


    }


}
