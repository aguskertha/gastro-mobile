package id.sindika.gastromobile.Food;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.Utils.StorageConstStatus;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    List<Food> foods;

    public FoodAdapter(List<Food> foods){
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_food,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        Glide.with(holder.imgFood.getContext())
                .load(APIConfig.BASE_IMAGE_URL+food.getPicture().get(0))
                .into(holder.imgFood);
        holder.tvName.setText(food.getName());
        holder.tvDescription.setText(food.getDescription());
        holder.btnFoodDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.slide_in_left, R.anim.slide_out_right);
                Intent intent = new Intent(view.getContext(), FoodDetailActivity.class);
                intent.putExtra(StorageConstStatus.EXTRA_FOOD_ID, food.getId());
                view.getContext().startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView tvName;
        TextView tvDescription;
        Button btnFoodDetail;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvName = itemView.findViewById(R.id.tv_food_name);
            tvDescription = itemView.findViewById(R.id.tv_food_description);
            btnFoodDetail = itemView.findViewById(R.id.btn_food_detail);
        }
    }
}
