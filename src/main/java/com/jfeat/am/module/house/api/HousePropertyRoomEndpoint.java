
package com.jfeat.am.module.house.api;


import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyRoomDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;
import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyRoomRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyRoom;

    import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
/**
 * <p>
 *  api
 * </p>
 *
 * @author Code generator
 * @since 2022-06-06
 */
    @RestController
    @Api("HousePropertyRoom")
            @RequestMapping("/api/crud/house/housePropertyRoom/housePropertyRooms")
    public class HousePropertyRoomEndpoint {

    @Resource
    HousePropertyRoomService housePropertyRoomService;

    @Resource
    QueryHousePropertyRoomDao queryHousePropertyRoomDao;



    @BusinessLog(name = "HousePropertyRoom", value = "create HousePropertyRoom")
    @Permission(HousePropertyRoomPermission.HOUSEPROPERTYROOM_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyRoom",response = HousePropertyRoom.class)
    public Tip createHousePropertyRoom(@RequestBody HousePropertyRoom entity){
        Integer affected=0;
        try{
                affected= housePropertyRoomService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HousePropertyRoomPermission.HOUSEPROPERTYROOM_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyRoom",response = HousePropertyRoom.class)
    public Tip getHousePropertyRoom(@PathVariable Long id){
                        return SuccessTip.create(housePropertyRoomService.queryMasterModel(queryHousePropertyRoomDao, id));
            }

    @BusinessLog(name = "HousePropertyRoom", value = "update HousePropertyRoom")
    @Permission(HousePropertyRoomPermission.HOUSEPROPERTYROOM_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyRoom",response = HousePropertyRoom.class)
    public Tip updateHousePropertyRoom(@PathVariable Long id,@RequestBody HousePropertyRoom entity){
        entity.setId(id);
                return SuccessTip.create(housePropertyRoomService.updateMaster(entity));
            }

    @BusinessLog(name = "HousePropertyRoom", value = "delete HousePropertyRoom")
    @Permission(HousePropertyRoomPermission.HOUSEPROPERTYROOM_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyRoom")
    public Tip deleteHousePropertyRoom(@PathVariable Long id){
            return SuccessTip.create(housePropertyRoomService.deleteMaster(id));
        }

    @Permission(HousePropertyRoomPermission.HOUSEPROPERTYROOM_VIEW)
    @ApiOperation(value = "HousePropertyRoom 列表信息",response = HousePropertyRoomRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "buildingId", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "unitId", dataType = "Long"),
                                                                                    @ApiImplicitParam(name = "number", dataType = "String") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHousePropertyRoomPage(Page<HousePropertyRoomRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "buildingId", required = false) Long buildingId,
                    
                                                                                                                                            @RequestParam(name = "unitId", required = false) Long unitId,
                    
                                                                                                                            @RequestParam(name = "number", required = false) String number,
        @RequestParam(name = "orderBy", required = false) String orderBy,
        @RequestParam(name = "sort", required = false)  String sort) {
                    
            if(orderBy!=null&&orderBy.length()>0){
        if(sort!=null&&sort.length()>0){
        String sortPattern = "(ASC|DESC|asc|desc)";
        if(!sort.matches(sortPattern)){
        throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
        }
        }else{
        sort = "ASC";
        }
        orderBy = "`"+orderBy+"`" +" "+sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

    HousePropertyRoomRecord record = new HousePropertyRoomRecord();
                                                                                                                                                                                                record.setBuildingId(buildingId);
                                                                                                                        record.setUnitId(unitId);
                                                                                                                record.setNumber(number);
                        
                                

    List<HousePropertyRoomRecord> housePropertyRoomPage = queryHousePropertyRoomDao.findHousePropertyRoomPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(housePropertyRoomPage);

        return SuccessTip.create(page);
    }
}

