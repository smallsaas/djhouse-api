package com.jfeat.am.module.house.services.domain.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseStatisticsDao {

    /**
     * 统计小区信息
     * @param communityId 小区id
     * @return 返回list 0-房子数 1-无效房子数 2-平房数 3-复式数 4-楼栋数 5-单元数 6-户型数 7-换房需求数 8-换房记录数 9-停车位数,10房东数 11-供应商数
     * 如果后面需要继续添加统计信息 请在后面加 不要在已有的查询语句中加
     */
    List<Integer> communityStatistics(@Param("communityId") Long communityId,@Param("issue") Integer issue);

    /**
     *
     * @param buildingId 楼栋id
     * @return 返回list 0-房子数 1-单元数 2-户型数 3-以回迁数 4-复式数
     *      * 如果后面需要继续添加统计信息 请在后面加 不要在已有的查询语句中加
     */
    List<Integer> buildingStatistics(@Param("buildingId") Long buildingId);
}
