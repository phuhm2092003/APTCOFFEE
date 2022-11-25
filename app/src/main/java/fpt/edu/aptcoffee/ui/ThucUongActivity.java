package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import android.widget.Spinner;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.SpinnerAdapterLoaiHang;
import fpt.edu.aptcoffee.dao.LoaiHangDAO;

public class ThucUongActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spFill;
    LoaiHangDAO loaiHangDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_uong);
        initToolBar();
        initView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loaiHangDAO = new LoaiHangDAO(this);
        fillSpinner();
    }

    private void fillSpinner() {
        SpinnerAdapterLoaiHang adapterLoaiHang = new SpinnerAdapterLoaiHang(this, loaiHangDAO.getAll());
        spFill.setAdapter(adapterLoaiHang);
    }

    private void initView() {
        spFill = findViewById(R.id.spinnerFill);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarThuUong);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}