package com.jfeat.am.module.house.api.userself.rent;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseRentAssetPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.weChatMiniprogram.constant.SecurityConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/rent/agentRentManage")
public class UserAgentRentManageEndpoint {

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseUserAssetService houseUserAssetService;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseAssetService houseAssetService;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;



    /*
    ??????????????????
     */

    @GetMapping("/getUserRentAsset")
    public Tip queryHouseRentAssetPage(Page<HouseRentAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "assetId", required = false) Long assetId,

                                       @RequestParam(name = "communityId", required = false) Long communityId,

                                       @RequestParam(name = "houseTypeId", required = false) Long houseTypeId,

                                       @RequestParam(name = "landlordId", required = false) Long landlordId,

                                       @RequestParam(name = "area", required = false) BigDecimal area,

                                       @RequestParam(name = "introducePicture", required = false) String introducePicture,

                                       @RequestParam(name = "serverId", required = false) Long serverId,

                                       @RequestParam(name = "cover", required = false) String cover,

                                       @RequestParam(name = "title", required = false) String title,

                                       @RequestParam(name = "price", required = false) BigDecimal price,

                                       @RequestParam(name = "slide", required = false) String slide,

                                       @RequestParam(name = "describe", required = false) String describe,

                                       @RequestParam(name = "rentStatus", required = false) Integer rentStatus,

                                       @RequestParam(name = "note", required = false) String note,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "rentTime", required = false) Date rentTime,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "shelvesTime", required = false) Date shelvesTime,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//??????????????????????????????????????????
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseRentAssetRecord record = new HouseRentAssetRecord();
        record.setAssetId(assetId);
        record.setCommunityId(communityId);
        record.setHouseTypeId(houseTypeId);
        record.setLandlordId(landlordId);
        record.setArea(area);
        record.setIntroducePicture(introducePicture);
        record.setServerId(serverId);
        record.setCover(cover);
        record.setTitle(title);
        record.setPrice(price);
        record.setSlide(slide);
        record.setRentDescribe(describe);
        record.setRentStatus(rentStatus);
        record.setNote(note);
        record.setRentTime(rentTime);
        record.setShelvesTime(shelvesTime);


        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseRentAssetDao.findHouseRentAssetPage(page, record, tag, search, orderBy, null, null);
        for (HouseRentAssetRecord houseRentAssetRecord:houseRentAssetPage){
            HouseRentAsset rentAsset = houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, houseRentAssetRecord.getId());
            if (rentAsset!=null && rentAsset.getExtra()!=null){
                houseRentAssetRecord.setExtra(rentAsset.getExtra());
            }
            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(houseRentAssetRecord.getAssetId());
            if (houseAssetModel!=null){
                houseRentAssetRecord.setHouseAssetModel(houseAssetModel);
            }

        }
        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }


    /*
    ???????????? ??????????????????
     */
    @PutMapping("/agentModifyRentInfo/{id}")
    public Tip AgentModifyRentInfo(@PathVariable Long id, @RequestBody HouseRentAsset entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
          /*
        ???????????????????????????
         */
        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(JWTKit.getUserId());
        if (endpointUserModel==null || !(endpointUserModel.getType().equals(SecurityConstants.USER_TYPE_INTERMEDIARY))){
            throw new BusinessException(BusinessCode.NoPermission,"????????????");
        }

        entity.setId(id);
        return SuccessTip.create(houseRentAssetService.updateMaster(entity));
    }


    /*
    ?????????????????? ??????????????????
     */
    @PutMapping("/modifyRentStatus")
    public Tip updateHouseRentAsset(@RequestBody HouseRentAsset entity) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }

        /*
        ???????????????????????????
         */
        EndpointUserModel endpointUserModel = queryEndpointUserDao.queryMasterModel(JWTKit.getUserId());
        if (endpointUserModel==null || !(endpointUserModel.getType().equals(SecurityConstants.USER_TYPE_INTERMEDIARY))){
            throw new BusinessException(BusinessCode.NoPermission,"????????????");
        }

        if (entity.getId()==null || "".equals(entity.getId())){
            throw new BusinessException(BusinessCode.BadRequest,"id?????????");
        }
        if (entity.getRentStatus()==null || "".equals(entity.getRentStatus())){
            throw new BusinessException(BusinessCode.BadRequest,"rentStatus");
        }

        HouseRentAssetModel houseRentAssetModel = queryHouseRentAssetDao.queryMasterModel(entity.getId());
        if (houseRentAssetModel==null){
            throw new BusinessException(BusinessCode.BadRequest,"????????????,???????????????????????????");
        }
        /*
        ???????????????????????????
         */
        houseRentAssetModel.setServerId(JWTKit.getUserId());
        houseRentAssetModel.setRentStatus(entity.getRentStatus());
        houseRentAssetModel.setShelvesTime(new Date());
        return SuccessTip.create(houseRentAssetService.updateMaster(houseRentAssetModel));
    }

    /*
    ??????????????????
     */
    @GetMapping("/userRentDetails/{id}")
    public Tip getHouseRentAsset(@PathVariable Long id) {
        return SuccessTip.create(houseRentAssetService.queryMasterModel(queryHouseRentAssetDao, id));
    }




}
