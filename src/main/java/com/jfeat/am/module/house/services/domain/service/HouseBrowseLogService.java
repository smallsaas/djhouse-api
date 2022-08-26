package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseBrowseLogService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;

/**
 * Created by vincent on 2017/10/19.
 */
public interface HouseBrowseLogService extends CRUDHouseBrowseLogService {

    int addBroseLog(Long userId);
}