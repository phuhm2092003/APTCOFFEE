package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.dao.ThongBaoDAO;
import fpt.edu.aptcoffee.fragment.SettingFragment;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.model.ThongBao;
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

    @NonNull
    private String getText(TextInputLayout til) {
        return Objects.requireNonNull(til.getEditText()).getText().toString();
    }

    private void clearText() {
        Objects.requireNonNull(tilMatKhauCu.getEditText()).setText("");
        Objects.requireNonNull(tilMatKhauMoi.getEditText()).setText("");
        Objects.requireNonNull(tilNhapLaiMatKhau.getEditText()).setText("");
    }

    private String getMaNguoiDung() {
        Intent intent = getIntent();
        return intent.getStringExtra(SettingFragment.MA_NGUOIDUNG);
    }

    private void updatePasword() {
        // L???y m?? ng?????i d??ng
        String maNguoiDung = getMaNguoiDung();
        // L???y ng?????i d??ng theo m??
        NguoiDung nguoiDung = nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
        // L???y d??? li???u c???a TextInputLayout
        String matKhauCu = getText(tilMatKhauCu);
        String matKhauMoi = getText(tilMatKhauMoi);
        String nhapLaimatKhau = getText(tilNhapLaiMatKhau);
        // Ki???m tra d??? li???u
        if (!matKhauCu.isEmpty() && !matKhauMoi.isEmpty() && !nhapLaimatKhau.isEmpty()) {
            // N???u m???t kh???u c??, m???t kh???u m???i, m???t kh???u nh???p l???i kh??c 0
            if (!matKhauCu.equals(nguoiDung.getMatKhau())) {
                // Ki???m tra m???t kh???u nh???p v??o c?? tr??ng v???i m???t kh???u c?? ?
                MyToast.error(DoiMatKhauActivity.this, "M???t kh???u c?? kh??ng ch??nh x??c");
            }
            if (!matKhauMoi.equals(nhapLaimatKhau)) {
                // Ki???m tra m???t kh???u nh???p l???i c?? kh???p v???i m???t kh???u m???i
                MyToast.error(DoiMatKhauActivity.this, "M???t kh???u m???i kh??ng kh???p");
            }
            if (matKhauCu.equals(nguoiDung.getMatKhau()) && matKhauMoi.equals(nhapLaimatKhau)) {
                /*
                M???t kh???u nh???p v??o b???ng v???i m???t kh???u c??
                m???t kh???u m??i kh???p v???i m???t kh???u nh???p l???i
                */
                // G??n l???i m???t kh???u m???i
                nguoiDung.setMatKhau(matKhauMoi);
                if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                    // C???p nh???t m???t kh???u
                    MyToast.successful(DoiMatKhauActivity.this, "?????i m???t kh???u th??nh c??ng");
                    ThemThongBaoMoi();
                    clearText();
                    // Khai b??o buider
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoiMatKhauActivity.this, R.style.AlertDialogTheme);
                    // G??n th???ng b??o
                    builder.setMessage("Quay l???i m??ng h??nh ????ng nh???p?");
                    // S??? ki???n ?????ng ?? chuy???n quan m??ng h??nh ????ng nh???p
                    builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(DoiMatKhauActivity.this, SignInActivity.class));

                        }
                    });
                    // S??? ki???n hu???
                    builder.setNegativeButton("Ti???p t???c s??? d???ng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    // Kh???i t???o dialog v?? hi???n th???
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    MyToast.error(DoiMatKhauActivity.this, "?????i m???t kh???u kh??ng th??nh c??ng");
                }
            }
        } else {
            MyToast.error(DoiMatKhauActivity.this, "Vui l??ng ??i???n ?????y ????? th??ng tin");
        }
    }

    private void ThemThongBaoMoi() {
        // T???o th??ng b??o c???p nh???t m???t kh???u
        ThongBao thongBao = new ThongBao();
        thongBao.setNoiDung("C???p nh???t m???t kh???u th??nh c??ng");
        thongBao.setTrangThai(ThongBao.STATUS_CHUA_XEM);
        Calendar calendar = Calendar.getInstance();
        thongBao.setNgayThongBao(calendar.getTime());
        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(DoiMatKhauActivity.this);
        thongBaoDAO.insertThongBao(thongBao);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}