package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;

/**
 * Created by Code generator on 2023-01-05
 */
public class HouseAssetTransactionRecord extends HouseAssetTransaction{

    // 是否已关注
    private Boolean existsFollow;

    // 关注数
    private Integer followNumber;

    // 社区，该社区信息从发表的用户中获取
    private Long community;

    public Long getCommunity() {
        return community;
    }
    public void setCommunity(Long community) {
        this.community = community;
    }

    public Integer getFollowNumber() {
        return followNumber;
    }
    public void setFollowNumber(Integer followNumber) {
        this.followNumber = followNumber;
    }

    public Boolean getExistsFollow() {
       return this.existsFollow;
    }
    public void setExistsFollow(Boolean isFollow) {
        this.existsFollow = isFollow;
    }
    
    }
