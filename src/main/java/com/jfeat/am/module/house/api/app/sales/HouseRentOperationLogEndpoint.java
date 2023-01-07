package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseRentLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/u/house/sales/houseRentLog")
public class HouseRentOperationLogEndpoint {


    @Resource
    HouseRentLogService houseRentLogService;

    @Resource
    QueryHouseRentLogDao queryHouseRentLogDao;



    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseRentLog", response = HouseRentLog.class)
    public Tip getHouseRentLog(@PathVariable Long id) {
        return SuccessTip.create(houseRentLogService.queryMasterModel(queryHouseRentLogDao, id));
    }




    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseRentLog")
    public Tip deleteHouseRentLog(@PathVariable Long id) {
        return SuccessTip.create(houseRentLogService.deleteMaster(id));
    }

    @ApiOperation(value = "HouseRentLog 列表信息", response = HouseRentLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "operator", dataType = "Long"),
            @ApiImplicitParam(name = "state", dataType = "Integer"),
            @ApiImplicitParam(name = "logNote", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "rentId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "communityId", dataType = "Long"),
            @ApiImplicitParam(name = "houseTypeId", dataType = "Long"),
            @ApiImplicitParam(name = "landlordId", dataType = "Long"),
            @ApiImplicitParam(name = "area", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "introducePicture", dataType = "String"),
            @ApiImplicitParam(name = "serverId", dataType = "Long"),
            @ApiImplicitParam(name = "cover", dataType = "String"),
            @ApiImplicitParam(name = "title", dataType = "String"),
            @ApiImplicitParam(name = "price", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "slide", dataType = "String"),
            @ApiImplicitParam(name = "rentDescribe", dataType = "String"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "rentStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "rentTime", dataType = "Date"),
            @ApiImplicitParam(name = "shelvesTime", dataType = "Date"),
            @ApiImplicitParam(name = "rate", dataType = "Integer"),
            @ApiImplicitParam(name = "houseNumber", dataType = "String"),
            @ApiImplicitParam(name = "floor", dataType = "Integer"),
            @ApiImplicitParam(name = "toward", dataType = "String"),
            @ApiImplicitParam(name = "buildingCode", dataType = "String"),
            @ApiImplicitParam(name = "communityName", dataType = "String"),
            @ApiImplicitParam(name = "configurationStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "rentCreateTime", dataType = "Date"),
            @ApiImplicitParam(name = "rentUpdateTime", dataType = "Date"),
            @ApiImplicitParam(name = "customImagesList", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseRentLogPage(Page<HouseRentLogRecord> page,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     // for tag feature query
                                     @RequestParam(name = "tag", required = false) String tag,
                                     // end tag
                                     @RequestParam(name = "search", required = false) String search,

                                     @RequestParam(name = "operator", required = false) Long operator,

                                     @RequestParam(name = "state", required = false) Integer state,

                                     @RequestParam(name = "logNote", required = false) String logNote,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "createTime", required = false) Date createTime,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "updateTime", required = false) Date updateTime,

                                     @RequestParam(name = "rentId", required = false) Long rentId,

                                     @RequestParam(name = "assetId", required = false) Long assetId,

                                     @RequestParam(name = "communityId", required = false) Long communityId,

                                     @RequestParam(name = "houseTypeId", required = false) Long houseTypeId,

                                     @RequestParam(name = "landlordId", required = false) Long landlordId,

                                     @RequestParam(name = "area", required = false) BigDecimal area,

                                     @RequestParam(name = "introducePicture", required = false) String introducePicture,

                                     @RequestParam(name = "serverId", required = false) Long serverId,

                                     @RequestParam(name = "cover", required = false) String cover,

                                     @RequestParam(name = "title", required = false) String title,

                                     @RequestParam(name = "price", required = false) BigDecimal price,

                                     @RequestParam(name = "slide", required = false) String slide,

                                     @RequestParam(name = "rentDescribe", required = false) String rentDescribe,

                                     @RequestParam(name = "status", required = false) Integer status,

                                     @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                     @RequestParam(name = "note", required = false) String note,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "rentTime", required = false) Date rentTime,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "shelvesTime", required = false) Date shelvesTime,

                                     @RequestParam(name = "rate", required = false) Integer rate,

                                     @RequestParam(name = "houseNumber", required = false) String houseNumber,

                                     @RequestParam(name = "floor", required = false) Integer floor,

                                     @RequestParam(name = "toward", required = false) String toward,

                                     @RequestParam(name = "buildingCode", required = false) String buildingCode,

                                     @RequestParam(name = "communityName", required = false) String communityName,

                                     @RequestParam(name = "configurationStatus", required = false) Integer configurationStatus,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "rentCreateTime", required = false) Date rentCreateTime,

                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(name = "rentUpdateTime", required = false) Date rentUpdateTime,

                                     @RequestParam(name = "customImagesList", required = false) String customImagesList,
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

        HouseRentLogRecord record = new HouseRentLogRecord();
        record.setOperator(operator);
        record.setState(state);
        record.setLogNote(logNote);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);
        record.setRentId(rentId);
        record.setAssetId(assetId);
        record.setCommunityId(communityId);
        record.setHouseTypeId(houseTypeId);
        record.setLandlordId(landlordId);
        record.setArea(area);
        record.setIntroducePicture(introducePicture);
        record.setServerId(serverId);
        record.setCover(cover);
        record.setTitle(title);
        record.setPrice(price);
        record.setSlide(slide);
        record.setRentDescribe(rentDescribe);
        record.setStatus(status);
        record.setRentStatus(rentStatus);
        record.setNote(note);
        record.setRentTime(rentTime);
        record.setShelvesTime(shelvesTime);
        record.setRate(rate);
        record.setHouseNumber(houseNumber);
        record.setFloor(floor);
        record.setToward(toward);
        record.setBuildingCode(buildingCode);
        record.setCommunityName(communityName);
        record.setConfigurationStatus(configurationStatus);
        record.setRentCreateTime(rentCreateTime);
        record.setRentUpdateTime(rentUpdateTime);
        record.setCustomImagesList(customImagesList);


        List<HouseRentLogRecord> houseRentLogPage = queryHouseRentLogDao.findHouseRentLogPage(page, record, tag, search, orderBy, null, null);

        houseRentLogService.setStateStr(houseRentLogPage);
        page.setRecords(houseRentLogPage);

        return SuccessTip.create(page);
    }
}
