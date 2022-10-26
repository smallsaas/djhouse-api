package com.jfeat.am.module.house.api.app.sales;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMatchLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/sales/landlordExchangeLog")
public class LandlordExchangeLogEndpoint {

    @Resource
    HouseAssetMatchLogMapper houseAssetMatchLogMapper;

    @Resource
    HouseAssetMatchLogService houseAssetMatchLogService;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;


    @GetMapping("/matchResult/{id}")
    public Tip getHouseAssetExchangeRequestResult(Page<HouseAssetMatchLogRecord> page,
                                                  @PathVariable("id") Long userId,
                                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setOwnerUserId(userId);
        List<HouseAssetMatchLogRecord> houseAssetMatchLogs = queryHouseAssetMatchLogDao.findHouseAssetMatchLogPage(page, record, null, null, null, null, null);
        for (int i = 0; i < houseAssetMatchLogs.size(); i++) {
            /*
            添加 匹配双方的房屋信息
             */
            HouseAsset ownerHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getOwnerAssetId());
            HouseAsset matchedHouseAsset = queryHouseAssetDao.queryMasterModel(houseAssetMatchLogs.get(i).getMathchedAssetId());
            houseAssetMatchLogs.get(i).setOwner(ownerHouseAsset);
            houseAssetMatchLogs.get(i).setMatcher(matchedHouseAsset);
        }
        page.setRecords(houseAssetMatchLogs);
        return SuccessTip.create(page);
    }
    //    换房匹配成功设置 已联系

    @PutMapping("/{id}/status/contact")
    public Tip setMatchLogToContact(@PathVariable("id")Long id){

        HouseAssetMatchLog houseAssetMatchLog = houseAssetMatchLogMapper.selectById(id);
        if (houseAssetMatchLog!=null){
            houseAssetMatchLog.setStatus(HouseAssetMatchLog.STATUS_CONTACT);
            return SuccessTip.create(houseAssetMatchLogMapper.updateById(houseAssetMatchLog));
        }

        return SuccessTip.create();
    }

//    换房匹配成功设置 已完成
    @PutMapping("/{id}/status/complete")
    public Tip setMatchLogToComplete(@PathVariable("id")Long id){
        return SuccessTip.create(houseAssetMatchLogService.setMatchLogStatusToComplete(id));
    }

//    换房成功取消 状态设置
    @PutMapping("/{id}/status/cancel")
    public Tip cancelMatchLogStatus(@PathVariable("id")Long id){
        return SuccessTip.create(houseAssetMatchLogService.cancelMatchLogStatus(id));
    }


}
