
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentTagDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseRentTagRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentTag;

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
 * @since 2022-06-27
 */
    @RestController
    @Api("HouseRentTag")
            @RequestMapping("/api/crud/house/houseRentTag/houseRentTags")
    public class HouseRentTagEndpoint {

    @Resource
    HouseRentTagService houseRentTagService;

    @Resource
    QueryHouseRentTagDao queryHouseRentTagDao;



    @BusinessLog(name = "HouseRentTag", value = "create HouseRentTag")
    @Permission(HouseRentTagPermission.HOUSERENTTAG_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseRentTag",response = HouseRentTag.class)
    public Tip createHouseRentTag(@RequestBody HouseRentTag entity){
        Integer affected=0;
        try{
                affected= houseRentTagService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HouseRentTagPermission.HOUSERENTTAG_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseRentTag",response = HouseRentTag.class)
    public Tip getHouseRentTag(@PathVariable Long id){
                        return SuccessTip.create(houseRentTagService.queryMasterModel(queryHouseRentTagDao, id));
            }

    @BusinessLog(name = "HouseRentTag", value = "update HouseRentTag")
    @Permission(HouseRentTagPermission.HOUSERENTTAG_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseRentTag",response = HouseRentTag.class)
    public Tip updateHouseRentTag(@PathVariable Long id,@RequestBody HouseRentTag entity){
        entity.setId(id);
                return SuccessTip.create(houseRentTagService.updateMaster(entity));
            }

    @BusinessLog(name = "HouseRentTag", value = "delete HouseRentTag")
    @Permission(HouseRentTagPermission.HOUSERENTTAG_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseRentTag")
    public Tip deleteHouseRentTag(@PathVariable Long id){
            return SuccessTip.create(houseRentTagService.deleteMaster(id));
        }

    @Permission(HouseRentTagPermission.HOUSERENTTAG_VIEW)
    @ApiOperation(value = "HouseRentTag 列表信息",response = HouseRentTagRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                            @ApiImplicitParam(name = "cnName", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "enName", dataType = "String") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHouseRentTagPage(Page<HouseRentTagRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                    @RequestParam(name = "cnName", required = false) String cnName,
                    
                                                                                                                                    @RequestParam(name = "enName", required = false) String enName,
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

    HouseRentTagRecord record = new HouseRentTagRecord();
                                                                                                                                                                                                record.setCnName(cnName);
                                                                                                                        record.setEnName(enName);
                        
                                

    List<HouseRentTagRecord> houseRentTagPage = queryHouseRentTagDao.findHouseRentTagPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(houseRentTagPage);

        return SuccessTip.create(page);
    }
}

