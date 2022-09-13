package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.jfeat.am.module.house.services.domain.service.UserRegisterService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserCommunityStatusMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.am.module.house.services.gen.persistence.model.OAuthTokenRequestExtend;
import com.jfeat.users.weChatMiniprogram.services.domain.service.SysThirdPartyUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userRegisterService")
public class UserRegisterServiceImp implements UserRegisterService {

    @Resource
    SysThirdPartyUserService sysThirdPartyUserService;

    @Resource
    HouseUserCommunityStatusMapper houseUserCommunityStatusMapper;

    @Override
    @Transactional
    public JSONObject register(OAuthTokenRequestExtend token) {
        JSONObject jsonObject =  sysThirdPartyUserService.login(token);
        if (jsonObject!=null && jsonObject.get("userId")!=null){
            Long userId = jsonObject.getLong("userId");

            HouseUserCommunityStatus record = new HouseUserCommunityStatus();
            record.setCommunityId(token.getCommunityId());
            record.setUserId(userId);

            QueryWrapper<HouseUserCommunityStatus> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserCommunityStatus.USER_ID,userId);
            HouseUserCommunityStatus houseUserCommunityStatus = houseUserCommunityStatusMapper.selectOne(queryWrapper);
            if (houseUserCommunityStatus!=null){
                record.setId(houseUserCommunityStatus.getId());
                houseUserCommunityStatusMapper.updateById(record);
            }else {
                houseUserCommunityStatusMapper.insert(record);
            }
        }

        return jsonObject;
    }
}
