package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserTagDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserTagRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserTagService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserTagRelateMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTag;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTagRelate;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.META;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/sales/landlordTag")
public class LandlordTagManageEndpoint {

    @Resource
    HouseUserTagService houseUserTagService;

    @Resource
    QueryHouseUserTagDao queryHouseUserTagDao;

    @Resource
    HouseUserTagRelateMapper houseUserTagRelateMapper;


    @PostMapping
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_SALES_STRING})
    public Tip createHouseUserTag(@RequestBody HouseUserTag entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        entity.setSalesId(JWTKit.getUserId());
        entity.setOrgId(JWTKit.getOrgId());
        Integer affected = 0;
        try {
            affected = houseUserTagService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/{id}")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_SALES_STRING})
    public Tip getHouseUserTag(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseUserTagService.queryMasterModel(queryHouseUserTagDao, id));
    }


    @PutMapping("/{id}")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_SALES_STRING})
    public Tip updateHouseUserTag(@PathVariable Long id, @RequestBody HouseUserTag entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        entity.setSalesId(JWTKit.getUserId());
        entity.setOrgId(JWTKit.getOrgId());
        entity.setId(id);
        return SuccessTip.create(houseUserTagService.updateMaster(entity));
    }

    @DeleteMapping("/{id}")
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_SALES_STRING})
    public Tip deleteHouseUserTag(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        QueryWrapper<HouseUserTagRelate> tagRelateQueryWrapper = new QueryWrapper<>();
        tagRelateQueryWrapper.eq(HouseUserTagRelate.TAG_ID,id);
        List<HouseUserTagRelate> relateList = houseUserTagRelateMapper.selectList(tagRelateQueryWrapper);
        if (relateList!=null && relateList.size()>0){
            throw new BusinessException(BusinessCode.DeleteAssociatedOne,"已有房东设置该画像,请删除后再尝试");
        }
        return SuccessTip.create(houseUserTagService.deleteMaster(id));
    }


    @GetMapping
    @EndUserPermission(value = {EndUserTypeSetting.USER_TYPE_SALES_STRING})
    public Tip queryHouseUserTagPage(Page<HouseUserTagRecord> page,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     // for tag feature query
                                     @RequestParam(name = "tag", required = false) String tag,
                                     // end tag
                                     @RequestParam(name = "search", required = false) String search,

                                     @RequestParam(name = "orgId", required = false) Long orgId,

                                     @RequestParam(name = "salesId", required = false) Long salesId,

                                     @RequestParam(name = "tagName", required = false) String tagName,

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

        HouseUserTagRecord record = new HouseUserTagRecord();
        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setSalesId(salesId);
        record.setTagName(tagName);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUserTagRecord> houseUserTagPage = queryHouseUserTagDao.findHouseUserTagPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserTagPage);

        return SuccessTip.create(page);
    }
}
