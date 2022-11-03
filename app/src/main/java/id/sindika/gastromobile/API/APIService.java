package id.sindika.gastromobile.API;

import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("food/query")
    Call<Food> predictImage(@Body PredictDTO predictDTO);

}
