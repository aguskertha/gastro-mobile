package id.sindika.gastromobile.ResultPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.Food.FoodDetailActivity;
import id.sindika.gastromobile.MainActivity;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.Utils.StorageConstStatus;
import id.sindika.gastromobile.databinding.ActivityMainBinding;
import id.sindika.gastromobile.databinding.ActivityResultPredictBinding;
import id.sindika.gastromobile.databinding.FragmentSearchBinding;

public class ResultPredictActivity extends AppCompatActivity {

    private ActivityResultPredictBinding binding;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultPredictBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();
        receiveData();

        if(food == null)
        {
            Toast.makeText(binding.getRoot().getContext(), "Food not found!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
            intent.putExtra(StorageConstStatus.EXTRA_NAVIGATION, StorageConstStatus.EXTRA_SEARCH_FRAGMENT);
            startActivity(intent);
        }

        fillData();

        binding.btnStartGastronomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(binding.getRoot().getContext(), FoodDetailActivity.class);
                intent.putExtra(StorageConstStatus.EXTRA_FOOD_ID, food.getId());
                startActivity(intent);
            }
        });

    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbarResultFood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void receiveData(){
        this.food = getIntent().getParcelableExtra(StorageConstStatus.EXTRA_FOOD);
    }

    private void fillData(){
        Glide.with(binding.getRoot().getContext())
                .load(APIConfig.BASE_IMAGE_URL+food.getPicture().get(0))
                .centerCrop()
                .into(binding.imgResultPredict);
        binding.tvResultFoodName.setText(food.getName());
        binding.tvResultFoodDescription.setText(food.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button press
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}