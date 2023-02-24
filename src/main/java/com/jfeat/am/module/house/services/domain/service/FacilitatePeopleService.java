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

    Page<FacilitatePeopleRecord> findFacilitatePeople(Page<FacilitatePeople> page,String serverName);

    FacilitatePeopleRecord getFacilitatePeople(Integer id);

    int updateFacilitatePeople(FacilitatePeopleRecord facilitatePeopleRecord);

    int saveFacilitatePeople(FacilitatePeopleRecord facilitatePeopleRecord);

    int removeFacilitatePeople(Integer id);
}
