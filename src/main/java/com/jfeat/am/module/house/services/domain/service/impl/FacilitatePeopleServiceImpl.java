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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
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
    public Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeopleRecord> page,String serverName) {

        // 参数封装
        FacilitatePeople facilitatePeople = new FacilitatePeople();
        if (serverName != null) facilitatePeople.setServerName(serverName);

        return facilitatePeopleDao.findFacilitatePeople(page,facilitatePeople);
    }

    @Override
    public Page<FacilitatePeople> managementFindFacilitatePeople(Page<FacilitatePeople> page,String serverName) {

         QueryWrapper<FacilitatePeople> facilitatePeopleQueryWrapper = new QueryWrapper<>();
         if (serverName != null && StringUtils.isNotBlank(serverName)) facilitatePeopleQueryWrapper.like("server_name",serverName);
         return facilitatePeopleDao.selectPage(page,facilitatePeopleQueryWrapper);
    }

    @Override
    public FacilitatePeople getFacilitatePeople(Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        // 参数校验
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");

        return facilitatePeopleDao.getFacilitatePeople(id);
    }

    @Override
    public int updateFacilitatePeople(FacilitatePeople facilitatePeople) {
        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        // 参数校验,除id以外的参数如果为 "" 空串，则修改为null,不写入数据库
        // id
        if (facilitatePeople.getId() == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");

        // 服务名
        if (StringUtils.isBlank(facilitatePeople.getServerName()) || facilitatePeople.getServerName().length() > FacilitatePeople.SERVER_NAME_LENGTH) throw new BusinessException(BusinessCode.BadRequest,"serverName cannot null and length cannot greater than 10");

        // 联系电话和微信号只能有一个为空
        if (StringUtils.isBlank(facilitatePeople.getContactNumber()) && StringUtils.isBlank(facilitatePeople.getWechat())) throw new BusinessException(BusinessCode.BadRequest,"contactNumber和wechat不能同时为空");

        // 联系电话
        if (StringUtils.isBlank(facilitatePeople.getContactNumber())) {
            facilitatePeople.setContactNumber(null);
        } else if (facilitatePeople.getContactNumber().length() != FacilitatePeople.CONTACT_NUMBER_LENGTH) {
            throw new BusinessException(BusinessCode.BadRequest,"contactNumber Break the rules");
        }

        // 微信号
        if (StringUtils.isBlank(facilitatePeople.getWechat())) {
            facilitatePeople.setWechat(null);
        } else if (facilitatePeople.getWechat().length() > FacilitatePeople.WECHAT_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "wechat length cannot greater than " + FacilitatePeople.WECHAT_LENGTH);
        }

        // 联系人
        if (StringUtils.isBlank(facilitatePeople.getLinkmanName())) {
            facilitatePeople.setLinkmanName(null);
        } else if (facilitatePeople.getLinkmanName().length() > FacilitatePeople.LINKMAN_NAME_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "linkmanName length cannot greater than " + FacilitatePeople.LINKMAN_NAME_LENGTH);
        }

        // 备注
        if (StringUtils.isBlank(facilitatePeople.getNotes())) {
            facilitatePeople.setNotes(null);
        } else if (facilitatePeople.getNotes().length() > FacilitatePeople.NOTES_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "notes length cannot greater than " + FacilitatePeople.NOTES_LENGTH);
        }

        // 服务标签
        if (StringUtils.isBlank(facilitatePeople.getTags())) {
            facilitatePeople.setTags(null);
        } else if (facilitatePeople.getTags().length() > FacilitatePeople.TAGS_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange,"tags length cannot greater than " + FacilitatePeople.TAGS_LENGTH);
        }

        // status数据库默认为1,该插入方法暂时不允许修改status
        facilitatePeople.setStatus(null);

        // 执行
        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError,"更新失败,请检查该id是否存在");

        return affected;

    }

    @Transactional
    @Override
    public int saveFacilitatePeople(FacilitatePeople facilitatePeople) {

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        // 参数校验
        // 如果参数为 "" 空串，则修改为null,不写入数据库
        // 服务名
        if (StringUtils.isBlank(facilitatePeople.getServerName()) || facilitatePeople.getServerName().length() > FacilitatePeople.SERVER_NAME_LENGTH) throw new BusinessException(BusinessCode.BadRequest,"serverName cannot null and length cannot greater than 10");

        // 联系电话和微信号只能有一个为空
        if (StringUtils.isBlank(facilitatePeople.getContactNumber()) && StringUtils.isBlank(facilitatePeople.getWechat())) throw new BusinessException(BusinessCode.BadRequest,"contactNumber和wechat不能同时为空");

        // 联系电话
        if (StringUtils.isBlank(facilitatePeople.getContactNumber())) {
            facilitatePeople.setContactNumber(null);
        } else if (facilitatePeople.getContactNumber().length() != FacilitatePeople.CONTACT_NUMBER_LENGTH) {
            throw new BusinessException(BusinessCode.BadRequest,"contactNumber Break the rules");
        }

        // 微信号
        if (StringUtils.isBlank(facilitatePeople.getWechat())) {
            facilitatePeople.setWechat(null);
        } else if (facilitatePeople.getWechat().length() > FacilitatePeople.WECHAT_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "wechat length cannot greater than " + FacilitatePeople.WECHAT_LENGTH);
        }

        // 联系人
        if (StringUtils.isBlank(facilitatePeople.getLinkmanName())) {
            facilitatePeople.setLinkmanName(null);
        } else if (facilitatePeople.getLinkmanName().length() > FacilitatePeople.LINKMAN_NAME_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "linkmanName length cannot greater than " + FacilitatePeople.LINKMAN_NAME_LENGTH);
        }

        // 备注
        if (StringUtils.isBlank(facilitatePeople.getNotes())) {
            facilitatePeople.setNotes(null);
        } else if (facilitatePeople.getNotes().length() > FacilitatePeople.NOTES_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange, "notes length cannot greater than " + FacilitatePeople.NOTES_LENGTH);
        }

        // 服务标签
        if (StringUtils.isBlank(facilitatePeople.getTags())) {
            facilitatePeople.setTags(null);
        } else if (facilitatePeople.getTags().length() > FacilitatePeople.TAGS_LENGTH) {
            throw new BusinessException(BusinessCode.OutOfRange,"tags length cannot greater than " + FacilitatePeople.TAGS_LENGTH);
        }

        // status数据库默认为1,该插入方法暂时不允许修改status
        facilitatePeople.setStatus(null);

        // 执行baseMapper.insert
        int affected = facilitatePeopleDao.insert(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseInsertError,"插入失败");

        return affected;
    }

    /**
     * 开放指定便民服务
     *
     * @param id
     * @return
     */
    @Override
    public int updateFacilitatePeopleOfStatusOpen(Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");

        FacilitatePeople facilitatePeople = new FacilitatePeople();
        facilitatePeople.setId(id);
        facilitatePeople.setStatus(true);

        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError,"更新失败");

        return affected;
    }

    /**
     * 关闭指定便民服务
     *
     * @param id
     * @return
     */
    @Override
    public int updateFacilitatePeopleOfStatusClose(Integer id) {
        // 判断用户是否拥有社区管理员权限
        if (userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_TENANT_MANAGER)) throw new BusinessException(BusinessCode.NoPermission,"没有社区管理权");

        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");

        FacilitatePeople facilitatePeople = new FacilitatePeople();
        facilitatePeople.setId(id);
        facilitatePeople.setStatus(false);

        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError,"更新失败");

        return affected;
    }

    @Override
    public int removeFacilitatePeople(Integer id) {
        return 0;
    }
}
