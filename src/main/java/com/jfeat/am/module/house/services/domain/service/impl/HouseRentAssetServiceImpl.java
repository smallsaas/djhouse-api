package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentSupportFacilitiesDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseRentSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentAssetServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentSupportFacilities;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import io.swagger.models.auth.In;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseRentAssetService")
public class HouseRentAssetServiceImpl extends CRUDHouseRentAssetServiceImpl implements HouseRentAssetService {

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    HouseRentSupportFacilitiesMapper houseRentSupportFacilitiesMapper;

    @Resource
    QueryHouseRentSupportFacilitiesDao queryHouseRentSupportFacilitiesDao;

    @Override
    protected String entityName() {
        return "HouseRentAsset";
    }


    @Override
    @Transactional
    public int createUserRentAsset(HouseRentAsset entity) {
          /*
        验证房子是否是用户的
         */
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        houseUserAssetRecord.setUserId(JWTKit.getUserId());
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord
                ,null,null,null,null,null,null);
        if (houseUserAssetRecordList==null || houseUserAssetRecordList.size()==0){
            throw new BusinessException(BusinessCode.NoPermission,"没有找到房子,请重试");
        }

        /*
        设置出租的 小区  户型 面积 房东
         */

        HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(entity.getAssetId());
        entity.setArea(houseAssetModel.getArea());
        entity.setCommunityId(houseAssetModel.getCommunityId());
        entity.setHouseTypeId(houseAssetModel.getDesignModelId());
        entity.setLandlordId(JWTKit.getUserId());

        /*
        查看用户是否出租过房子 出租过就修改 没有就新增
         */
        HouseRentAssetRecord houseRentAssetRecord = new HouseRentAssetRecord();
        houseRentAssetRecord.setAssetId(entity.getAssetId());
        houseRentAssetRecord.setLandlordId(JWTKit.getUserId());
        List<HouseRentAssetRecord> list = queryHouseRentAssetDao.findHouseRentAssetPage(null,houseRentAssetRecord,null,null,null,null,null);

        Integer affected = 0;
        if (list!=null &&list.size()>0){
            entity.setId(list.get(0).getId());
            entity.setRentTime(new Date());
            affected = houseRentAssetService.updateMaster(entity);
        }else {
            try {
                affected = houseRentAssetService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        if (affected>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
            QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseRentSupportFacilities.ASSET_ID,entity.getAssetId());
            affected+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
            affected+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
        }

        return affected;
    }

    @Override
    @Transactional
    public int updateUserRentAsset(HouseRentAsset entity) {

        Integer affected = 0;
        affected =  houseRentAssetService.updateMaster(entity);
        if (entity.getAssetId()==null){
            return affected;
        }

        if (affected>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
            QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseRentSupportFacilities.ASSET_ID,entity.getAssetId());
            affected+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
            affected+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
        }
        return affected;
    }
}
