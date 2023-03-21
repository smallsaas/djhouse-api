package com.jfeat.am.module.house.api.app.operations;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.core.model.UserTypeEnum;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.domain.model.HouseApplicationIntermediaryRecord;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.EndpointUserMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.am.module.kehai.services.domain.service.VenderOverModelService;
import com.jfeat.am.module.kehai.services.gen.persistence.model.Vender;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.apache.commons.lang3.StringUtils;
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

    // 供应商服务类
    @Resource
    VenderOverModelService venderService;

    /*
    返回最近注册的10个用户 提供电话查询
     */
    @GetMapping("/getRecentlyRegisteredUser/{appid}")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_OPERATION_STRING,EndUserTypeSetting.USER_TYPE_TENANT_MANAGER_STRING})
    public Tip getRecentlyRegisteredUser(
            Page<EndpointUserRecord> page,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @PathVariable("appid") String appid,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(name = "subValue", required = false) Long subValue) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        System.out.println("tenant" + JWTKit.getTenantOrgId());
        List<Long> appids = new ArrayList<>();
        if ("house".equals(appid)){
            appids.add(1L);
            appids.add(2L);
            appids.add(3L);
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        EndpointUserRecord record = new EndpointUserRecord();
        record.setPhone(phone);
        record.setType(type);
        record.setAppids(appids);
        record.setDeleteFlag(0);

        List<EndpointUserRecord> userRecordList = queryEndpointUserDao.findEndUserPage(page, record, null, search, null, null, null);




        for (int i = 0; i < userRecordList.size(); i++) {
            if (userRecordList.get(i).getType() == null) {
                continue;
            }

            // 配合dynamic-page前端框架使用的参数，这里代表置业顾问id
//            将用户类型 转为list 返回个前端
            List<Integer> userTypeList = userAccountService.getUserTypeList(userRecordList.get(i).getType());
            if (userRecordList != null && userRecordList.size() > 0) {
                userRecordList.get(i).setTypeList(userTypeList);
                if (subValue != null) userRecordList.get(i).setSubValue(subValue);
            }

        }
        page.setRecords(userRecordList);

        return SuccessTip.create(page);
    }



    /*
    修改设置用户类型
     */

    @PutMapping("/updateUserAccountType/{id}")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_OPERATION_STRING,EndUserTypeSetting.USER_TYPE_TENANT_MANAGER_STRING})
    public Tip updateUserCountType(
            @PathVariable("id") Long id,
            @RequestBody EndpointUser entity) {

        // 验证发送请求的用户是否是运营身份
        if (entity.getTypeList() == null && entity.getTypeList().size() > 0) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "typeList不能为空");
        }

        // 查询id对应的用户信息
        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(id);

        // 初始化前端可以修改的权限列表
        List<Integer> modifyAble = Arrays.asList(
                EndUserTypeSetting.USER_TYPE_LANDLORD,       // 房东
                EndUserTypeSetting.USER_TYPE_EXPERIENCE,     // 体验用户
                EndUserTypeSetting.USER_TYPE_SUPPLIER,       // 供应商
                EndUserTypeSetting.USER_TYPE_INTERMEDIARY,   // 中介
                EndUserTypeSetting.USER_TYPE_OPERATION,      // 运维
                EndUserTypeSetting.USER_TYPE_SALES,          // 销售
                EndUserTypeSetting.USER_TYPE_RESIDENTS,      // 社区居民
                EndUserTypeSetting.USER_TYPE_INVESTOR,       // 投资商
                EndUserTypeSetting.USER_TYPE_BRAND,          // 品牌商
                EndUserTypeSetting.USER_TYPE_GROUP_MEMBER ,  // 团友
                EndUserTypeSetting.USER_TYPE_DEVELOPER ,     // 开发者
                EndUserTypeSetting.USER_TYPE_TEAM_LEADER     // 团长
        );

        // 该id用户原来拥护的所有权限
        List<Integer> userTypeList = userAccountService.getUserTypeList(endpointUserModel.getType());
        // 去除可修改的权限
        List<Integer> terminalType = userTypeList.stream().filter(item->!modifyAble.contains(item)).collect(Collectors.toList());

        if (endpointUserModel != null) {
            // 如果添加了供应商权限则将该用户加入供应商表
            if (entity.getTypeList() != null && entity.getTypeList().size() > 0 && entity.getTypeList().contains(EndUserTypeSetting.USER_TYPE_SUPPLIER)) {
                if (StringUtils.isBlank(endpointUserModel.getName())) throw new BusinessException(BusinessCode.EmptyNotAllowed,"如果要设为供应商，用户的name不能为空");
                if (endpointUserModel.getOrgId() == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"如果要设为供应商，用户的orgId不能为空");

                Vender vender = new Vender();
                vender.setUserId(endpointUserModel.getId());
                vender.setName(endpointUserModel.getName());
                vender.setOrgId(endpointUserModel.getOrgId());
                int affected = venderService.save(vender);
                if (affected == 0) throw new BusinessException(BusinessCode.DatabaseInsertError,"供应商设置失败");
            }

            // 将list的用户类型 转回int存储回数据库
            terminalType.addAll(entity.getTypeList());
            Integer userType = userAccountService.getUserTypeByList(terminalType);
            endpointUserModel.setType(userType);
            return SuccessTip.create(endpointUserMapper.updateById(endpointUserModel));
        }

        return SuccessTip.create();
    }

}
