package com.jfeat.am.module.house.api.app.sales;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.api.permission.HouseUserNotePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserNoteDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserTagRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserNoteService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.*;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/u/house/sales/landlord")
public class LandlordManageEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;



    @Resource
    HouseRentAssetMapper houseRentAssetMapper;


    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;







    //    房东列表
    @GetMapping
    public Tip getLandlordList(Page<HouseUserAssetRecord> page,
                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(name = "search", required = false) String search) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }


        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();


        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.queryLandlordPage(page, record,null, null, search, null, null, null);

        List<Long> userId = new ArrayList<>();
        for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
            if (!userId.contains(houseUserAssetRecord.getId())) {
                userId.add(houseUserAssetRecord.getId());
            }
        }


        List<HouseUserAssetModel> houseUserAssetModelList = queryHouseUserAssetDao.queryLandlordAssetNumber(null, null);
        for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
            for (HouseUserAssetModel houseUserAssetModel : houseUserAssetModelList) {
                if (houseUserAssetRecord.getId().equals(houseUserAssetModel.getUserId())) {
                    houseUserAssetRecord.setAssetNumber(houseUserAssetModel.getAssetNumber());
                }

            }
        }


        if (userId!=null && userId.size()>0){
            QueryWrapper<HouseRentAsset> rentAssetQueryWrapper = new QueryWrapper<>();
            rentAssetQueryWrapper.in(HouseRentAsset.LANDLORD_ID, userId);
            rentAssetQueryWrapper.groupBy(HouseRentAsset.LANDLORD_ID);
            List<HouseRentAsset> houseRentAssets = houseRentAssetMapper.selectList(rentAssetQueryWrapper);

            for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
                for (HouseRentAsset houseRentAsset : houseRentAssets) {
                    if (houseUserAssetRecord.getId().equals(houseRentAsset.getLandlordId())) {
                        houseUserAssetRecord.setExistRent(true);
                    }

                }
            }
        }



        page.setRecords(userAssetRecords);

        return SuccessTip.create(page);
    }





//    房东详情
    @GetMapping("/{id}")
    public Tip getLandlordDetails(@PathVariable("id") Long userId){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setOrgId(JWTKit.getOrgId());
        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.queryLandlordPage(null, record, JWTKit.getOrgId(), null, null, null, null, null);


//       查询房屋
        List<HouseUserAssetRecord> assetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,record,null,null,null,null,null,null);

        if (userAssetRecords!=null && userAssetRecords.size()>0){
            userAssetRecords.get(0).setHouseUserAssetRecordList(assetRecordList);
            return SuccessTip.create(userAssetRecords.get(0));
        }

        return SuccessTip.create();
    }

//    房东产权列表
    @GetMapping("/landlordAsset/{id}")
    public Tip getLandLordAssetList(@PathVariable("id") Long userId,Page<HouseUserAssetRecord> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "search", required = false) String search){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        page.setSize(pageSize);
        page.setCurrent(pageNum);

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);

        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(page,record,null,null,search,null,null,null);
        page.setRecords(houseUserAssetRecordList);
        return SuccessTip.create(page);
    }

//    添加房东
    @PostMapping("/userAccount")
    public Tip createUserAccount(@RequestBody UserAccount entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        if (entity.getPhone()==null || entity.getPhone().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"phone为必填项");
        }
        if (entity.getName()==null || entity.getName().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"name为必填项");
        }

        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserAccount.PHONE,entity.getPhone());
        List<UserAccount> userAccountList = userAccountMapper.selectList(queryWrapper);
        if (userAccountList!=null && userAccountList.size()>0){
            throw new BusinessException(BusinessCode.UserAlreadyReg,"用户已存在");
        }

        String uuid = UUID.randomUUID().toString().replace("-","");
        entity.setAccount(uuid);
        entity.setRealName(entity.getName());
        entity.setAppid("1");
        entity.setUnionId("House");
        entity.setType(EndUserTypeSetting.USER_TYPE_LANDLORD);
        return SuccessTip.create(userAccountMapper.insert(entity));
    }


//    @GetMapping("/userAccount")
//    public Tip getUserAccountList(Page<HouseUserAssetRecord> page,
//                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
//                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
//                                  @RequestParam(name = "search", required = false) String search) {
//        if (JWTKit.getUserId()==null){
//            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
//        }
//
//
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
//
//        HouseUserAssetRecord record = new HouseUserAssetRecord();
//
//
//        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.queryUserAccountList(page, record, search);
//
//
//        List<HouseUserAssetModel> houseUserAssetModelList = queryHouseUserAssetDao.queryLandlordAssetNumber(null, null);
//        for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
//            Boolean flag =true;
//            for (HouseUserAssetModel houseUserAssetModel : houseUserAssetModelList) {
//                if (houseUserAssetRecord.getId().equals(houseUserAssetModel.getUserId())) {
//                    flag=false;
//                    houseUserAssetRecord.setAssetNumber(houseUserAssetModel.getAssetNumber());
//                }
//
//            }
//            if (flag){
//                houseUserAssetRecord.setAssetNumber(0);
//            }
//        }
//
//        page.setRecords(userAssetRecords);
//        return SuccessTip.create(page);
//    }


    @GetMapping("/allCommunity")
    public Tip getALlCommunity(){
        QueryWrapper<HousePropertyCommunity> queryWrapper = new QueryWrapper<>();
        return SuccessTip.create(housePropertyCommunityMapper.selectList(queryWrapper));
    }

    //    查询楼栋
    @GetMapping("/community/{id}")
    public Tip getBuildingList(@PathVariable("id") Long communityId){
        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID,communityId);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);
        return SuccessTip.create(buildingList);
    }


    //    查询房号
    @GetMapping("/building/{id}")
    public Tip getAssetList(@PathVariable("id") Long buildingId,
                            Page<HouseAsset> page,
                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        QueryWrapper<HouseAsset> assetQueryWrapper = new QueryWrapper<>();
        assetQueryWrapper.eq(HouseAsset.BUILDING_ID,buildingId);
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = houseAssetMapper.selectPage(page,assetQueryWrapper);

        List<HouseAsset> houseAssetList = page.getRecords();
        List<Long> assetIds = houseAssetList.stream().map(HouseAsset::getId).collect(Collectors.toList());


        if (assetIds!=null && assetIds.size()>0){
            QueryWrapper<HouseUserAsset> rentAssetQueryWrapper = new QueryWrapper<>();
            rentAssetQueryWrapper.in(HouseRentAsset.ASSET_ID,assetIds);
            List<HouseUserAsset> rentAssetList = houseUserAssetMapper.selectList(rentAssetQueryWrapper);

            for (HouseAsset houseAsset:houseAssetList){

                for (HouseUserAsset houseUserAsset:rentAssetList){
                    if (houseAsset.getId().equals(houseUserAsset.getAssetId())){
                        houseAsset.setExistUser(true);
                    }

                }
            }
        }


        return SuccessTip.create(page);
    }

//    房号绑定房东
    @PostMapping("/bindLandlord")
    public Tip bindLandlord(@RequestBody HouseUserAsset entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        if (entity.getUserId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"userId为必填项");
        }
        if (entity.getAssetId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"assetId为必填项");
        }

        QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseUserAsset.ASSET_ID,entity.getAssetId());
        HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);
        if (houseUserAsset!=null){
            throw new BusinessException(BusinessCode.CodeBase,"改房子已被绑定");
        }else {
            return SuccessTip.create(houseUserAssetMapper.insert(entity));
        }
    }



}
