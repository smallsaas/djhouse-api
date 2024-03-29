
package com.jfeat.am.module.house.api.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.module.house.api.permission.HouseAssetTransactionPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
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
 * @since 2023-01-05
 */
@RestController
@Api("HouseAssetTransaction")
@RequestMapping("/api/crud/house/houseAssetTransaction/houseAssetTransactions")
public class HouseAssetTransactionEndpoint {

    @Resource
    HouseAssetTransactionService houseAssetTransactionService;

    @Resource
    QueryHouseAssetTransactionDao queryHouseAssetTransactionDao;


    @BusinessLog(name = "HouseAssetTransaction", value = "create HouseAssetTransaction")
    @Permission(HouseAssetTransactionPermission.HOUSEASSETTRANSACTION_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip createHouseAssetTransaction(@RequestBody HouseAssetTransaction entity) {
        Integer affected = 0;
        try {
            affected = houseAssetTransactionService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetTransactionPermission.HOUSEASSETTRANSACTION_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip getHouseAssetTransaction(@PathVariable Long id) {
        return SuccessTip.create(houseAssetTransactionService.queryMasterModel(queryHouseAssetTransactionDao, id));
    }

    @BusinessLog(name = "HouseAssetTransaction", value = "update HouseAssetTransaction")
    @Permission(HouseAssetTransactionPermission.HOUSEASSETTRANSACTION_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip updateHouseAssetTransaction(@PathVariable Long id, @RequestBody HouseAssetTransaction entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetTransactionService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetTransaction", value = "delete HouseAssetTransaction")
    @Permission(HouseAssetTransactionPermission.HOUSEASSETTRANSACTION_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetTransaction")
    public Tip deleteHouseAssetTransaction(@PathVariable Long id) {
        return SuccessTip.create(houseAssetTransactionService.deleteMaster(id));
    }

    @Permission(HouseAssetTransactionPermission.HOUSEASSETTRANSACTION_VIEW)
    @ApiOperation(value = "HouseAssetTransaction 列表信息", response = HouseAssetTransactionRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "houseTypeId", dataType = "Long"),
            @ApiImplicitParam(name = "state", dataType = "Integer"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetTransactionPage(Page<HouseAssetTransactionRecord> page,
                                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              // for tag feature query
                                              @RequestParam(name = "tag", required = false) String tag,
                                              // end tag
                                              @RequestParam(name = "search", required = false) String search,

                                              @RequestParam(name = "userId", required = false) Long userId,

                                              @RequestParam(name = "assetId", required = false) Long assetId,

                                              @RequestParam(name = "houseType", required = false) String houseType,

                                              @RequestParam(name = "state", required = false) Integer state,

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

        HouseAssetTransactionRecord record = new HouseAssetTransactionRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setHouseType(houseType);
        record.setState(state);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);


        List<HouseAssetTransactionRecord> houseAssetTransactionPage = queryHouseAssetTransactionDao.findHouseAssetTransactionPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseAssetTransactionPage);

        return SuccessTip.create(page);
    }
}

