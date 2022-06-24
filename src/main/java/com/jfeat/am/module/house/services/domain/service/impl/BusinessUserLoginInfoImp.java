package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyCommunityDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.users.account.services.domain.service.BusinessUserLoginInfo;
import com.jfeat.users.account.services.domain.service.impl.ServiceStore;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusinessUserLoginInfoImp implements BusinessUserLoginInfo {

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;

    @Resource
    QueryEndpointUserDao queryEndpointUserDao;

    @Resource
    QueryHousePropertyCommunityDao queryHousePropertyCommunityDao;


    @Override
    public JSONObject expandUserInfo(UserAccount userAccount) {
        JSONObject jsonObject = new JSONObject();

        Long communityId = null;
        String communityName = null;
        String tenantName = null;

        HousePropertyCommunity community = queryHousePropertyCommunityDao.queryHouseCommunityByOrgId(userAccount.getId());
        if (community!=null){
            communityId = community.getId();
            communityName = community.getCommunity();
            tenantName = community.getTenant();
        }
        jsonObject.put("communityId",communityId);
        jsonObject.put("communityName",communityName);
        jsonObject.put("tenantName",tenantName);
        return jsonObject;
    }
}
