package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfeat.am.module.house.services.gen.persistence.model.OAuthTokenRequestExtend;

public interface UserRegisterService {

    JSONObject register(OAuthTokenRequestExtend token);
}
