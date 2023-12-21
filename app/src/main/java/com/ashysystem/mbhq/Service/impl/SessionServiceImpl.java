package com.ashysystem.mbhq.Service.impl;

import android.content.Context;

import com.ashysystem.mbhq.Service.interfaces.SessionService;
import com.ashysystem.mbhq.model.AddRewardModel;
import java.util.HashMap;
import retrofit2.Call;


/**
 * Created by android-krishnendu on 9/27/16.
 */

public class SessionServiceImpl extends ServiceSessionMain {

    private SessionService sessionService;
    public SessionServiceImpl(Context context) {
        super(context);
        this.sessionService=super.getRetrofit().create(SessionService.class);
    }

    public Call<AddRewardModel> addRewardApi(HashMap<String, Object> hashReq) {
        return sessionService.AddRewardApi(hashReq);
    }

}
