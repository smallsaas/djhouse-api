package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    private Logger logger = LoggerFactory.getLogger(UserRegisterServiceImp.class);

    @Override
    @Transactional
    public JSONObject register(OAuthTokenRequestExtend token) {

        logger.info("传入信息"+token.toString());
        HousePropertyCommunity housePropertyCommunity =  housePropertyCommunityMapper.selectById(token.getCommunityId());
        if (housePropertyCommunity!=null){
            token.setOrgId(housePropertyCommunity.getTenantId());
        }


        JSONObject jsonObject =  sysThirdPartyUserService.login(token);
        logger.info("token"+jsonObject.toString());
        Long userId = null;
        if (jsonObject!=null && jsonObject.get("userId")!=null){
            userId = jsonObject.getLong("userId");

            UserAccount userAccount = userAccountMapper.selectById(userId);
            logger.info("用户信息"+userAccount.toString());
            if (userAccount==null){
                throw new BusinessException(BusinessCode.LoginFailure,"登录失败");
            }
            if (housePropertyCommunity!=null){
                QueryWrapper<HouseUserCommunityStatus> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseUserCommunityStatus.USER_ID,userId);
                HouseUserCommunityStatus houseUserCommunityStatus =  houseUserCommunityStatusMapper.selectOne(queryWrapper);

                HouseUserCommunityStatus record = new HouseUserCommunityStatus();
                record.setCommunityId(token.getCommunityId());
                record.setUserId(userId);

                if (houseUserCommunityStatus==null){
                    logger.info("添加小区状态"+record.toString());
                    houseUserCommunityStatusMapper.insert(record);
                }else {
                    record.setId(houseUserCommunityStatus.getId());
                    logger.info("修改状态"+record.toString());
                    houseUserCommunityStatusMapper.updateById(record);
                }
                jsonObject.put("communityId",token.getCommunityId());
                jsonObject.put("communityName",housePropertyCommunity.getCommunity());
            }

            if (userAccount.getOrgId()==null){
                logger.info(userId+"orgid为空");
                if (token.getCommunityId()==null){
                    return jsonObject;
                }else {
                    token.setOrgId(housePropertyCommunity.getTenantId());
                    logger.info("设置orgId");
                    userAccount.setOrgId(housePropertyCommunity.getTenantId());
                    userAccountMapper.updateById(userAccount);
                }
            }

            logger.info("getChangeCommunity");
            logger.info(""+token.getChangeCommunity());
            if (token.getChangeCommunity()!=null && token.getChangeCommunity()){
                if (token.getCommunityId()==null){
                    throw new BusinessException(BusinessCode.BadRequest,"小区id不能为空");
                }
                userAccount.setCurrentOrgId(housePropertyCommunity.getTenantId());
                userAccountMapper.updateById(userAccount);
                logger.info("更新用orgId");

                QueryWrapper<HouseUserCommunityStatus> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseUserCommunityStatus.USER_ID,userId);
                HouseUserCommunityStatus houseUserCommunityStatus =  houseUserCommunityStatusMapper.selectOne(queryWrapper);

                HouseUserCommunityStatus record = new HouseUserCommunityStatus();
                record.setCommunityId(token.getCommunityId());
                record.setUserId(userId);

                if (houseUserCommunityStatus==null){
                    logger.info("添加小区状态"+record.toString());
                    houseUserCommunityStatusMapper.insert(record);
                }else {
                    record.setId(houseUserCommunityStatus.getId());
                    logger.info("修改状态"+record.toString());
                    houseUserCommunityStatusMapper.updateById(record);
                }
                jsonObject.put("communityId",token.getCommunityId());
                jsonObject.put("communityName",housePropertyCommunity.getCommunity());

            }

        }
        logger.info("返回信息"+jsonObject.toString());

        return jsonObject;
    }
}
