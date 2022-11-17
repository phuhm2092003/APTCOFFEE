package fpt.edu.aptcoffee.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.PhotoAdapter;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.model.Photo;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tvHi;
    CircleImageView civHinhAnh;
    ViewPager2 vpSlideImage;
    CircleIndicator3 indicator3;
    CardView cvBan, cvLoai, cvThucUong, cvNhanVien, cvHoaDon, cvDoanhThu;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        iniView(view);
        initOnClickCard();
        loadSlideImage();

        mainActivity = ((MainActivity)getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());

        loadInfoNguoiDung(view);
        return view;
    }

    private void iniView(View view) {
        vpSlideImage = view.findViewById(R.id.vpSlideImage);
        indicator3 = view.findViewById(R.id.circleIndicator3);
        cvBan = view.findViewById(R.id.cardBan);
        cvLoai = view.findViewById(R.id.cardLoaiThucUong);
        cvThucUong = view.findViewById(R.id.cardThucUong);
        cvNhanVien = view.findViewById(R.id.cardNhanVien);
        cvHoaDon = view.findViewById(R.id.cardHoaDon);
        cvDoanhThu = view.findViewById(R.id.cardDoanhThu);
        tvHi = view.findViewById(R.id.tvHi);
        civHinhAnh = view.findViewById(R.id.hinhAnh);
    }

    private void initOnClickCard() {
        cvBan.setOnClickListener(this);
        cvLoai.setOnClickListener(this);
        cvThucUong.setOnClickListener(this);
        cvNhanVien.setOnClickListener(this);
        cvHoaDon.setOnClickListener(this);
        cvDoanhThu.setOnClickListener(this);
    }

    private void loadSlideImage() {
        PhotoAdapter adapter = new PhotoAdapter(getListImage());
        vpSlideImage.setAdapter(adapter);
        vpSlideImage.setOffscreenPageLimit(2);
        indicator3.setViewPager(vpSlideImage);
    }

    @NonNull
    private ArrayList<Photo> getListImage() {
        ArrayList<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.image_slide_show1));
        list.add(new Photo(R.drawable.image_slide_show2));
        list.add(new Photo(R.drawable.image_slide_show3));
        list.add(new Photo(R.drawable.image_slide_show4));
        list.add(new Photo(R.drawable.image_slide_show5));

        return list;
    }

    @SuppressLint("SetTextI18n")
    private void loadInfoNguoiDung(View view) {
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        NguoiDung nguoiDung = nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);

        tvHi.setText("Hello, "+nguoiDung.getHoVaTen());
        civHinhAnh.setImageBitmap(bitmap);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardBan:
                // chuyển quan màng hình quản lý bàn
                break;
            case R.id.cardLoaiThucUong:
                // chuyển quan màng hình quản lý loại
                break;
            case R.id.cardThucUong:
                // chuyển quan màng hình quản lý thức uống
                break;
            case R.id.cardNhanVien:
                // chuyển quan màng hình quản lý nhân viên
                break;
            case R.id.cardHoaDon:
                // chuyển quan màng hình quản lý hoá đơn
                break;
            case R.id.cardDoanhThu:
                // chuyển quan màng hình quản lý doanh thu
                break;
        }
    }
}