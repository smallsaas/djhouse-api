package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleCommentService;
import com.jfeat.am.module.house.services.gen.persistence.model.DTO.FacilitatePeopleCommentDTO;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeopleComment;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/26 09:32
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/api/u/house/operations/facilitate-people/comment")
public class FacilitatePeopleCommentEndpoint {

    @Resource
    FacilitatePeopleCommentService facilitatePeopleCommentService; // 便民服务评论服务类

    /**
     * 分页查询 便民服务评论
     * @param facilitatePeopleId 便民服务id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{facilitatePeopleId}")
    public Tip findFacilitatePeopleCommentDTOById(@PathVariable(value = "facilitatePeopleId") Integer facilitatePeopleId,
                                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        Page<FacilitatePeopleCommentDTO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleCommentService.findFacilitatePeopleCommentDTOById(page, facilitatePeopleId));
    }

    /**
     * 创建评论
     * @param facilitatePeopleCommentDTO 便民服务评论数据传输类
     * @return
     */
    @PostMapping()
    public Tip insertFacilitatePeopleComment(@RequestBody FacilitatePeopleCommentDTO facilitatePeopleCommentDTO) {
        // 从token中获取用户id
        Long userId = JWTKit.getUserId();
        if (userId == null) throw new BusinessException(BusinessCode.UserNotExisted);

        // 封装参数
        FacilitatePeopleComment facilitatePeopleComment = new FacilitatePeopleComment();
        facilitatePeopleComment.setUid(facilitatePeopleCommentDTO.getUid());
        facilitatePeopleComment.setFacilitatePeopleId(facilitatePeopleCommentDTO.getFacilitatePeopleId());
        facilitatePeopleComment.setUserId(userId);
        facilitatePeopleComment.setValue(facilitatePeopleCommentDTO.getValue());
        facilitatePeopleComment.setAnonymous(facilitatePeopleCommentDTO.getAnonymous());

        // 执行插入
        facilitatePeopleCommentService.saveFacilitatePeopleComment(facilitatePeopleComment);
        // saveFacilitatePeopleComment内部已经做了插入成功与否的判断，插入失败会报异常，这里直接返回成功即可
        return SuccessTip.create();
    }

    @GetMapping("/published/{facilitatePeopleId}")
    public Tip PublishedFacilitatePeopleComment(@PathVariable(value = "facilitatePeopleId") Integer facilitatePeopleId) {

        // 从token中获取用户的id
        Long userId = JWTKit.getUserId();
        if (userId == null) throw new BusinessException(BusinessCode.UserNotExisted);

        return SuccessTip.create(facilitatePeopleCommentService.PublishedFacilitatePeopleComment(facilitatePeopleId, userId));
    }
}
