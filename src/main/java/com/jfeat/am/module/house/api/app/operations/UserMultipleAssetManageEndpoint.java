package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.ibatis.annotations.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/userMultipleAssetEndpoint")
public class UserMultipleAssetManageEndpoint {

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HouseAssetMapper houseAssetMapper;

    @GetMapping
    public Tip getMultipleAssetByBuildingId(@RequestParam("buildingId") Long builidngId){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }

        HouseAssetRecord record = new HouseAssetRecord();
        record.setAssetFlag(HouseAsset.ASSET_FLAG_MULTIPLE);
        record.setBuildingId(builidngId);


        List<HouseAssetRecord> houseAssetRecordList = queryHouseAssetDao.getHouseAssetPage(null,record,null,null,null,null,null);

        return SuccessTip.create(houseAssetRecordList);
    }

//    复式绑定户型
    @PutMapping("/bind/{id}")
    public Tip updateMultipleAssetBindHouseType(@PathVariable("id")Long id,@RequestBody HouseAsset entity){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        if (entity.getHouseTypeId()==null){
            throw new BusinessException(BusinessCode.BadRequest,"houseTypeId为必填项");
        }

        HouseAsset houseAsset =  houseAssetMapper.selectById(id);
        if (houseAsset!=null && houseAsset.getAssetFlag().equals(HouseAsset.ASSET_FLAG_MULTIPLE)){
            houseAsset.setHouseTypeId(entity.getHouseTypeId());
            return SuccessTip.create(houseAssetMapper.updateById(houseAsset));
        }
        return SuccessTip.create();
    }

}
