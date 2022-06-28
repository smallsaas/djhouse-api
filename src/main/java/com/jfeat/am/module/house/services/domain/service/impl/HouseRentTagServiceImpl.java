package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentTagDao;
import com.jfeat.am.module.house.services.domain.service.HouseRentTagService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseRentTagServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentTag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseRentTagService")
public class HouseRentTagServiceImpl extends CRUDHouseRentTagServiceImpl implements HouseRentTagService {

    @Resource
    QueryHouseRentTagDao queryHouseRentTagDao;

    @Override
    protected String entityName() {
        return "HouseRentTag";
    }


    @Override
    public List<HouseRentTag> getHouseRentTags(String tagIds) {
        List<HouseRentTag> houseRentTagList = new ArrayList<>();
        List<String> ids = Arrays.asList(tagIds.split(","));
        for (String id:ids){
            HouseRentTag houseRentTag = queryHouseRentTagDao.queryMasterModel(Long.parseLong(id));
            if (houseRentTag!=null){
                houseRentTagList.add(houseRentTag);
            }
        }

        return houseRentTagList;
    }
}
