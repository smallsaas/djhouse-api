package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.constants.CacheConst;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/23 18:19
 * @author: hhhhhtao
 */
@Service("facilitatePeople")
public class FacilitatePeopleServiceImpl implements FacilitatePeopleService {

    private static final Logger logger = LoggerFactory.getLogger(FacilitatePeopleServiceImpl.class);

    @Resource
    FacilitatePeopleDao facilitatePeopleDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeopleRecord> page, String search) {

        Page<FacilitatePeopleRecord> facilitatePeoplePage = facilitatePeopleDao.findFacilitatePeople(page, search);
        // 获取便民服务拨打次数
        facilitatePeoplePage.getRecords().stream()
                .map(facilitatePeople -> {
                    facilitatePeople.setFrequency(getFacilitatePeoPleDialFrequency(facilitatePeople.getId()));
                    return facilitatePeople;
                })
                .collect(Collectors.toList());

        return facilitatePeoplePage;
    }

    @Override
    public Page<FacilitatePeople> managementFindFacilitatePeople(Page<FacilitatePeople> page, String search) {

        QueryWrapper<FacilitatePeople> facilitatePeopleQueryWrapper = new QueryWrapper<>();
        facilitatePeopleQueryWrapper.orderByDesc("create_date_time");
        if (search != null && StringUtils.isNotBlank(search)) {
            facilitatePeopleQueryWrapper.like("server_name", search).or().like("tags", search);
        }

        return facilitatePeopleDao.selectPage(page, facilitatePeopleQueryWrapper);
    }

    @Override
    public FacilitatePeople getFacilitatePeople(Integer id) {

        // 参数校验
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed, "id cannot null");

        return facilitatePeopleDao.getFacilitatePeople(id);
    }

    @Override
    public int updateFacilitatePeople(FacilitatePeople facilitatePeople) {

        // 参数校验,除id以外的参数如果为 "" 空串，则修改为null,不写入数据库
        // id
        if (facilitatePeople.getId() == null)
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "id cannot null");

        // 服务名
        if (StringUtils.isBlank(facilitatePeople.getServerName()) || facilitatePeople.getServerName().length() > FacilitatePeople.SERVER_NAME_LENGTH)
            throw new BusinessException(BusinessCode.BadRequest, "serverName cannot null and length cannot greater than 10");

        // 联系电话和微信号只能有一个为空 （需求变动，都可以为空）
        // if (StringUtils.isBlank(facilitatePeople.getContactNumber()) && StringUtils.isBlank(facilitatePeople.getWechat()))
        //     throw new BusinessException(BusinessCode.BadRequest, "contactNumber和wechat不能同时为空");

        // 联系电话
        if (StringUtils.isBlank(facilitatePeople.getContactNumber())) {
            facilitatePeople.setContactNumber(null);
        } else if (facilitatePeople.getContactNumber().length() != FacilitatePeople.CONTACT_NUMBER_LENGTH) {
            throw new BusinessException(BusinessCode.BadRequest, "contactNumber Break the rules");
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
            throw new BusinessException(BusinessCode.OutOfRange, "tags length cannot greater than " + FacilitatePeople.TAGS_LENGTH);
        }

        // status数据库默认为1,该插入方法暂时不允许修改status
        facilitatePeople.setStatus(null);

        // 执行
        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError, "更新失败,请检查该id是否存在");

        return affected;

    }

    @Transactional
    @Override
    public int saveFacilitatePeople(FacilitatePeople facilitatePeople) {

        // 参数校验，如果参数为 "" 空串，则修改为null,不写入数据库
        // 服务名
        if (StringUtils.isBlank(facilitatePeople.getServerName()) || facilitatePeople.getServerName().length() > FacilitatePeople.SERVER_NAME_LENGTH)
            throw new BusinessException(BusinessCode.BadRequest, "serverName cannot null and length cannot greater than 10");

        // 联系电话和微信号只能有一个为空 （需求变动，都可以为空）
        // if (StringUtils.isBlank(facilitatePeople.getContactNumber()) && StringUtils.isBlank(facilitatePeople.getWechat())) throw new BusinessException(BusinessCode.BadRequest,"contactNumber和wechat不能同时为空");

        // 联系电话
        if (StringUtils.isBlank(facilitatePeople.getContactNumber())) {
            facilitatePeople.setContactNumber(null);
        } else if (facilitatePeople.getContactNumber().length() != FacilitatePeople.CONTACT_NUMBER_LENGTH) {
            throw new BusinessException(BusinessCode.BadRequest, "contactNumber Break the rules");
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
            throw new BusinessException(BusinessCode.OutOfRange, "tags length cannot greater than " + FacilitatePeople.TAGS_LENGTH);
        }

        // 创建时间
        facilitatePeople.setCreateDateTime(LocalDateTime.now());

        // status数据库默认为1（1为开启，0为关闭）
        facilitatePeople.setStatus(null);

        // 执行插入
        int affected = facilitatePeopleDao.insert(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseInsertError, "插入失败");

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

        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed, "id cannot null");

        FacilitatePeople facilitatePeople = new FacilitatePeople();
        facilitatePeople.setId(id);
        facilitatePeople.setStatus(true);

        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError, "更新失败");

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

        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed, "id cannot null");

        FacilitatePeople facilitatePeople = new FacilitatePeople();
        facilitatePeople.setId(id);
        facilitatePeople.setStatus(false);

        int affected = facilitatePeopleDao.updateById(facilitatePeople);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError, "更新失败");

        return affected;
    }

    @Override
    public int removeFacilitatePeople(Integer id) {

        return facilitatePeopleDao.deleteById(id);
    }

    /**
     * 初始化便民服务的拨打电话次数
     *
     * @param id 便民服务id
     * @return
     */
    @Override
    public String initializationFacilitatePeoPleDialFrequency(Integer id) {

        stringRedisTemplate.opsForValue().set(CacheConst.getFacilitatePeopleRedisKey(id),CacheConst.FACILITATE_PEOPLE_INITIALIZATION_NUMBER);

        return stringRedisTemplate.opsForValue().get(CacheConst.getFacilitatePeopleRedisKey(id));
    }

    /**
     * 便民服务拨打电话数加一
     * 添加成功返回当前拨打次数
     *
     * @param id 便民服务id
     */
    @Override
    public String addFacilitatePeoPleDialFrequency(Integer id) {

        // increment：获取指定key的值进行加1，如果没有对应的key则会创建，默认值为0
        stringRedisTemplate.opsForValue().increment(CacheConst.getFacilitatePeopleRedisKey(id), CacheConst.FACILITATE_PEOPLE_ONCE_ADD_NUMBER);

        return stringRedisTemplate.opsForValue().get(CacheConst.getFacilitatePeopleRedisKey(id));
    }

    /**
     * 获取指定便民服务拨打电话数
     *
     * @param id 便民服务id
     * @return
     */
    @Override
    public String getFacilitatePeoPleDialFrequency(Integer id) {
        String frequency = stringRedisTemplate.opsForValue().get(CacheConst.getFacilitatePeopleRedisKey(id));
        // 如果没有该key则交由 addFacilitatePeoPleDialQuantity() 去创建
        if (frequency == null) frequency = initializationFacilitatePeoPleDialFrequency(id);
        return frequency;
    }
}
