package fpt.edu.aptcoffee.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.ui.DoiMatKhauActivity;
import fpt.edu.aptcoffee.ui.SignInActivity;
import fpt.edu.aptcoffee.utils.MyToast;

public class SettingFragment extends Fragment implements View.OnClickListener {
    TextView tvDanhGia, tvLienHe, tvThietLapTaiKhoan, tvDoiMatKhuau, tvDangXuat, tvTenNguoiDung, tvChucVu;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;
    CircleImageView civHinhAnh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        initOnClick();
        mainActivity = ((MainActivity) getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());
        getInfoNguoiDung();
        return view;
    }

    private void initOnClick() {
        tvDanhGia.setOnClickListener(this);
        tvLienHe.setOnClickListener(this);
        tvThietLapTaiKhoan.setOnClickListener(this);
        tvDoiMatKhuau.setOnClickListener(this);
        tvDangXuat.setOnClickListener(this);
    }

    private void initView(View view) {
        tvDanhGia = view.findViewById(R.id.tvDanhGia);
        tvLienHe = view.findViewById(R.id.tvLienHe);
        tvThietLapTaiKhoan = view.findViewById(R.id.tvThietLapTaiKhoan);
        tvDoiMatKhuau = view.findViewById(R.id.tvDoiMatKhau);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
        civHinhAnh = view.findViewById(R.id.civHinhAnh);
        tvTenNguoiDung = view.findViewById(R.id.tvTenNguoiDung);
        tvChucVu = view.findViewById(R.id.tvChucVu);
    }

    private void getInfoNguoiDung() {
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        NguoiDung nguoiDung = nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);
        tvTenNguoiDung.setText(nguoiDung.getHoVaTen());
        tvChucVu.setText(nguoiDung.getChucVu());
        civHinhAnh.setImageBitmap(bitmap);
    }

    private void onpenDoiMatKhauAcitvity() {
        Intent intent = new Intent(getContext(), DoiMatKhauActivity.class);
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        intent.putExtra("MA_NGUOIDUNG", maNguoiDung);
        startActivity(intent);
        ((Activity) requireContext()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private void logoutSytem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getContext(), SignInActivity.class));
                ((Activity) requireContext()).overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDanhGia:
                MyToast.successful(getContext(), "Đánh giá");
                break;
            case R.id.tvLienHe:
                MyToast.successful(getContext(), "Liên hệ");
                break;
            case R.id.tvThietLapTaiKhoan:
                MyToast.successful(getContext(), "Thiết lập tài khoản");
                break;
            case R.id.tvDoiMatKhau:
                onpenDoiMatKhauAcitvity();
                break;
            case R.id.tvDangXuat:
                logoutSytem();
                break;
        }
    }
}