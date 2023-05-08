package id.sindika.gastromobile.Food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import id.sindika.gastromobile.MainActivity;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.Utils.StorageConstStatus;
import id.sindika.gastromobile.databinding.ActivityFoodDetailMakeBinding;
import id.sindika.gastromobile.databinding.ActivityFoodDetailNutritionBinding;

public class FoodDetailNutritionActivity extends AppCompatActivity implements FoodDetailListener {

    private ActivityFoodDetailNutritionBinding binding;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailNutritionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();

        receiveData();

        if(foodId.isEmpty())
        {
            Toast.makeText(binding.getRoot().getContext(), "FoodId not found!", Toast.LENGTH_LONG).show();
            ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
            Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
            intent.putExtra(StorageConstStatus.EXTRA_NAVIGATION, StorageConstStatus.EXTRA_SEARCH_FRAGMENT);
            startActivity(intent, options.toBundle());
        }
        else
        {
            FoodRepository.getFood(binding.getRoot(), this::onGetFood, this.foodId, 3);
        }
    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbarFoodDetailNutrition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void receiveData(){
        this.foodId = getIntent().getStringExtra(StorageConstStatus.EXTRA_FOOD_ID);
    }

    @Override
    public void onGetFood(Food food) {
        MakeAdapter makeAdapter = new MakeAdapter(food.getNutritions());
        binding.rvFoodNutritions.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
        binding.rvFoodNutritions.setAdapter(makeAdapter);

        binding.flNextDetailNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
    }
}