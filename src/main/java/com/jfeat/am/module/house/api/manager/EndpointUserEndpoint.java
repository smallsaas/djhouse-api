
package com.jfeat.am.module.house.api.manager;


import com.jfeat.am.module.house.services.constant.AuthorizationConst;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
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
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/api/crud/house/endUser/endUsers")
public class EndpointUserEndpoint {

    @Resource
    EndpointUserService endpointUserService;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseAssetExchangeRequestDao queryHouseAssetExchangeRequestDao;



//    @Resource
//    QueryProductDao queryProductDao;


    @Permission(EndUserPermission.ENDUSER_NEW)
    @PostMapping
    @ApiOperation(value = "新建 EndpointUser", response = EndpointUser.class)
    public Tip createEndUser(@RequestBody EndpointUser entity) {
        Integer affected = 0;
        try {
            affected = endpointUserService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(EndUserPermission.ENDUSER_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 EndpointUser", response = EndpointUser.class)
    public Tip getEndUser(@PathVariable Long id) {
        EndpointUser endpointUser =  endpointUserService.listUser(id);
        if (endpointUser!=null){
            HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
            houseUserAssetRecord.setUserId(id);
            List<HouseUserAssetRecord>houseUserAssetRecordList =  queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord,null,null,null,null,null);
            endpointUser.setHouseUserAssetRecords(houseUserAssetRecordList);

            HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord = new HouseAssetExchangeRequestRecord();
            houseAssetExchangeRequestRecord.setUserId(id);
            List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecords = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,houseAssetExchangeRequestRecord,null,null,null,null,null);
            endpointUser.setExchangeRequestRecords(houseAssetExchangeRequestRecords);

        }

        return SuccessTip.create(endpointUser);
    }

    @BusinessLog(name = "EndpointUser", value = "update EndpointUser")
    @Permission(EndUserPermission.ENDUSER_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 EndpointUser", response = EndpointUser.class)
    public Tip updateEndUser(@PathVariable Long id, @RequestBody EndpointUser entity) {
        entity.setId(id);
        return SuccessTip.create(endpointUserService.updateMaster(entity));
    }

    @BusinessLog(name = "EndpointUser", value = "delete EndpointUser")
    @Permission(EndUserPermission.ENDUSER_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 EndpointUser")
    public Tip deleteEndUser(@PathVariable Long id) {
        return SuccessTip.create(endpointUserService.deleteMaster(id));
    }

    @Permission(EndUserPermission.ENDUSER_VIEW)
    @ApiOperation(value = "EndpointUser 列表信息", response = EndpointUserRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "avatar", dataType = "String"),
            @ApiImplicitParam(name = "account", dataType = "String"),
            @ApiImplicitParam(name = "password", dataType = "String"),
            @ApiImplicitParam(name = "salt", dataType = "String"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "dob", dataType = "Date"),
            @ApiImplicitParam(name = "gender", dataType = "Integer"),
            @ApiImplicitParam(name = "email", dataType = "String"),
            @ApiImplicitParam(name = "phone", dataType = "String"),
            @ApiImplicitParam(name = "inviteCode", dataType = "String"),
            @ApiImplicitParam(name = "invitorId", dataType = "Long"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "teamCount", dataType = "Integer"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "version", dataType = "Integer"),
            @ApiImplicitParam(name = "deleteFlag", dataType = "Integer"),
            @ApiImplicitParam(name = "registeredGithubUsername", dataType = "String"),
            @ApiImplicitParam(name = "registeredPhone", dataType = "String"),
            @ApiImplicitParam(name = "registeredEmail", dataType = "String"),
            @ApiImplicitParam(name = "emailValidated", dataType = "Integer"),
            @ApiImplicitParam(name = "phoneValidated", dataType = "Integer"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "tag", dataType = "String"),
            @ApiImplicitParam(name = "categoryTag", dataType = "String"),
            @ApiImplicitParam(name = "isCompere", dataType = "Integer"),
            @ApiImplicitParam(name = "isRegister", dataType = "Integer"),
            @ApiImplicitParam(name = "type", dataType = "Integer"),
            @ApiImplicitParam(name = "sysUserId", dataType = "Long"),
            @ApiImplicitParam(name = "vendor", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryEndUserPage(Page<EndpointUserRecord> page,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                // for tag feature query
                                @RequestParam(name = "tag", required = false) String tag,
                                // end tag
                                @RequestParam(name = "search", required = false) String search,

                                @RequestParam(name = "avatar", required = false) String avatar,

                                @RequestParam(name = "account", required = false) String account,

                                @RequestParam(name = "password", required = false) String password,

                                @RequestParam(name = "salt", required = false) String salt,

                                @RequestParam(name = "name", required = false) String name,

                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                @RequestParam(name = "dob", required = false) Date dob,

                                @RequestParam(name = "gender", required = false) Integer gender,

                                @RequestParam(name = "email", required = false) String email,

                                @RequestParam(name = "phone", required = false) String phone,

                                @RequestParam(name = "inviteCode", required = false) String inviteCode,

                                @RequestParam(name = "invitorId", required = false) Long invitorId,

                                @RequestParam(name = "status", required = false) Integer status,

                                @RequestParam(name = "teamCount", required = false) Integer teamCount,

                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                @RequestParam(name = "createTime", required = false) Date createTime,

                                @RequestParam(name = "version", required = false) Integer version,

                                @RequestParam(name = "deleteFlag", required = false) Integer deleteFlag,

                                @RequestParam(name = "registeredGithubUsername", required = false) String registeredGithubUsername,

                                @RequestParam(name = "registeredPhone", required = false) String registeredPhone,

                                @RequestParam(name = "registeredEmail", required = false) String registeredEmail,

                                @RequestParam(name = "emailValidated", required = false) Integer emailValidated,

                                @RequestParam(name = "phoneValidated", required = false) Integer phoneValidated,

                                @RequestParam(name = "orgId", required = false) Long orgId,


                                @RequestParam(name = "categoryTag", required = false) String categoryTag,

                                @RequestParam(name = "isCompere", required = false) Integer isCompere,

                                @RequestParam(name = "isRegister", required = false) Integer isRegister,

                                @RequestParam(name = "type", required = false) Integer type,

                                @RequestParam(name = "sysUserId", required = false) Long sysUserId,

                                @RequestParam(name = "vendor", required = false) String vendor,
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

        EndpointUserRecord record = new EndpointUserRecord();
        record.setAvatar(avatar);
        record.setAccount(account);
        record.setPassword(password);
        record.setSalt(salt);
        record.setName(name);
        record.setDob(dob);
        record.setGender(gender);
        record.setEmail(email);
        record.setPhone(phone);
        record.setInviteCode(inviteCode);
        record.setInvitorId(invitorId);
        record.setStatus(status);
        record.setTeamCount(teamCount);
        record.setCreateTime(createTime);
        record.setVersion(version);
        record.setDeleteFlag(deleteFlag);
        record.setRegisteredGithubUsername(registeredGithubUsername);
        record.setRegisteredPhone(registeredPhone);
        record.setRegisteredEmail(registeredEmail);
        record.setEmailValidated(emailValidated);
        record.setPhoneValidated(phoneValidated);
        if (META.enabledSaas()) {
            if(JWTKit.getAccount().equals(AuthorizationConst.masterAccount)){
            }else{
                record.setOrgId(JWTKit.getOrgId());
            }
        }
        record.setTag(tag);
        record.setCategoryTag(categoryTag);
        record.setIsCompere(isCompere);
        record.setIsRegister(isRegister);
        record.setType(type);
        record.setSysUserId(sysUserId);
        record.setVendor(vendor);

        List<EndpointUserRecord> endUserPage = queryEndpointUserDao.findEndUserPage(page, record, tag, search, orderBy, null, null);

        for (int i=0;i<endUserPage.size();i++){

            Long id = endUserPage.get(i).getId();
//            统计资产数
            HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
            houseUserAssetRecord.setUserId(id);
            List<HouseUserAssetRecord>houseUserAssetRecordList =  queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord,null,null,null,null,null);
            endUserPage.get(i).setAssetCount(houseUserAssetRecordList.size());

//            统计置换需求
            HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord = new HouseAssetExchangeRequestRecord();
            houseAssetExchangeRequestRecord.setUserId(id);
            List<HouseAssetExchangeRequestRecord> houseAssetExchangeRequestRecords = queryHouseAssetExchangeRequestDao.findHouseAssetExchangeRequestPage(null,houseAssetExchangeRequestRecord,null,null,null,null,null);
            endUserPage.get(i).setExchangeCount(houseAssetExchangeRequestRecords.size());

        }

        page.setRecords(endUserPage);

        return SuccessTip.create(page);
    }

    
}

