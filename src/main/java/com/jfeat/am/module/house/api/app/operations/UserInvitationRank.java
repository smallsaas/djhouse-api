package com.jfeat.am.module.house.api.app.operations;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/u/house/operations/invitationRank")
public class UserInvitationRank {


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

        Map<Long,JSONObject> userMap = new HashMap<>();

        for (UserAccount userAccount:userAccountList){
            JSONObject json = new JSONObject();
            json.put("id",userAccount.getId());
            json.put("phone",userAccount.getPhone());
            json.put("name",userAccount.getName());
            json.put("number",0);
            userMap.put(userAccount.getId(),json);
        }


        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        List<UserAccount> userAccounts = userAccountMapper.selectList(userAccountQueryWrapper);


        for (UserAccount userAccount:userAccounts){

            if (userAccount.getInvitorId()!=null) {
                JSONObject json = userMap.get(userAccount.getInvitorId());
                if (json==null){
                    continue;
                }
                if (json.containsKey("number")) {
                    json.put("number", json.getIntValue("number") + 1);
                } else {
                    json.put("number", 0);
                }
            }
        }

        JSONArray jsonArray = new JSONArray();
        for (UserAccount userAccount:userAccountList){
            jsonArray.add(userMap.get(userAccount.getId()));
        }


        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("number")).reversed());

        for (int i=0;i<jsonArray.size();i++){
            JSONObject json  = jsonArray.getJSONObject(i);
            json.put("rank",i+1);
        }


        return SuccessTip.create(jsonArray);
    }
}
