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

public class ThemNhanVienActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBack;
    TextInputLayout tilMaNguoiDung, tilHoVaTen, tilNgaySinh, tilEmail, tilMatKhau;
    TextInputEditText tieNgaySinh;
    Button btnAdd;
    RadioGroup rdgGender;
    NguoiDungDAO nguoiDungDAO;
    public static final String MATCHES_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thanh_vien);
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        ivBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tieNgaySinh.setOnClickListener(this);
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tilMaNguoiDung = findViewById(R.id.tilMaNguoiDung);
        tilHoVaTen = findViewById(R.id.tilHoVaTen);
        tilNgaySinh = findViewById(R.id.tilNgaySinh);
        tilEmail = findViewById(R.id.tilEmail);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        tieNgaySinh = findViewById(R.id.tieNgaySinh);
        btnAdd = findViewById(R.id.btnAdd);
        rdgGender = findViewById(R.id.rdgGender);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnAdd:
                addNguoiDung();
                break;
            case R.id.tieNgaySinh:
                showDatePicker();
                break;
        }
    }

    private void addNguoiDung() {
        String maNguoiDung = getText(tilMaNguoiDung);
        String hoVaTen = getText(tilHoVaTen);
        String ngaySinh = getText(tilNgaySinh);
        String email = getText(tilEmail);
        String matKhau = getText(tilMatKhau);
        if (maNguoiDung.isEmpty() || hoVaTen.isEmpty() || ngaySinh.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
            MyToast.error(ThemNhanVienActivity.this, "Vui l??ng nh???p ?????y ????? th??ng tin");
        } else {
            try {
                Date date = XDate.toDate(ngaySinh);
                if (!email.matches(MATCHES_EMAIL)) {
                    MyToast.error(ThemNhanVienActivity.this, "Nh???p email sai ?????nh d???ng");
                } else {
                    addStaff(maNguoiDung, hoVaTen, ngaySinh, email, matKhau);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                MyToast.error(ThemNhanVienActivity.this, "Nh???p ng??y sinh sai ?????nh d???ng");
            }
        }
    }

    private void addStaff(String maNguoiDung, String hoVaTen, String ngaySinh, String email, String matKhau) throws ParseException {
        // Th??m nh??n vi??n
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung(maNguoiDung);
        nguoiDung.setHoVaTen(hoVaTen);
        nguoiDung.setHinhAnh(ImageToByte.drawableToByte(ThemNhanVienActivity.this, R.drawable.avatar_user_md));
        nguoiDung.setNgaySinh(XDate.toDate(ngaySinh));
        nguoiDung.setEmail(email);
        nguoiDung.setChucVu(NguoiDung.POSITION_STAFF);
        nguoiDung.setGioiTinh(getGender());
        nguoiDung.setMatKhau(matKhau);
        if (nguoiDungDAO.insertNguoiDung(nguoiDung)) {
            MyToast.successful(ThemNhanVienActivity.this, "Th??m th??nh c??ng");
            clearText();
        } else {
            MyToast.error(ThemNhanVienActivity.this, "T??n ????ng nh???p t???n t???i");
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
        return Objects.requireNonNull(tilMaNguoiDung.getEditText()).getText().toString().trim();
    }

    private void showDatePicker() {
        // Show dialog ch???n ng??y
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThemNhanVienActivity.this,
                R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(y, m, d);
                tieNgaySinh.setText(XDate.toStringDate(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private String getGender() {
        // Gi???i t??nh
        if (rdgGender.getCheckedRadioButtonId() == R.id.rdNam) {
            return NguoiDung.GENDER_MALE;
        }
        return NguoiDung.GENDER_FEMALE;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}