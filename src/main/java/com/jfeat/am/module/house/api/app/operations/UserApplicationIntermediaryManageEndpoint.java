package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseApplicationIntermediaryDao;
import com.jfeat.am.module.house.services.domain.model.HouseApplicationIntermediaryRecord;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationIntermediaryService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseApplicationIntermediaryMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/u/house/operations/userApplicationIntermediary")
public class UserApplicationIntermediaryManageEndpoint {


    @Resource
    HouseApplicationIntermediaryService houseApplicationIntermediaryService;

    @Resource
    QueryHouseApplicationIntermediaryDao queryHouseApplicationIntermediaryDao;

    @Resource
    HouseApplicationIntermediaryMapper houseApplicationIntermediaryMapper;


    @GetMapping
    public Tip queryHouseApplicationIntermediaryPage(Page<HouseApplicationIntermediaryRecord> page,
                                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                     // for tag feature query
                                                     @RequestParam(name = "tag", required = false) String tag,
                                                     // end tag
                                                     @RequestParam(name = "search", required = false) String search,

                                                     @RequestParam(name = "userId", required = false) Long userId,

                                                     @RequestParam(name = "organization", required = false) String organization,

                                                     @RequestParam(name = "name", required = false) String name,

                                                     @RequestParam(name = "phone", required = false) String phone,

                                                     @RequestParam(name = "idcard", required = false) String idcard,

                                                     @RequestParam(name = "evidence", required = false) String evidence,

                                                     @RequestParam(name = "reason", required = false) String reason,

                                                     @RequestParam(name = "signFlag", required = false) Integer signFlag,

                                                     @RequestParam(name = "status", required = false) Integer status,

                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                     @RequestParam(name = "createTime", required = false) Date createTime,

                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                     @RequestParam(name = "updateTime", required = false) Date updateTime,
                                                     @RequestParam(name = "orderBy", required = false) String orderBy,
                                                     @RequestParam(name = "sort", required = false) String sort) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

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

        HouseApplicationIntermediaryRecord record = new HouseApplicationIntermediaryRecord();
        record.setUserId(userId);
        record.setOrganization(organization);
        record.setName(name);
        record.setPhone(phone);
        record.setIdcard(idcard);
        record.setEvidence(evidence);
        record.setReason(reason);
        record.setSignFlag(signFlag);
        record.setStatus(status);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseApplicationIntermediaryRecord> houseApplicationIntermediaryPage = queryHouseApplicationIntermediaryDao.findHouseApplicationIntermediaryPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseApplicationIntermediaryPage);

        return SuccessTip.create(page);
    }


//    详情
    @GetMapping("/{id}")
    public Tip getHouseApplicationIntermediary(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseApplicationIntermediaryService.queryMasterModel(queryHouseApplicationIntermediaryDao, id));
    }


    //    置业顾问申请处理--通过
    @PutMapping("/status/pass/{id}")
    public Tip passApplicationIntermediary(@PathVariable("id") Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseApplicationIntermediaryService.passHouseApplicationIntermediary(id));
    }

    //    置业顾问申请处理--拒绝
    @PutMapping("/status/refuse/{id}")
    public Tip refuseApplicationIntermediary(@PathVariable("id") Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseApplicationIntermediary houseApplicationIntermediary = houseApplicationIntermediaryMapper.selectById(id);
        if (houseApplicationIntermediary != null) {
            houseApplicationIntermediary.setStatus(HouseApplicationIntermediary.STATUS_REFUSE);
            SuccessTip.create(houseApplicationIntermediaryMapper.updateById(houseApplicationIntermediary));
        }
        return SuccessTip.create();
    }



}
