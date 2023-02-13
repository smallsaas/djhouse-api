package com.jfeat.am.module.house.api.app.sales;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/house/sales/rank")
public class AppDataRankEndpoint {

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    TenantUtility tenantUtility;

    /**
     * 销售排名
     * @param search
     * @return
     */
    @GetMapping
    public Tip getSaleRankList(
            @RequestParam(value = "search",required = false) String search
    ){

        Integer rank=1;
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
        if (search!=null && search!=null){
            queryWrapper.and(e->e.like(UserAccount.PHONE,search).or(a->a.like(UserAccount.NAME,search)));
        }

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
                    jsonObject.put("rank",rank);
                    jsonObject.put("phone",userAccount.getPhone());
                    jsonArray.add(jsonObject);
                    rank+=1;
                    break;
                }
            }

        }

        return SuccessTip.create(jsonArray);
    }


    /***
     * 房东 房屋数排名
     * @param page
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/landlord")
    public Tip getLandlordRandList(Page<HouseUserAssetRecord> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "search", required = false) String search){

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        Long orgId = tenantUtility.getCurrentOrgId(userId);
        if (orgId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有社区信息");
        }

        Integer startRank = 0;
        if (pageNum!=null &&pageNum>0 && pageSize!=null && pageSize>0){
            startRank = (pageNum-1)*pageSize;
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setOrgId(orgId);
        List<HouseUserAssetRecord> houseUserAssets = queryHouseUserAssetDao.queryUserAssetRank(page,record,null,search,null,null,null);

        for (HouseUserAssetRecord houseUserAssetRecord:houseUserAssets){
            startRank+=1;
            houseUserAssetRecord.setRank(startRank);

        }

        page.setRecords(houseUserAssets);
        return SuccessTip.create(page);
    }

}
