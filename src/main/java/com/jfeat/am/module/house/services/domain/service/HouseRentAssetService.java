package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseRentAssetService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseRentAssetService extends CRUDHouseRentAssetService {

//    用户上架出租自己房子
    int createUserRentAsset(HouseRentAsset entity);

    int createUserRentAssetNotAssetId(HouseRentAsset entity);

    int updateUserRentAsset(HouseRentAsset entity);

    int updateUserRentAssetNotAssetId(HouseRentAsset entity);

    void setRentTitle(List<HouseRentAssetRecord> houseRentAssetRecordList);

    void setRentDescribe(HouseRentAsset houseRentAsset ,List<HouseSupportFacilitiesTypeRecord> rentHouseSupportFacilitiesStatus);


    void setRentDescribe(List<HouseRentAssetRecord> houseRentAssetRecordList);

    /**
     * 插入值到redis[0]中，如果存在则覆盖，如果不存在则加入
     * @param field 字段map: { "字段名" ："字段值"}
     */
    void saveAccurateQueryField(Map<String,String> field);

    /**
     * 在redis[0]中获取精准查询字段
     *
     * @return
     */
    Map<Object,Object> listAccurateField();

    /**
     * 更新房源的serverId
     * @param id 房源id
     * @param serverId 置业顾问id
     * @return 更新条目数
     */
    int updateServerId(Long id,Long serverId);

    /**
     * 销售 更新房源出租状态
     * @param id 房源id
     * @param state 出租状态
     * @return 更新条目数
     */
    int salesUpdateState(Long id,Integer state,Date startDate,Date endDate);

}