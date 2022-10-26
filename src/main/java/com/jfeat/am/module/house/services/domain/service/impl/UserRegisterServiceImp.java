package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.jfeat.am.module.house.services.domain.service.UserRegisterService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserCommunityStatusMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.am.module.house.services.gen.persistence.model.OAuthTokenRequestExtend;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import com.jfeat.users.weChatMiniprogram.services.domain.service.SysThirdPartyUserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.logging.Logger;

@Service("userRegisterService")
public class UserRegisterServiceImp implements UserRegisterService {

    @Resource
    SysThirdPartyUserService sysThirdPartyUserService;

    @Resource
    HouseUserCommunityStatusMapper houseUserCommunityStatusMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    UserAccountMapper userAccountMapper;

    Logger log = Logger.getLogger("login");
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SysThirdPartyUserService.class);

    @Override
    @Transactional
    public JSONObject register(OAuthTokenRequestExtend token) {

        JSONObject jsonObject =  sysThirdPartyUserService.login(token);
        logger.info("token",jsonObject);
        Long userId = null;
        if (jsonObject!=null && jsonObject.get("userId")!=null){
            userId = jsonObject.getLong("userId");
            UserAccount userAccount = userAccountMapper.selectById(userId);
            if (userAccount==null){
                throw new BusinessException(BusinessCode.LoginFailure,"登录失败");
            }

            if (userAccount.getOrgId()==null){
                if (token.getCommunityId()==null){
                    return jsonObject;
                }else {
                    HousePropertyCommunity housePropertyCommunity =  housePropertyCommunityMapper.selectById(token.getCommunityId());
                    if (housePropertyCommunity==null){
                        throw new BusinessException(BusinessCode.BadRequest,"未找到改小区信息");
                    }
                    token.setOrgId(housePropertyCommunity.getTenantId());
                    jsonObject =  sysThirdPartyUserService.login(token);

                }
            }

            log.info("getChangeCommunity");
            if (token.getChangeCommunity()!=null && token.getChangeCommunity()){
                if (token.getCommunityId()==null){
                    throw new BusinessException(BusinessCode.BadRequest,"小区id不能为空");
                }
                HousePropertyCommunity housePropertyCommunity =  housePropertyCommunityMapper.selectById(token.getCommunityId());
                if (housePropertyCommunity==null){
                    throw new BusinessException(BusinessCode.BadRequest,"未找到改小区信息");
                }
                userAccount.setCurrentOrgId(housePropertyCommunity.getTenantId());
                userAccountMapper.updateById(userAccount);

                QueryWrapper<HouseUserCommunityStatus> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseUserCommunityStatus.USER_ID,userId);
                HouseUserCommunityStatus houseUserCommunityStatus =  houseUserCommunityStatusMapper.selectOne(queryWrapper);

                HouseUserCommunityStatus record = new HouseUserCommunityStatus();
                record.setCommunityId(token.getCommunityId());
                record.setUserId(userId);

                if (houseUserCommunityStatus==null){

                    houseUserCommunityStatusMapper.insert(record);
                }else {
                    record.setId(houseUserCommunityStatus.getId());
                    houseUserCommunityStatusMapper.updateById(record);
                }
                jsonObject.put("communityId",token.getCommunityId());
                jsonObject.put("communityName",housePropertyCommunity.getCommunity());

            }

        }
        logger.info("loginJson",jsonObject);

        return jsonObject;
    }
}
