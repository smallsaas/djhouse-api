package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.FacilitatePeopleDao;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleService;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/23 18:19
 * @author: hhhhhtao
 */
@Service("facilitatePeople")
public class FacilitatePeopleServiceImpl implements FacilitatePeopleService {

    @Resource
    FacilitatePeopleDao facilitatePeopleDao;

    @Resource
    UserAccountUtility userAccountUtility;

    @Override
    public Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeople> page,String serverName) {

        // 参数封装
        // 目前没有找到在使用DTO 和 DO 的情况下使用baseMapper，先注释掉
        // QueryWrapper<FacilitatePeople> facilitatePeopleQueryWrapper = new QueryWrapper<>();
        // if(serverName != null) facilitatePeopleQueryWrapper.like("server_name",serverName);

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权限");

        FacilitatePeople facilitatePeople = new FacilitatePeople();
        if (serverName != null) facilitatePeople.setServerName(serverName);

        return facilitatePeopleDao.findFacilitatePeople(page,facilitatePeople);
    }

    @Override
    public FacilitatePeopleRecord getFacilitatePeople(Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        // 参数校验
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");

        return facilitatePeopleDao.getFacilitatePeople(id);
    }

    @Override
    public int updateFacilitatePeople(FacilitatePeopleRecord facilitatePeopleRecord) {
        return 0;
    }

    @Transactional
    @Override
    public int saveFacilitatePeople(FacilitatePeopleRecord facilitatePeopleRecord) {

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        // 参数校验
        String serverName = facilitatePeopleRecord.getServerName();
        if (serverName == null || serverName.length() > 10)
            throw new BusinessException(BusinessCode.BadRequest,"serverName cannot null and length cannot greater than 10");
        String contactNumber = facilitatePeopleRecord.getContactNumber();
        if (contactNumber == null || contactNumber.length() != 11)
            throw new BusinessException(BusinessCode.BadRequest,"contactNumber Break the rules");

        // DTO 转换 DO
        FacilitatePeople facilitatePeople = new FacilitatePeople();
        facilitatePeople.setServerName(facilitatePeopleRecord.getServerName());
        facilitatePeople.setLinkmanName(facilitatePeopleRecord.getLinkmanName());
        facilitatePeople.setContactNumber(facilitatePeopleRecord.getContactNumber());
        facilitatePeople.setNotes(facilitatePeopleRecord.getNotes());

        // 执行baseMapper.insert
        int affected = facilitatePeopleDao.insert(facilitatePeople);
        if (affected != 1) throw new BusinessException(BusinessCode.DatabaseInsertError,"插入失败");

        return affected;
    }

    @Override
    public int removeFacilitatePeople(Integer id) {
        return 0;
    }
}
