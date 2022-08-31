package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserNoteDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserNoteRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserNoteService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;
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
@RequestMapping("/api/u/house/sales/userLandlordNote")
public class UserLandlordNoteEndpoint {
    @Resource
    HouseUserNoteService houseUserNoteService;

    @Resource
    QueryHouseUserNoteDao queryHouseUserNoteDao;



    @PostMapping
    public Tip createHouseUserNote(@RequestBody HouseUserNote entity) {

        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        if (entity.getUserId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"userId必填项");
        }
        if (entity.getNote()==null||entity.getNote().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"note必填项");
        }

        entity.setSalesId(JWTKit.getUserId());

        Integer affected = 0;
        try {
            affected = houseUserNoteService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/{id}")
    public Tip getHouseUserNote(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseUserNoteService.queryMasterModel(queryHouseUserNoteDao, id));
    }


    @PutMapping("/{id}")
    public Tip updateHouseUserNote(@PathVariable Long id, @RequestBody HouseUserNote entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        if (entity.getUserId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"userId必填项");
        }
        if (entity.getNote()==null||entity.getNote().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"note必填项");
        }

        entity.setSalesId(JWTKit.getUserId());
        entity.setId(id);
        return SuccessTip.create(houseUserNoteService.updateMaster(entity));
    }

    @DeleteMapping("/{id}")
    public Tip deleteHouseUserNote(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseUserNoteService.deleteMaster(id));
    }


    @GetMapping
    public Tip queryHouseUserNotePage(Page<HouseUserNoteRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      // for tag feature query
                                      @RequestParam(name = "tag", required = false) String tag,
                                      // end tag
                                      @RequestParam(name = "search", required = false) String search,

                                      @RequestParam(name = "userId", required = true) Long userId,

                                      @RequestParam(name = "salesId", required = false) Long salesId,

                                      @RequestParam(name = "note", required = false) String note,

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

        HouseUserNoteRecord record = new HouseUserNoteRecord();
        record.setUserId(userId);
        record.setSalesId(salesId);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseUserNoteRecord> houseUserNotePage = queryHouseUserNoteDao.findHouseUserNotePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserNotePage);

        return SuccessTip.create(page);
    }
}
