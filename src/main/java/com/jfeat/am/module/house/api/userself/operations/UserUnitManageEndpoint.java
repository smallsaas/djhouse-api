package com.jfeat.am.module.house.api.userself.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/house/operations/userCommunityManage")
public class UserUnitManageEndpoint {


    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    Authentication authentication;

//    修改房屋单元绑定
    @PutMapping("/updateAssetUnitBind")
    public Tip updateAssetUnitBind(@RequestBody HouseAsset entity){

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }
        if (entity.getId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"id不能为空");
        }
        if (entity.getUnitId()==null || entity.getUnitId().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"unitId不能为空");
        }
        HouseAsset houseAsset =  houseAssetMapper.selectById(entity.getId());
        if (houseAsset!=null){
            houseAsset.setUnitId(entity.getUnitId());
            return SuccessTip.create(houseAssetMapper.updateById(houseAsset));
        }

        return SuccessTip.create();
    }
}
