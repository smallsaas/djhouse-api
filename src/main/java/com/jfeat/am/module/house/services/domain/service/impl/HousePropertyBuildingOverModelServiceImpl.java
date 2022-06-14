package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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

@Service("housePropertyBuildingService")
public class HousePropertyBuildingOverModelServiceImpl extends CRUDHousePropertyBuildingOverModelServiceImpl implements HousePropertyBuildingOverModelService {

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;
    @Resource
    HouseAssetService housePropertyRoomService;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Override
    protected String entityName() {
        return "HousePropertyBuilding";
    }


    @Override
    public int initHouseProperty(HousePropertyBuildingModel entity) {
        Assert.isTrue((entity.getUnits()!=null || entity.getUnits()>0),"单元数不能为空或者小于等于0");
        if ((entity.getUnits()==null || entity.getUnits()<=0)){
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Assert.isTrue((entity.getUnits()!=null || entity.getUnits()>0),"楼层数不能为空或者小于等于0");
        if (entity.getFloors()==null || entity.getFloors()<=0){
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Integer affected=0;

//        更新单元表
        for (int i=1;i<=entity.getUnits();i++){
            HousePropertyBuildingUnit housePropertyBuildingUnit = new HousePropertyBuildingUnit();
            housePropertyBuildingUnit.setBuildingId(entity.getId());

//            单元编号为B1-1
            housePropertyBuildingUnit.setUnitCode("".concat(entity.getCode()).concat("-").concat(String.valueOf(i)));
            try{
                affected+= housePropertyBuildingUnitService.createMaster(housePropertyBuildingUnit);
            }catch(DuplicateKeyException e){
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }

//        自动插入房屋表
        List<HousePropertyBuildingUnit> housePropertyBuildingUnitList =  queryHousePropertyBuildingUnitDao.queryHouseBuildingUnitByBuildingId(entity.getId());
        Assert.isTrue(housePropertyBuildingUnitList.size()==entity.getUnits(),"单元表插入有误");
        if (housePropertyBuildingUnitList.size()!=entity.getUnits()){
            throw new BusinessException(BusinessCode.DatabaseInsertError);
        }
        for (int i=1;i<=entity.getFloors();i++){
            for (int j=1;j<=entity.getUnits();j++){
                HouseAsset housePropertyRoom = new HouseAsset();
                housePropertyRoom.setBuildingId(entity.getId());

//                获取单元表id
                housePropertyRoom.setUnitId(housePropertyBuildingUnitList.get(j-1).getId());

                housePropertyRoom.setFloor(i);

                housePropertyRoom.setNumber(String.format("%d%02d",i,j));
                try{
                    affected += housePropertyRoomService.createMaster(housePropertyRoom);
                }catch(DuplicateKeyException e){
                    throw new BusinessException(BusinessCode.DuplicateKey);
                }
            }
        }

        return affected;
    }
    }
