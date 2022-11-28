package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.HoaDonChiTietDAO;
import fpt.edu.aptcoffee.utils.XDate;

public class DoanhThuActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvDoanhThuNam, tvDoanhThuThang, tvDoanhThuNgay, tvThang;
    HoaDonChiTietDAO hoaDonChiTietDAO;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        initToolBar();
        initView();

        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);

        getDoanhThuNgay();
        getDoangThuThang();
        getDoanhThuNam();


    }

    @SuppressLint("SetTextI18n")
    private void getDoangThuThang() {
        Calendar calendar = Calendar.getInstance();
        int moth = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, moth, 1);
        Date tuNgay = calendar.getTime();

        int dateTuNgay = getDate(moth, year);
        calendar.set(year, moth, dateTuNgay);
        Date denNgay = calendar.getTime();
        tvThang.setText("Tháng "+(moth + 1));
        tvDoanhThuThang.setText(hoaDonChiTietDAO.getDTThangNam(XDate.toStringDate(tuNgay),
                XDate.toStringDate(denNgay)) + "VND");
    }

    private int getDate(int moth, int year) {
        switch (moth) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                // tháng 12
                return 31;

            case 3:
            case 5:
            case 8:
            case 10:
                //tháng 11
                return 30;
            case 1:
                // tháng 2
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
        }
        return 0;
    }

    @SuppressLint("SetTextI18n")
    private void getDoanhThuNam() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, 0, 1);
        Date tuNgay = calendar.getTime();

        calendar.set(year, 11, 31);
        Date denNgay = calendar.getTime();
        tvDoanhThuNam.setText(hoaDonChiTietDAO.getDTThangNam(
                XDate.toStringDate(tuNgay),
                XDate.toStringDate(denNgay)) + "VND"
        );
    }

    @SuppressLint("SetTextI18n")
    private void getDoanhThuNgay() {
        Date dateNow = Calendar.getInstance().getTime();
        int doanhThuNgay = hoaDonChiTietDAO.getDoanhThuNgay(XDate.toStringDate(dateNow));
        tvDoanhThuNgay.setText(doanhThuNgay + "VND");
    }

    private void initView() {
        tvDoanhThuNam = findViewById(R.id.tvDoanhThuNam);
        tvDoanhThuThang = findViewById(R.id.tvDoanhThuThang);
        tvDoanhThuNgay = findViewById(R.id.tvDoanhThuNgay);
        tvThang = findViewById(R.id.tvThang);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarDoanhThu);
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