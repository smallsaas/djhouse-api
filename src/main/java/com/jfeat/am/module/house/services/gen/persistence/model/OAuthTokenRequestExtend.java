package com.jfeat.am.module.house.services.gen.persistence.model;

import com.jfeat.users.weChatMiniprogram.services.domain.model.wx.OAuthTokenRequest;

public class OAuthTokenRequestExtend extends OAuthTokenRequest {
    private Long communityId;

    private Boolean isChangeCommunity;


    public Boolean getChangeCommunity() {
        return isChangeCommunity;
    }

    public void setChangeCommunity(Boolean changeCommunity) {
        isChangeCommunity = changeCommunity;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
