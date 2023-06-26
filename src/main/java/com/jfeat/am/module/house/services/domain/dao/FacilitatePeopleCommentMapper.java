package com.jfeat.am.module.house.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.persistence.model.DTO.FacilitatePeopleCommentDTO;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeopleComment;
import org.apache.ibatis.annotations.Param;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/24 22:51
 * @author: hhhhhtao
 */
public interface FacilitatePeopleCommentMapper extends BaseMapper<FacilitatePeopleComment> {

    Page<FacilitatePeopleCommentDTO> findFacilitatePeopleCommentDTOById(Page<FacilitatePeopleCommentDTO> page, @Param("facilitatePeopleId") Integer facilitatePeopleId);
}
