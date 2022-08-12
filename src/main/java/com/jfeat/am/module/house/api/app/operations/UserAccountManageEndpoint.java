package com.jfeat.am.module.house.api.app.operations;

import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.EndpointUserMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_OPERATION_STRING,EndUserTypeSetting.USER_TYPE_TENANT_MANAGER_STRING})
    public Tip getRecentlyRegisteredUser(
            @PathVariable("appid") String appid,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(value = "search",required = false) String search) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
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
        record.setDeleteFlag(0);



        List<EndpointUserRecord> userRecordList = queryEndpointUserDao.findEndUserPage(null, record, null, search, null, null, null);

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

//        前端可以修改的权限列表
        List<Integer> modifyAble = Arrays.asList(EndUserTypeSetting.USER_TYPE_LANDLORD,EndUserTypeSetting.USER_TYPE_EXPERIENCE,EndUserTypeSetting.USER_TYPE_SUPPLIER,EndUserTypeSetting.USER_TYPE_INTERMEDIARY,EndUserTypeSetting.USER_TYPE_OPERATION);

//        用户原来拥护的所有权限
        List<Integer> userTypeList = userAccountService.getUserTypeList(endpointUserModel.getType());

//        去除可修改的权限
        List<Integer> terminalType = userTypeList.stream().filter(item->!modifyAble.contains(item)).collect(Collectors.toList());

        if (endpointUserModel != null) {
            /*
            将list的用户类型 转回int存储会数据库
             */
            terminalType.addAll(entity.getTypeList());
            Integer userType = userAccountService.getUserTypeByList(terminalType);
            endpointUserModel.setType(userType);
            return SuccessTip.create(endpointUserMapper.updateById(endpointUserModel));
        }

        return SuccessTip.create();
    }


}
