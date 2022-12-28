package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetLogMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.module.blacklist.services.domain.service.EndUserBlacklistService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetService")
public class HouseAssetServiceImpl extends CRUDHouseAssetServiceImpl implements HouseAssetService {


    public static Integer CHANGE_ASSET_LIMIT = 1;
    
    @Resource
    EndUserBlacklistService endUserBlacklistService;


    @Resource
    UserAccountUtility userAccountUtility;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @Resource
    HouseAssetExchangeRequestService houseAssetExchangeRequestService;

    @Resource
    HouseAssetLogService houseAssetLogService;
    @Resource
    HouseUserAssetService houseUserAssetService;
    @Resource
    HouseEmailService houseEmailService;
    @Resource
    QueryHouseAssetDao queryHouseAssetDao;
    @Resource
    HouseAssetLogMapper houseAssetLogMapper;
    @Resource
    HouseAssetComplaintService houseAssetComplaintService;

    @Override
    protected String entityName() {
        return "HouseAsset";
    }


    @Override
    @Transactional
    public Integer addAsset(HouseUserAsset entity) {

        if (entity.getAssetId() == null || "".equals(entity.getAssetId())) {
            throw new BusinessException(BusinessCode.BadRequest, "assetId为必填项");
        }

        if (endUserBlacklistService.isUserShield(entity.getUserId())){
            throw new BusinessException(BusinessCode.CodeBase,"已被拉黑");
        }
        

        Integer affected = 0;




//        产权记录
        HouseAssetLog houseAssetLog = new HouseAssetLog();
        houseAssetLog.setUserId(entity.getUserId());



//        判断用户是是房东 还是二房东
        List<Integer> typeList =  userAccountUtility.getUserTypeList(entity.getUserId());
        if (typeList==null || typeList.size()<=0){
            throw new BusinessException(BusinessCode.NoPermission,"无权限");
        }

        Integer userType = 2;

        if (typeList.contains(EndUserTypeSetting.USER_TYPE_SECOND_LANDLORD)){
            userType = 2;
        }else{
            userType=1;
        }





        entity.setUserType(userType);
        HouseUserAssetRecord houseUserAssetRecord = new HouseUserAssetRecord();
        houseUserAssetRecord.setAssetId(entity.getAssetId());
        houseUserAssetRecord.setUserType(userType);
        List<HouseUserAssetRecord> houseUserAssetRecordList = queryHouseUserAssetDao.findHouseUserAssetPage(null, houseUserAssetRecord, null, null, null, null, null);

        /*
        判断这个房子是否有人，没人直接进行增加 有人判断是否是最终用户
         */

        if (houseUserAssetRecordList == null || houseUserAssetRecordList.size() == 0) {
            entity.setUserId(entity.getUserId());
            try {
                affected = houseUserAssetService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }


            houseAssetLog.setAssetId(entity.getAssetId());
            affected+=houseAssetLogService.createMaster(houseAssetLog);




            return affected;
        } else {
            /*
            判读房子是否是自己的
             */
            if (!(houseUserAssetRecordList.get(0).getUserId().equals(entity.getUserId()))) {
                /*
                判断是否是最终用户 不是可以进行修改
                 */
                if (!(HouseUserAsset.FINAL_FLAG_CONFIRM.equals(houseUserAssetRecordList.get(0).getFinalFlag()))) {

                    //        超过换房限制次数 发送邮件
//                    QueryWrapper<HouseAssetLog> houseAssetLogQueryWrapper = new QueryWrapper<>();
//                    houseAssetLogQueryWrapper.eq(HouseAssetLog.ASSET_ID,entity.getAssetId());
//                    List<HouseAssetLog> houseAssetLogList = houseAssetLogMapper.selectList(houseAssetLogQueryWrapper);
//                    if (houseAssetLogList!=null && houseAssetLogList.size()>=CHANGE_ASSET_LIMIT){
//                        houseEmailService.sendMoreThanAssetLimit(houseUserAssetRecordList.get(0).getUserId(),entity.getUserId(),queryHouseAssetDao.queryMasterModel(entity.getAssetId()));
//                    }

//                    entity.setId(houseUserAssetRecordList.get(0).getId());



                    HouseAssetComplaint complaint = new HouseAssetComplaint();
                    complaint.setUserId(entity.getUserId());
                    complaint.setOldUserId(houseUserAssetRecordList.get(0).getUserId());
                    complaint.setAssetId(houseUserAssetRecordList.get(0).getAssetId());





//                    添加记录
                    houseAssetLog.setOldUserId(houseUserAssetRecordList.get(0).getUserId());
                    houseAssetLog.setAssetId(houseUserAssetRecordList.get(0).getAssetId());
                    affected+=houseAssetLogService.createMaster(houseAssetLog);


                    entity = houseUserAssetRecordList.get(0);
                    entity.setUserId(entity.getUserId());
                    entity.setUnlike(HouseUserAsset.UNLIKE_STATUS_LIKE);
                    entity.setLocked(HouseUserAsset.LOCKED_STATUS_UNLOCKED);
                    affected = houseUserAssetService.updateMaster(entity);
                    if (affected > 0) {
                        /*
                        添加产权申述
                         */
                        try {
                            affected += houseAssetComplaintService.createMaster(complaint);

                        } catch (DuplicateKeyException e) {
                            throw new BusinessException(BusinessCode.DuplicateKey);
                        }
                    }


                    return affected;
                } else {
                    throw new BusinessException(BusinessCode.CodeBase, "该资产已被确认");
                }

            }
        }
        return affected;
    }
}
