package fpt.edu.aptcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpt.edu.aptcoffee.adapter.ViewPagerMainAdapter;


public class MainActivity extends AppCompatActivity {
    ViewPager2 vp2Main;
    BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager2Main();

        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // fragment Home
                        vp2Main.setCurrentItem(0, false);
                        break;
                    case R.id.menu_search:
                        // fragment Search
                        vp2Main.setCurrentItem(1, false);
                        break;
                    case R.id.menu_notification:
                        // fragment Messenger
                        vp2Main.setCurrentItem(2, false);
                        break;
                    case R.id.menu_setting:
                        // fragment Setting
                        vp2Main.setCurrentItem(3, false);
                        break;
                }
                return true;
            }
        });
    }

    private void initView() {
        bnvMain = findViewById(R.id.bnvMain);
    }

    private void initViewPager2Main() {
        ViewPagerMainAdapter adapter = new ViewPagerMainAdapter(this);
        vp2Main = findViewById(R.id.viewPager2Main);
        vp2Main.setUserInputEnabled(false);
        vp2Main.setAdapter(adapter);
    }
}