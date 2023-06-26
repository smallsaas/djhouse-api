package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.DTO.FacilitatePeopleCommentDTO;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeopleComment;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/24 22:42
 * @author: hhhhhtao
 */
public interface FacilitatePeopleCommentService {

    Page<FacilitatePeopleCommentDTO> findFacilitatePeopleCommentDTOById(Page<FacilitatePeopleCommentDTO> page, Integer id);

    int saveFacilitatePeopleComment(FacilitatePeopleComment facilitatePeopleComment);

    /**
     * 判断用户是否已经在该便民服务下发布过评论
     * @param facilitatePeopleId 便民服务id
     * @param userId 用户id
     * @return {facilitatePeopleId: ?, Published: ？}， Published：1 已发布 / 0 未发布
     */
    JSONObject PublishedFacilitatePeopleComment(Integer facilitatePeopleId, Long userId);
}
