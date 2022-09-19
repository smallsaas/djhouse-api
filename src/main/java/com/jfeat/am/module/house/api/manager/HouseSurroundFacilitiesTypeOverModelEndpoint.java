
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSurroundFacilitiesTypeDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesTypeRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSurroundFacilitiesTypeModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilitiesType;

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
 * @since 2022-08-05
 */
    @RestController
    @Api("HouseSurroundFacilitiesType")
            @RequestMapping("/api/crud/house/houseSurroundFacilitiesType/houseSurroundFacilitiesTypes")
    public class HouseSurroundFacilitiesTypeOverModelEndpoint {

    @Resource
    HouseSurroundFacilitiesTypeOverModelService houseSurroundFacilitiesTypeOverModelService;

    @Resource
    QueryHouseSurroundFacilitiesTypeDao queryHouseSurroundFacilitiesTypeDao;


    // 要查询[从表]关联数据，取消下行注释
    // @Resource
    // QueryHouseSurroundFacilitiesDao queryHouseSurroundFacilitiesDao;

    @BusinessLog(name = "HouseSurroundFacilitiesType", value = "create HouseSurroundFacilitiesType")
    @Permission(HouseSurroundFacilitiesTypePermission.HOUSESURROUNDFACILITIESTYPE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseSurroundFacilitiesType",response = HouseSurroundFacilitiesTypeModel.class)
    public Tip createHouseSurroundFacilitiesType(@RequestBody HouseSurroundFacilitiesTypeModel entity){
        Integer affected=0;
        try{
                DefaultFilterResult filterResult = new DefaultFilterResult();
            affected= houseSurroundFacilitiesTypeOverModelService.createMaster(entity,filterResult,null,null);
            if(affected>0){
               return SuccessTip.create(filterResult.result());
            }
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @BusinessLog(name = "HouseSurroundFacilitiesType", value = "查看 HouseSurroundFacilitiesTypeModel")
    @Permission(HouseSurroundFacilitiesTypePermission.HOUSESURROUNDFACILITIESTYPE_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseSurroundFacilitiesType",response = HouseSurroundFacilitiesTypeModel.class)
    public Tip getHouseSurroundFacilitiesType(@PathVariable Long id){
    CRUDObject<HouseSurroundFacilitiesTypeModel> entity = houseSurroundFacilitiesTypeOverModelService
            .registerQueryMasterDao(queryHouseSurroundFacilitiesTypeDao)
            // 要查询[从表]关联数据，取消下行注释
            //.registerQuerySlaveModelListDao(HouseSurroundFacilities.class, queryHouseSurroundFacilitiesDao)
            .retrieveMaster(id,null,null,null);

            // sample query for registerQueryMasterDao
            // e.g. <select id="queryMasterModel" resultType="PlanModel">
            //       SELECT t_plan_model.*, t_org.name as orgName
            //       FROM t_plan_model
            //       LEFT JOIN t_org ON t_org.id==t_plan_model.org_id
            //       WHERE t_plan_model.id=#{id}
            //       GROUP BY t_plan_model.id
            //    </select>

            if(entity != null) {
                return SuccessTip.create(entity.toJSONObject());
            } else {
                return SuccessTip.create();
            }

            }

    @BusinessLog(name = "HouseSurroundFacilitiesType", value = "update HouseSurroundFacilitiesType")
    @Permission(HouseSurroundFacilitiesTypePermission.HOUSESURROUNDFACILITIESTYPE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseSurroundFacilitiesType",response = HouseSurroundFacilitiesTypeModel.class)
    public Tip updateHouseSurroundFacilitiesType(@PathVariable Long id,@RequestBody HouseSurroundFacilitiesTypeModel entity){
        entity.setId(id);
                // use update flags
            int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;  //default to delete not exist items
            // newOptions = FlagUtil.setFlag(newOptions, META.UPDATE_ALL_COLUMNS_FLAG);

            return SuccessTip.create(houseSurroundFacilitiesTypeOverModelService.updateMaster(entity,null,null,null, newOptions));
            }

    @BusinessLog(name = "HouseSurroundFacilitiesType", value = "delete HouseSurroundFacilitiesType")
    @Permission(HouseSurroundFacilitiesTypePermission.HOUSESURROUNDFACILITIESTYPE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseSurroundFacilitiesType")
    public Tip deleteHouseSurroundFacilitiesType(@PathVariable Long id){
            return SuccessTip.create(houseSurroundFacilitiesTypeOverModelService.deleteMaster(id));
        }

    @Permission(HouseSurroundFacilitiesTypePermission.HOUSESURROUNDFACILITIESTYPE_VIEW)
    @ApiOperation(value = "HouseSurroundFacilitiesType 列表信息",response = HouseSurroundFacilitiesTypeRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "communityId", dataType = "Long"),
                                                                                            @ApiImplicitParam(name = "enName", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "cnName", dataType = "String"),
                                                                                    @ApiImplicitParam(name = "icon", dataType = "String") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHouseSurroundFacilitiesTypePage(Page<HouseSurroundFacilitiesTypeRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "communityId", required = false) Long communityId,
                    
                                                                                                                                    @RequestParam(name = "enName", required = false) String enName,
                    
                                                                                                                                    @RequestParam(name = "cnName", required = false) String cnName,
                    
                                                                                                                            @RequestParam(name = "icon", required = false) String icon,
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

    HouseSurroundFacilitiesTypeRecord record = new HouseSurroundFacilitiesTypeRecord();
                                                                                                                                                                                                record.setCommunityId(communityId);
                                                                                                                        record.setEnName(enName);
                                                                                                                        record.setCnName(cnName);
                                                                                                                record.setIcon(icon);
                        
                                

    List<HouseSurroundFacilitiesTypeRecord> houseSurroundFacilitiesTypePage = queryHouseSurroundFacilitiesTypeDao.findHouseSurroundFacilitiesTypePage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(houseSurroundFacilitiesTypePage);

        return SuccessTip.create(page);
    }
}

