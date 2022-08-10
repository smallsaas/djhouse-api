package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingUnitServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingUnitMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyBuildingUnitService")
public class HousePropertyBuildingUnitServiceImpl extends CRUDHousePropertyBuildingUnitServiceImpl implements HousePropertyBuildingUnitService {

    @Resource
    HousePropertyBuildingUnitMapper housePropertyBuildingUnitMapper;

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HouseAssetMapper houseAssetMapper;


    @Override
    protected String entityName() {
        return "HousePropertyBuildingUnit";
    }


    @Override
    @Transactional
    public int updateUnitBind(Long unitId, HousePropertyBuildingUnit unit) {
        if (unit.getUnitNumber()==null||unit.getUnitNumber().equals("")){
            return 0;
        }
        Integer effect  = 0;
        HousePropertyBuildingUnit housePropertyBuildingUnit = housePropertyBuildingUnitMapper.selectById(unitId);

        HousePropertyBuilding housePropertyBuilding = housePropertyBuildingMapper.selectById(housePropertyBuildingUnit.getBuildingId());
//        验证楼层数是否正确
        if (housePropertyBuilding ==null){
            throw new BusinessException(BusinessCode.CodeBase,"楼栋出错，未找到");
        }
        if (unit.getStartFloors()<=0 || unit.getStartFloors()>unit.getEndFloors() || unit.getEndFloors()>housePropertyBuilding.getFloors()){
            throw new BusinessException(BusinessCode.CodeBase,"楼层范围设置有误");
        }

        QueryWrapper<HouseAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseAsset.UNIT_ID,unitId).between(HouseAsset.FLOOR,unit.getStartFloors(),unit.getEndFloors());
        List<HouseAsset> houseAssetList = houseAssetMapper.selectList(queryWrapper);
        for (HouseAsset houseAsset:houseAssetList){
            houseAsset.setHouseNumber("".concat(String.valueOf(houseAsset.getFloor())).concat(unit.getUnitNumber()));
            effect+= houseAssetMapper.updateById(houseAsset);
        }
        return effect;
    }

    @Override
    @Transactional
    public int updateUnitInfo() {
        QueryWrapper<HousePropertyBuildingUnit> unitQueryWrapper = new QueryWrapper<>();
        Integer effect = 0;
        List<HousePropertyBuildingUnit> unitList =  housePropertyBuildingUnitMapper.selectList(unitQueryWrapper);
        for (HousePropertyBuildingUnit unit:unitList){
            String code = unit.getUnitCode();
            String[] codes = code.split("-");
            unit.setUnitNumber(codes[1]);
            effect+=housePropertyBuildingUnitMapper.updateById(unit);
        }
        return effect;
    }
}
