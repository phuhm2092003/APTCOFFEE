package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.utils.ImageToByte;
import fpt.edu.aptcoffee.utils.MyToast;
import fpt.edu.aptcoffee.utils.XDate;

public class SignUpActivity extends AppCompatActivity {
    public static final String MATCHES_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ImageView ivBack;
    TextInputLayout tilMaNguoiDung, tilHoVaTen, tilNgaySinh, tilEmail, tilMatKhau;
    RadioGroup rdgGender, rdgPermission;
    Button btnRegister;
    TextView tvSignIn;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Objects.requireNonNull(tilNgaySinh.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show dialog chọn ngày
                showDateDialog();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // đắng ký tài khoản
                signUp();
            }
        });

    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tilMaNguoiDung = findViewById(R.id.tilMaNguoiDung);
        tilHoVaTen = findViewById(R.id.tilHoVaTen);
        tilNgaySinh = findViewById(R.id.tilNgaySinh);
        tilEmail = findViewById(R.id.tilEmail);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        rdgGender = findViewById(R.id.rdgGender);
        rdgPermission = findViewById(R.id.rdgPermission);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                Objects.requireNonNull(tilNgaySinh.getEditText()).setText(XDate.toStringDate(calendar.getTime()));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void signUp() {
        String maNguoiDung = getText(tilMaNguoiDung);
        String hoTen = getText(tilHoVaTen);
        String ngaySinhh = getText(tilNgaySinh);
        String email = getText(tilEmail);
        String matKhau = getText(tilMatKhau);

        if (maNguoiDung.isEmpty() || hoTen.isEmpty() || ngaySinhh.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
            MyToast.error(SignUpActivity.this, "Vui lòng nhập đẩy đủ thông tin");
        } else {
            try {
                Date date = XDate.toDate(ngaySinhh);
                if (!email.matches(MATCHES_EMAIL)) {
                    MyToast.error(SignUpActivity.this, "Nhập email sai định dạng");
                } else {
                    registerNguoiDung();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                MyToast.error(SignUpActivity.this, "Nhập ngày sinh sai định dạng");
            }

        }
    }

    private void registerNguoiDung() throws ParseException {
        String maNguoiDung = getText(tilMaNguoiDung);
        String hoVaTen = getText(tilHoVaTen);
        String ngaySinh = getText(tilNgaySinh);
        String email = getText(tilEmail);
        String matKhau = getText(tilMatKhau);

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung(maNguoiDung);
        nguoiDung.setHoVaTen(hoVaTen);
        nguoiDung.setNgaySinh(XDate.toDate(ngaySinh));
        nguoiDung.setHinhAnh(ImageToByte.drawableToByte(SignUpActivity.this, R.drawable.avatar_user_md));
        nguoiDung.setEmail(email);
        nguoiDung.setMatKhau(matKhau);

        // giới tính
        int checkGender = rdgGender.getCheckedRadioButtonId();
        if (checkGender == R.id.rdNam) {
            nguoiDung.setGioiTinh("Nam");
        } else {
            nguoiDung.setGioiTinh("Nu");
        }
        // quyền
        int checkPermission = rdgPermission.getCheckedRadioButtonId();
        if (checkPermission == R.id.rdAdmin) {
            nguoiDung.setChucVu("Admin");
        } else {
            nguoiDung.setChucVu("NhanVien");
        }

        if (nguoiDungDAO.insertNguoiDung(nguoiDung)) {
            MyToast.successful(SignUpActivity.this, "Đăng ký thành công");
            clearText();
        } else {
            MyToast.error(SignUpActivity.this, "Tên đăng nhập tồn tại");
        }
    }

    private void clearText() {
        Objects.requireNonNull(tilMaNguoiDung.getEditText()).setText("");
        Objects.requireNonNull(tilHoVaTen.getEditText()).setText("");
        Objects.requireNonNull(tilNgaySinh.getEditText()).setText("");
        Objects.requireNonNull(tilEmail.getEditText()).setText("");
        Objects.requireNonNull(tilMatKhau.getEditText()).setText("");
    }

    @NonNull
    private String getText(TextInputLayout tilMaNguoiDung) {
        return Objects.requireNonNull(tilMaNguoiDung.getEditText()).getText().toString();
    }
}