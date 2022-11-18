package fpt.edu.aptcoffee.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import pl.droidsonroids.gif.GifImageView;

public class SettingFragment extends Fragment implements View.OnClickListener {
    TextView tvDanhGia, tvLienHe, tvThietLapTaiKhoan, tvDoiMatKhuau, tvDangXuat, tvTenNguoiDung, tvChucVu, tvEmail;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;
    CircleImageView civHinhAnh;
    ImageView ivDoiHinhAnh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        initOnClick();
        mainActivity = ((MainActivity) getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());
        getInfoNguoiDung();
        ivDoiHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đổi ảnh đại diện
                MyToast.successful(getContext(), "Doi hinh anh");
            }
        });
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
        tvEmail = view.findViewById(R.id.tvEmail);
        ivDoiHinhAnh = view.findViewById(R.id.ivDoiHinhAnh);
    }

    private void getInfoNguoiDung() {
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        NguoiDung nguoiDung = nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);

        tvTenNguoiDung.setText(nguoiDung.getHoVaTen());
        tvChucVu.setText(nguoiDung.getChucVu());
        tvEmail.setText(nguoiDung.getEmail());
        civHinhAnh.setImageBitmap(bitmap);
    }

    private void onpenDoiMatKhauAcitvity() {
        Intent intent = new Intent(getContext(), DoiMatKhauActivity.class);
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        intent.putExtra("MA_NGUOIDUNG", maNguoiDung);
        startActivity(intent);
        ((Activity) requireContext()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private void openSignInActivity() {
        startActivity(new Intent(getContext(), SignInActivity.class));
        ((Activity) requireContext()).overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDanhGia:
                showDialogDanhGia();
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

    private void logoutSytem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSignInActivity();
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

    private void showDialogDanhGia() {
        // Tạo view mới từ package layout
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.layout_danh_gia, null);
        // Ánh xạ View từ viewDialog
        RatingBar ratingBar = viewDialog.findViewById(R.id.rtbDanhGia);
        Button btnDanhGia = viewDialog.findViewById(R.id.btnDanhGia);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);
        GifImageView gifImageView = viewDialog.findViewById(R.id.imgGif);
        // Tạo mới dialog
        Dialog dialog = new Dialog(getContext());
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
        // Sự kiện thay đổi số lượng sao đánh giá
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (ratingBar.getRating() <= 3) {
                    // số lượng sao nhỏ hơn hoặc bằng 3
                    gifImageView.setImageResource(R.drawable.git_sad);
                } else {
                    // số lượng sao lớn hơn 3
                    gifImageView.setImageResource(R.drawable.gif_danh_gia);
                }
            }
        });
        // Sự kiện đánh giá
        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.successful(getContext(), "Đã đánh giá " + ratingBar.getRating() + " sao");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}