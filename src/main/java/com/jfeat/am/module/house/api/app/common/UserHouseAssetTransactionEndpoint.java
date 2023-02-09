package com.jfeat.am.module.house.api.app.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.definition.HouseAssetTransactionStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetTransactionDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionIntentionRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetTransactionRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionIntentionService;
import com.jfeat.am.module.house.services.domain.service.HouseAssetTransactionService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetTransactionMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
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
    HouseAssetTransactionIntentionService houseAssetTransactionIntentionService;

    @Resource
    QueryHouseAssetTransactionDao queryHouseAssetTransactionDao;

    @Resource
    HouseAssetTransactionMapper houseAssetTransactionMapper;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    UserAccountMapper userAccountMapper;
    @Resource
    UserAccountService userAccountService;


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

                                              // 该方法不再提供userId的插叙
//                                              @RequestParam(name = "userId", required = false) Long userId,

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

        // 判断排序参数
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

        // 分页
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetTransactionRecord record = new HouseAssetTransactionRecord();
        // 该方法默认获取非当前用户的记录
        Long userId = JWTKit.getUserId();
        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setHouseType(houseType);
        record.setState(state);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);
        // 设置 display(是否显示) = 1,该方法内只需要返回display = 1的记录
        record.setDisplay(1);

        // 查询transaction所有记录
        List<HouseAssetTransactionRecord> houseAssetTransactionPage = queryHouseAssetTransactionDao.findHouseAssetTransactionPageDetail(page, record, tag, search, orderBy, null, null);

        // 查询该用户是否已经关注了订单
        HouseAssetTransactionIntentionRecord transactionIntention = new HouseAssetTransactionIntentionRecord();
        for (HouseAssetTransactionRecord transaction : houseAssetTransactionPage) {
            transactionIntention.setTransactionId(transaction.getId());
            transactionIntention.setUserId(userId);
            Boolean existsFollow =  houseAssetTransactionIntentionService.existsTransactionIntention(transactionIntention);
            transaction.setExistsFollow(existsFollow);
        }

        houseAssetTransactionService.setStatus(houseAssetTransactionPage);

        page.setRecords(houseAssetTransactionPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/sales")
    public Tip queryTransactionPage(Page<HouseAssetTransactionRecord> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    // for tag feature query
                                    @RequestParam(name = "tag", required = false) String tag,
                                    // end tag
                                    @RequestParam(name = "search", required = false) String search,

//                                    @RequestParam(name = "userId", required = false) Long userId,

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

        // 销售权限判断
        Integer userType = JWTKit.getType();
        if (userType == null) throw new BusinessException(BusinessCode.AuthorizationError,"用户类型为null");
        List<Integer> typeList = userAccountService.getUserTypeList(userType);
        if (!(typeList.contains(EndUserTypeSetting.USER_TYPE_SALES))) throw new BusinessException(BusinessCode.NoPermission,"没有销售权限");

        // 判断排序参数
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

        // 分页
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetTransactionRecord record = new HouseAssetTransactionRecord();
//        record.setUserId(userId);
        record.setAssetId(assetId);
        record.setHouseType(houseType);
        record.setState(state);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);

        // 查询transaction所有记录
        List<HouseAssetTransactionRecord> houseAssetTransactionPage = queryHouseAssetTransactionDao.findHouseAssetTransactionPageDetail(page, record, tag, search, orderBy, null, null);

        // 查询该用户是否已经关注了订单
        HouseAssetTransactionIntentionRecord transactionIntention = new HouseAssetTransactionIntentionRecord();
        Long userId = JWTKit.getUserId();
        for (HouseAssetTransactionRecord transaction : houseAssetTransactionPage) {
            transactionIntention.setTransactionId(transaction.getId());
            transactionIntention.setUserId(userId);
            Boolean existsFollow =  houseAssetTransactionIntentionService.existsTransactionIntention(transactionIntention);
            transaction.setExistsFollow(existsFollow);
        }

        houseAssetTransactionService.setStatus(houseAssetTransactionPage);

        page.setRecords(houseAssetTransactionPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/priceList")
    public Tip queryPriceList() {
        return SuccessTip.create(houseAssetTransactionService.listPriceOfTransaction());
    }

    @GetMapping("/myTransactions")
    public Tip queryMyTransactions() {
        // 从token中获取id
        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }


        // 获取我的记录
        List<HouseAssetTransactionRecord> myTransactions = houseAssetTransactionService.listTransaction(userId);
        /**
         * 根据state判断状态，需求 or 转让
         * 该方法有非常大的优化空间，可根据state就可以直接判断状态，可是因为前端对接的时候已经使用了cnStatus，enStatus这两个字段，
         * 所以为了先不影响前端使用继续使用该方法，后续可在前端修改逻辑后去掉此方法
         */
        houseAssetTransactionService.setStatus(myTransactions);

        return SuccessTip.create(myTransactions);
    }

    @PostMapping("/updateTransactionDisplay/{transactionId}")
    public Tip updateTransactionDisplay(@PathVariable Long transactionId,@RequestBody HouseAssetTransactionRecord transaction) {
        Long userId = JWTKit.getUserId();
        if (userId==null) throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        if (transaction.getDisplay() == null || (transaction.getDisplay() != 1 && transaction.getDisplay() != 0) ) throw new BusinessException(BusinessCode.BadRequest,"display不能为空，并且值只能为1 或者 0");

        // 封装参数
        transaction.setId(transactionId);
        transaction.setUserId(userId);

        Integer affected =  houseAssetTransactionService.updateDisplay(transaction);
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError,"更新失败");

        return SuccessTip.create(affected);
    }
}
