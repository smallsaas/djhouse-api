
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
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyUserRoomDao;
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
import com.jfeat.am.module.house.services.domain.model.HousePropertyUserRoomRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserRoom;

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
    @Api("HousePropertyUserRoom")
            @RequestMapping("/api/crud/house/housePropertyUserRoom/housePropertyUserRooms")
    public class HousePropertyUserRoomEndpoint {

    @Resource
    HousePropertyUserRoomService housePropertyUserRoomService;

    @Resource
    QueryHousePropertyUserRoomDao queryHousePropertyUserRoomDao;



    @BusinessLog(name = "HousePropertyUserRoom", value = "create HousePropertyUserRoom")
    @Permission(HousePropertyUserRoomPermission.HOUSEPROPERTYUSERROOM_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyUserRoom",response = HousePropertyUserRoom.class)
    public Tip createHousePropertyUserRoom(@RequestBody HousePropertyUserRoom entity){
        Integer affected=0;
        try{
                affected= housePropertyUserRoomService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HousePropertyUserRoomPermission.HOUSEPROPERTYUSERROOM_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyUserRoom",response = HousePropertyUserRoom.class)
    public Tip getHousePropertyUserRoom(@PathVariable Long id){
                        return SuccessTip.create(housePropertyUserRoomService.queryMasterModel(queryHousePropertyUserRoomDao, id));
            }

    @BusinessLog(name = "HousePropertyUserRoom", value = "update HousePropertyUserRoom")
    @Permission(HousePropertyUserRoomPermission.HOUSEPROPERTYUSERROOM_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyUserRoom",response = HousePropertyUserRoom.class)
    public Tip updateHousePropertyUserRoom(@PathVariable Long id,@RequestBody HousePropertyUserRoom entity){
        entity.setId(id);
                return SuccessTip.create(housePropertyUserRoomService.updateMaster(entity));
            }

    @BusinessLog(name = "HousePropertyUserRoom", value = "delete HousePropertyUserRoom")
    @Permission(HousePropertyUserRoomPermission.HOUSEPROPERTYUSERROOM_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyUserRoom")
    public Tip deleteHousePropertyUserRoom(@PathVariable Long id){
            return SuccessTip.create(housePropertyUserRoomService.deleteMaster(id));
        }

    @Permission(HousePropertyUserRoomPermission.HOUSEPROPERTYUSERROOM_VIEW)
    @ApiOperation(value = "HousePropertyUserRoom 列表信息",response = HousePropertyUserRoomRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "userId", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "roomId", dataType = "Long") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHousePropertyUserRoomPage(Page<HousePropertyUserRoomRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "userId", required = false) Long userId,
                    
                                                                                                                                            @RequestParam(name = "roomId", required = false) Long roomId,
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

    HousePropertyUserRoomRecord record = new HousePropertyUserRoomRecord();
                                                                                                                                                                                                record.setUserId(userId);
                                                                                                                        record.setRoomId(roomId);
                        
                                

    List<HousePropertyUserRoomRecord> housePropertyUserRoomPage = queryHousePropertyUserRoomDao.findHousePropertyUserRoomPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(housePropertyUserRoomPage);

        return SuccessTip.create(page);
    }
}

