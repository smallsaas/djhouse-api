package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentSupportFacilitiesDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSupportFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseSupportFacilitiesService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSupportFacilitiesServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<HouseSupportFacilitiesTypeRecord> getRentHouseSupportFacilitiesStatus(Long rentId, List<HouseSupportFacilitiesTypeRecord> list) {
        if (list==null || list.size()<=0 || rentId==null){
            return null;
        }
        HouseRentSupportFacilitiesRecord rentSupportFacilitiesRecord = new HouseRentSupportFacilitiesRecord();

        rentSupportFacilitiesRecord.setRentId(rentId);

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


    /**
     * 删除出租选项 并重新调整排序
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Integer deleteHouseSupportFacilities(Long id) {

        Integer affect = 0;
        HouseSupportFacilities houseSupportFacilities = houseSupportFacilitiesMapper.selectById(id);
        if (houseSupportFacilities==null){
            return affect;
        }

        Long typeId = houseSupportFacilities.getTypeId();
        Integer sortNum = houseSupportFacilities.getSortNum();

        affect+=houseSupportFacilitiesMapper.deleteById(id);

        QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("sort_num",sortNum).eq(HouseSupportFacilities.TYPE_ID,typeId);
        List<HouseSupportFacilities> houseSupportFacilitiesList = houseSupportFacilitiesMapper.selectList(queryWrapper);
        if (houseSupportFacilitiesList!=null&&houseSupportFacilitiesList.size()>0){

            for (HouseSupportFacilities facilities:houseSupportFacilitiesList){

                if (facilities.getSortNum()!=null){
                    facilities.setSortNum(facilities.getSortNum()-1);
                    affect+=houseSupportFacilitiesMapper.updateById(facilities);
                }
            }

        }

        return affect;
    }

    /**
     * 设置排序
     * @param id
     * @param orderBy
     * @return
     */
    @Override
    @Transactional
    public Integer setSortNum(Long id, String orderBy) {


        Integer affect = 0;

        if (orderBy==null&&!(orderBy.equals("rise")||orderBy.equals("decline"))){
            throw new BusinessException(BusinessCode.BadRequest,"参数只能[desc | asc]");
        }

        HouseSupportFacilities houseSupportFacilities = houseSupportFacilitiesMapper.selectById(id);
        if (houseSupportFacilities==null){
            throw new BusinessException(BusinessCode.CodeBase,"该选项不存在");
        }

        Integer maxSort = getMaxSortNum(houseSupportFacilities.getTypeId());

        Integer minSort = getMinSortNum(houseSupportFacilities.getTypeId());



        /**
         * 向上移动
         */
        if (orderBy.equals("rise")&&houseSupportFacilities.getSortNum()!=null&&houseSupportFacilities.getSortNum()!=minSort){



            QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sort_num",houseSupportFacilities.getSortNum()-1);
            queryWrapper.eq(HouseSupportFacilities.TYPE_ID,houseSupportFacilities.getTypeId());
            HouseSupportFacilities houseSupportFacilities1 = houseSupportFacilitiesMapper.selectOne(queryWrapper);

            houseSupportFacilities.setSortNum(houseSupportFacilities.getSortNum()-1);

            if (houseSupportFacilities1!=null){
                houseSupportFacilities1.setSortNum(houseSupportFacilities.getSortNum()+1);

                affect+=houseSupportFacilitiesMapper.updateById(houseSupportFacilities1);
                affect+=houseSupportFacilitiesMapper.updateById(houseSupportFacilities);
            }
        }


        /**
         * 向下移动
         */
        if (orderBy.equals("decline")&&houseSupportFacilities.getSortNum()!=null&&houseSupportFacilities.getSortNum()!=maxSort){


            QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sort_num",houseSupportFacilities.getSortNum()+1);
            queryWrapper.eq(HouseSupportFacilities.TYPE_ID,houseSupportFacilities.getTypeId());
            HouseSupportFacilities houseSupportFacilities1 = houseSupportFacilitiesMapper.selectOne(queryWrapper);

            houseSupportFacilities.setSortNum(houseSupportFacilities.getSortNum()+1);

            if (houseSupportFacilities1!=null){
                houseSupportFacilities1.setSortNum(houseSupportFacilities.getSortNum()-1);

                affect+=houseSupportFacilitiesMapper.updateById(houseSupportFacilities1);
                affect+=houseSupportFacilitiesMapper.updateById(houseSupportFacilities);
            }

        }

        return affect;
    }

    @Override
    public Integer getMaxSortNum(Long typeId) {
        QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseSupportFacilities.TYPE_ID,typeId);
        queryWrapper.orderByDesc("sort_num").last("limit 1");
        HouseSupportFacilities houseSupportFacilities = houseSupportFacilitiesMapper.selectOne(queryWrapper);
        if (houseSupportFacilities!=null){
            return houseSupportFacilities.getSortNum();
        }
        return null;
    }

    @Override
    public Integer getMinSortNum(Long typeId) {
        QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseSupportFacilities.TYPE_ID,typeId);
        queryWrapper.orderByAsc("sort_num").last("limit 1");
        HouseSupportFacilities houseSupportFacilities = houseSupportFacilitiesMapper.selectOne(queryWrapper);
        if (houseSupportFacilities!=null){
            return houseSupportFacilities.getSortNum();
        }
        return null;
    }


    /**
     * 初始化出租选项的排序
     * @return
     */
    @Override
    @Transactional
    public Integer initSortNum() {
        Integer affect  = 0;
        QueryWrapper<HouseSupportFacilities> queryWrapper = new QueryWrapper<>();
        List<HouseSupportFacilities> houseSupportFacilitiesList = houseSupportFacilitiesMapper.selectList(queryWrapper);

        if (houseSupportFacilitiesList==null&&houseSupportFacilitiesList.size()<=0){
            return 0;
        }

        Map<Long,Integer> map = new HashMap<>();

        for (HouseSupportFacilities houseSupportFacilities:houseSupportFacilitiesList){
            Integer sortNum = 1;
            if (map.containsKey(houseSupportFacilities.getTypeId())){
                sortNum  = map.get(houseSupportFacilities.getTypeId())+1;
            }
            map.put(houseSupportFacilities.getTypeId(),sortNum);
            houseSupportFacilities.setSortNum(sortNum);
            affect+=houseSupportFacilitiesMapper.updateById(houseSupportFacilities);

        }

        return affect;
    }

    @Override
    public void setStatus(Long typeId,List<HouseSupportFacilitiesRecord> houseSupportFacilitiesRecordList) {

        if (houseSupportFacilitiesRecordList!=null&&houseSupportFacilitiesRecordList.size()>0){
            Integer maxSortNum = getMaxSortNum(typeId);
            Integer minSortNum = getMinSortNum(typeId);

            for (HouseSupportFacilitiesRecord facilities:houseSupportFacilitiesRecordList){

                if (facilities.getSortNum()==maxSortNum){
                    facilities.setDown(false);
                }else {
                    facilities.setDown(true);
                }

                if (facilities.getSortNum()==minSortNum){
                    facilities.setTop(false);
                }else {
                    facilities.setTop(true);
                }

            }


        }

    }


}
