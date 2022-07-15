package com.jfeat.am.module.house.api.userself.operations;

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
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/*
运维管理用户转换
 */

@RestController
@RequestMapping("/api/u/house/operations/userCountManage")
public class UserCountManageEndpoint {

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    EndpointUserService endpointUserService;

    @Resource
    Authentication authentication;

    @Resource
    EndpointUserMapper endpointUserMapper;

    UserAccountMapper userAccountMapper;

    /*
    返回最近注册的10个用户 提供电话查询
     */
    @GetMapping("getRecentlyRegisteredUser")
    public Tip getRecentlyRegisteredUser( @RequestParam(name = "phone", required = false) String phone) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        EndpointUserRecord record = new EndpointUserRecord();
        record.setPhone(phone);

        List<EndpointUserRecord> userRecordList =  queryEndpointUserDao.findEndUserPage(null,record,null,null,null,null,null);

         /*
         按照创建时间倒序排列
          */
        if (userRecordList!=null &&userRecordList.size()>0){
            userRecordList.sort((t1, t2) -> t2.getCreateTime().compareTo(t1.getCreateTime()));
        }

        /*
        当大于10个时分页
         */
        if (userRecordList.size()>10){
            userRecordList = userRecordList.subList(0,10);
        }


        return SuccessTip.create(userRecordList);
    }



    /*
    修改设置用户类型
     */

    @PutMapping("/updateUserCountType")
    public Tip updateUserCountType(@RequestBody EndpointUser entity){

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        if (entity.getId()==null){
            throw new BusinessException(BusinessCode.EmptyNotAllowed,"id不能为空");
        }
        if (entity.getType()==null){
            throw new BusinessException(BusinessCode.EmptyNotAllowed,"type不能为空");
        }

        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(entity.getId());
        if (endpointUserModel!=null){
            endpointUserModel.setType(entity.getType());
            return SuccessTip.create(endpointUserMapper.updateById(endpointUserModel));
        }

        return SuccessTip.create();
    }



}
