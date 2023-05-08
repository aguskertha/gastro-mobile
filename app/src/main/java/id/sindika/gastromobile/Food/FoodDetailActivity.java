package id.sindika.gastromobile.Food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import id.sindika.gastromobile.databinding.ActivityFoodDetailBinding;
import id.sindika.gastromobile.databinding.ActivityResultPredictBinding;

public class FoodDetailActivity extends AppCompatActivity implements FoodDetailListener{

    private ActivityFoodDetailBinding binding;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
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
            FoodRepository.getFood(binding.getRoot(), this::onGetFood, this.foodId, 1);
        }
    }

    private void setToolbar(){
        setSupportActionBar(binding.toolbarFoodDetail);
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
        binding.tvDetailName.setText(food.getName());
        binding.tvDetailDescription.setText(food.getDescription());
        binding.tvDetailHistory.setText(food.getHistory());
        binding.tvDetailCulture.setText(food.getCulture());

        ImageAdapter imageAdapter = new ImageAdapter(food.getPicture());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 1, GridLayoutManager.HORIZONTAL, false);
        binding.imageRecyclerView.setLayoutManager(gridLayoutManager);
        binding.imageRecyclerView.setAdapter(imageAdapter);

        binding.flNextDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                Intent intent = new Intent(view.getContext(), FoodDetailMakeActivity.class);
                intent.putExtra(StorageConstStatus.EXTRA_FOOD_ID, food.getId());
                view.getContext().startActivity(intent, options.toBundle());
            }
        });

    }
}