package id.sindika.gastromobile.Food;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.MainActivity;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.Utils.StorageConstStatus;
import id.sindika.gastromobile.databinding.ActivityFoodDetailBinding;
import id.sindika.gastromobile.databinding.ActivityFoodDetailMakeBinding;

public class FoodDetailMakeActivity extends YouTubeBaseActivity implements FoodDetailListener, AppCompatCallback {

    private ActivityFoodDetailMakeBinding binding;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailMakeBinding.inflate(getLayoutInflater());

        AppCompatDelegate delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(binding.getRoot());

        delegate.setSupportActionBar(binding.toolbarFoodDetailMake);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiveData();
        fillData();

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
            FoodRepository.getFood(binding.getRoot(), this::onGetFood, this.foodId, 2);
        }
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

    private void fillData(){
//        binding.imgBtnNextDetailMake.setImageResource(R.drawable.ic_arrow_fat_lines_right);
        binding.imgUnderlineHowtomake.setImageResource(R.drawable.underline);
        binding.imgUnderlineIngredient.setImageResource(R.drawable.underline);
    }

    @Override
    public void onGetFood(Food food) {
        IngredientAdapter ingredientAdapter = new IngredientAdapter(food.getIngredients());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 1, GridLayoutManager.HORIZONTAL, false);
        binding.rvFoodIngredients.setLayoutManager(gridLayoutManager);
        binding.rvFoodIngredients.setAdapter(ingredientAdapter);

        MakeAdapter makeAdapter = new MakeAdapter(food.getHowToMakes());
        binding.rvFoodMakes.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false));
        binding.rvFoodMakes.setAdapter(makeAdapter);

        binding.flNextDetailMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(binding.getRoot().getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                Intent intent = new Intent(view.getContext(), FoodDetailNutritionActivity.class);
                intent.putExtra(StorageConstStatus.EXTRA_FOOD_ID, food.getId());
                view.getContext().startActivity(intent, options.toBundle());
            }
        });

        String[] separated = food.getLink().split("v=");
        String videoId = separated[separated.length-1];


        binding.youtubeMakes.initialize("AIzaSyBynrbhEPKOsdx-6tyGKlj0N3iCma03wwk", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "FAIL INIT", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}