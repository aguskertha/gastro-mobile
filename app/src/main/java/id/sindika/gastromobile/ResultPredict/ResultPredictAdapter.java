package id.sindika.gastromobile.ResultPredict;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.sindika.gastromobile.API.APIConfig;
import id.sindika.gastromobile.Food.FoodDetailActivity;
import id.sindika.gastromobile.Models.Food;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.Utils.StorageConstStatus;

public class ResultPredictAdapter extends RecyclerView.Adapter<ResultPredictAdapter.ImageViewHolder> {

    List<Food> foods;

    public ResultPredictAdapter(List<Food> foods){
        this.foods = foods;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_related_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Food food = foods.get(position);
        Glide.with(holder.imgFood.getContext())
                .load(APIConfig.BASE_IMAGE_URL+food.getPicture().get(0))
                .into(holder.imgFood);
        holder.tvName.setText(food.getName());

        holder.clFoodRelated.setOnClickListener(new View.OnClickListener() {
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

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView tvName;
        ConstraintLayout clFoodRelated;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_carousel_related);
            tvName = itemView.findViewById(R.id.tv_name_related);
            clFoodRelated = itemView.findViewById(R.id.cl_food_related);
        }

    }
}
