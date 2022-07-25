package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAssetComplaintService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetComplaintServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetComplaintService")
public class HouseAssetComplaintServiceImpl extends CRUDHouseAssetComplaintServiceImpl implements HouseAssetComplaintService {

    @Override
    protected String entityName() {
        return "HouseAssetComplaint";
    }


                            }
