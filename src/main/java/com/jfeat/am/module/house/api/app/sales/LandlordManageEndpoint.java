package com.jfeat.am.module.house.api.app.sales;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseUserNotePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserNoteDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserTagRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserNoteService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/u/house/sales/landlord")
public class LandlordManageEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    HouseUserNoteService houseUserNoteService;

    @Resource
    QueryHouseUserNoteDao queryHouseUserNoteDao;



    //    房东列表
    @GetMapping
    public Tip getLandlordList(Page<HouseUserAssetRecord> page,
                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                               @RequestParam(name = "search", required = false) String search) {


//        page.setCurrent(pageNum);
//        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();


        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.queryLandlordPage(null, record, JWTKit.getOrgId(), null, search, null, null, null);

        List<Long> userId = new ArrayList<>();
        for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
            if (!userId.contains(houseUserAssetRecord.getId())) {
                userId.add(houseUserAssetRecord.getId());
            }
        }


        List<HouseUserAssetModel> houseUserAssetModelList = queryHouseUserAssetDao.queryLandlordAssetNumber(null, 30L);
        for (HouseUserAssetRecord houseUserAssetRecord : userAssetRecords) {
            for (HouseUserAssetModel houseUserAssetModel : houseUserAssetModelList) {
                if (houseUserAssetRecord.getId().equals(houseUserAssetModel.getUserId())) {
                    houseUserAssetRecord.setAssetNumber(houseUserAssetModel.getAssetNumber());
                }

            }
        }


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
        page.setRecords(userAssetRecords);

        return SuccessTip.create(userAssetRecords);
    }

    @GetMapping("/{id}")
    public Tip getLandlordDetails(@PathVariable("id") Long userId){

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



}
