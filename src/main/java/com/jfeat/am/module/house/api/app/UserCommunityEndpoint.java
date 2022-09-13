package com.jfeat.am.module.house.api.app;


import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/house/userCommunity")
public class UserCommunityEndpoint {

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @GetMapping("/currentCommunity")
    public Tip getCurrentCommunity(){
        Long userId = JWTKit.getUserId();

        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        Long currentCommunityId =  userCommunityAsset.getUserCommunityStatus(userId);

        return SuccessTip.create( housePropertyCommunityMapper.selectById(currentCommunityId));

    }
}
