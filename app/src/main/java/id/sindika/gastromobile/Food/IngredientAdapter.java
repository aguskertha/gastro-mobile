package id.sindika.gastromobile.Food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.sindika.gastromobile.Models.Ingredient;
import id.sindika.gastromobile.R;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredient,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.tvName.setText(ingredient.getName());
        IngredientItemAdapter ingredientItemAdapter = new IngredientItemAdapter(ingredient.getItems());
        holder.rvIngredient.setLayoutManager(new GridLayoutManager(holder.rvIngredient.getContext(), 1, GridLayoutManager.VERTICAL, false));
        holder.rvIngredient.setAdapter(ingredientItemAdapter);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        RecyclerView rvIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_ingredient_name);
            rvIngredient = itemView.findViewById(R.id.rv_ingredient_items);
        }
    }
}
