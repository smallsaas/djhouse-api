package com.jfeat.am.module.house.services.domain.dao;

import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-06-14
 */
public interface QueryEndpointUserDao extends QueryMasterDao<EndpointUser> {
   /*
    * Query entity list by page
    */
    List<EndpointUserRecord> findEndUserPage(Page<EndpointUserRecord> page, @Param("record") EndpointUserRecord record,
                                             @Param("tag") String tag,
                                             @Param("search") String search, @Param("orderBy") String orderBy,
                                             @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    EndpointUserModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<EndpointUserModel> queryMasterModelList(@Param("masterId") Object masterId);

    EndpointUser queryEndpointUserByOrgId(@Param("orgId") Long orgId);


    List<UserAccount> getAllUserList(Page<UserAccount> page, @Param("record") UserAccount record,
                                     @Param("tag") String tag,
                                     @Param("search") String search, @Param("orderBy") String orderBy,
                                     @Param("startTime") Date startTime, @Param("endTime") Date endTime);

 /**
z  * 统计指定id的记录条目数，用作判断指定id的用户记录是否存在
  * @param id
  * @return
  */
 Integer countUserById(@Param("id") Long id);
}