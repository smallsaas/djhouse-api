
package com.jfeat.am.module.house.api.manager;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.common.annotation.EndUserPermission;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import springfox.documentation.spring.web.json.Json;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@RestController
@Api("HouseUserAsset")
@RequestMapping("/api/crud/house/houseUserAsset/houseUserAssets")
public class HouseUserAssetEndpoint {

    @Resource
    HouseUserAssetService houseUserAssetService;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseAssetComplaintDao queryHouseAssetComplaintDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;


    @BusinessLog(name = "HouseUserAsset", value = "create HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserAsset", response = HouseUserAsset.class)
    public Tip createHouseUserAsset(@RequestBody HouseUserAsset entity) {
        Integer affected = 0;
        try {
            affected = houseUserAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserAsset", response = HouseUserAsset.class)
    public Tip getHouseUserAsset(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetService.queryMasterModel(queryHouseUserAssetDao, id));
    }

    @BusinessLog(name = "HouseUserAsset", value = "update HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserAsset", response = HouseUserAsset.class)
    public Tip updateHouseUserAsset(@PathVariable Long id, @RequestBody HouseUserAsset entity) {
        entity.setId(id);
        return SuccessTip.create(houseUserAssetService.updateMaster(entity));
    }

    @BusinessLog(name = "HouseUserAsset", value = "delete HouseUserAsset")
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserAsset")
    public Tip deleteHouseUserAsset(@PathVariable Long id) {
        return SuccessTip.create(houseUserAssetService.deleteMaster(id));
    }

    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @ApiOperation(value = "HouseUserAsset 列表信息", response = HouseUserAssetRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "assetId", dataType = "Long"),
            @ApiImplicitParam(name = "rentStatus", dataType = "Integer"),
            @ApiImplicitParam(name = "rentTitle", dataType = "String"),
            @ApiImplicitParam(name = "rentPrice", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "rentDescribe", dataType = "String"),
            @ApiImplicitParam(name = "rentTags", dataType = "String"),
            @ApiImplicitParam(name = "slideshow", dataType = "String"),
            @ApiImplicitParam(name = "rentTime", dataType = "Date"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "clashUserId", dataType = "Long"),
            @ApiImplicitParam(name = "clashDescribe", dataType = "String"),
            @ApiImplicitParam(name = "clashCertificate", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseUserAssetPage(Page<HouseUserAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "userId", required = false) Long userId,

                                       @RequestParam(name = "assetId", required = false) Long assetId,


                                       @RequestParam(name = "note", required = false) String note,


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

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setAssetId(assetId);

        record.setNote(note);

        record.setCreateTime(createTime);


        List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(page, record, tag, search, orderBy, null, null);
        page.setRecords(houseUserAssetPage);

        return SuccessTip.create(page);
    }


    /*
      获取申诉列表
     */
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @GetMapping("/clash")
    public Tip getALlClash(Page<HouseAssetComplaintRecord> page,
                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           // for tag feature query
                           @RequestParam(name = "tag", required = false) String tag,
                           // end tag
                           @RequestParam(name = "search", required = false) String search,

                           @RequestParam(name = "userId", required = false) Long userId,

                           @RequestParam(name = "oldUserId", required = false) Long oldUserId,

                           @RequestParam(name = "assetId", required = false) Long assetId,

                           @RequestParam(name = "clashCertificate", required = false) String clashCertificate,

                           @RequestParam(name = "clashDescribe", required = false) String clashDescribe,

                           @RequestParam(name = "solveStatus", required = false) Integer solveStatus,

                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                           @RequestParam(name = "createTime", required = false) Date createTime,

                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                           @RequestParam(name = "solveTime", required = false) Date solveTime,
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

        HouseAssetComplaintRecord record = new HouseAssetComplaintRecord();
        record.setUserId(userId);
        record.setOldUserId(oldUserId);
        record.setAssetId(assetId);
        record.setClashCertificate(clashCertificate);
        record.setClashDescribe(clashDescribe);
        record.setSolveStatus(solveStatus);
        record.setCreateTime(createTime);
        record.setSolveTime(solveTime);


        List<HouseAssetComplaintRecord> houseAssetComplaintPage = queryHouseAssetComplaintDao.findHouseAssetComplaintPageDetails(page, record, tag, search, orderBy, null, null);
        List<HouseAssetComplaintRecord> newHouseAssetComplainPage = new ArrayList<>();
        for (HouseAssetComplaintRecord houseAssetComplaintRecord:houseAssetComplaintPage){
            houseAssetComplaintRecord.setSolveStatusStr(houseAssetComplaintRecord.getSolveStatus().toString());
            newHouseAssetComplainPage.add(houseAssetComplaintRecord);
        }

//        安装状态排序
        newHouseAssetComplainPage.sort((a, b) -> {
            if ((a.getSolveStatus() - b.getSolveStatus()) > 0) {
                return 1;
            } else if ((a.getSolveStatus() - b.getSolveStatus()) < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        page.setRecords(newHouseAssetComplainPage);

        return SuccessTip.create(page);
    }


    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_VIEW)
    @GetMapping("/clash/{id}")
    @ApiOperation(value = "查看 HouseAssetComplaint", response = HouseAssetComplaint.class)
    public Tip getHouseAssetComplaint(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }



        HouseAssetComplaintRecord record = new HouseAssetComplaintRecord();
        record.setId(id);
        List<HouseAssetComplaintRecord> complaintRecord = queryHouseAssetComplaintDao.findHouseAssetComplaintPage(null, record, null, null, null
                , null, null);
        if (complaintRecord != null && complaintRecord.size() == 1) {
//            设置房屋信息
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(complaintRecord.get(0).getAssetId());
            if (houseAssetModel != null) {
                complaintRecord.get(0).setHouseAssetModel(houseAssetModel);
            }

//            添加个人信息
            EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(complaintRecord.get(0).getUserId());
            if (endpointUserModel != null) {
                complaintRecord.get(0).setUserAvatar(endpointUserModel.getAvatar());
                complaintRecord.get(0).setUserName(endpointUserModel.getName());
                complaintRecord.get(0).setUserPhone(endpointUserModel.getPhone());
            }
        } else {
            return SuccessTip.create();
        }


        return SuccessTip.create(complaintRecord.get(0));
    }

    //取消验证产权
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_EDIT)
    @PutMapping("/clash/cancel/{id}")
    public Tip cancelClashInfo(@PathVariable("id") Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }


        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint != null) {
//            提交申诉材料
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_UNFINISHED);
            houseAssetComplaint.setSolveTime(new Date());

            //否定用户为最终产权所有者
            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.USER_ID, houseAssetComplaint.getUserId()).eq(HouseUserAsset.ASSET_ID, houseAssetComplaint.getAssetId());
            HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);
            if (houseUserAsset != null) {
                houseUserAsset.setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
                houseUserAssetMapper.update(houseUserAsset, queryWrapper);
            }
            return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
        }
        return SuccessTip.create();
    }

    //    拒绝产权申诉
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_EDIT)
    @PutMapping("/clash/refuse/{id}")
    public Tip refuseClashInfo(@PathVariable("id") Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint != null) {
//            提交申诉材料
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_refuse);
            houseAssetComplaint.setSolveTime(new Date());

            //否定用户为最终产权所有者
            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.USER_ID, houseAssetComplaint.getUserId()).eq(HouseUserAsset.ASSET_ID, houseAssetComplaint.getAssetId());
            HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);
            if (houseUserAsset != null) {
                houseUserAsset.setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
                houseUserAssetMapper.update(houseUserAsset, queryWrapper);
            }
            return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
        }
        return SuccessTip.create();
    }

    /*
    提交确认信息
     */
    @Permission(HouseUserAssetPermission.HOUSEUSERASSET_EDIT)
    @PutMapping("/clash/confirm/{id}")
    public Tip commitAppealMaterials(@PathVariable("id") Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        /*
         * 取消申请材料必填
         * */
//        if (entity.getClashCertificate() == null || "".equals(entity.getClashCertificate())) {
//            throw new BusinessException(BusinessCode.CodeBase, "证明材料需要上传");
//        }

        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint != null) {
//            提交申诉材料
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_COMPLETE);
            houseAssetComplaint.setSolveTime(new Date());

            //当运维上传资料确认为最终户主
            HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
            houseUserAssetRecord.setAssetId(houseAssetComplaint.getAssetId());
            List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(null, houseUserAssetRecord, null, null, null, null, null);
            if (houseUserAssetPage != null && houseUserAssetPage.size() == 1) {
                HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(houseUserAssetPage.get(0).getId());
                if (houseUserAssetModel == null) {
                    throw new BusinessException(BusinessCode.BadRequest, "请求错误");
                }
//                将房子更新为申诉人的房子 且确定最终状态
                houseUserAssetModel.setUserId(houseAssetComplaint.getUserId());
                houseUserAssetModel.setFinalFlag(HouseUserAsset.FINAL_FLAG_CONFIRM);
                houseUserAssetService.updateMaster(houseUserAssetModel);

            }
            return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
        }
        return SuccessTip.create();
    }

}

