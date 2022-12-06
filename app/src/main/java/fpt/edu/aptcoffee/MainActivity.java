package fpt.edu.aptcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import fpt.edu.aptcoffee.adapter.ViewPagerMainAdapter;
import fpt.edu.aptcoffee.dao.ThongBaoDAO;
import fpt.edu.aptcoffee.model.ThongBao;
import fpt.edu.aptcoffee.ui.SignInActivity;


public class MainActivity extends AppCompatActivity {
    private String keyUser = "";
    ViewPager2 vp2Main;
    BottomNavigationView bnvMain;
    View iconNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager2Main();
        setKeyUser();
        showIconThongBao();

        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        // Open fragment Home
                        vp2Main.setCurrentItem(0, false);
                        break;
                    case R.id.menu_search:
                        // Open fragment Search
                        vp2Main.setCurrentItem(1, false);
                        break;
                    case R.id.menu_notification:
                        // Open fragment Messenger
                        vp2Main.setCurrentItem(2, false);
                        break;
                    case R.id.menu_setting:
                        // Open fragment Setting
                        vp2Main.setCurrentItem(3, false);
                        break;
                }
                checkStatusNotification();
                return true;
            }
        });

    }

    private void initView() {
        bnvMain = findViewById(R.id.bnvMain);
        vp2Main = findViewById(R.id.viewPager2Main);
    }

    private void initViewPager2Main() {
        ViewPagerMainAdapter adapter = new ViewPagerMainAdapter(this);

        vp2Main.setUserInputEnabled(false);
        vp2Main.setOffscreenPageLimit(3);
        vp2Main.setAdapter(adapter);
    }

    private void showIconThongBao() {
        BottomNavigationItemView itemView = bnvMain.findViewById(R.id.menu_notification);
        iconNotification = getLayoutInflater().inflate(R.layout.layout_ic_thongbao, bnvMain, false);
        checkStatusNotification();

        itemView.addView(iconNotification);
    }

    private void checkStatusNotification() {
        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(this);
        ArrayList<ThongBao> listNotification = thongBaoDAO.getByTrangThaiChuaXem();

        if (listNotification.size() == 0) {
            iconNotification.setVisibility(View.GONE); // Ẩn thông báo
        } else {
            iconNotification.setVisibility(View.VISIBLE); // Hiện thông báo
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        checkStatusNotification();
    }
}