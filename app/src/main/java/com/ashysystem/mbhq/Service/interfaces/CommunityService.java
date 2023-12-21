package com.ashysystem.mbhq.Service.interfaces;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommunityService {

    @POST("api/auth")
    @FormUrlEncoded
    Call<JsonObject> communityLoginApi(@FieldMap Map<String, Object> params);

    @POST("api/create-account")
    @FormUrlEncoded
    Call<JsonObject> createAccountCommunityApi(@FieldMap Map<String, Object> params);

    @POST("api/get-general-data")
    @FormUrlEncoded
    Call<JsonObject> communityNotificationCount(@FieldMap Map<String, Object> params,@Query("access_token") String access_token);

    @POST("api/update-user-data")
    @FormUrlEncoded
    Call<JsonObject> communityUpdateUserApi(@FieldMap Map<String, Object> params,@Query("access_token") String access_token);
}
