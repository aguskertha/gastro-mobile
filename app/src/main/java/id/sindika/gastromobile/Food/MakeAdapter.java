package id.sindika.gastromobile.Food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.sindika.gastromobile.R;

public class MakeAdapter extends RecyclerView.Adapter<MakeAdapter.MakeViewHolder> {

    List<String> ingredientItems;

    public MakeAdapter(List<String> ingredientItems){
        this.ingredientItems = ingredientItems;
    }

    @NonNull
    @Override
    public MakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_make_item,parent,false);
        return new MakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakeViewHolder holder, int position) {
        String item = ingredientItems.get(position);
        holder.tvItem.setText(item);
        holder.tvNumber.setText(position+1+". ");

    }

    @Override
    public int getItemCount() {
        return ingredientItems.size();
    }

    public class MakeViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        TextView tvNumber;

        public MakeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_ingredient_item);
            tvNumber = itemView.findViewById(R.id.tv_make_number);
        }
    }
}
