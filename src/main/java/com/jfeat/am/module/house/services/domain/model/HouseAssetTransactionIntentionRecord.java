package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransactionIntention;

import java.util.List;

/**
 * @description: HouseAssetTransactionIntention封装类
 * 使用mybatis的resultMap进行复杂映射的时候可以将属性添加在这里，而不影响原有的实体类
 *
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/1 12:14
 * @author: hhhhhtao
 */
public class HouseAssetTransactionIntentionRecord extends HouseAssetTransactionIntention {

    // 用户列表
    private List<EndpointUser> userList;

    public List<EndpointUser> getUserList() {
        return userList;
    }

    public void setUserList(List<EndpointUser> userList) {
        this.userList = userList;
    }
}
