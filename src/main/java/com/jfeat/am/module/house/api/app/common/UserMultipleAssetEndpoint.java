package com.jfeat.am.module.house.api.app.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/userMultipleAsset")
public class UserMultipleAssetEndpoint {

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @GetMapping("/getMultipleAssetInfo")
    public Tip getCurrentCommunityMultipleAssetInfo(Page<HouseAssetRecord> page,
                                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }

        Long communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        if (communityId == null) {
            throw new BusinessException(BusinessCode.CodeBase, "未获取到当前小区信息");
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseAssetRecord record = new HouseAssetRecord();
        record.setCommunityId(communityId);
        record.setAssetFlag(HouseAsset.ASSET_FLAG_MULTIPLE);

        List<HouseAssetRecord> records = queryHouseAssetDao.getHouseAssetPage(null, record, null, null, null, null, null);


        List<Map<String, Object>> mapList = new ArrayList<>();
        for (HouseAssetRecord houseAssetRecord : records) {
            Boolean flag = true;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).containsKey(houseAssetRecord.getBuildingCode())) {
                    flag = false;
                    Integer count = (Integer) mapList.get(i).get(houseAssetRecord.getBuildingCode());

                    List<HouseAssetRecord> items = (List<HouseAssetRecord>) mapList.get(i).get("items");
                    items.add(houseAssetRecord);
                    mapList.get(i).put(houseAssetRecord.getBuildingCode(), count + 1);
                    mapList.get(i).put("items", items);
                    break;
                }
            }
            if (flag) {
                Map<String, Object> map = new HashMap();
                List<HouseAssetRecord> item = new ArrayList<>();
                map.put(houseAssetRecord.getBuildingCode(), 1);
                item.add(houseAssetRecord);
                map.put("items", item);
                map.put("buildingCode",houseAssetRecord.getBuildingCode());
                mapList.add(map);
            }
        }
        return SuccessTip.create(mapList);
    }

}
