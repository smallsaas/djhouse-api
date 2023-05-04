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

    List<HouseUserAssetModel> queryLandlordAssetNumber(@Param("userId") Long userId, @Param("orgId") Long orgId);

    //    子查询画像
    List<HouseUserTag> queryHouseUserTagList(@Param("userId") Long userId);

    //    子查询备注
    List<HouseUserNote> queryHouseUserNoteList(@Param("userId") Long userId);


    List<HouseUserAssetRecord> queryHouseUserAssetAndServerName(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                                @Param("search") String search);

    List<HouseUserAssetRecord> queryUserAccountList(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                    @Param("search") String search);


    List<HouseUserAssetRecord> queryUserAssetRank(Page<HouseUserAssetRecord> page, @Param("record") HouseUserAssetRecord record,
                                                  @Param("tag") String tag,
                                                  @Param("search") String search, @Param("orderBy") String orderBy,
                                                  @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 获取用户的所有回迁房
     *
     * @param userId 用户id
     * @return 房产列表
     */
    List<HouseUserAsset> listUserAssetByUserId(@Param("userId") Long userId, @Param("communityId") Long communityId);

    /**
     * 查询"我的回迁房"，根据sequence_num排序
     *
     * @param userId      用户id
     * @param communityId 用户社区
     * @return
     */
    List<HouseUserAssetRecord> pageMyHouse(@Param("userId") Long userId, @Param("communityId") Long communityId);

    /**
     * 根据id获取 houseUserAssetRecord
     *
     * @param id 用户房产记录id
     * @return
     */
    HouseUserAssetRecord getUserAssetRecordById(@Param("id") Long id);

    /**
     * 根据id获取 houseUserAsset
     *
     * @param id 用户房产记录id
     * @return
     */
    HouseUserAsset getUserAssetById(@Param("id") Long id);

}