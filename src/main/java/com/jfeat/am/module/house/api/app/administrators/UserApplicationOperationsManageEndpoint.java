package com.jfeat.am.module.house.api.app.administrators;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseApplicationOperationsPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseApplicationOperationsDao;
import com.jfeat.am.module.house.services.domain.model.HouseApplicationOperationsRecord;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationOperationsService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseApplicationOperationsMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationOperations;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/administrators/applicationOperationsManage")
public class UserApplicationOperationsManageEndpoint {

    @Resource
    HouseApplicationOperationsService houseApplicationOperationsService;

    @Resource
    QueryHouseApplicationOperationsDao queryHouseApplicationOperationsDao;

    @Resource
    HouseApplicationOperationsMapper houseApplicationOperationsMapper;


    @GetMapping("/{id}")
    public Tip getHouseApplicationOperations(@PathVariable Long id) {
        return SuccessTip.create(houseApplicationOperationsService.queryMasterModel(queryHouseApplicationOperationsDao, id));
    }


    @GetMapping
    public Tip queryHouseApplicationOperationsPage(Page<HouseApplicationOperationsRecord> page,
                                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                   // for tag feature query
                                                   @RequestParam(name = "tag", required = false) String tag,
                                                   // end tag
                                                   @RequestParam(name = "search", required = false) String search,

                                                   @RequestParam(name = "userId", required = false) Long userId,

                                                   @RequestParam(name = "tenantName", required = false) String tenantName,

                                                   @RequestParam(name = "name", required = false) String name,

                                                   @RequestParam(name = "phone", required = false) String phone,

                                                   @RequestParam(name = "idcard", required = false) String idcard,

                                                   @RequestParam(name = "evidence", required = false) String evidence,

                                                   @RequestParam(name = "reason", required = false) String reason,

                                                   @RequestParam(name = "status", required = false) Integer status,

                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   @RequestParam(name = "createTime", required = false) Date createTime,

                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   @RequestParam(name = "updateTime", required = false) Date updateTime,
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

        HouseApplicationOperationsRecord record = new HouseApplicationOperationsRecord();
        record.setUserId(userId);
        record.setTenantName(tenantName);
        record.setName(name);
        record.setPhone(phone);
        record.setIdcard(idcard);
        record.setEvidence(evidence);
        record.setReason(reason);
        record.setStatus(status);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseApplicationOperationsRecord> houseApplicationOperationsPage = queryHouseApplicationOperationsDao.findHouseApplicationOperationsPage(page, record, tag, search, orderBy, null, null);

        houseApplicationOperationsPage.sort((a,b)->{
            if ((a.getStatus() - b.getStatus() ) > 0) {
                return 1;
            } else if ((a.getStatus()  - b.getStatus() ) < 0) {
                return -1;
            } else {
                return 0;
            }
        });

        page.setRecords(houseApplicationOperationsPage);

        return SuccessTip.create(page);
    }

    //    通过申请
//    置业顾问申请处理--通过
    @PutMapping("/status/pass/{id}")
    public Tip passApplicationIntermediary(@PathVariable("id") Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseApplicationOperationsService.passApplicationOperations(id));
    }

    //    拒绝申请
    @PutMapping("/status/refuse/{id}")
    public Tip refuseApplicationIntermediary(@PathVariable("id") Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseApplicationOperations houseApplicationOperations =  houseApplicationOperationsMapper.selectById(id);

        houseApplicationOperations.setStatus(HouseApplicationOperations.STATUS_REFUSE);


        return SuccessTip.create(houseApplicationOperationsMapper.updateById(houseApplicationOperations));
    }


}
