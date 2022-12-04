package fpt.edu.aptcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpt.edu.aptcoffee.adapter.ViewPagerMainAdapter;
import fpt.edu.aptcoffee.ui.SignInActivity;


public class MainActivity extends AppCompatActivity {
    private String keyUser = "";
    ViewPager2 vp2Main;
    BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager2Main();
        setKeyUser();

        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // Open fragment Home
                        vp2Main.setCurrentItem(0, false);
                        bnvMain.getMenu().findItem(R.id.menu_home).setIcon(R.drawable.ic_home_fill_24);
                        bnvMain.getMenu().findItem(R.id.menu_setting).setIcon(R.drawable.ic_settings_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_notification).setIcon(R.drawable.ic_notification_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_search).setIcon(R.drawable.ic_search_24);
                        break;
                    case R.id.menu_search:
                        // Open fragment Search
                        vp2Main.setCurrentItem(1, false);
                        bnvMain.getMenu().findItem(R.id.menu_home).setIcon(R.drawable.ic_home_5_line);
                        bnvMain.getMenu().findItem(R.id.menu_setting).setIcon(R.drawable.ic_settings_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_notification).setIcon(R.drawable.ic_notification_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_search).setIcon(R.drawable.ic_search_24);
                        break;
                    case R.id.menu_notification:
                        // Open fragment Messenger
                        vp2Main.setCurrentItem(2, false);
                        bnvMain.getMenu().findItem(R.id.menu_home).setIcon(R.drawable.ic_home_5_line);
                        bnvMain.getMenu().findItem(R.id.menu_setting).setIcon(R.drawable.ic_settings_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_notification).setIcon(R.drawable.ic_notification);
                        bnvMain.getMenu().findItem(R.id.menu_search).setIcon(R.drawable.ic_search_24);
                        break;
                    case R.id.menu_setting:
                        // Open fragment Setting
                        vp2Main.setCurrentItem(3, false);
                        bnvMain.getMenu().findItem(R.id.menu_home).setIcon(R.drawable.ic_home_5_line);
                        bnvMain.getMenu().findItem(R.id.menu_setting).setIcon(R.drawable.ic_setting_fill_24);
                        bnvMain.getMenu().findItem(R.id.menu_notification).setIcon(R.drawable.ic_notification_3_line);
                        bnvMain.getMenu().findItem(R.id.menu_search).setIcon(R.drawable.ic_search_24);
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
        vp2Main.setOffscreenPageLimit(3);
        vp2Main.setAdapter(adapter);
    }

    private void setKeyUser() {
        Intent intent = this.getIntent();
        keyUser = intent.getStringExtra(SignInActivity.KEY_USER);
    }

    public String getKeyUser() {
        return keyUser;
    }

    @Override
    public void onBackPressed() {
        // Open fragment Home
        vp2Main.setCurrentItem(0, false);
        bnvMain.setSelectedItemId(R.id.menu_home);
    }
}