package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetMatchLogServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetMatchLogService")
public class HouseAssetMatchLogServiceImpl extends CRUDHouseAssetMatchLogServiceImpl implements HouseAssetMatchLogService {

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Override
    protected String entityName() {
        return "HouseAssetMatchLog";
    }


    @Override
    public Integer setMatchLogStatusToComplete(Long id) {
        Integer affect = 0;
        HouseAssetMatchLog houseAssetMatchLog = houseAssetMatchLogMapper.selectById(id);
        if (houseAssetMatchLog!=null){
            if (!houseAssetMatchLog.getStatus().equals(HouseAssetMatchLog.STATUS_CONTACT)){
                throw new BusinessException(BusinessCode.ErrorStatus,"请先联系");
            }
            QueryWrapper<HouseAssetMatchLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID,houseAssetMatchLog.getOwnerUserId())
                    .eq(HouseAssetMatchLog.OWNER_ASSET_ID,houseAssetMatchLog.getOwnerAssetId())
                    .eq(HouseAssetMatchLog.MATCHED_USER_ID,houseAssetMatchLog.getMatchedUserId())
                    .eq(HouseAssetMatchLog.MATHCHED_ASSET_ID,houseAssetMatchLog.getMathchedAssetId())
                    .or()
                    .eq(HouseAssetMatchLog.OWNER_USER_ID,houseAssetMatchLog.getMatchedUserId())
                    .eq(HouseAssetMatchLog.OWNER_ASSET_ID,houseAssetMatchLog.getMathchedAssetId())
                    .eq(HouseAssetMatchLog.MATCHED_USER_ID,houseAssetMatchLog.getOwnerUserId())
                    .eq(HouseAssetMatchLog.MATHCHED_ASSET_ID,houseAssetMatchLog.getOwnerAssetId());

            List<HouseAssetMatchLog> matchLogList = houseAssetMatchLogMapper.selectList(queryWrapper);
            if (matchLogList!=null){
                for (int i=0; i<matchLogList.size();i++){
                    HouseAssetMatchLog matchLog = matchLogList.get(i);
                    matchLog.setStatus(HouseAssetMatchLog.STATUS_COMPLETE);
                    affect+=houseAssetMatchLogMapper.updateById(matchLog);
                }
            }

        }

        return affect;
    }

    @Override
    public Integer cancelMatchLogStatus(Long id) {
        Integer affect = 0;
        HouseAssetMatchLog houseAssetMatchLog = houseAssetMatchLogMapper.selectById(id);
        if (houseAssetMatchLog!=null){
            Integer status = houseAssetMatchLog.getStatus();

            Integer temp = status;



            if (status==null||status.equals(HouseAssetMatchLog.STATUS_NOT_SET) ||status.equals(HouseAssetMatchLog.STATUS_CONTACT)){
                status = HouseAssetMatchLog.STATUS_NOT_SET;
            }else if (status.equals(HouseAssetMatchLog.STATUS_NOT_SET)){
                status = HouseAssetMatchLog.STATUS_CONTACT;
            }else {
                status = HouseAssetMatchLog.STATUS_NOT_SET;
            }

            if (temp.equals(HouseAssetMatchLog.STATUS_COMPLETE)){
                QueryWrapper<HouseAssetMatchLog> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseAssetMatchLog.OWNER_USER_ID,houseAssetMatchLog.getOwnerUserId())
                        .eq(HouseAssetMatchLog.OWNER_ASSET_ID,houseAssetMatchLog.getOwnerAssetId())
                        .eq(HouseAssetMatchLog.MATCHED_USER_ID,houseAssetMatchLog.getMatchedUserId())
                        .eq(HouseAssetMatchLog.MATHCHED_ASSET_ID,houseAssetMatchLog.getMathchedAssetId())
                        .or()
                        .eq(HouseAssetMatchLog.OWNER_USER_ID,houseAssetMatchLog.getMatchedUserId())
                        .eq(HouseAssetMatchLog.OWNER_ASSET_ID,houseAssetMatchLog.getMathchedAssetId())
                        .eq(HouseAssetMatchLog.MATCHED_USER_ID,houseAssetMatchLog.getOwnerUserId())
                        .eq(HouseAssetMatchLog.MATHCHED_ASSET_ID,houseAssetMatchLog.getOwnerAssetId());

                List<HouseAssetMatchLog> matchLogList = houseAssetMatchLogMapper.selectList(queryWrapper);
                if (matchLogList!=null){
                    for (int i=0; i<matchLogList.size();i++){
                        HouseAssetMatchLog matchLog = matchLogList.get(i);
                        matchLog.setStatus(HouseAssetMatchLog.STATUS_CONTACT);
                        affect+=houseAssetMatchLogMapper.updateById(matchLog);
                    }
                }
            }else {
                houseAssetMatchLog.setStatus(status);
                affect+=houseAssetMatchLogMapper.updateById(houseAssetMatchLog);
            }


        }

        return affect;
    }
}
