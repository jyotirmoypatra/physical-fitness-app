package com.ashysystem.mbhq.Service.interfaces;
import com.ashysystem.mbhq.model.AddRewardModel;
import com.ashysystem.mbhq.model.ShowIndividualVideoModel;

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
    @POST("api/exercise/GetExercise")
    Call<ShowIndividualVideoModel> getIndividualVideo(@Body HashMap<String, Object> modelHashMap);

}
