package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.definition.HouseRentLogStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentSupportFacilitiesDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseRentLogService;
import com.jfeat.am.module.house.services.domain.service.HouseRentSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentAssetServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import io.swagger.models.auth.In;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    @Resource
    HouseUserAssetMapper houseUserAssetMapper;

    @Resource
    HouseEmailService houseEmailService;

    @Resource
    HouseRentLogService houseRentLogService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

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
                ,null,null,null,null,null);
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
        entity.setCommunityName(houseAssetModel.getCommunityName());


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

            //            添加更新信息日志
            houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.updateRentInfo.name());

            affected = houseRentAssetService.updateMaster(entity);
        }else {
            try {
                affected = houseRentAssetService.createMaster(entity);

                //            添加发布信息日志
                houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.createRentInfo.name());

            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        if (affected>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
            QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseRentSupportFacilities.RENT_ID,entity.getId());
            affected+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
            for (HouseRentSupportFacilities houseRentSupportFacilities:entity.getSupportFacilitiesList()){
                houseRentSupportFacilities.setRentId(entity.getId());
            }
            affected+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
        }

        return affected;
    }


    @Transactional
    public int createUserRentAssetNotAssetId(HouseRentAsset entity) {
        Integer affected = 0;
        Boolean flag = false;
        Boolean isSendEmail = false;

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
//        判断是否有房子
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setCommunityName(entity.getCommunityName());
        houseAssetRecord.setCommunityId(entity.getCommunityId());
        houseAssetRecord.setBuildingCode(entity.getBuildingCode());
        houseAssetRecord.setHouseNumber(entity.getHouseNumber());
        List<HouseAssetRecord> queryHouseAssetDaoList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);

//        是否存在房子
        if (queryHouseAssetDaoList==null || queryHouseAssetDaoList.size()==0){

            QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
            houseRentAssetQueryWrapper.eq(HouseRentAsset.BUILDING_CODE,entity.getBuildingCode()).eq(HouseRentAsset.COMMUNITY_NAME,entity.getCommunityName()).eq(HouseRentAsset.HOUSE_NUMBER,entity.getHouseNumber());
            List<HouseRentAsset> houseRentAssetList = houseRentAssetMapper.selectList(houseRentAssetQueryWrapper);
            if (houseRentAssetList!=null && houseRentAssetList.size()>0){
                throw new BusinessException(BusinessCode.CodeBase,"已存在出租房屋");
            }
            flag=true;
            isSendEmail = true;

        }else if (queryHouseAssetDaoList.size()==1){

             /*
        验证房子是否是用户的
         */

            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.ASSET_ID,queryHouseAssetDaoList.get(0).getId()).eq(HouseUserAsset.USER_ID,userId);
            HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);

            if (houseUserAsset==null){
                throw new BusinessException(BusinessCode.CodeBase,"该房子属于其他用户");
            }
            HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
            houseUserAssetRecord.setAssetId(queryHouseAssetDaoList.get(0).getId());
            houseUserAssetRecord.setUserId(JWTKit.getUserId());
            List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,houseUserAssetRecord
                    ,null,null,null,null,null);
            if (houseUserAssetRecordList==null || houseUserAssetRecordList.size()==0){
                throw new BusinessException(BusinessCode.NoPermission,"没有找到房子,请重试");
            }

        /*
        设置出租的 小区  户型 面积 房东
         */

//            HouseAssetModel houseAssetModel = queryHouseAssetDao.queryMasterModel(entity.getAssetId());
            entity.setArea(queryHouseAssetDaoList.get(0).getArea());
            entity.setCommunityId(queryHouseAssetDaoList.get(0).getCommunityId());
            entity.setHouseTypeId(queryHouseAssetDaoList.get(0).getDesignModelId());
            entity.setLandlordId(JWTKit.getUserId());
            entity.setAssetId(queryHouseAssetDaoList.get(0).getId());
            entity.setCommunityName(queryHouseAssetDaoList.get(0).getCommunityName());
            entity.setToward(queryHouseAssetDaoList.get(0).getDirection());
            flag = true;
        }else {
            throw new BusinessException(BusinessCode.OutOfRange,"存在多个房屋");
        }


//        是否可出租
        if (flag){
            QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
            houseRentAssetQueryWrapper.eq(HouseRentAsset.BUILDING_CODE,entity.getBuildingCode()).eq(HouseRentAsset.COMMUNITY_NAME,entity.getCommunityName()).eq(HouseRentAsset.HOUSE_NUMBER,entity.getHouseNumber()).eq(HouseRentAsset.LANDLORD_ID,userId);
            List<HouseRentAsset> houseRentAssetList = houseRentAssetMapper.selectList(houseRentAssetQueryWrapper);

            entity.setLandlordId(userId);
            if (houseRentAssetList==null|| houseRentAssetList.size()==0){
                entity.setRentStatus(HouseRentAsset.RENT_STATUS_SOLD_OUT);
                try {
                    affected = houseRentAssetService.createMaster(entity);

                    //            添加发布日志
                    houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.createRentInfo.name());

                    if (affected>0 && isSendEmail!=null&& isSendEmail){
                        houseEmailService.sendRentAssetInfo(entity);
                    }
                } catch (DuplicateKeyException e) {
                    throw new BusinessException(BusinessCode.DuplicateKey);
                }
            }else if (houseRentAssetList.size()==1){
                entity.setId(houseRentAssetList.get(0).getId());
                entity.setRentTime(new Date());

                //            添加更新信息日志
                houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.updateRentInfo.name());

                entity.setCreateTime(houseRentAssetList.get(0).getCreateTime());
                entity.setCreateTime(houseRentAssetList.get(0).getCreateTime());
                entity.setStatus(houseRentAssetList.get(0).getStatus());
                entity.setRentStatus(houseRentAssetList.get(0).getRentStatus());
                entity.setServerId(houseRentAssetList.get(0).getServerId());
                affected = houseRentAssetService.updateMaster(entity);


            }else {
                throw new BusinessException(BusinessCode.CodeBase,"出租房屋已存在");
            }
//        判断是否填入家居
            if (affected>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
                QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseRentSupportFacilities.RENT_ID,entity.getId());
                affected+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
                for (HouseRentSupportFacilities houseRentSupportFacilities:entity.getSupportFacilitiesList()){
                    houseRentSupportFacilities.setRentId(entity.getId());
                }
                affected+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
            }
        }

        return affected;
    }



    @Override
    @Transactional
    public int updateUserRentAsset(HouseRentAsset entity) {

        Integer affected = 0;

        //            添加更新信息日志
        houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.updateRentInfo.name());


        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectById(entity.getId());
        entity.setCreateTime(houseRentAsset.getCreateTime());
        entity.setCreateTime(houseRentAsset.getCreateTime());
        entity.setStatus(houseRentAsset.getStatus());
        entity.setRentStatus(houseRentAsset.getRentStatus());
        entity.setServerId(houseRentAsset.getServerId());

        affected =  houseRentAssetService.updateMaster(entity);


        if (affected>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
            QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseRentSupportFacilities.RENT_ID,entity.getId());
            affected+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
            for (HouseRentSupportFacilities houseRentSupportFacilities:entity.getSupportFacilitiesList()){
                houseRentSupportFacilities.setRentId(entity.getId());
            }
            affected+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
        }
        return affected;
    }


    @Override
    @Transactional
    public int updateUserRentAssetNotAssetId(HouseRentAsset entity) {
        int affect = 0;
        Boolean flag = false;

        HouseRentAsset houseRentAsset = houseRentAssetMapper.selectById(entity.getId());
        entity.setCreateTime(houseRentAsset.getCreateTime());
        entity.setStatus(houseRentAsset.getStatus());
        entity.setRentStatus(houseRentAsset.getRentStatus());
        entity.setServerId(houseRentAsset.getServerId());

        Long userId = JWTKit.getUserId();
        if (userId==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
//        判断是否有房子
        HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
        houseAssetRecord.setCommunityId(entity.getCommunityId());
        houseAssetRecord.setBuildingCode(entity.getBuildingCode());
        houseAssetRecord.setHouseNumber(entity.getBuildingCode());
        List<HouseAssetRecord> queryHouseAssetDaoList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);

//        是否存在房子
        if (queryHouseAssetDaoList==null || queryHouseAssetDaoList.size()==0){

            QueryWrapper<HouseRentAsset> houseRentAssetQueryWrapper = new QueryWrapper<>();
            houseRentAssetQueryWrapper.eq(HouseRentAsset.BUILDING_CODE,entity.getBuildingCode()).eq(HouseRentAsset.COMMUNITY_NAME,entity.getCommunityName()).eq(HouseRentAsset.HOUSE_NUMBER,entity.getHouseNumber());
            List<HouseRentAsset> houseRentAssetList = houseRentAssetMapper.selectList(houseRentAssetQueryWrapper);

            if (houseRentAssetList!=null && houseRentAssetList.size()==1){


                if (houseRentAssetList.get(0).getLandlordId().equals(userId)){
                    flag=true;
                }else {
                    throw new BusinessException(BusinessCode.CodeBase,"该房子其他用户拥有");
                }

            }else {
                throw new BusinessException(BusinessCode.CodeBase,"未找到该房子");
            }



        }else if (queryHouseAssetDaoList.size()==1){

            Long assetId = queryHouseAssetDaoList.get(0).getId();

            QueryWrapper<HouseUserAsset> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(HouseUserAsset.ASSET_ID,assetId).eq(HouseUserAsset.USER_ID,userId);
            HouseUserAsset houseUserAsset = houseUserAssetMapper.selectOne(queryWrapper);

            if (houseUserAsset==null){
                throw new BusinessException(BusinessCode.CodeBase,"该房子属于其他用户");
            }
            flag = true;
        }else {
            throw new BusinessException(BusinessCode.OutOfRange,"存在多个房屋");
        }


        if (flag){

            //            添加更新信息日志
            houseRentLogService.addHouseRentLog(entity.getId(), HouseRentLogStatus.updateRentInfo.name());

            affect = houseRentAssetService.updateMaster(entity);




            if (affect>0 && entity.getSupportFacilitiesList()!=null && entity.getSupportFacilitiesList().size()>0){
                QueryWrapper<HouseRentSupportFacilities> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HouseRentSupportFacilities.RENT_ID,entity.getId());
                affect+=houseRentSupportFacilitiesMapper.delete(queryWrapper);
                for (HouseRentSupportFacilities houseRentSupportFacilities:entity.getSupportFacilitiesList()){
                    houseRentSupportFacilities.setRentId(entity.getId());
                }
                affect+=queryHouseRentSupportFacilitiesDao.batchInsertHouseRentSupportFacilities(entity.getSupportFacilitiesList());
            }
        }


        return affect;
    }

    @Override
    public void setRentTitle(List<HouseRentAssetRecord> houseRentAssetRecordList) {


        if (houseRentAssetRecordList!=null && houseRentAssetRecordList.size()>0){

            StringBuffer sb = new StringBuffer();
            for (HouseRentAssetRecord record:houseRentAssetRecordList){

                if (record.getHouseSupportFacilitiesList()!=null&&record.getHouseSupportFacilitiesList().size()>0){

                    for (HouseSupportFacilities houseSupportFacilities:record.getHouseSupportFacilitiesList()){
                        if (houseSupportFacilities.getTypeEnName().equals("rentDescription")){
                            sb.append(houseSupportFacilities.getTitle());
                            sb.append(" ");
                        }

                    }

                }
                if (record.getTitle()!=null){
                    sb.append(record.getTitle());
                }

                record.setTitle(sb.toString());
            }

        }

    }

    @Override
    public void setRentDescribe(HouseRentAsset houseRentAssetModel, List<HouseSupportFacilitiesTypeRecord> rentHouseSupportFacilitiesStatus) {

        StringBuffer sb = new StringBuffer();


        for (HouseSupportFacilitiesTypeRecord record:rentHouseSupportFacilitiesStatus){

            if (record.getEnName().equals("rentDescription")&&record.getItems()!=null&&record.getItems().size()>0){

                for (HouseSupportFacilities houseSupportFacilities:record.getItems()){
                    if (houseSupportFacilities.getStatus()!=null&&houseSupportFacilities.getStatus()==1){
                        sb.append(houseSupportFacilities.getTitle());
                        sb.append(" ");
                    }

                }

            }

        }
        if (houseRentAssetModel.getRentDescribe()!=null){
            sb.append(houseRentAssetModel.getRentDescribe());
        }
        houseRentAssetModel.setRentDescribe(sb.toString());
    }

    @Override
    public void setRentDescribe(List<HouseRentAssetRecord> houseRentAssetRecordList) {

        if (houseRentAssetRecordList!=null && houseRentAssetRecordList.size()>0){

            StringBuffer sb = new StringBuffer();
            for (HouseRentAssetRecord record:houseRentAssetRecordList){

                if (record.getHouseSupportFacilitiesList()!=null&&record.getHouseSupportFacilitiesList().size()>0){

                    for (HouseSupportFacilities houseSupportFacilities:record.getHouseSupportFacilitiesList()){
                        if (houseSupportFacilities.getTypeEnName().equals("rentDescription")){
                            sb.append(houseSupportFacilities.getTitle());
                            sb.append(" ");
                        }
                    }

                }
                if (record.getSubscribeStatus()!=null){
                    sb.append(record.getRentDescribe());
                }

                record.setRentDescribe(sb.toString());
            }

        }

    }

    /**
     * 插入值到redis[0]中，如果存在则覆盖，如果不存在则加入
     *
     * @param field 字段map: { "字段名" ："字段值"}
     */
    @Override
    public void saveAccurateQueryField(Map<String, String> field) {
        // 精准查询字段的key
        String key = "accurate";
        // opsForHash().putAll 以map集合的形式添加,没有的话插入，有的话覆盖
        try {
            stringRedisTemplate.opsForHash().putAll(key,field);
        } catch (BusinessException businessException) {
            throw new BusinessException(BusinessCode.DatabaseInsertError, "redis插入异常");
        }
    }

    /**
     * 在redis[0]中获取精准查询字段
     *
     * @return
     */
    @Override
    public Map<Object,Object> listAccurateField() {
        // 精准查询字段的key
        String key = "accurate";
        return stringRedisTemplate.opsForHash().entries(key);
    }


}
