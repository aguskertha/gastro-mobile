package id.sindika.gastromobile.API;

import java.util.List;

import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("food/query")
    Call<Food> predictImage(@Body PredictDTO predictDTO);

    @GET("food")
    Call<List<Food>> getFoods();

}
