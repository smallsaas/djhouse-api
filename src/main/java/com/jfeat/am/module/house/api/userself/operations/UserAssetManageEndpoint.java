package com.jfeat.am.module.house.api.userself.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseAssetPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/u/house/operations/userAssetManage")
public class UserAssetManageEndpoint {
    @Resource
    HouseAssetService houseAssetService;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HouseAssetMapper houseAssetMapper;


    @Resource
    Authentication authentication;

    //    房子

    /*
    添加房子
     */
    @PostMapping
    public Tip createHouseAsset(@RequestBody HouseAsset entity) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        Integer affected = 0;
        try {
            affected = houseAssetService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    /*
    修改楼层
     */
    @PutMapping("/{id}")
    public Tip updateHouseAsset(@PathVariable Long id, @RequestBody HouseAsset entity) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        entity.setId(id);
        HouseAsset houseAssetModel = houseAssetMapper.selectById(id);
        if (houseAssetModel!=null){
            houseAssetModel.setAssetFlag(entity.getAssetFlag());
            return SuccessTip.create(houseAssetService.updateMaster(houseAssetModel));
        }
        return SuccessTip.create();
    }


    /*
    删除房子
     */

    @DeleteMapping("/{id}")
    public Tip deleteHouseAsset(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        return SuccessTip.create(houseAssetService.deleteMaster(id));
    }



    /*
    查询房子
     */
    @GetMapping
    public Tip queryHouseAssetPage(Page<HouseAssetRecord> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   // for tag feature query
                                   @RequestParam(name = "tag", required = false) String tag,
                                   // end tag
                                   @RequestParam(name = "search", required = false) String search,

                                   @RequestParam(name = "buildingId", required = false) Long buildingId,

                                   @RequestParam(name = "unitId", required = false) Long unitId,

                                   @RequestParam(name = "floor", required = false) Integer floor,

                                   @RequestParam(name = "number", required = false) String number,

                                   @RequestParam(name = "assetSlot", required = false) String assetSlot,

                                   @RequestParam(name = "assetType", required = false) Integer assetType,
                                   @RequestParam(name = "buildingCode", required = false) String buildingCode,
                                   @RequestParam(name = "communityName", required = false) String communityName,

                                   @RequestParam(name = "orderBy", required = false) String orderBy,
                                   @RequestParam(name = "sort", required = false) String sort) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

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

        if (buildingId==null || buildingId.equals("") || buildingId<=0){
            return SuccessTip.create();
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetRecord record = new HouseAssetRecord();
        record.setBuildingId(buildingId);
        record.setUnitId(unitId);
        record.setFloor(floor);
        record.setNumber(number);
        record.setAssetSlot(assetSlot);
        record.setAssetType(assetType);
        record.setBuildingCode(buildingCode);
        record.setCommunityName(communityName);
        System.out.println(buildingCode);
        List<HouseAssetRecord> houseAssetPage = queryHouseAssetDao.findHouseAssetPage(page, record, tag, search, orderBy, null, null);
        page.setRecords(houseAssetPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/{id}")
    public Tip getHouseAsset(@PathVariable Long id) {
         /*
        验证用户是否是运营身份
         */
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }
        return SuccessTip.create(houseAssetService.queryMasterModel(queryHouseAssetDao, id));
    }




}
