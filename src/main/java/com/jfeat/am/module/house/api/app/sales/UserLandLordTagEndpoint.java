package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserTagRelateDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserTagRelateRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserTagRelateService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTagRelate;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/u/house/sales/userLandlordTag")
public class UserLandLordTagEndpoint {
    @Resource
    HouseUserTagRelateService houseUserTagRelateService;

    @Resource
    QueryHouseUserTagRelateDao queryHouseUserTagRelateDao;


    @PostMapping
    public Tip createHouseUserTagRelate(@RequestBody HouseUserTagRelate entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Integer affected = 0;
        entity.setSalesId(JWTKit.getUserId());
        try {
            affected = houseUserTagRelateService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/{id}")
    public Tip getHouseUserTagRelate(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseUserTagRelateService.queryMasterModel(queryHouseUserTagRelateDao, id));
    }


    @PutMapping("/{id}")
    public Tip updateHouseUserTagRelate(@PathVariable Long id, @RequestBody HouseUserTagRelate entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        entity.setId(id);
        entity.setSalesId(JWTKit.getUserId());
        return SuccessTip.create(houseUserTagRelateService.updateMaster(entity));
    }


    @DeleteMapping("/{id}")
    public Tip deleteHouseUserTagRelate(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseUserTagRelateService.deleteMaster(id));
    }

    @GetMapping
    public Tip queryHouseUserTagRelatePage(Page<HouseUserTagRelateRecord> page,
                                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                           // for tag feature query
                                           @RequestParam(name = "tag", required = false) String tag,
                                           // end tag
                                           @RequestParam(name = "search", required = false) String search,

                                           @RequestParam(name = "tagId", required = false) Long tagId,

                                           @RequestParam(name = "userId", required = true) Long userId,

                                           @RequestParam(name = "salesId", required = false) Long salesId,

                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam(name = "createTime", required = false) Date createTime,

                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam(name = "updateTime", required = false) Date updateTime,
                                           @RequestParam(name = "orderBy", required = false) String orderBy,
                                           @RequestParam(name = "sort", required = false) String sort) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
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

        HouseUserTagRelateRecord record = new HouseUserTagRelateRecord();
        record.setTagId(tagId);
        record.setUserId(userId);
        record.setSalesId(salesId);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUserTagRelateRecord> houseUserTagRelatePage = queryHouseUserTagRelateDao.findHouseUserTagRelatePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserTagRelatePage);

        return SuccessTip.create(page);
    }
}
