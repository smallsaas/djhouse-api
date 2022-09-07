package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserTag;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAssetModel;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-11
 */
public interface QueryHouseUserAssetDao extends QueryMasterDao<HouseUserAsset> {
    /*
     * Query entity list by page
     */
    List<HouseUserAssetRecord> findHouseUserAssetPage(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                      @Param("communityId") Long communityId,
                                                      @Param("tag") String tag,
                                                      @Param("search") String search, @Param("orderBy") String orderBy,
                                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    HouseUserAssetModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<HouseUserAssetModel> queryMasterModelList(@Param("masterId") Object masterId);



    int updateUserAssetByUserIdAndAsset(@Param("userId") Long userId, @Param("assetId") Long assetId, @Param("entity") HouseUserAsset entity);





    HouseUserAsset queryHouseUserAssetByAssetId(@Param("assetId") Long assetId);

    List<HouseUserAssetRecord> queryLandlordPage(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                 @Param("orgId") Long orgId,
                                                 @Param("tag") String tag,
                                                 @Param("search") String search, @Param("orderBy") String orderBy,
                                                 @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<HouseUserAssetModel> queryLandlordAssetNumber(@Param("userId")Long userId,@Param("orgId") Long orgId);

//    子查询画像
    List<HouseUserTag> queryHouseUserTagList(@Param("userId")Long userId);

//    子查询备注
    List<HouseUserNote> queryHouseUserNoteList(@Param("userId") Long userId);


    List<HouseUserAssetRecord> queryHouseUserAssetAndServerName(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                                @Param("search") String search);

    List<HouseUserAssetRecord> queryUserAccountList(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                 @Param("search") String search);



}