package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.utils.MyToast;

public class DoiMatKhauActivity extends AppCompatActivity {
    ImageView ivBack;
    TextInputLayout tilMatKhauCu, tilMatKhauMoi, tilNhapLaiMatKhau;
    Button btnUpdate;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        inniView();
        nguoiDungDAO = new NguoiDungDAO(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePasword();
            }
        });

    }

    private void inniView() {
        ivBack = findViewById(R.id.ivBack);
        tilMatKhauCu = findViewById(R.id.tilMatKhauCu);
        tilMatKhauMoi = findViewById(R.id.tilMatKhauMoi);
        tilNhapLaiMatKhau = findViewById(R.id.tilNhapLaiMatKhau);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void updatePasword() {
        Intent intent = getIntent();
        String maNguoiDung = intent.getStringExtra("MA_NGUOIDUNG");
        NguoiDung nguoiDung = nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
        String matKhauCu = tilMatKhauCu.getEditText().getText().toString();
        String matKhauMoi = tilMatKhauMoi.getEditText().getText().toString();
        String nhapLaimatKhau = tilNhapLaiMatKhau.getEditText().getText().toString();
        if (!matKhauCu.isEmpty() && !matKhauMoi.isEmpty() && !nhapLaimatKhau.isEmpty()) {
            if (!matKhauCu.equals(nguoiDung.getMatKhau())) {
                MyToast.error(DoiMatKhauActivity.this, "Mật khẩu cũ không chính xác");
            }
            if (!matKhauMoi.equals(nhapLaimatKhau)) {
                MyToast.error(DoiMatKhauActivity.this, "Mật khẩu mới không khớp");
            }
            if (matKhauCu.equals(nguoiDung.getMatKhau()) && matKhauMoi.equals(nhapLaimatKhau)){
                nguoiDung.setMatKhau(matKhauMoi);
                if (nguoiDungDAO.updateNguoiDung(nguoiDung)){
                    MyToast.successful(DoiMatKhauActivity.this, "Đổi mật khẩu thành công");
                    tilMatKhauCu.getEditText().setText("");
                    tilMatKhauMoi.getEditText().setText("");
                    tilNhapLaiMatKhau.getEditText().setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoiMatKhauActivity.this);
                    builder.setMessage("Quay lại màng hình đăng nhập.");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(DoiMatKhauActivity.this, SignInActivity.class));
                        }
                    });
                    builder.setNegativeButton("Tiếp tục sử dụng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    MyToast.error(DoiMatKhauActivity.this, "Đổi mật khẩu không thành công");
                }
            }
        } else {
            MyToast.error(DoiMatKhauActivity.this, "Vui lòng điền đầy đủ thông tin");
        }
    }
}