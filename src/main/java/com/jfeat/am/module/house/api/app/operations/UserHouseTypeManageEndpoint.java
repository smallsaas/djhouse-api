package com.jfeat.am.module.house.api.app.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrPictureMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.plaf.SeparatorUI;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/userHouseTypeManageEndpoint")
public class UserHouseTypeManageEndpoint {

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseDesignModelMapper houseDesignModelMapper;

    @Resource
    HouseVrPictureMapper houseVrPictureMapper;

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    /**
     * 获取小区户型 当小区id为空时 获取当前小区信息
     * @param communityId
     * @return
     */
    @GetMapping
    public Tip getCommunityHouseType(@RequestParam(value = "communityId",required = false) Long communityId){

        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        if (communityId==null){
            communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        }
        if (communityId==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有找到当前小区信息");
        }
        QueryWrapper<HouseDesignModel> designModelQueryWrapper = new QueryWrapper<>();
        designModelQueryWrapper.eq(HouseDesignModel.COMMUNITY_ID,communityId);
        List<HouseDesignModel> houseDesignModelList = houseDesignModelMapper.selectList(designModelQueryWrapper);
        return SuccessTip.create(houseDesignModelList);

    }

    /**
     * 修改户型 当填写的小区id为空时 填写当前用户所在小区id
     * @param id 户型id
     * @param entity 户型实体类
     * @return 修改条数
     */
    @PutMapping("/{id}")
    public Tip updateCommunityHouseType(@PathVariable("id") Long id, @RequestBody HouseDesignModel entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId = null;
        if (entity.getCommunityId()==null){
            communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        }else {
            communityId = entity.getCommunityId();
        }
        if (communityId==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有找到当前小区信息");
        }
        entity.setId(id);
        entity.setCommunityId(communityId);

        return SuccessTip.create(houseDesignModelMapper.updateById(entity));

    }

//    添加户型
    @PostMapping
    public Tip createCommunityHouseType(@RequestBody HouseDesignModel entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId = null;
        if (entity.getCommunityId()==null){
            communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        }else {
            communityId = entity.getCommunityId();
        }
        if (communityId==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有找到当前小区信息");
        }
        entity.setCommunityId(communityId);

        return SuccessTip.create(houseDesignModelMapper.insert(entity));

    }


//    删除户型
    @DeleteMapping("/{id}")
    public Tip deleteHouseType(@PathVariable("id") Long id){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq(HousePropertyBuildingUnit.DESIGN_MODEL_ID,id);
        List<HousePropertyBuildingUnit> unitList = housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);
        if (unitList!=null && unitList.size()>0){
            throw new BusinessException(BusinessCode.CodeBase,"已有单元绑定该户型,不能删除");
        }
        return SuccessTip.create(houseDesignModelMapper.deleteById(id));
    }

//    绑定Vr列表
    @PutMapping("/bindVr/{id}")
    public Tip updateHouseTypeBindVrPicture(@PathVariable("id")Long id,@RequestBody HouseDesignModel entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        if (entity.getVrId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"vrId为必填项");
        }

        HouseVrPicture houseVrPicture = houseVrPictureMapper.selectById(entity.getVrId());
        if (houseVrPicture==null){
            throw new BusinessException(BusinessCode.CodeBase,"未找到改Vr数据");
        }
        HouseDesignModel houseDesignModel = houseDesignModelMapper.selectById(id);
        if (houseDesignModel!=null){
            houseDesignModel.setVrId(entity.getVrId());
            SuccessTip.create(houseDesignModelMapper.updateById(houseDesignModel));
        }

        return SuccessTip.create();

    }




}
