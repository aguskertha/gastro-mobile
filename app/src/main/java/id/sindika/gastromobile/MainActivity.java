package id.sindika.gastromobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.sindika.gastromobile.Home.HomeFragment;
import id.sindika.gastromobile.Search.SearchFragment;
import id.sindika.gastromobile.Utils.StorageConstStatus;
import id.sindika.gastromobile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Fragment fragment;
    private static int currentMenuID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentMenuID = R.id.bottom_menu_home;
        binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_home);
        binding.buttomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.bottom_menu_home:
                        currentMenuID = R.id.bottom_menu_home;
                        fragment = new HomeFragment();
                        break;
                    case R.id.bottom_menu_search:
                        currentMenuID = R.id.bottom_menu_search;
                        fragment = new SearchFragment();
                        break;

                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .commit();
                return true;
            }
        });
        if(this.fragment == null){
            this.fragment = new HomeFragment();
        }

        if(getIntent().getStringExtra(StorageConstStatus.EXTRA_NAVIGATION) != null)
        {
            String dataIntent = getIntent().getStringExtra(StorageConstStatus.EXTRA_NAVIGATION);

            if(dataIntent.equals(StorageConstStatus.EXTRA_SEARCH_FRAGMENT))
            {
                fragment = new SearchFragment();
                binding.buttomNavigation.setSelectedItemId(R.id.bottom_menu_search);
            }
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();

    }



}