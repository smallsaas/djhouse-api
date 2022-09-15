package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.appointment.services.gen.persistence.dao.AppointmentTimeMapper;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAppointment;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/u/house/rent/userAgentAppointment")
public class UserAgentAppointmentEndpoint {

    protected final Log logger = LogFactory.getLog(getClass());

    @Resource
    AppointmentTimeMapper appointmentTimeMapper;

    //    添加预约时间段
    @PostMapping
    public Tip createAppointmentTime(@RequestBody AppointmentTime entity) {

        Date startDateTime = new Date();
        Date endDateTime = new Date();

        if (entity.getStartTimeStr()!=null && entity.getEndTimeStr()!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                Date startTime = simpleDateFormat.parse(entity.getStartTimeStr());
                Date endTime = simpleDateFormat.parse(entity.getEndTimeStr());
                if (startTime.after(endTime)){
                    throw new BusinessException(BusinessCode.CodeBase,"开始时间不能大于结束时间");
                }

                int t =0;
                if (startTime.getSeconds()>=30){
                    t = 1;
                }

                startDateTime.setHours(startTime.getHours());
                startDateTime.setMinutes(startTime.getMinutes()-t);
                entity.setStartTime(startDateTime);

                endDateTime.setHours(endTime.getHours());
                endDateTime.setMinutes(endTime.getMinutes()-t);
                entity.setEndTime(endDateTime);

            }catch (ParseException e){
                throw new BusinessException(BusinessCode.CodeBase,"解析时间失败");
            }

        }else {
            throw new BusinessException(BusinessCode.BadRequest,"开始时间和结束时间都不能为空");
        }



        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        entity.setUserId(userId);

        return SuccessTip.create(appointmentTimeMapper.insert(entity));
    }

    //    删除预约时间段
    @DeleteMapping("/{id}")
    public Tip deleteAppointmentTime(@PathVariable("id") Long id) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        AppointmentTime appointmentTime = appointmentTimeMapper.selectById(id);
        if (appointmentTime == null || !appointmentTime.getUserId().equals(userId)) {
            return SuccessTip.create();
        }
        return SuccessTip.create(appointmentTimeMapper.deleteById(id));

    }

    //    修改预约时间段
    @PutMapping("/{id}")
    public Tip updateAppointmentTime(@PathVariable("id") Long id, @RequestBody AppointmentTime entity) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        entity.setId(id);
        AppointmentTime appointmentTime = appointmentTimeMapper.selectById(id);
        if (appointmentTime == null || !appointmentTime.getUserId().equals(userId)) {
            return SuccessTip.create();
        }
        if (entity.getStartTime() != null) {
            appointmentTime.setStartTime(entity.getStartTime());
        }
        if (entity.getEndTime() != null) {
            appointmentTime.setEndTime(entity.getEndTime());
        }
        return SuccessTip.create(appointmentTimeMapper.updateById(appointmentTime));

    }

    //    预约时间段列表
    @GetMapping
    public Tip getAppointmentTimeList(@RequestParam(value = "category",required = false) String category) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        QueryWrapper<AppointmentTime> appointmentTimeQueryWrapper = new QueryWrapper<>();
        appointmentTimeQueryWrapper.eq(AppointmentTime.USER_ID, userId);
        List<AppointmentTime> appointmentTimeList = appointmentTimeMapper.selectList(appointmentTimeQueryWrapper);



        List<AppointmentTime> result = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String mid = "12:00:00";

        Date midDate = null;
        try {
            midDate = simpleDateFormat.parse(mid);
        } catch (ParseException e) {
            logger.error(e);
        }

        for (AppointmentTime appointmentTime : appointmentTimeList) {

            String s = simpleDateFormat.format(appointmentTime.getStartTime());
            String endTime = simpleDateFormat.format(appointmentTime.getEndTime());


            appointmentTime.setStartTimeStr(s);
            appointmentTime.setEndTimeStr(endTime);

            Date startTime = null;
            try {
                startTime = simpleDateFormat.parse(s);

            } catch (ParseException e) {
                logger.error(e);
            }

            if (startTime.before(midDate) && category!=null &&"am".equals(category)) {
                result.add(appointmentTime);
            } else if (startTime.after(midDate) && category!=null && "pm".equals(category)){
                result.add(appointmentTime);
            }
        }

        if (category==null||category.equals("")){
            return SuccessTip.create(appointmentTimeList);
        }

        return SuccessTip.create(result);

    }

//    停止服务时间
    @PutMapping("/stopTimeService/{id}")
    public Tip stopAppointmentService(@PathVariable("id") Long id) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        AppointmentTime appointmentTime = appointmentTimeMapper.selectById(id);
        if (appointmentTime == null || !appointmentTime.getUserId().equals(userId)) {
            return SuccessTip.create();
        }

        appointmentTime.setStatus(AppointmentTime.STATUS_SHUT_DOWN);

        return SuccessTip.create(appointmentTimeMapper.updateById(appointmentTime));
    }


    //    开启服务时间
    @PutMapping("/openTimeService/{id}")
    public Tip openAppointmentService(@PathVariable("id") Long id) {
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        AppointmentTime appointmentTime = appointmentTimeMapper.selectById(id);
        if (appointmentTime == null || !appointmentTime.getUserId().equals(userId)) {
            return SuccessTip.create();
        }

        appointmentTime.setStatus(AppointmentTime.STATUS_OPEN);

        return SuccessTip.create(appointmentTimeMapper.updateById(appointmentTime));
    }
}
