package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseAssetComplaintPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetComplaintDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetComplaintService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/*
运维 产权申诉
 */

@RestController
@RequestMapping("/api/u/house/operations/userAssetComplaintEndpoint")
public class UserAssetComplaintEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    HouseUserAssetService houseUserAssetService;

    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Resource
    QueryHouseAssetComplaintDao queryHouseAssetComplaintDao;

    @Resource
    Authentication authentication;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    /*
    获取申诉列表
     */
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


//        安装状态排序
        houseAssetComplaintPage.sort((a,b)->{
            if ((a.getSolveStatus() - b.getSolveStatus() ) > 0) {
                return 1;
            } else if ((a.getSolveStatus()  - b.getSolveStatus() ) < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        page.setRecords(houseAssetComplaintPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/clash/{id}")
    @ApiOperation(value = "查看 HouseAssetComplaint", response = HouseAssetComplaint.class)
    public Tip getHouseAssetComplaint(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }

        HouseAssetComplaintRecord record = new HouseAssetComplaintRecord();
        record.setId(id);
        List<HouseAssetComplaintRecord> complaintRecord = queryHouseAssetComplaintDao.findHouseAssetComplaintPage(null,record,null,null,null
                ,null,null);
        if (complaintRecord!=null && complaintRecord.size()==1){
//            设置房屋信息
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(complaintRecord.get(0).getAssetId());
            if (houseAssetModel!=null){
                complaintRecord.get(0).setHouseAssetModel(houseAssetModel);
            }

//            添加个人信息
            EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(complaintRecord.get(0).getUserId());
            if (endpointUserModel!=null){
                complaintRecord.get(0).setUserAvatar(endpointUserModel.getAvatar());
                complaintRecord.get(0).setUserName(endpointUserModel.getName());
                complaintRecord.get(0).setUserPhone(endpointUserModel.getPhone());
            }
        }else {
            return SuccessTip.create();
        }


        return SuccessTip.create(complaintRecord.get(0));
    }


    //取消验证产权
    @PutMapping("/clash/cancel/{id}")
    public Tip cancelClashInfo(@PathVariable("id")Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }
        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint!=null){
//            提交申诉材料
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_UNFINISHED);
            houseAssetComplaint.setSolveTime(new Date());

            //否定用户为最终产权所有者
            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.USER_ID,houseAssetComplaint.getUserId()).eq(HouseUserAsset.ASSET_ID,houseAssetComplaint.getAssetId());
            HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);
            if (houseUserAsset!=null){
                houseUserAsset.setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
                houseUserAssetMapper.update(houseUserAsset,queryWrapper);
            }
            return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
        }
        return SuccessTip.create();
    }

//    拒绝产权申诉
@PutMapping("/clash/refuse/{id}")
public Tip refuseClashInfo(@PathVariable("id")Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
    if (JWTKit.getUserId() == null) {
        throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
    }

    if (!authentication.verifyOperation(JWTKit.getUserId())) {
        throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
    }
    HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
    if (houseAssetComplaint!=null){
//            提交申诉材料
        houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
        houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
        houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_refuse);
        houseAssetComplaint.setSolveTime(new Date());

        //否定用户为最终产权所有者
        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.USER_ID,houseAssetComplaint.getUserId()).eq(HouseUserAsset.ASSET_ID,houseAssetComplaint.getAssetId());
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);
        if (houseUserAsset!=null){
            houseUserAsset.setFinalFlag(HouseUserAsset.FINAL_FLAG_NOT_CONFIRM);
            houseUserAssetMapper.update(houseUserAsset,queryWrapper);
        }
        return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
    }
    return SuccessTip.create();
}

    /*
    提交确认信息
     */
    @PutMapping("/clash/confirm/{id}")
    public Tip commitAppealMaterials(@PathVariable("id")Long id, @RequestBody HouseAssetComplaint entity) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }

        if (entity.getClashCertificate()==null || "".equals(entity.getClashCertificate())){
            throw new BusinessException(BusinessCode.CodeBase, "证明材料需要上传");
        }

        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint!=null){
//            提交申诉材料
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            houseAssetComplaint.setSolveStatus(HouseAssetComplaint.SOLVE_STATUS_COMPLETE);
            houseAssetComplaint.setSolveTime(new Date());

            //当运维上传资料确认为最终户主
            HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
            houseUserAssetRecord.setAssetId(houseAssetComplaint.getAssetId());
            List<HouseUserAssetRecord> houseUserAssetPage = queryHouseUserAssetDao.findHouseUserAssetPage(null, houseUserAssetRecord, null,null, null, null, null, null);
            if (houseUserAssetPage!=null && houseUserAssetPage.size()==1){
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
