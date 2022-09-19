
package com.jfeat.am.module.house.api.manager;


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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDesignModelDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;

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
 * @since 2022-06-08
 */
@RestController
@Api("HouseDesignModel")
@RequestMapping("/api/crud/house/houseDesignModel/houseDesignModels")
public class HouseDesignModelEndpoint {

    @Resource
    HouseDesignModelService houseDesignModelService;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;


    @BusinessLog(name = "HouseDesignModel", value = "create HouseDesignModel")
    @Permission(HouseDesignModelPermission.HOUSEDESIGNMODEL_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseDesignModel", response = HouseDesignModel.class)
    public Tip createHouseDesignModel(@RequestBody HouseDesignModel entity) {
        Integer affected = 0;
        try {
            affected = houseDesignModelService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseDesignModelPermission.HOUSEDESIGNMODEL_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseDesignModel", response = HouseDesignModel.class)
    public Tip getHouseDesignModel(@PathVariable Long id) {

        return SuccessTip.create(houseDesignModelService.queryMasterModel(queryHouseDesignModelDao, id));
    }

    @BusinessLog(name = "HouseDesignModel", value = "update HouseDesignModel")
    @Permission(HouseDesignModelPermission.HOUSEDESIGNMODEL_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseDesignModel", response = HouseDesignModel.class)
    public Tip updateHouseDesignModel(@PathVariable Long id, @RequestBody HouseDesignModel entity) {
        entity.setId(id);
        return SuccessTip.create(houseDesignModelService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseDesignModel", value = "delete HouseDesignModel")
    @Permission(HouseDesignModelPermission.HOUSEDESIGNMODEL_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseDesignModel")
    public Tip deleteHouseDesignModel(@PathVariable Long id) {
        return SuccessTip.create(houseDesignModelService.deleteMaster(id));
    }

    @Permission(HouseDesignModelPermission.HOUSEDESIGNMODEL_VIEW)
    @ApiOperation(value = "HouseDesignModel 列表信息", response = HouseDesignModelRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "houseType", dataType = "String"),
            @ApiImplicitParam(name = "houseTypePicture", dataType = "String"),
            @ApiImplicitParam(name = "hallWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "hallSubWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "hallLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "hall_frenchWindowWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "hall_frenchWindowHight", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "diningRoomWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "diningRoomLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "firstRoomWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "firstRoomLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "first_roomDoorwallWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "first_roomWindowWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "first_room_windowSubWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "first_roomWindowHight", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "secondRoomWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "secondRoomLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "second_roomDoorwallWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "second_roomWindowWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "second_room_windowSubWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "second_roomWindowHight", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "thirdRoomWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "thirdRoomLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "third_roomDoorwallWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "third_roomWindowWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "third_room_windowSubWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "third_roomWindowHight", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourthRoomWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourthRoomLength", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourth_roomDoorwallWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourth_roomWindowWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourth_room_windowSubWidth", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "fourth_roomWindowHight", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseDesignModelPage(Page<HouseDesignModelRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         // for tag feature query
                                         @RequestParam(name = "tag", required = false) String tag,
                                         // end tag
                                         @RequestParam(name = "search", required = false) String search,

                                         @RequestParam(name = "houseType", required = false) String houseType,

                                         @RequestParam(name = "houseTypePicture", required = false) String houseTypePicture,

                                         @RequestParam(name = "hallWidth", required = false) BigDecimal hallWidth,

                                         @RequestParam(name = "hallSubWidth", required = false) BigDecimal hallSubWidth,

                                         @RequestParam(name = "hallLength", required = false) BigDecimal hallLength,

                                         @RequestParam(name = "hallFrenchWindowWidth", required = false) BigDecimal hallFrenchWindowWidth,

                                         @RequestParam(name = "hallFrenchWindowHight", required = false) BigDecimal hallFrenchWindowHight,

                                         @RequestParam(name = "diningRoomWidth", required = false) BigDecimal diningRoomWidth,

                                         @RequestParam(name = "diningRoomLength", required = false) BigDecimal diningRoomLength,

                                         @RequestParam(name = "firstRoomWidth", required = false) BigDecimal firstRoomWidth,

                                         @RequestParam(name = "firstRoomLength", required = false) BigDecimal firstRoomLength,

                                         @RequestParam(name = "firstRoomDoorwallWidth", required = false) BigDecimal firstRoomDoorwallWidth,

                                         @RequestParam(name = "firstRoomWindowWidth", required = false) BigDecimal firstRoomWindowWidth,

                                         @RequestParam(name = "firstRoomWindowSubWidth", required = false) BigDecimal firstRoomWindowSubWidth,

                                         @RequestParam(name = "firstRoomWindowHight", required = false) BigDecimal firstRoomWindowHight,

                                         @RequestParam(name = "secondRoomWidth", required = false) BigDecimal secondRoomWidth,

                                         @RequestParam(name = "secondRoomLength", required = false) BigDecimal secondRoomLength,

                                         @RequestParam(name = "secondRoomDoorwallWidth", required = false) BigDecimal secondRoomDoorwallWidth,

                                         @RequestParam(name = "secondRoomWindowWidth", required = false) BigDecimal secondRoomWindowWidth,

                                         @RequestParam(name = "secondRoomWindowSubWidth", required = false) BigDecimal secondRoomWindowSubWidth,

                                         @RequestParam(name = "secondRoomWindowHight", required = false) BigDecimal secondRoomWindowHight,

                                         @RequestParam(name = "thirdRoomWidth", required = false) BigDecimal thirdRoomWidth,

                                         @RequestParam(name = "thirdRoomLength", required = false) BigDecimal thirdRoomLength,

                                         @RequestParam(name = "thirdRoomDoorwallWidth", required = false) BigDecimal thirdRoomDoorwallWidth,

                                         @RequestParam(name = "thirdRoomWindowWidth", required = false) BigDecimal thirdRoomWindowWidth,

                                         @RequestParam(name = "thirdRoomWindowSubWidth", required = false) BigDecimal thirdRoomWindowSubWidth,

                                         @RequestParam(name = "thirdRoomWindowHight", required = false) BigDecimal thirdRoomWindowHight,

                                         @RequestParam(name = "fourthRoomWidth", required = false) BigDecimal fourthRoomWidth,

                                         @RequestParam(name = "fourthRoomLength", required = false) BigDecimal fourthRoomLength,

                                         @RequestParam(name = "fourthRoomDoorwallWidth", required = false) BigDecimal fourthRoomDoorwallWidth,

                                         @RequestParam(name = "fourthRoomWindowWidth", required = false) BigDecimal fourthRoomWindowWidth,

                                         @RequestParam(name = "fourthRoomWindowSubWidth", required = false) BigDecimal fourthRoomWindowSubWidth,

                                         @RequestParam(name = "fourthRoomWindowHight", required = false) BigDecimal fourthRoomWindowHight,
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

        HouseDesignModelRecord record = new HouseDesignModelRecord();
        record.setHouseType(houseType);
        record.setHouseTypePicture(houseTypePicture);
        record.setHallWidth(hallWidth);
        record.setHallSubWidth(hallSubWidth);
        record.setHallLength(hallLength);
        record.setHallFrenchWindowWidth(hallFrenchWindowWidth);
        record.setHallFrenchWindowHight(hallFrenchWindowHight);
        record.setDiningRoomWidth(diningRoomWidth);
        record.setDiningRoomLength(diningRoomLength);
        record.setFirstRoomWidth(firstRoomWidth);
        record.setFirstRoomLength(firstRoomLength);
        record.setFirstRoomDoorwallWidth(firstRoomDoorwallWidth);
        record.setFirstRoomWindowWidth(firstRoomWindowWidth);
        record.setFirstRoomWindowSubWidth(firstRoomWindowSubWidth);
        record.setFirstRoomWindowHight(firstRoomWindowHight);
        record.setSecondRoomWidth(secondRoomWidth);
        record.setSecondRoomLength(secondRoomLength);
        record.setSecondRoomDoorwallWidth(secondRoomDoorwallWidth);
        record.setSecondRoomWindowWidth(secondRoomWindowWidth);
        record.setSecondRoomWindowSubWidth(secondRoomWindowSubWidth);
        record.setSecondRoomWindowHight(secondRoomWindowHight);
        record.setThirdRoomWidth(thirdRoomWidth);
        record.setThirdRoomLength(thirdRoomLength);
        record.setThirdRoomDoorwallWidth(thirdRoomDoorwallWidth);
        record.setThirdRoomWindowWidth(thirdRoomWindowWidth);
        record.setThirdRoomWindowSubWidth(thirdRoomWindowSubWidth);
        record.setThirdRoomWindowHight(thirdRoomWindowHight);
        record.setFourthRoomWidth(fourthRoomWidth);
        record.setFourthRoomLength(fourthRoomLength);
        record.setFourthRoomDoorwallWidth(fourthRoomDoorwallWidth);
        record.setFourthRoomWindowWidth(fourthRoomWindowWidth);
        record.setFourthRoomWindowSubWidth(fourthRoomWindowSubWidth);
        record.setFourthRoomWindowHight(fourthRoomWindowHight);


        List<HouseDesignModelRecord> houseDesignModelPage = queryHouseDesignModelDao.findHouseDesignModelPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseDesignModelPage);

        return SuccessTip.create(page);
    }
}

