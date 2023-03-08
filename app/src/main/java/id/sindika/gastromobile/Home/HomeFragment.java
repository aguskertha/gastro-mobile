package id.sindika.gastromobile.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.sindika.gastromobile.Food.FoodListFragment;
import id.sindika.gastromobile.R;
import id.sindika.gastromobile.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private AppCompatActivity activity;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = ((AppCompatActivity)getActivity());
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, new FoodListFragment())
                        .commit();
            }
        });

        return view;
    }
}