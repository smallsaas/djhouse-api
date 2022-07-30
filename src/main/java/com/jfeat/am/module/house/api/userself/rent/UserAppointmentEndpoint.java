package com.jfeat.am.module.house.api.userself.rent;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseAppointmentPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAppointmentDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAppointmentRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAppointmentService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAppointmentModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.util.DateTimeKit;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/houseAppointment/houseAppointments")
public class UserAppointmentEndpoint {

    @Resource
    HouseAppointmentService houseAppointmentService;

    @Resource
    QueryHouseAppointmentDao queryHouseAppointmentDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @BusinessLog(name = "HouseAppointment", value = "create HouseAppointment")
    @PostMapping
    @ApiOperation(value = "新建 HouseAppointment", response = HouseAppointment.class)
    public Tip createHouseAppointment(@RequestBody HouseAppointment entity) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        entity.setUserId(JWTKit.getUserId());
        if (entity.getStatus()==null){
            entity.setStatus(HouseAppointment.STATUS_NOT_SET);
        }


        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.set(Calendar.DAY_OF_YEAR, afterCalendar.get(Calendar.DAY_OF_YEAR) + 7);
        Date today = afterCalendar.getTime();
        if (entity.getAppointmentTime().after(today)){
            throw new BusinessException(5500,"预约时间异常，只能提前7天预约！");
        }

        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.set(Calendar.DAY_OF_YEAR, beforeCalendar.get(Calendar.DAY_OF_YEAR));
        today = beforeCalendar.getTime();
//        entity.getAppointmentTime()+entity.getEarliestTime()
        if (entity.getAppointmentTime().before(today)){
            throw new BusinessException(5500,"预约时间异常，无法对已经过去的时间进行预约！");
        }

        Integer affected = 0;
        try {
            affected = houseAppointmentService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @ApiOperation(value = "HouseAppointment 列表信息", response = HouseAppointmentRecord.class)
    @GetMapping
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

                                         @RequestParam(name = "status", required = false) String status,

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

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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

        HouseAppointmentRecord record = new HouseAppointmentRecord();
        EndpointUserModel userModel = queryEndpointUserDao.queryMasterModel(JWTKit.getUserId());
        if (userModel.getType().equals(SecurityConstants.USER_TYPE_INTERMEDIARY)){
            record.setServerId(JWTKit.getUserId());
        }else {
            record.setUserId(JWTKit.getUserId());
        }

        record.setCode(code);
        record.setTypeId(typeId);
        record.setType(type);
//        record.setUserId(userId);
        record.setUserPhone(userPhone);
        record.setUserName(userName);
//        record.setServerId(serverId);
        record.setServerName(serverName);
        record.setServerPhone(serverPhone);
        record.setName(name);
        record.setAddressId(addressId);
        record.setAddressName(addressName);
        record.setDescription(description);
        record.setIcon(icon);

//        设置状态
        if ("notSet".equals(status)){
            record.setStatus(HouseAppointment.STATUS_NOT_SET);
        }else if ("sign".equals(status)){
            record.setStatus(HouseAppointment.STATUS_Sign);
        }else if ("miss".equals(status)){
            record.setStatus(HouseAppointment.STATUS_MISS);
        }else if ("contact".equals(status)){
            record.setStatus(HouseAppointment.STATUS_CONTACT);
        }else if ("looked".equals(status)){
            record.setStatus(HouseAppointment.STATUS_LOOKED);
        }

        record.setFee(fee);
        record.setCreateTime(createTime);
        record.setAppointmentTime(appointmentTime);
        record.setCloseTime(closeTime);
        record.setPaymentTimestamp(paymentTimestamp);
        record.setPaymentMethod(paymentMethod);
        record.setEarliestTime(earliestTime);
        record.setLatestTime(latestTime);
        record.setFieldC(fieldC);

//        填写信息电话
        List<HouseAppointmentRecord> houseAppointmentPage = queryHouseAppointmentDao.findHouseAppointmentPage(page, record, tag, search, orderBy, null, null);
        for (HouseAppointmentRecord houseAppointmentRecord:houseAppointmentPage){
            EndpointUserModel user = queryEndpointUserDao.queryMasterModel(houseAppointmentRecord.getUserId());
            if (user!=null){
                houseAppointmentRecord.setUserPhone(user.getPhone());
                houseAppointmentRecord.setUserName(user.getName());
                houseAppointmentRecord.setUserAvatar(user.getAvatar());
            }

            EndpointUserModel server = queryEndpointUserDao.queryMasterModel(houseAppointmentRecord.getServerId());
            if (server!=null){
                houseAppointmentRecord.setServerPhone(server.getPhone());
                houseAppointmentRecord.setServerAvatar(server.getAvatar());
                houseAppointmentRecord.setServerName(server.getName());
            }
//            设置房屋地址
            HouseAsset houseAsset = queryHouseAssetDao.queryMasterModel(houseAppointmentRecord.getAddressId());
            if (houseAsset!=null){
                houseAppointmentRecord.setHouseAsset(houseAsset);
            }
            houseAppointmentRecord.setSimpleTime(DateTimeKit.toTimeline(houseAppointmentRecord.getCreateTime()));
            houseAppointmentRecord.setAppointmentTimeStamp(houseAppointmentRecord.getAppointmentTime().getTime());
        }
        page.setRecords(houseAppointmentPage);
        return SuccessTip.create(page);
    }


//    修改预约状态
    @PutMapping
    public Tip updateHouseAppointment(@RequestBody HouseAppointmentModel entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getId()==null  || "".equals(entity.getId())){
            throw new BusinessException(BusinessCode.BadRequest,"id不能为空");
        }
        if (entity.getStatusStr()==null  || "".equals(entity.getStatusStr())){
            throw new BusinessException(BusinessCode.BadRequest,"statusStr不能为空");
        }
        String status = entity.getStatusStr();
        Long id = entity.getId();
        HouseAppointmentModel houseAppointmentModel = queryHouseAppointmentDao.queryMasterModel(id);
        if ("notSet".equals(status)){
            houseAppointmentModel.setStatus(HouseAppointment.STATUS_NOT_SET);
        }else if ("sign".equals(status)){
            houseAppointmentModel.setStatus(HouseAppointment.STATUS_Sign);
        }else if ("miss".equals(status)){
            houseAppointmentModel.setStatus(HouseAppointment.STATUS_MISS);
        }else if ("contact".equals(status)){
            houseAppointmentModel.setStatus(HouseAppointment.STATUS_CONTACT);
        }else if ("looked".equals(status)){
            houseAppointmentModel.setStatus(HouseAppointment.STATUS_LOOKED);
        }
        return SuccessTip.create(houseAppointmentService.updateMaster(houseAppointmentModel));
    }


    @DeleteMapping("/{id}")
    public Tip deleteHouseAppointment(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseAppointmentService.deleteMaster(id));
    }

}
