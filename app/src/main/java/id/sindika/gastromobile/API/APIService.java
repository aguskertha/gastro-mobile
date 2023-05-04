package id.sindika.gastromobile.API;

import java.util.List;

import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.MultipleFoodsDTO;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("food/query")
    Call<Food> predictImage(@Body PredictDTO predictDTO);

    @POST("food/ids")
    Call<List<Food>> getMultipleFoods(@Body MultipleFoodsDTO multipleFoodsDTO);

    @GET("food")
    Call<List<Food>> getFoods();

    @GET("food/{foodId}")
    Call<Food> getFood(@Path("foodId") String foodId, @Query("detail") int detailCode);

}
