package id.sindika.gastromobile.ResultPredict;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.LoadingDialog;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.Models.Request.MultipleFoodsDTO;
import id.sindika.gastromobile.Models.Request.PredictDTO;
import id.sindika.gastromobile.Search.PredictListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPredictRepository {

    public static void getMultipleFoods(View view, MultipleFoodsDTO multipleFoodsDTO, MultipleFoodsListener multipleFoodsListener)
    {
        LoadingDialog loadingDialog = new LoadingDialog((Activity) view.getContext());
        loadingDialog.startLoadingDialog();
        Call<List<Food>> callMultipleFoods = APIConfig.getApiService().getMultipleFoods(multipleFoodsDTO);
        callMultipleFoods.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful())
                {
                    List<Food> foods = response.body();
                    multipleFoodsListener.onMultipleFoods(foods);
                    loadingDialog.dismissLoadingDialog();
                }
                else
                {
                    loadingDialog.dismissLoadingDialog();
                    Toast.makeText(view.getContext(), "Food not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(view.getContext(), "FAIL API: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
