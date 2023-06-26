package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.constant.FacilitatePeopleCommentConst;
import com.jfeat.am.module.house.services.domain.dao.FacilitatePeopleCommentMapper;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleCommentService;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleService;
import com.jfeat.am.module.house.services.gen.persistence.model.DTO.FacilitatePeopleCommentDTO;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeopleComment;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/24 22:50
 * @author: hhhhhtao
 */
@Service("FacilitatePeopleCommentService")
public class FacilitatePeopleCommentServiceImpl implements FacilitatePeopleCommentService {

    @Resource
    FacilitatePeopleCommentMapper facilitatePeopleCommentMapper;

    @Resource
    FacilitatePeopleService facilitatePeopleService;

    @Resource
    EndpointUserService endpointUserService;

    /**
     * 分页查询，根据便民服务的id查询非逻辑删除的评论列表
     * @param page
     * @param facilitatePeopleId
     * @return
     */
    @Override
    public Page<FacilitatePeopleCommentDTO> findFacilitatePeopleCommentDTOById(Page<FacilitatePeopleCommentDTO> page, Integer facilitatePeopleId) {

        return facilitatePeopleCommentMapper.findCommentDTOByFacilitatePeopleId(page, facilitatePeopleId);
    }

    @Override
    public int saveFacilitatePeopleComment(FacilitatePeopleComment facilitatePeopleComment) {

        // 判断是否有设定uuid，如果没有则设定
        if (facilitatePeopleComment.getUid() == null) {
            String uid = UUID.randomUUID().toString().replaceAll("-", "");
            facilitatePeopleComment.setUid(uid);
        }

        // 便民服务id不允许为空，并且判断是否存在这个便民服务
        if (facilitatePeopleComment.getFacilitatePeopleId() == null) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed,"便民服务id不允许为空");
        }
        Boolean existFacilitatePeople = facilitatePeopleService.ExistFacilitatePeopleById(facilitatePeopleComment.getFacilitatePeopleId());
        if (!existFacilitatePeople) {
            throw new BusinessException(BusinessCode.InvalidKey, "评论的便民服务不存在");
        }

        // 用户id不能为空，并且判断是否有该用户
        if (facilitatePeopleComment.getUserId() == null) {
            throw new BusinessException(BusinessCode.EmptyNotAllowed,"userId不允许为空");
        }
        Boolean existUser = endpointUserService.existUser(facilitatePeopleComment.getUserId());
        if (!existUser) {
            throw new BusinessException(BusinessCode.InvalidKey, "指定的用户不存在");
        }

        // value不能为空，长度不允许超过200
        String value = facilitatePeopleComment.getValue();
        if (StringUtils.isNotBlank(value)) {
            if (value.length() > 200) {
                throw new BusinessException(BusinessCode.OutOfRange, "评论字符超过了200个字符");
            }
        } else {
            throw new BusinessException(BusinessCode.EmptyNotAllowed, "不允许发布空的评论");
        }

        // 插入评论
        int affected = 0;
        affected = facilitatePeopleCommentMapper.insert(facilitatePeopleComment);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseInsertError);

        return affected;
    }

    /**
     * 判断用户是否已经在该便民服务下发布过评论
     *
     * @param facilitatePeopleId 便民服务id
     * @param userId             用户id
     * @return {facilitatePeopleId: ?, published: ？}， Published：1 已发布 / 0 未发布
     */
    @Override
    public JSONObject PublishedFacilitatePeopleComment(Integer facilitatePeopleId, Long userId) {

        JSONObject result = new JSONObject();
        result.put("facilitatePeopleId", facilitatePeopleId);
        int entry = facilitatePeopleCommentMapper.countCommentByFacilitatePeopleIdAndUserId(facilitatePeopleId, userId);
        if (entry > 0) {
            result.put("published", FacilitatePeopleCommentConst.PUBLISHED);
        } else {
            result.put("published", FacilitatePeopleCommentConst.UNRELEASED);
        }
        return result;
    }
}
