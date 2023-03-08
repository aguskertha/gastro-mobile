package id.sindika.gastromobile.Food;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.databinding.FragmentFoodListBinding;

public class FoodListFragment extends Fragment implements FoodListListener{

    private AppCompatActivity activity;
    private FragmentFoodListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = ((AppCompatActivity)getActivity());
        binding = FragmentFoodListBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

//        setToolbar();
        FoodRepository.getFoods(binding.getRoot(), this::onGetFoods);

        return view;
    }
//    private void setToolbar(){
//        activity.setSupportActionBar(binding.toolbarFoodList);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

    @Override
    public void onGetFoods(List<Food> foods) {
        binding.toolbarFoodList.setSubtitle(foods.size()+" food found");
        RecyclerView rvFoodList = activity.findViewById(R.id.rv_food_list);
        FoodAdapter foodAdapter = new FoodAdapter(foods);
        rvFoodList.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(),2, GridLayoutManager.VERTICAL, false));
        rvFoodList.setAdapter(foodAdapter);
    }
}