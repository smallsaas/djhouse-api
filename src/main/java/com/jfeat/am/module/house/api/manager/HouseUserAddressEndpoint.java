
package com.jfeat.am.module.house.api.manager;


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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAddressDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseUserAddressRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAddress;

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
 * @since 2022-08-01
 */
    @RestController
    @Api("HouseUserAddress")
            @RequestMapping("/api/crud/house/houseUserAddress/houseUserAddresses")
    public class HouseUserAddressEndpoint {

    @Resource
    HouseUserAddressService houseUserAddressService;

    @Resource
    QueryHouseUserAddressDao queryHouseUserAddressDao;



    @BusinessLog(name = "HouseUserAddress", value = "create HouseUserAddress")
    @Permission(HouseUserAddressPermission.HOUSEUSERADDRESS_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseUserAddress",response = HouseUserAddress.class)
    public Tip createHouseUserAddress(@RequestBody HouseUserAddress entity){
        Integer affected=0;
        try{
                affected= houseUserAddressService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HouseUserAddressPermission.HOUSEUSERADDRESS_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseUserAddress",response = HouseUserAddress.class)
    public Tip getHouseUserAddress(@PathVariable Long id){
                        return SuccessTip.create(houseUserAddressService.queryMasterModel(queryHouseUserAddressDao, id));
            }

    @BusinessLog(name = "HouseUserAddress", value = "update HouseUserAddress")
    @Permission(HouseUserAddressPermission.HOUSEUSERADDRESS_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseUserAddress",response = HouseUserAddress.class)
    public Tip updateHouseUserAddress(@PathVariable Long id,@RequestBody HouseUserAddress entity){
        entity.setId(id);
                return SuccessTip.create(houseUserAddressService.updateMaster(entity));
            }

    @BusinessLog(name = "HouseUserAddress", value = "delete HouseUserAddress")
    @Permission(HouseUserAddressPermission.HOUSEUSERADDRESS_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseUserAddress")
    public Tip deleteHouseUserAddress(@PathVariable Long id){
            return SuccessTip.create(houseUserAddressService.deleteMaster(id));
        }

    @Permission(HouseUserAddressPermission.HOUSEUSERADDRESS_VIEW)
    @ApiOperation(value = "HouseUserAddress 列表信息",response = HouseUserAddressRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "userId", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "assetId", dataType = "Long"),
                                                                                    @ApiImplicitParam(name = "community", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "buildingCode", dataType = "String"),
                                                                                    @ApiImplicitParam(name = "address", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "roomNumber", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "userName", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "userPhone", dataType = "String") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHouseUserAddressPage(Page<HouseUserAddressRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "userId", required = false) Long userId,
                    
                                                                                                                                            @RequestParam(name = "assetId", required = false) Long assetId,
                    
                                                                                                                            @RequestParam(name = "community", required = false) String community,
                    
                                                                                                                                    @RequestParam(name = "buildingCode", required = false) String buildingCode,
                    
                                                                                                                            @RequestParam(name = "address", required = false) String address,
                    
                                                                                                                                    @RequestParam(name = "roomNumber", required = false) String roomNumber,
                    
                                                                                                                                    @RequestParam(name = "userName", required = false) String userName,
                    
                                                                                                                                    @RequestParam(name = "userPhone", required = false) String userPhone,
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

    HouseUserAddressRecord record = new HouseUserAddressRecord();
                                                                                                                                                                                                record.setUserId(userId);
                                                                                                                        record.setAssetId(assetId);
                                                                                                                record.setCommunity(community);
                                                                                                                        record.setBuildingCode(buildingCode);
                                                                                                                record.setAddress(address);
                                                                                                                        record.setRoomNumber(roomNumber);
                                                                                                                        record.setUserName(userName);
                                                                                                                        record.setUserPhone(userPhone);
                        
                                

    List<HouseUserAddressRecord> houseUserAddressPage = queryHouseUserAddressDao.findHouseUserAddressPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(houseUserAddressPage);

        return SuccessTip.create(page);
    }
}

