package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetComplaintDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetComplaintService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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


        List<HouseAssetComplaintRecord> houseAssetComplaintPage = queryHouseAssetComplaintDao.findHouseAssetComplaintPage(page, record, tag, search, orderBy, null, null);

        for (HouseAssetComplaintRecord complaintRecord :houseAssetComplaintPage){
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(complaintRecord.getAssetId());
            if (houseAssetModel!=null){
                complaintRecord.setHouseAssetModel(houseAssetModel);
            }

            EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(complaintRecord.getUserId());
            if (endpointUserModel!=null){
                complaintRecord.setUserAvatar(endpointUserModel.getAvatar());
                complaintRecord.setUserName(endpointUserModel.getName());
                complaintRecord.setUserPhone(endpointUserModel.getPhone());
            }
        }

        page.setRecords(houseAssetComplaintPage);

        return SuccessTip.create(page);
    }

    //    确认申述
    @PostMapping("/clash/confirm/{id}")
    public Tip confirmClashInfo(@PathVariable("id") Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }


        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (houseUserAssetModel == null) {
            throw new BusinessException(BusinessCode.BadRequest, "请求错误");
        }
        Long confirmUserId = houseUserAssetModel.getClashUserId();
        houseUserAssetModel.setUserId(confirmUserId);
        houseUserAssetModel.setClashUserId(null);
        houseUserAssetModel.setClashDescribe(null);
        houseUserAssetModel.setClashCertificate(null);
        houseUserAssetModel.setFinalFlag(HouseUserAsset.FINAL_FLAG_CONFIRM);
        return SuccessTip.create(houseUserAssetService.updateMaster(houseUserAssetModel));
    }

    //    驳回产权申述
    @PostMapping("/clash/cancel/{id}")
    public Tip cancelClashInfo(@PathVariable("id") Long id) {

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())) {
            throw new BusinessException(BusinessCode.NoPermission, "该用户没有权限");
        }

        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (houseUserAssetModel == null) {
            throw new BusinessException(BusinessCode.BadRequest, "请求错误");
        }
        houseUserAssetModel.setClashUserId(null);
        houseUserAssetModel.setClashDescribe(null);
        houseUserAssetModel.setClashCertificate(null);
        return SuccessTip.create(houseUserAssetService.updateMaster(houseUserAssetModel));
    }

    /*
    提交申诉材料
     */
    @PutMapping("/commitAppealMaterials/{id}")
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

        HouseAssetComplaint houseAssetComplaint = queryHouseAssetComplaintDao.queryMasterModel(id);
        if (houseAssetComplaint!=null){
            houseAssetComplaint.setClashCertificate(entity.getClashCertificate());
            houseAssetComplaint.setClashDescribe(entity.getClashDescribe());
            return SuccessTip.create(houseAssetComplaintService.updateMaster(houseAssetComplaint));
        }
        return SuccessTip.create();
    }


}
