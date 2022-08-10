package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentSupportFacilitiesDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseSupportFacilitiesService")
public class HouseSupportFacilitiesServiceImpl extends CRUDHouseSupportFacilitiesServiceImpl implements HouseSupportFacilitiesService {

    @Resource
    QueryHouseRentSupportFacilitiesDao queryHouseRentSupportFacilitiesDao;

    @Override
    protected String entityName() {
        return "HouseSupportFacilities";
    }


    @Override
    public List<HouseSupportFacilitiesTypeRecord> getRentHouseSupportFacilitiesStatus(Long assetId, List<HouseSupportFacilitiesTypeRecord> list) {
        if (list==null || list.size()<=0 || assetId==null){
            return null;
        }
        HouseRentSupportFacilitiesRecord rentSupportFacilitiesRecord = new HouseRentSupportFacilitiesRecord();
        rentSupportFacilitiesRecord.setAssetId(assetId);
        List<HouseRentSupportFacilitiesRecord> rentSupportFacilitiesRecordList = queryHouseRentSupportFacilitiesDao.findHouseRentSupportFacilitiesPage(null,rentSupportFacilitiesRecord,null,null,null,null,null);
        if (rentSupportFacilitiesRecordList!=null && rentSupportFacilitiesRecordList.size()>0){
            for (HouseSupportFacilitiesTypeRecord typeRecord:list){
                if (typeRecord.getItems()!=null && typeRecord.getItems().size()>0){

                    for (HouseSupportFacilities supportFacilities:typeRecord.getItems()){
                        for (HouseRentSupportFacilitiesRecord record:rentSupportFacilitiesRecordList){
                            if (supportFacilities.getId().equals(record.getFacilitiesId())){
                                supportFacilities.setStatus(1);
                                break;
                            }
                            else {
                                supportFacilities.setStatus(0);
                            }
                        }
                    }
                }
            }


        }

        return list;
    }
}
