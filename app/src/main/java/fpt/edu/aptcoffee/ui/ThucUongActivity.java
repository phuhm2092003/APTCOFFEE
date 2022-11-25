package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.SpinnerAdapterLoaiHang;
import fpt.edu.aptcoffee.adapter.ThucUongAdapter;
import fpt.edu.aptcoffee.dao.HangHoaDAO;
import fpt.edu.aptcoffee.dao.LoaiHangDAO;
import fpt.edu.aptcoffee.model.HangHoa;
import fpt.edu.aptcoffee.model.LoaiHang;

public class ThucUongActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spFill;
    LoaiHangDAO loaiHangDAO;
    HangHoaDAO hangHoaDAO;
    RecyclerView recyclerViewThucUong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_uong);
        initToolBar();
        initView();
        loaiHangDAO = new LoaiHangDAO(this);
        hangHoaDAO = new HangHoaDAO(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fillSpinner();
        loadData();

        spFill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    loadData();
                }else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThucUongActivity.this, RecyclerView.VERTICAL, false);
                    recyclerViewThucUong.setLayoutManager(linearLayoutManager);

                    ThucUongAdapter thucUongAdapter = new ThucUongAdapter(hangHoaDAO.getByMaLoai(String.valueOf(position)));
                    recyclerViewThucUong.setAdapter(thucUongAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        recyclerViewThucUong = findViewById(R.id.recyclerViewThucUong);
        spFill = findViewById(R.id.spinnerFill);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarThuUong);
        setSupportActionBar(toolbar);
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewThucUong.setLayoutManager(linearLayoutManager);

        ThucUongAdapter thucUongAdapter = new ThucUongAdapter(hangHoaDAO.getAll());
        recyclerViewThucUong.setAdapter(thucUongAdapter);
    }

    private void fillSpinner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Tất cả");
        ArrayList<LoaiHang> loaiHangsar = loaiHangDAO.getAll();
        for (int i = 0; i < loaiHangDAO.getAll().size(); i++) {
            list.add(loaiHangsar.get(i).getTenLoai());
        }
        SpinnerAdapterLoaiHang adapterLoaiHang = new SpinnerAdapterLoaiHang(this, list);
        spFill.setAdapter(adapterLoaiHang);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}