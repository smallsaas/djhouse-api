
package com.jfeat.am.module.house.api;


import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyRoomDao;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-06
 */
@RestController
@Api("HousePropertyBuilding")
@RequestMapping("/api/crud/house/housePropertyBuilding/housePropertyBuildings")
public class HousePropertyBuildingOverModelEndpoint {

    @Resource
    HousePropertyBuildingOverModelService housePropertyBuildingOverModelService;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    QueryHousePropertyRoomDao queryHousePropertyRoomDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;


    // 要查询[从表]关联数据，取消下行注释
    // @Resource
    // QueryHousePropertyRoomDao queryHousePropertyRoomDao;

    @BusinessLog(name = "HousePropertyBuilding", value = "create HousePropertyBuilding")
    @Permission(HousePropertyBuildingPermission.HOUSEPROPERTYBUILDING_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyBuilding", response = HousePropertyBuildingModel.class)
    public Tip createHousePropertyBuilding(@RequestBody HousePropertyBuildingModel entity) {
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

    @BusinessLog(name = "HousePropertyBuilding", value = "查看 HousePropertyBuildingModel")
    @Permission(HousePropertyBuildingPermission.HOUSEPROPERTYBUILDING_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyBuilding", response = HousePropertyBuildingModel.class)
    public Tip getHousePropertyBuilding(@PathVariable Long id) {
        CRUDObject<HousePropertyBuildingModel> entity = housePropertyBuildingOverModelService
                .registerQueryMasterDao(queryHousePropertyBuildingDao)
                // 要查询[从表]关联数据，取消下行注释
                //.registerQuerySlaveModelListDao(HousePropertyRoom.class, queryHousePropertyRoomDao)
                .retrieveMaster(id, null, null, null);

        // sample query for registerQueryMasterDao
        // e.g. <select id="queryMasterModel" resultType="PlanModel">
        //       SELECT t_plan_model.*, t_org.name as orgName
        //       FROM t_plan_model
        //       LEFT JOIN t_org ON t_org.id==t_plan_model.org_id
        //       WHERE t_plan_model.id=#{id}
        //       GROUP BY t_plan_model.id
        //    </select>

        if (entity != null) {
            return SuccessTip.create(entity.toJSONObject());
        } else {
            return SuccessTip.create();
        }

    }

    @BusinessLog(name = "HousePropertyBuilding", value = "update HousePropertyBuilding")
    @Permission(HousePropertyBuildingPermission.HOUSEPROPERTYBUILDING_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyBuilding", response = HousePropertyBuildingModel.class)
    public Tip updateHousePropertyBuilding(@PathVariable Long id, @RequestBody HousePropertyBuildingModel entity) {
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
        return SuccessTip.create();
    }

    @BusinessLog(name = "HousePropertyBuilding", value = "delete HousePropertyBuilding")
    @Permission(HousePropertyBuildingPermission.HOUSEPROPERTYBUILDING_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyBuilding")
    public Tip deleteHousePropertyBuilding(@PathVariable Long id) {
        Integer affect = housePropertyBuildingOverModelService.deleteMaster(id);
        if (affect>0){
            queryHousePropertyRoomDao.deleteHouseRoomByBuildingId(id);
            queryHousePropertyBuildingUnitDao.deleteHouseBuildingUnitByBuildingId(id);
        }
        return SuccessTip.create(affect);
    }

    @Permission(HousePropertyBuildingPermission.HOUSEPROPERTYBUILDING_VIEW)
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
}

