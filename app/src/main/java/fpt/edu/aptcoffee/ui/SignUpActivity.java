package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MATCHES_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ImageView ivBack;
    TextInputLayout tilMaNguoiDung, tilHoVaTen, tilNgaySinh, tilEmail, tilMatKhau;
    TextInputEditText tieNgaySinh;
    RadioGroup rdgGender, rdgPosition;
    Button btnSignUp;
    TextView tvSignIn;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        ivBack.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        tieNgaySinh.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tilMaNguoiDung = findViewById(R.id.tilMaNguoiDung);
        tilHoVaTen = findViewById(R.id.tilHoVaTen);
        tilNgaySinh = findViewById(R.id.tilNgaySinh);
        tilEmail = findViewById(R.id.tilEmail);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        tieNgaySinh = findViewById(R.id.tieNgaySinh);
        rdgGender = findViewById(R.id.rdgGender);
        rdgPosition = findViewById(R.id.rdgPosition);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(y, m, d);
                Objects.requireNonNull(tilNgaySinh.getEditText()).setText(XDate.toStringDate(calendar.getTime()));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void signUpAccount() {
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
                    registerAccount();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                MyToast.error(SignUpActivity.this, "Nhập ngày sinh sai định dạng");
            }

        }
    }

    private void registerAccount() throws ParseException {
        String maNguoiDung = getText(tilMaNguoiDung);
        String hoVaTen = getText(tilHoVaTen);
        String ngaySinh = getText(tilNgaySinh);
        String email = getText(tilEmail);
        String matKhau = getText(tilMatKhau);
        // Tạo người dùng mới
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung(maNguoiDung);
        nguoiDung.setHoVaTen(hoVaTen);
        nguoiDung.setHinhAnh(ImageToByte.drawableToByte(SignUpActivity.this, R.drawable.avatar_user_md));
        nguoiDung.setNgaySinh(XDate.toDate(ngaySinh));
        nguoiDung.setEmail(email);
        nguoiDung.setChucVu(getPostion());
        nguoiDung.setGioiTinh(getGender());
        nguoiDung.setMatKhau(matKhau);

        if (nguoiDungDAO.insertNguoiDung(nguoiDung)) {
            MyToast.successful(SignUpActivity.this, "Đăng ký thành công");
            clearText();
        } else {
            MyToast.error(SignUpActivity.this, "Tên đăng nhập tồn tại");
        }
    }

    private String getGender() {
        // Giới tính
        if (rdgGender.getCheckedRadioButtonId() == R.id.rdNam) {
            return NguoiDung.GENDER_MALE;
        }
        return NguoiDung.GENDER_FEMALE;
    }

    private String getPostion() {
        // Chức vụ
        if (rdgPosition.getCheckedRadioButtonId() == R.id.rdAdmin) {
            return NguoiDung.POSITION_ADMIN;
        }
        return NguoiDung.POSITION_STAFF;
    }

    private void clearText() {
        Objects.requireNonNull(tilMaNguoiDung.getEditText()).setText("");
        Objects.requireNonNull(tilHoVaTen.getEditText()).setText("");
        Objects.requireNonNull(tilNgaySinh.getEditText()).setText("");
        Objects.requireNonNull(tilEmail.getEditText()).setText("");
        Objects.requireNonNull(tilMatKhau.getEditText()).setText("");
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
            case R.id.tvSignIn:
                onBackPressed();
                break;
            case R.id.tieNgaySinh:
                showDateDialog();
                break;
            case R.id.btnSignUp:
                signUpAccount();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}