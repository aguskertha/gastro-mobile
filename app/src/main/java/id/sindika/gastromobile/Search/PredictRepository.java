package id.sindika.gastromobile.Search;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.Food.FoodListListener;
import id.sindika.gastromobile.LoadingDialog;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictRepository {
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


}
