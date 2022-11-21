package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.fragment.SettingFragment;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.utils.MyToast;
import fpt.edu.aptcoffee.utils.XDate;
import pl.droidsonroids.gif.GifImageView;

public class ThietLapTaiKhoanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack, ivEditTen, ivEditNgaySinh, ivEditEmail, ivEditGioiTinh, ivEditChucVu;
    CircleImageView civHinhAnh;
    TextView tvMaNguoiDung, tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvEmail, tvChucVu;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_tai_khoan);
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        getInfoNguoiDung();
        initOnclickIv();


    }


    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        civHinhAnh = findViewById(R.id.civHinhAnh);
        tvMaNguoiDung = findViewById(R.id.tvMaNguoiDung);
        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvEmail = findViewById(R.id.tvEmail);
        tvChucVu = findViewById(R.id.tvChucVu);
        ivEditTen = findViewById(R.id.ivEditHoVaTen);
        ivEditNgaySinh = findViewById(R.id.ivEditNgaySinh);
        ivEditEmail = findViewById(R.id.ivEditEmail);
        ivEditGioiTinh = findViewById(R.id.ivEditGioiTinh);
        ivEditChucVu = findViewById(R.id.ivEditChucVu);
    }

    private void initOnclickIv() {
        ivBack.setOnClickListener(this);
        ivEditTen.setOnClickListener(this);
        ivEditGioiTinh.setOnClickListener(this);
        ivEditNgaySinh.setOnClickListener(this);
        ivEditEmail.setOnClickListener(this);
        ivEditChucVu.setOnClickListener(this);
    }

    private NguoiDung getObjectNguoiDung() {
        Intent intent = getIntent();
        String maNguoiDung = intent.getStringExtra(SettingFragment.MA_NGUOIDUNG);
        return nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEditHoVaTen:
                showDialogEditTen();
                break;
            case R.id.ivEditNgaySinh:
                showDialogEditNgaySinh();
                break;
            case R.id.ivEditGioiTinh:
                showDialogEditGioiTinh();
                break;
            case R.id.ivEditEmail:
                showDialogEditEmail();
                break;
            case R.id.ivEditChucVu:
                showDialogEditChucVu();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getInfoNguoiDung() {
        NguoiDung nguoiDung = getObjectNguoiDung();
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(), 0, nguoiDung.getHinhAnh().length);
        civHinhAnh.setImageBitmap(bitmap);
        tvMaNguoiDung.setText(nguoiDung.getMaNguoiDung());
        tvTenNguoiDung.setText(nguoiDung.getHoVaTen());
        tvNgaySinh.setText(XDate.toStringDate(nguoiDung.getNgaySinh()));
        if (nguoiDung.getGioiTinh().equals("Nu")) {
            tvGioiTinh.setText("Nữ");
        } else {
            tvGioiTinh.setText("Nam");
        }
        tvEmail.setText(nguoiDung.getEmail());
        tvChucVu.setText(nguoiDung.getChucVu());
    }

    private void showDialogEditTen() {
        // Cập nhật tên người dùng
        NguoiDung nguoiDung = getObjectNguoiDung();
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_edit_ten_nd, null);
        // Ánh xạ View từ viewDialog
        TextInputLayout tilNgaySinh = viewDialog.findViewById(R.id.til);
        Objects.requireNonNull(tilNgaySinh.getEditText()).setText(nguoiDung.getHoVaTen());
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        // Tạo mới dialog
        Dialog dialog = new Dialog(this);
        // Gán Backgound trong suốt cho dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Gán view cho dialog
        dialog.setContentView(viewDialog);
        // Khởi tạo chiều rộng và chiều cao cho dialog
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Gán Size cho dialog
        dialog.getWindow().setLayout(width, height);
        // Sự kiện bỏ qua
        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Cập nhật họ và tên
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoVaTen = tilNgaySinh.getEditText().getText().toString().trim();
                if (hoVaTen.isEmpty()) {
                    MyToast.error(ThietLapTaiKhoanActivity.this, "Vui lòng không để trống");
                } else {
                    nguoiDung.setHoVaTen(hoVaTen);
                    if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                        MyToast.successful(ThietLapTaiKhoanActivity.this, "Cập nhật thành công");
                        dialog.dismiss();
                    }
                }
                getInfoNguoiDung();
            }
        });
        dialog.show();
    }

    private void showDialogEditEmail() {
        // Cập nhật Email
        NguoiDung nguoiDung = getObjectNguoiDung();
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_edit_email_nd, null);
        // Ánh xạ View từ viewDialog
        TextInputLayout tilEmail = viewDialog.findViewById(R.id.til);
        Objects.requireNonNull(tilEmail.getEditText()).setText(nguoiDung.getEmail());
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        // Tạo mới dialog
        Dialog dialog = new Dialog(this);
        // Gán Backgound trong suốt cho dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Gán view cho dialog
        dialog.setContentView(viewDialog);
        // Khởi tạo chiều rộng và chiều cao cho dialog
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Gán Size cho dialog
        dialog.getWindow().setLayout(width, height);
        // Sự kiện bỏ qua
        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Cập nhật họ và tên
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tilEmail.getEditText().getText().toString().trim();
                if (email.isEmpty()) {
                    MyToast.error(ThietLapTaiKhoanActivity.this, "Vui lòng không để trống");
                } else {
                    nguoiDung.setEmail(email);
                    if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                        MyToast.successful(ThietLapTaiKhoanActivity.this, "Cập nhật thành công");
                        dialog.dismiss();
                    }
                }
                getInfoNguoiDung();
            }
        });
        dialog.show();
    }

    private void showDialogEditNgaySinh() {
        // cập nhật ngày sinh người dùng
        NguoiDung nguoiDung = getObjectNguoiDung();
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_edit_ngay_sinh, null);
        // Ánh xạ View từ viewDialog
        TextInputLayout tilNgaySinh = viewDialog.findViewById(R.id.til);
        Objects.requireNonNull(tilNgaySinh.getEditText()).setText(XDate.toStringDate(nguoiDung.getNgaySinh()));

        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        // Tạo mới dialog
        Dialog dialog = new Dialog(this);
        // Gán Backgound trong suốt cho dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Gán view cho dialog
        dialog.setContentView(viewDialog);
        // Khởi tạo chiều rộng và chiều cao cho dialog
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Gán Size cho dialog
        dialog.getWindow().setLayout(width, height);
        // Sự kiện bỏ qua
        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Cập nhật ngày sinh
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngaySinh = tilNgaySinh.getEditText().getText().toString().trim();
                if (ngaySinh.isEmpty()) {
                    MyToast.error(ThietLapTaiKhoanActivity.this, "Vui lòng không để trống");
                } else {
                    try {
                        Date date = XDate.toDate(ngaySinh);
                        nguoiDung.setNgaySinh(date);
                        if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                            MyToast.successful(ThietLapTaiKhoanActivity.this, "Cập nhật thành công");
                            dialog.dismiss();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        MyToast.error(ThietLapTaiKhoanActivity.this, "Nhập ngày sinh sai dịnh dạng");
                    }
                }
                getInfoNguoiDung();
            }
        });
        // sự kiện onclick TextInput layout
        tilNgaySinh.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nguoiDung.getNgaySinh());
                int date = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThietLapTaiKhoanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        tilNgaySinh.getEditText().setText(XDate.toStringDate(calendar.getTime()));
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });
        dialog.show();
    }

    private void showDialogEditGioiTinh() {
        // Cập nhật tên người dùng
        NguoiDung nguoiDung = getObjectNguoiDung();
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_edit_gioi_tinh_nd, null);
        // Ánh xạ View từ viewDialog
        RadioGroup radioGroup = viewDialog.findViewById(R.id.grGender);
        RadioButton rdNam = viewDialog.findViewById(R.id.rbNam);
        RadioButton rdNu = viewDialog.findViewById(R.id.rbNu);
        if (nguoiDung.getGioiTinh().equals("Nam")) {
            rdNam.setChecked(true);
        } else {
            rdNu.setChecked(true);
        }
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        // Tạo mới dialog
        Dialog dialog = new Dialog(this);
        // Gán Backgound trong suốt cho dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Gán view cho dialog
        dialog.setContentView(viewDialog);
        // Khởi tạo chiều rộng và chiều cao cho dialog
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Gán Size cho dialog
        dialog.getWindow().setLayout(width, height);
        // Sự kiện bỏ qua
        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Cập nhật họ và tên
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rbCheck = radioGroup.getCheckedRadioButtonId();
                if (rbCheck == R.id.rbNam) {
                    nguoiDung.setGioiTinh("Nam");
                } else {
                    nguoiDung.setGioiTinh("Nu");
                }
                if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                    MyToast.successful(ThietLapTaiKhoanActivity.this, "Cập nhật thành công");
                    dialog.dismiss();
                }
                getInfoNguoiDung();
            }
        });
        dialog.show();
    }

    private void showDialogEditChucVu() {
        // Cập nhật tên người dùng
        NguoiDung nguoiDung = getObjectNguoiDung();
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_edit_chuc_vu, null);
        // Ánh xạ View từ viewDialog
        RadioGroup radioGroup = viewDialog.findViewById(R.id.grChucVu);
        RadioButton rdAdmin = viewDialog.findViewById(R.id.rbAdmin);
        RadioButton rdNhanVien = viewDialog.findViewById(R.id.rbNhanVien);
        if (nguoiDung.getChucVu().equals("Admin")) {
            rdAdmin.setChecked(true);
        } else {
            rdNhanVien.setChecked(true);
        }
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        // Tạo mới dialog
        Dialog dialog = new Dialog(this);
        // Gán Backgound trong suốt cho dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Gán view cho dialog
        dialog.setContentView(viewDialog);
        // Khởi tạo chiều rộng và chiều cao cho dialog
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Gán Size cho dialog
        dialog.getWindow().setLayout(width, height);
        // Sự kiện bỏ qua
        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Cập nhật chức vụ
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rbCheck = radioGroup.getCheckedRadioButtonId();
                if (rbCheck == R.id.rbAdmin) {
                    nguoiDung.setChucVu("Admin");
                } else {
                    nguoiDung.setChucVu("NhanVien");
                }
                if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
                    MyToast.successful(ThietLapTaiKhoanActivity.this, "Cập nhật thành công");
                    dialog.dismiss();
                }
                getInfoNguoiDung();
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}