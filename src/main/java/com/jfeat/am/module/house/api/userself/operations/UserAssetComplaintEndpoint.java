package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    Authentication authentication;
    /*
    获取申诉列表
     */
    @GetMapping("/clash")
    public Tip getALlClash(Page<HouseUserAsset> page,
                           @RequestParam(name = "username",required = false) String username,
                           @RequestParam(name = "search" ,required = false) String search,
                           @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        List<HouseUserAsset> houseUserAssetList = queryHouseUserAssetDao.queryClashUserAsset(username,search);
        for (int i = 0; i < houseUserAssetList.size(); i++) {
            EndpointUser endpointUser = queryEndpointUserDao.queryMasterModel(houseUserAssetList.get(i).getClashUserId());
            if (endpointUser != null) {
                houseUserAssetList.get(i).setClashUserName(endpointUser.getName());
                houseUserAssetList.get(i).setClashUserPhone(endpointUser.getPhone());
                houseUserAssetList.get(i).setClashUserAvatar(endpointUser.getAvatar());
            }

        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(houseUserAssetList);
        return SuccessTip.create(page);
    }

    //    确认申述
    @PostMapping("/clash/confirm/{id}")
    public Tip confirmClashInfo(@PathVariable("assetId") Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }


        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (houseUserAssetModel==null){
            throw new BusinessException(BusinessCode.BadRequest,"请求错误");
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

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(id);
        if (houseUserAssetModel==null){
            throw new BusinessException(BusinessCode.BadRequest,"请求错误");
        }
        houseUserAssetModel.setClashUserId(null);
        houseUserAssetModel.setClashDescribe(null);
        houseUserAssetModel.setClashCertificate(null);
        return SuccessTip.create(houseUserAssetService.updateMaster(houseUserAssetModel));
    }

    /*
    提交申诉材料
     */
    @PostMapping("/commitAppealMaterials")
    public Tip commitAppealMaterials(@RequestBody HouseUserAsset entity){

         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        if (entity.getId()==null || "".equals(entity.getId())){
            throw new BusinessException(BusinessCode.BadRequest,"id不能为空");
        }
        HouseUserAssetModel houseUserAssetModel = queryHouseUserAssetDao.queryMasterModel(entity.getId());
        if (houseUserAssetModel==null){
            throw new BusinessException(BusinessCode.BadRequest,"请求错误");
        }

        if (entity.getClashCertificate()!=null){
            houseUserAssetModel.setClashCertificate(entity.getClashCertificate());
        }
        if (entity.getClashDescribe()!=null){
            houseUserAssetModel.setClashDescribe(entity.getClashDescribe());
        }

        return SuccessTip.create(houseUserAssetService.updateMaster(houseUserAssetModel));
    }


}
