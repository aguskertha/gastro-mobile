package id.sindika.gastromobile.Food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.sindika.gastromobile.R;

public class IngredientItemAdapter extends RecyclerView.Adapter<IngredientItemAdapter.IngredientItemViewHolder> {

    List<String> ingredientItems;

    public IngredientItemAdapter(List<String> ingredientItems){
        this.ingredientItems = ingredientItems;
    }

    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_ingredient_item,parent,false);
        return new IngredientItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        String item = ingredientItems.get(position);
        holder.tvItem.setText(item);

    }

    @Override
    public int getItemCount() {
        return ingredientItems.size();
    }

    public class IngredientItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public IngredientItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_ingredient_item);
        }
    }
}
