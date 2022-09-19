
package com.jfeat.am.module.house.api.manager;


import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@RestController
@Api("HouseAssetMatchLog")
@RequestMapping("/api/crud/house/houseAssetMatchLog/houseAssetMatchLogs")
public class HouseAssetMatchLogEndpoint {

    @Resource
    HouseAssetMatchLogService houseAssetMatchLogService;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;


    @BusinessLog(name = "HouseAssetMatchLog", value = "create HouseAssetMatchLog")
    @Permission(HouseAssetMatchLogPermission.HOUSEASSETMATCHLOG_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseAssetMatchLog", response = HouseAssetMatchLog.class)
    public Tip createHouseAssetMatchLog(@RequestBody HouseAssetMatchLog entity) {
        Integer affected = 0;
        try {
            affected = houseAssetMatchLogService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseAssetMatchLogPermission.HOUSEASSETMATCHLOG_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetMatchLog", response = HouseAssetMatchLog.class)
    public Tip getHouseAssetMatchLog(@PathVariable Long id) {
        return SuccessTip.create(houseAssetMatchLogService.queryMasterModel(queryHouseAssetMatchLogDao, id));
    }

    @BusinessLog(name = "HouseAssetMatchLog", value = "update HouseAssetMatchLog")
    @Permission(HouseAssetMatchLogPermission.HOUSEASSETMATCHLOG_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetMatchLog", response = HouseAssetMatchLog.class)
    public Tip updateHouseAssetMatchLog(@PathVariable Long id, @RequestBody HouseAssetMatchLog entity) {
        entity.setId(id);
        return SuccessTip.create(houseAssetMatchLogService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseAssetMatchLog", value = "delete HouseAssetMatchLog")
    @Permission(HouseAssetMatchLogPermission.HOUSEASSETMATCHLOG_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetMatchLog")
    public Tip deleteHouseAssetMatchLog(@PathVariable Long id) {
        return SuccessTip.create(houseAssetMatchLogService.deleteMaster(id));
    }

    @Permission(HouseAssetMatchLogPermission.HOUSEASSETMATCHLOG_VIEW)
    @ApiOperation(value = "HouseAssetMatchLog 列表信息", response = HouseAssetMatchLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "ownerUserId", dataType = "Long"),
            @ApiImplicitParam(name = "ownerAssetId", dataType = "Long"),
            @ApiImplicitParam(name = "matchedUserId", dataType = "Long"),
            @ApiImplicitParam(name = "mathchedAssetId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseAssetMatchLogPage(Page<HouseAssetMatchLogRecord> page,
                                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                           // for tag feature query
                                           @RequestParam(name = "tag", required = false) String tag,
                                           // end tag
                                           @RequestParam(name = "search", required = false) String search,

                                           @RequestParam(name = "ownerUserId", required = false) Long ownerUserId,

                                           @RequestParam(name = "ownerAssetId", required = false) Long ownerAssetId,

                                           @RequestParam(name = "matchedUserId", required = false) Long matchedUserId,

                                           @RequestParam(name = "mathchedAssetId", required = false) Long mathchedAssetId,

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

        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setOwnerUserId(ownerUserId);
        record.setOwnerAssetId(ownerAssetId);
        record.setMatchedUserId(matchedUserId);
        record.setMathchedAssetId(mathchedAssetId);
        record.setCreateTime(createTime);
        List<HouseAssetMatchLogRecord> houseAssetMatchLogPage = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(page, record, tag, search, orderBy, null, null);

//        for (int i=0;i<houseAssetMatchLogPage.size();i++){
//            HouseAsset ownerHouseAsset=  queryHouseAssetDao.queryMasterModel(houseAssetMatchLogPage.get(i).getOwnerAssetId());
//            EndpointUser ownerEndUser = queryEndpointUserDao.queryMasterModel(houseAssetMatchLogPage.get(i).getOwnerUserId());
//            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogPage.get(i).getMathchedAssetId());
//            EndpointUser matchedEndUser = queryEndpointUserDao.queryMasterModel(houseAssetMatchLogPage.get(i).getMatchedUserId());
//            if (ownerHouseAsset!=null && ownerEndUser!=null && matchedHouseAsset!=null &&matchedEndUser!=null){
//                houseAssetMatchLogPage.get(i).setOwnerBuilding(ownerHouseAsset.getBuildingCode());
//                houseAssetMatchLogPage.get(i).setOwnerCommunity(ownerHouseAsset.getCommunityName());
//                houseAssetMatchLogPage.get(i).setOwnerNumber(ownerHouseAsset.getNumber());
//
//                houseAssetMatchLogPage.get(i).setMatchedBuilding(matchedHouseAsset.getBuildingCode());
//                houseAssetMatchLogPage.get(i).setMatchedCommunity(matchedHouseAsset.getCommunityName());
//                houseAssetMatchLogPage.get(i).setMatchedNumber(matchedHouseAsset.getNumber());
//
//            }
//
//        }

        page.setRecords(houseAssetMatchLogPage);

        return SuccessTip.create(page);
    }
}

