package com.jfeat.am.module.house.services.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/23 18:18
 * @author: hhhhhtao
 */
public interface FacilitatePeopleService {

    Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeopleRecord> page,String search);

    Page<FacilitatePeople> managementFindFacilitatePeople(Page<FacilitatePeople> page,String serverName);

    FacilitatePeople getFacilitatePeople(Integer id);

    int updateFacilitatePeople(FacilitatePeople facilitatePeople);

    int saveFacilitatePeople(FacilitatePeople facilitatePeople);

    /**
     * 开放指定便民服务
     * @param id
     * @return
     */
    int updateFacilitatePeopleOfStatusOpen(Integer id);

    /**
     * 关闭指定便民服务
     * @param id
     * @return
     */
    int updateFacilitatePeopleOfStatusClose(Integer id);

    int removeFacilitatePeople(Integer id);

    /**
     * 指定便民服务的拨打电话数加一
     * 添加成功返回当前拨打次数，失败返回null
     *
     * @param id 便民服务id
     */
    String addFacilitatePeoPleDialFrequency(Integer id);

    /**
     * 获取指定便民服务拨打电话数
     *
     * @param id 便民服务id
     * @return
     */
    String getFacilitatePeoPleDialFrequency(Integer id);
}
