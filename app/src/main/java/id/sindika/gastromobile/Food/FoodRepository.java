package id.sindika.gastromobile.Food;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.LoadingDialog;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import id.sindika.gastromobile.Search.PredictListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {
    public static void predictImage(View view, PredictDTO predictDTO, PredictListener predictListener)
    {
        LoadingDialog loadingDialog = new LoadingDialog((Activity) view.getContext());
        loadingDialog.startLoadingDialog();
        Call<Food> callPredictImage = APIConfig.getApiService().predictImage(predictDTO);
        callPredictImage.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if(response.isSuccessful())
                {
                    Food food = response.body();
                    predictListener.onPredictImage(food);
                    loadingDialog.dismissLoadingDialog();
                }
                else
                {
                    loadingDialog.dismissLoadingDialog();
                    Toast.makeText(view.getContext(), "Food not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void getFoods(View view, FoodListListener foodListListener)
    {
        LoadingDialog loadingDialog = new LoadingDialog((Activity) view.getContext());
        loadingDialog.startLoadingDialog();
        Call<List<Food>> callFoods = APIConfig.getApiService().getFoods();
        callFoods.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful())
                {
                    List<Food> foods = response.body();
                    foodListListener.onGetFoods(foods);
                    loadingDialog.dismissLoadingDialog();
                }
                else
                {
                    loadingDialog.dismissLoadingDialog();
                    Toast.makeText(view.getContext(), "Foods not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void getFood(View view, FoodDetailListener foodDetailListener, String foodId, int detailCode)
    {
        LoadingDialog loadingDialog = new LoadingDialog((Activity) view.getContext());
        loadingDialog.startLoadingDialog();
        Call<Food> callFood = APIConfig.getApiService().getFood(foodId, detailCode);
        callFood.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if(response.isSuccessful())
                {
                    Food food = response.body();
                    foodDetailListener.onGetFood(food);
                    loadingDialog.dismissLoadingDialog();
                }
                else
                {
                    loadingDialog.dismissLoadingDialog();
                    Toast.makeText(view.getContext(), "Food not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
