package com.ashysystem.mbhq.Service.interfaces;
import com.ashysystem.mbhq.model.AddRewardModel;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by android-krishnendu on 9/27/16.
 */

public interface SessionService
{
    @POST("api/user/AddSquadUserAction")
    Call<AddRewardModel> AddRewardApi(@Body HashMap<String, Object> modelHashMap);

}
