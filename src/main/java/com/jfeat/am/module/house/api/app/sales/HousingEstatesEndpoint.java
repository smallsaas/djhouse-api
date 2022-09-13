package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/u/house/sales/housingEstates")
public class HousingEstatesEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    TenantUtility tenantUtility;


    @GetMapping
    public Tip getHousingEstatesPage(Page<HouseUserAssetRecord> page,
                                     @RequestParam(name = "userId",required = false) Long userId,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(name = "search", required = false) String search){

        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);



        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setOrgId(tenantUtility.getCurrentOrgId(JWTKit.getUserId()));

        if (search!=null && search.contains("-")){
            String[] strings = search.split("-");
            for (int i=0;i<strings.length;i++){
                if (!strings[i].equals("")){
                    if (i==0){
                        record.setBuildingCode(strings[0]);
                    }
                    if (i==1){
                        record.setRoomNumber(strings[1]);
                    }
                }
            }
            search = null;
        }
        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.queryHouseUserAssetAndServerName(page,record,search);

        List<Long> assetIds = userAssetRecords.stream().map(HouseUserAssetRecord::getAssetId).collect(Collectors.toList());

//        查询出租情况
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.in(HouseRentAsset.ASSET_ID,assetIds);
        List<HouseRentAsset> houseRentAssets = houseRentAssetMapper.selectList(houseRentAssetQueryWrapper);

        for (HouseUserAssetRecord houseUserAssetRecord:userAssetRecords){
            Boolean flag = true;
            for (HouseRentAsset houseRentAsset:houseRentAssets){
                if (houseUserAssetRecord.getAssetId().equals(houseRentAsset.getAssetId())){
                    flag = false;
                    houseUserAssetRecord.setStatus(1);
                    houseUserAssetRecord.setStatusStr("已挂盘");
                    if (houseRentAsset.getServerId()!=null){
                        if (houseRentAsset.getStatus()==HouseRentAsset.STATUS_RENTED){
                            houseUserAssetRecord.setStatus(4);
                            houseUserAssetRecord.setStatusStr("已出租");
                        }else {
                            houseUserAssetRecord.setStatus(3);
                            houseUserAssetRecord.setStatusStr("已指定顾问");
                        }
                    }
                }
            }
            if (flag){
                houseUserAssetRecord.setStatus(2);
                houseUserAssetRecord.setStatusStr("房东自用");
            }
        }

        userAssetRecords.sort((a,b)->a.getStatus()-b.getStatus());

        page.setRecords(userAssetRecords);
        return SuccessTip.create(page);
    }

//    房东挂盘
    @PostMapping("/createRentAsset/{id}")
    public Tip createRentAsset(@PathVariable("id") Long assetId){
//        查看是否存产权
        QueryWrapper<HouseUserAsset> houseUserAssetQueryWrapper = new QueryWrapper<>();
        houseUserAssetQueryWrapper.eq(HouseUserAsset.ASSET_ID,assetId);
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(houseUserAssetQueryWrapper);
        if (houseUserAsset==null){
            throw new BusinessException(BusinessCode.CodeBase,"房屋没有产权不能创盘");
        }

//        查看是否出租
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.ASSET_ID,assetId);
        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectOne(houseRentAssetQueryWrapper);
        if (houseRentAsset!=null){
            throw new BusinessException(BusinessCode.CodeBase,"房屋已出租,不能重复创盘");
        }

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(assetId);

        HouseRentAsset entity = new HouseRentAsset();
        entity.setAssetId(assetId);
        entity.setLandlordId(houseUserAsset.getUserId());
        entity.setCommunityId(houseAssetModel.getCommunityId());
        entity.setHouseTypeId(houseAssetModel.getDesignModelId());
        entity.setArea(houseAssetModel.getArea());
        entity.setServerId(null);
        return SuccessTip.create(houseRentAssetMapper.insert(entity));
    }


//    指派置业顾问
    @PutMapping("/pointIntermediary")
    public Tip updateIntermediary(@RequestBody HouseRentAsset entity){
        if (entity.getAssetId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId必填");
        }
        if (entity.getServerId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"serverId为必填项");
        }

//        判断是否已经出租
        QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
        houseRentAssetQueryWrapper.eq(HouseRentAsset.ASSET_ID,entity.getAssetId());
        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectOne(houseRentAssetQueryWrapper);
        if (houseRentAsset==null){
            throw new BusinessException(BusinessCode.CodeBase,"房屋未出租,不能指定置业顾问");
        }

        houseRentAsset.setServerId(entity.getServerId());
        return SuccessTip.create(houseRentAssetMapper.updateById(houseRentAsset));

    }

}
