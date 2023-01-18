package com.jfeat.am.module.house.api.app.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.definition.HouseAssetTransactionStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetTransactionMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
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


@RestController
@Api("HouseAssetTransaction")
@RequestMapping("/api/u/house/houseAssetTransaction")
public class UserHouseAssetTransactionEndpoint {


    @Resource
    HouseAssetTransactionService houseAssetTransactionService;

    @Resource
    QueryHouseAssetTransactionDao queryHouseAssetTransactionDao;

    @Resource
    HouseAssetTransactionMapper houseAssetTransactionMapper;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;


    @PostMapping
    @ApiOperation(value = "新建 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip createHouseAssetTransaction(@RequestBody HouseAssetTransaction entity) {

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        if (entity.getAssetId()!=null && entity.getHouseType()!=null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId和houseType不能同时设置");
        }

        if (entity.getEnStatus()!=null&&entity.getEnStatus().equals(HouseAssetTransactionStatus.getNameByState(HouseAssetTransaction.STATE_BUY))){
            entity.setState(HouseAssetTransaction.STATE_BUY);
            entity.setAssetId(null);
        }else if (entity.getEnStatus()!=null&&entity.getEnStatus().equals(HouseAssetTransactionStatus.getNameByState(HouseAssetTransaction.STATE_SELL))){

            if (entity.getAssetId()==null){
                throw new BusinessException(BusinessCode.BadRequest,"assetId必填");
            }

            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.ASSET_ID,entity.getAssetId()).eq(HouseUserAsset.USER_ID,userId);
            if (houseUserAssetMapper.selectOne(queryWrapper)==null){
                throw new BusinessException(BusinessCode.NoPermission,"无权出售");
            }
            entity.setHouseType(null);
            entity.setState(HouseAssetTransaction.STATE_SELL);
        }else {
            throw new BusinessException(BusinessCode.BadRequest,"enStatus不在[BUY|SELL]");
        }

        Integer affected = 0;
        try {
            entity.setUserId(userId);
            affected = houseAssetTransactionService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip getHouseAssetTransaction(@PathVariable Long id) {

        HouseAssetTransactionRecord record = new HouseAssetTransactionRecord();
        record.setId(id);
        List<HouseAssetTransactionRecord> houseAssetTransactionPage = queryHouseAssetTransactionDao.findHouseAssetTransactionPageDetail(null, record, null, null, null, null, null);

        if (houseAssetTransactionPage != null && houseAssetTransactionPage.size() > 0) {
            houseAssetTransactionService.setStatus(houseAssetTransactionPage);
            return SuccessTip.create(houseAssetTransactionPage.get(0));
        }

        return SuccessTip.create();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseAssetTransaction", response = HouseAssetTransaction.class)
    public Tip updateHouseAssetTransaction(@PathVariable Long id, @RequestBody HouseAssetTransaction entity) {

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        HouseAssetTransaction houseAssetTransaction = houseAssetTransactionMapper.selectById(id);
        if (houseAssetTransaction==null||!houseAssetTransaction.getUserId().equals(userId)){
            throw new BusinessException(BusinessCode.NoPermission);
        }

        if (entity.getAssetId()!=null && entity.getHouseType()!=null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId和houseTypeId不能同时设置");
        }

        if (entity.getEnStatus()!=null&&entity.getEnStatus().equals(HouseAssetTransactionStatus.getNameByState(HouseAssetTransaction.STATE_BUY))){
            entity.setState(HouseAssetTransaction.STATE_BUY);
            entity.setAssetId(null);
        }else if (entity.getEnStatus()!=null&&entity.getEnStatus().equals(HouseAssetTransactionStatus.getNameByState(HouseAssetTransaction.STATE_SELL))){

            if (entity.getAssetId()==null){
                throw new BusinessException(BusinessCode.BadRequest,"assetId必填");
            }

            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.ASSET_ID,entity.getAssetId()).eq(HouseUserAsset.USER_ID,userId);
            if (houseUserAssetMapper.selectOne(queryWrapper)==null){
                throw new BusinessException(BusinessCode.NoPermission,"无权出售");
            }
            entity.setHouseType(null);
            entity.setState(HouseAssetTransaction.STATE_SELL);
        }else {
            throw new BusinessException(BusinessCode.BadRequest,"enStatus不在[BUY|SELL]");
        }



        entity.setId(id);
        entity.setUserId(userId);
        return SuccessTip.create(houseAssetTransactionService.updateMaster(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseAssetTransaction")
    public Tip deleteHouseAssetTransaction(@PathVariable Long id) {
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        HouseAssetTransaction houseAssetTransaction = houseAssetTransactionMapper.selectById(id);
        if (houseAssetTransaction==null||!houseAssetTransaction.getUserId().equals(userId)){
            throw new BusinessException(BusinessCode.NoPermission);
        }

        return SuccessTip.create(houseAssetTransactionService.deleteMaster(id));
    }


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


        List<HouseAssetTransactionRecord> houseAssetTransactionPage = queryHouseAssetTransactionDao.findHouseAssetTransactionPageDetail(page, record, tag, search, orderBy, null, null);

        houseAssetTransactionService.setStatus(houseAssetTransactionPage);

        page.setRecords(houseAssetTransactionPage);

        return SuccessTip.create(page);
    }
}
