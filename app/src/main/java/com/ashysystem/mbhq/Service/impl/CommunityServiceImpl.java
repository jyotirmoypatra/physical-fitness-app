package com.ashysystem.mbhq.Service.impl;

import android.content.Context;

import com.ashysystem.mbhq.Service.interfaces.CommunityService;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;

public class CommunityServiceImpl extends ServiceCommunity{

    private CommunityService communityService;
    public CommunityServiceImpl(Context context) {
        super(context);
        this.communityService=super.getRetrofit().create(CommunityService.class);
    }

    public Call<JsonObject> communityLoginApi(HashMap<String, Object> modelHashMap)
    {
        return communityService.communityLoginApi(modelHashMap);
    }

    public Call<JsonObject> createAccountCommunityApi(HashMap<String, Object> modelHashMap)
    {
        return communityService.createAccountCommunityApi(modelHashMap);
    }

    public Call<JsonObject> communityUpdateUserApi(HashMap<String, Object> modelHashMap,String access_token)
    {
        return communityService.communityUpdateUserApi(modelHashMap,access_token);
    }

    public Call<JsonObject> communityNotificationCount(HashMap<String, Object> modelHashMap,String access_token)
    {
        return communityService.communityNotificationCount(modelHashMap,access_token);
    }

}
