
package com.jfeat.am.module.house.api.manager;


import com.jfeat.am.module.house.services.gen.crud.model.HouseVrPictureModel;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrPictureDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-07-04
 */
@RestController
@Api("HouseVrPicture")
@RequestMapping("/api/crud/house/houseVrPicture/houseVrPictures")
public class HouseVrPictureEndpoint {

    @Resource
    HouseVrPictureService houseVrPictureService;

    @Resource
    QueryHouseVrPictureDao queryHouseVrPictureDao;


    @BusinessLog(name = "HouseVrPicture", value = "create HouseVrPicture")
    @Permission(HouseVrPicturePermission.HOUSEVRPICTURE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseVrPicture", response = HouseVrPicture.class)
    public Tip createHouseVrPicture(@RequestBody HouseVrPicture entity) {
        Integer affected = 0;
        try {
            affected = houseVrPictureService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseVrPicturePermission.HOUSEVRPICTURE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseVrPicture", response = HouseVrPicture.class)
    public Tip getHouseVrPicture(@PathVariable Long id) {
        return SuccessTip.create(houseVrPictureService.queryMasterModel(queryHouseVrPictureDao, id));
    }

    @BusinessLog(name = "HouseVrPicture", value = "update HouseVrPicture")
    @Permission(HouseVrPicturePermission.HOUSEVRPICTURE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseVrPicture", response = HouseVrPicture.class)
    public Tip updateHouseVrPicture(@PathVariable Long id, @RequestBody HouseVrPicture entity) {
        entity.setId(id);
        return SuccessTip.create(houseVrPictureService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseVrPicture", value = "delete HouseVrPicture")
    @Permission(HouseVrPicturePermission.HOUSEVRPICTURE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseVrPicture")
    public Tip deleteHouseVrPicture(@PathVariable Long id) {
        return SuccessTip.create(houseVrPictureService.deleteMaster(id));
    }

    @Permission(HouseVrPicturePermission.HOUSEVRPICTURE_VIEW)
    @ApiOperation(value = "HouseVrPicture 列表信息", response = HouseVrPictureRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "link", dataType = "String"),
            @ApiImplicitParam(name = "vrPicture", dataType = "String"),
            @ApiImplicitParam(name = "snapshot", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String"),
            @ApiImplicitParam(name = "type", dataType = "Long")
    })
    public Tip queryHouseVrPicturePage(Page<HouseVrPictureRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "name", required = false) String name,

                                       @RequestParam(name = "link", required = false) String link,

                                       @RequestParam(name = "vrPicture", required = false) String vrPicture,
                                       @RequestParam(name = "snapshot", required = false) String snapshot,
                                       @RequestParam(name = "star", required = false) Integer star,
                                       @RequestParam(name = "bedrooms", required = false) String bedrooms,
                                       @RequestParam(name = "style", required = false) String style,

                                       @RequestParam(name = "typeOption", required = false) String typeOption,
                                       @RequestParam(name = "note", required = false) String note,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort,
                                       @RequestParam(name = "type", required = false) Long type
                                       ) {

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

        HouseVrPictureRecord record = new HouseVrPictureRecord();
        record.setName(name);
        record.setLink(link);
        record.setVrPicture(vrPicture);
        record.setSnapshot(snapshot);
        record.setStar(star);
        record.setBedrooms(bedrooms);
        record.setStyle(style);
        record.setTypeOption(typeOption);
        record.setNote(note);
        record.setTypeId(type);


        List<HouseVrPictureRecord> houseVrPicturePage = queryHouseVrPictureDao.findHouseVrPicturePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseVrPicturePage);

        return SuccessTip.create(page);
    }

    /*
上架vr图
 */
    @PutMapping("/shelvesVrPicture/{id}")
    public Tip shelvesVrPicture(@PathVariable("id") Long id) {
        HouseVrPictureModel houseVrPictureModel = queryHouseVrPictureDao.queryMasterModel(id);
        if (houseVrPictureModel!=null){
            houseVrPictureModel.setStatus(HouseVrPicture.STATUS_SHELVES);
            return SuccessTip.create(houseVrPictureService.updateMaster(houseVrPictureModel));
        }
        return SuccessTip.create();
    }

    /*
    下架vr图
     */
    @PutMapping("/unshelvesVrPicture/{id}")
    public Tip unshelvesVrPicture(@PathVariable("id") Long id) {
        HouseVrPictureModel houseVrPictureModel = queryHouseVrPictureDao.queryMasterModel(id);
        if (houseVrPictureModel!=null){
            houseVrPictureModel.setStatus(HouseVrPicture.STATUS_UNSHELVES);
            return SuccessTip.create(houseVrPictureService.updateMaster(houseVrPictureModel));
        }
        return SuccessTip.create();
    }





}

