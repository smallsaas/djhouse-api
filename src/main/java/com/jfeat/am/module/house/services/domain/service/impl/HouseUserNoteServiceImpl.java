package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserNoteService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserNoteServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserNoteService")
public class HouseUserNoteServiceImpl extends CRUDHouseUserNoteServiceImpl implements HouseUserNoteService {

    @Override
    protected String entityName() {
        return "HouseUserNote";
    }


                            }
