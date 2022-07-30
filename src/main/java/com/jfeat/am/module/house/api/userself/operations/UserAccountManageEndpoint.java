package com.jfeat.am.module.house.api.userself.operations;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.EndpointUserMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.META;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
运维管理用户转换
 */

@RestController
@RequestMapping("/api/u/house/operations/userAccountManage")
public class UserAccountManageEndpoint {

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    EndpointUserService endpointUserService;

    @Resource
    Authentication authentication;

    @Resource
    EndpointUserMapper endpointUserMapper;

    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    /*
    返回最近注册的10个用户 提供电话查询
     */
    @GetMapping("/getRecentlyRegisteredUser/{appid}")
    public Tip getRecentlyRegisteredUser(
            @PathVariable("appid") String appid,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "type", required = false) Integer type) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }



        System.out.println("tenant" + JWTKit.getTenantOrgId());
        List<Long> appids = new ArrayList<>();
        if ("house".equals(appid)){
            appids.add(1L);
            appids.add(2L);
        }

        EndpointUserRecord record = new EndpointUserRecord();
        record.setPhone(phone);
        record.setType(type);
        record.setAppids(appids);



        List<EndpointUserRecord> userRecordList = queryEndpointUserDao.findEndUserPage(null, record, null, null, null, null, null);

         /*
         按照创建时间倒序排列
          */
        if (userRecordList != null && userRecordList.size() > 0) {
            userRecordList.sort((t1, t2) -> t2.getCreateTime().compareTo(t1.getCreateTime()));
        }

        /*
        当大于10个时分页
         */
        if (userRecordList.size() > 10) {
            userRecordList = userRecordList.subList(0, 10);
        }

        for (int i = 0; i < userRecordList.size(); i++) {
            if (userRecordList.get(i).getType() == null) {
                continue;
            }

//            将用户类型 转为list 返回个前端
            List<Integer> userTypeList = userAccountService.getUserTypeList(userRecordList.get(i).getType());
            if (userRecordList != null && userRecordList.size() > 0) {
                userRecordList.get(i).setTypeList(userTypeList);
            }

        }


        return SuccessTip.create(userRecordList);
    }



    /*
    修改设置用户类型
     */

    @PutMapping("/updateUserAccountType/{id}")
    public Tip updateUserCountType(
            @PathVariable("id") Long id,
            @RequestBody EndpointUser entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }
        if (entity.getTypeList() == null && entity.getTypeList().size() > 0) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "typeList不能为空");
        }

        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(id);
        if (endpointUserModel != null) {
            /*
            将list的用户类型 转回int存储会数据库
             */
            Integer userType = userAccountService.getUserTypeByList(entity.getTypeList());
            endpointUserModel.setType(userType);
            return SuccessTip.create(endpointUserMapper.updateById(endpointUserModel));
        }

        return SuccessTip.create();
    }


}
