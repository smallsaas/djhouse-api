
package com.jfeat.am.module.house.api.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseSubscribePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSubscribeDao;
import com.jfeat.am.module.house.services.domain.model.HouseSubscribeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSubscribeService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-08-24
 */
@RestController
@Api("HouseSubscribe")
@RequestMapping("/api/crud/house/houseSubscribe/houseSubscribes")
public class HouseSubscribeEndpoint {

    @Resource
    HouseSubscribeService houseSubscribeService;

    @Resource
    QueryHouseSubscribeDao queryHouseSubscribeDao;


    @BusinessLog(name = "HouseSubscribe", value = "create HouseSubscribe")
    @Permission(HouseSubscribePermission.HOUSESUBSCRIBE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseSubscribe", response = HouseSubscribe.class)
    public Tip createHouseSubscribe(@RequestBody HouseSubscribe entity) {
        Integer affected = 0;
        try {
            affected = houseSubscribeService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseSubscribePermission.HOUSESUBSCRIBE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseSubscribe", response = HouseSubscribe.class)
    public Tip getHouseSubscribe(@PathVariable Long id) {
        return SuccessTip.create(houseSubscribeService.queryMasterModel(queryHouseSubscribeDao, id));
    }

    @BusinessLog(name = "HouseSubscribe", value = "update HouseSubscribe")
    @Permission(HouseSubscribePermission.HOUSESUBSCRIBE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseSubscribe", response = HouseSubscribe.class)
    public Tip updateHouseSubscribe(@PathVariable Long id, @RequestBody HouseSubscribe entity) {
        entity.setId(id);
        return SuccessTip.create(houseSubscribeService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseSubscribe", value = "delete HouseSubscribe")
    @Permission(HouseSubscribePermission.HOUSESUBSCRIBE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseSubscribe")
    public Tip deleteHouseSubscribe(@PathVariable Long id) {
        return SuccessTip.create(houseSubscribeService.deleteMaster(id));
    }

    @Permission(HouseSubscribePermission.HOUSESUBSCRIBE_VIEW)
    @ApiOperation(value = "HouseSubscribe 列表信息", response = HouseSubscribeRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "subscribeId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseSubscribePage(Page<HouseSubscribeRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "userId", required = false) Long userId,

                                       @RequestParam(name = "subscribeId", required = false) Long subscribeId,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "createTime", required = false) Date createTime,
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

        HouseSubscribeRecord record = new HouseSubscribeRecord();
        record.setUserId(userId);
        record.setSubscribeId(subscribeId);
        record.setCreateTime(createTime);


        List<HouseSubscribeRecord> houseSubscribePage = queryHouseSubscribeDao.findHouseSubscribePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseSubscribePage);

        return SuccessTip.create(page);
    }
}

