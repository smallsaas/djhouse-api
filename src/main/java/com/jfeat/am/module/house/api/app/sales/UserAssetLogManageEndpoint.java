package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseAssetLogPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetLog;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/u/house/sales/userAssetLog")
public class UserAssetLogManageEndpoint {

    @Resource
    HouseAssetLogService houseAssetLogService;

    @Resource
    QueryHouseAssetLogDao queryHouseAssetLogDao;


    @BusinessLog(name = "HouseAssetLog", value = "create HouseAssetLog")
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetLog", response = HouseAssetLog.class)
    public Tip createHouseAssetLog(@RequestBody HouseAssetLog entity) {
        Integer affected = 0;
        try {
            affected = houseAssetLogService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetLog", response = HouseAssetLog.class)
    public Tip getHouseAssetLog(@PathVariable Long id) {
        return SuccessTip.create(houseAssetLogService.queryMasterModel(queryHouseAssetLogDao, id));
    }

    @BusinessLog(name = "HouseAssetLog", value = "update HouseAssetLog")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetLog", response = HouseAssetLog.class)
    public Tip updateHouseAssetLog(@PathVariable Long id, @RequestBody HouseAssetLog entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetLogService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetLog", value = "delete HouseAssetLog")
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetLog")
    public Tip deleteHouseAssetLog(@PathVariable Long id) {
        return SuccessTip.create(houseAssetLogService.deleteMaster(id));
    }

    @ApiOperation(value = "HouseAssetLog 列表信息", response = HouseAssetLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "oldUserId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetLogPage(Page<HouseAssetLogRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      // for tag feature query
                                      @RequestParam(name = "tag", required = false) String tag,
                                      // end tag
                                      @RequestParam(name = "search", required = false) String search,

                                      @RequestParam(name = "userId", required = false) Long userId,

                                      @RequestParam(name = "oldUserId", required = false) Long oldUserId,

                                      @RequestParam(name = "assetId", required = false) Long assetId,

                                      @RequestParam(name = "note", required = false) String note,

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

        HouseAssetLogRecord record = new HouseAssetLogRecord();
        record.setUserId(userId);
        record.setOldUserId(oldUserId);
        record.setAssetId(assetId);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseAssetLogRecord> houseAssetLogPage = queryHouseAssetLogDao.findHouseAssetLogPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetLogPage);

        return SuccessTip.create(page);
    }
}
