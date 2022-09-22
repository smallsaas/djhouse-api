package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.HouseUnlikeLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUnlikeLogServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetExchangeRequestMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetExchangeRequest;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUnlikeLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUnlikeLogService")
public class HouseUnlikeLogServiceImpl extends CRUDHouseUnlikeLogServiceImpl implements HouseUnlikeLogService {

    @Resource
    HouseAssetExchangeRequestMapper houseAssetExchangeRequestMapper;

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Override
    protected String entityName() {
        return "HouseUnlikeLog";
    }


    @Override
    public int deleteUnlikeLog(Long userId, Long assetId) {
        int affect = 0;

        QueryWrapper<HouseUnlikeLog> unlikeLogQueryWrapper = new QueryWrapper<>();
        unlikeLogQueryWrapper.eq(HouseUnlikeLog.USER_ID, userId).eq(HouseUnlikeLog.ASSET_ID, assetId);
        affect+=houseUnlikeLogMapper.delete(unlikeLogQueryWrapper);

        QueryWrapper<HouseAssetExchangeRequest> exchangeRequestQueryWrapper = new QueryWrapper<>();
        exchangeRequestQueryWrapper.eq(HouseAssetExchangeRequest.USER_ID, userId).eq(HouseAssetExchangeRequest.TARGET_ASSET, assetId);
        affect+=houseAssetExchangeRequestMapper.delete(exchangeRequestQueryWrapper);

        QueryWrapper<HouseAssetMatchLog> matchLogQueryWrapper = new QueryWrapper<>();
        matchLogQueryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID, userId).eq(HouseAssetMatchLog.MATHCHED_ASSET_ID, assetId)
                .or(e -> e.eq(HouseAssetMatchLog.OWNER_ASSET_ID, assetId).eq(HouseAssetMatchLog.MATCHED_USER_ID, userId));
        affect+=houseAssetMatchLogMapper.delete(matchLogQueryWrapper);

        return affect;
    }
}
