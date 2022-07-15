
package com.jfeat.am.module.house.api;


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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAppointmentDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseAppointmentRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;

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
 * @since 2022-07-13
 */
@RestController
@Api("HouseAppointment")
@RequestMapping("/api/crud/house/houseAppointment/houseAppointments")
public class HouseAppointmentEndpoint {

    @Resource
    HouseAppointmentService houseAppointmentService;

    @Resource
    QueryHouseAppointmentDao queryHouseAppointmentDao;


    @BusinessLog(name = "HouseAppointment", value = "create HouseAppointment")
    @Permission(HouseAppointmentPermission.HOUSEAPPOINTMENT_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAppointment", response = HouseAppointment.class)
    public Tip createHouseAppointment(@RequestBody HouseAppointment entity) {
        Integer affected = 0;
        try {
            affected = houseAppointmentService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAppointmentPermission.HOUSEAPPOINTMENT_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAppointment", response = HouseAppointment.class)
    public Tip getHouseAppointment(@PathVariable Long id) {
        return SuccessTip.create(houseAppointmentService.queryMasterModel(queryHouseAppointmentDao, id));
    }

    @BusinessLog(name = "HouseAppointment", value = "update HouseAppointment")
    @Permission(HouseAppointmentPermission.HOUSEAPPOINTMENT_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAppointment", response = HouseAppointment.class)
    public Tip updateHouseAppointment(@PathVariable Long id, @RequestBody HouseAppointment entity) {
        entity.setId(id);
        return SuccessTip.create(houseAppointmentService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAppointment", value = "delete HouseAppointment")
    @Permission(HouseAppointmentPermission.HOUSEAPPOINTMENT_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAppointment")
    public Tip deleteHouseAppointment(@PathVariable Long id) {
        return SuccessTip.create(houseAppointmentService.deleteMaster(id));
    }

    @Permission(HouseAppointmentPermission.HOUSEAPPOINTMENT_VIEW)
    @ApiOperation(value = "HouseAppointment 列表信息", response = HouseAppointmentRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "code", dataType = "String"),
            @ApiImplicitParam(name = "typeId", dataType = "Long"),
            @ApiImplicitParam(name = "type", dataType = "String"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "userPhone", dataType = "String"),
            @ApiImplicitParam(name = "userName", dataType = "String"),
            @ApiImplicitParam(name = "serverId", dataType = "Long"),
            @ApiImplicitParam(name = "serverName", dataType = "String"),
            @ApiImplicitParam(name = "serverPhone", dataType = "String"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "addressId", dataType = "Long"),
            @ApiImplicitParam(name = "addressName", dataType = "String"),
            @ApiImplicitParam(name = "description", dataType = "String"),
            @ApiImplicitParam(name = "icon", dataType = "String"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "fee", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "appointmentTime", dataType = "Date"),
            @ApiImplicitParam(name = "closeTime", dataType = "Date"),
            @ApiImplicitParam(name = "paymentTimestamp", dataType = "Date"),
            @ApiImplicitParam(name = "paymentMethod", dataType = "String"),
            @ApiImplicitParam(name = "earliestTime", dataType = "Date"),
            @ApiImplicitParam(name = "latestTime", dataType = "Date"),
            @ApiImplicitParam(name = "fieldC", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAppointmentPage(Page<HouseAppointmentRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         // for tag feature query
                                         @RequestParam(name = "tag", required = false) String tag,
                                         // end tag
                                         @RequestParam(name = "search", required = false) String search,

                                         @RequestParam(name = "code", required = false) String code,

                                         @RequestParam(name = "typeId", required = false) Long typeId,

                                         @RequestParam(name = "type", required = false) String type,

                                         @RequestParam(name = "userId", required = false) Long userId,

                                         @RequestParam(name = "userPhone", required = false) String userPhone,

                                         @RequestParam(name = "userName", required = false) String userName,

                                         @RequestParam(name = "serverId", required = false) Long serverId,

                                         @RequestParam(name = "serverName", required = false) String serverName,

                                         @RequestParam(name = "serverPhone", required = false) String serverPhone,

                                         @RequestParam(name = "name", required = false) String name,

                                         @RequestParam(name = "addressId", required = false) Long addressId,

                                         @RequestParam(name = "addressName", required = false) String addressName,

                                         @RequestParam(name = "description", required = false) String description,

                                         @RequestParam(name = "icon", required = false) String icon,

                                         @RequestParam(name = "status", required = false) Integer status,

                                         @RequestParam(name = "fee", required = false) BigDecimal fee,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "createTime", required = false) Date createTime,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "appointmentTime", required = false) Date appointmentTime,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "closeTime", required = false) Date closeTime,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "paymentTimestamp", required = false) Date paymentTimestamp,

                                         @RequestParam(name = "paymentMethod", required = false) String paymentMethod,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "earliestTime", required = false) Date earliestTime,

                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         @RequestParam(name = "latestTime", required = false) Date latestTime,

                                         @RequestParam(name = "fieldC", required = false) String fieldC,
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

        HouseAppointmentRecord record = new HouseAppointmentRecord();
        record.setCode(code);
        record.setTypeId(typeId);
        record.setType(type);
        record.setUserId(userId);
        record.setUserPhone(userPhone);
        record.setUserName(userName);
        record.setServerId(serverId);
        record.setServerName(serverName);
        record.setServerPhone(serverPhone);
        record.setName(name);
        record.setAddressId(addressId);
        record.setAddressName(addressName);
        record.setDescription(description);
        record.setIcon(icon);
        record.setStatus(status);
        record.setFee(fee);
        record.setCreateTime(createTime);
        record.setAppointmentTime(appointmentTime);
        record.setCloseTime(closeTime);
        record.setPaymentTimestamp(paymentTimestamp);
        record.setPaymentMethod(paymentMethod);
        record.setEarliestTime(earliestTime);
        record.setLatestTime(latestTime);
        record.setFieldC(fieldC);


        List<HouseAppointmentRecord> houseAppointmentPage = queryHouseAppointmentDao.findHouseAppointmentPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAppointmentPage);

        return SuccessTip.create(page);
    }


}

