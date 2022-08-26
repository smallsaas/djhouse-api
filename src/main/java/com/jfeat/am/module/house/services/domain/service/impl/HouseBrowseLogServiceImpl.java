package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.HouseBrowseLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseBrowseLogServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseBrowseLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseBrowseLogService")
public class HouseBrowseLogServiceImpl extends CRUDHouseBrowseLogServiceImpl implements HouseBrowseLogService {

    @Override
    protected String entityName() {
        return "HouseBrowseLog";
    }

    @Resource
    HouseBrowseLogMapper houseBrowseLogMapper;

    @Override
    public int addBroseLog(Long userId) {

        QueryWrapper<HouseBrowseLog> browseLogQueryWrapper = new QueryWrapper<>();
        browseLogQueryWrapper.eq(HouseBrowseLog.USER_ID,userId);
        HouseBrowseLog houseBrowseLog = houseBrowseLogMapper.selectOne(browseLogQueryWrapper);
        if (houseBrowseLog!=null){
            houseBrowseLog.setBrowseNumber(houseBrowseLog.getBrowseNumber()+1);
            return houseBrowseLogMapper.updateById(houseBrowseLog);
        }else {
            HouseBrowseLog log = new HouseBrowseLog();
            log.setUserId(userId);
            log.setBrowseNumber(1);
            return houseBrowseLogMapper.insert(log);
        }
    }
}
