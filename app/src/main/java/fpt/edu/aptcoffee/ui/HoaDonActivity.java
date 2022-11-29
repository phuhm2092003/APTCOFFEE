package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.HoaDonAdapter;
import fpt.edu.aptcoffee.dao.HoaDonDAO;
import fpt.edu.aptcoffee.interfaces.ItemHoaDonOnClick;
import fpt.edu.aptcoffee.model.HoaDon;

public class HoaDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewHoaDon;
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        initToolBar();

        initView();
        hoaDonDAO = new HoaDonDAO(this);
        loadData();

    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewHoaDon.setLayoutManager(linearLayoutManager);

        HoaDonAdapter adapter = new HoaDonAdapter(HoaDonActivity.this, hoaDonDAO.getByTrangThai(HoaDon.DA_THANH_TOAN), new ItemHoaDonOnClick() {
            @Override
            public void itemOclick(View view, HoaDon hoaDon) {
                // show popmenu chi tiết
                // tạo menu chi tiết
            }
        });
        recyclerViewHoaDon.setAdapter(adapter);
    }

    private void initView() {
        recyclerViewHoaDon = findViewById(R.id.recyclerViewHoaDon);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarHoaDon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}