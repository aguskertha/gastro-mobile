package id.sindika.gastromobile.Food;

import java.util.List;

import id.sindika.gastromobile.Models.Food;

public interface FoodListListener {
    public void onGetFoods(List<Food> foods);
}
