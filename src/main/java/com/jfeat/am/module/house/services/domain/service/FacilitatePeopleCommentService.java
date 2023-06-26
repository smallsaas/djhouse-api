package com.jfeat.am.module.house.services.domain.service;

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
}
