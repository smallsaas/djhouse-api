package com.jfeat.am.module.house.api.app.sales;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/house/sales/rank")
public class SalesRankEndpoint {

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    TenantUtility tenantUtility;

    @GetMapping
    public Tip getSaleRankList(){

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        Long orgId = tenantUtility.getCurrentOrgId(userId);
        if (orgId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有社区信息");
        }

        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(e->e.eq(UserAccount.ORG_ID,orgId).isNull("current_org_id")).or(e->e.eq("current_org_id",orgId));

        List<UserAccount> userAccountList = userAccountMapper.selectList(queryWrapper);


        QueryWrapper<HouseRentAsset> rentAssetQueryWrapper = new QueryWrapper<>();
        rentAssetQueryWrapper.select(HouseRentAsset.SERVER_ID,"count(*) as number").isNotNull(HouseRentAsset.SERVER_ID).groupBy(HouseRentAsset.SERVER_ID).orderByDesc("number");
        houseRentAssetMapper.selectMaps(rentAssetQueryWrapper);

        List<Map<String, Object>> maps = houseRentAssetMapper.selectMaps(rentAssetQueryWrapper);

        JSONArray jsonArray = new JSONArray();
        for (Map<String,Object> map:maps){
            for (UserAccount userAccount:userAccountList){
                if (userAccount.getId().equals((Long) map.get("server_id"))){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",userAccount.getId());
                    jsonObject.put("name",userAccount.getName());
                    jsonObject.put("realName",userAccount.getRealName());
                    jsonObject.put("avatar",userAccount.getAvatar());
                    jsonObject.put("number",map.get("number"));
                    jsonArray.add(jsonObject);
                    break;
                }
            }

        }
        return SuccessTip.create(jsonArray);
    }

}
