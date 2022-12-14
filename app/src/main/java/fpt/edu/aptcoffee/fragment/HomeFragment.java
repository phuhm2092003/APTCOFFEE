package fpt.edu.aptcoffee.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
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
import fpt.edu.aptcoffee.adapter.ThucUongHomeFragmentAdapter;
import fpt.edu.aptcoffee.dao.HangHoaDAO;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.model.HangHoa;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.model.Photo;
import fpt.edu.aptcoffee.ui.DoanhThuActivity;
import fpt.edu.aptcoffee.ui.HoaDonActivity;
import fpt.edu.aptcoffee.ui.LoaiThucUongActivity;
import fpt.edu.aptcoffee.ui.NhanVienActivity;
import fpt.edu.aptcoffee.ui.QuanLyBanActivity;
import fpt.edu.aptcoffee.ui.ThucUongActivity;
import fpt.edu.aptcoffee.utils.MyToast;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tvHi;
    CircleImageView civHinhAnh;
    ViewPager2 vpSlideImage;
    CircleIndicator3 indicator3;
    CardView cvBan, cvLoai, cvThucUong, cvNhanVien, cvHoaDon, cvDoanhThu;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;
    HangHoaDAO hangHoaDAO;
    RecyclerView recyclerViewThucUong;
    Handler handler;
    Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initOnClickCard();
        loadSlideImage();

        mainActivity = ((MainActivity) getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());
        hangHoaDAO = new HangHoaDAO(getContext());

        welcomeUser();
        loadListThucUong();
        autoRunSildeImage();
        return view;
    }

    private void autoRunSildeImage() {
        // T??? ?????ng chuy???n ???nh trong SlideImage
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(vpSlideImage.getCurrentItem() == getListImage().size() -1){
                    vpSlideImage.setCurrentItem(0, false);
                }else {
                    vpSlideImage.setCurrentItem(vpSlideImage.getCurrentItem() + 1);
                }
            }
        };
        handler.postDelayed(runnable, 2000);

        // S??? ki???n Slide Image chuy???n ???nh
        vpSlideImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);            }
        });
    }

    private void loadListThucUong() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewThucUong.setLayoutManager(linearLayoutManager);

        // L???y danh s??ch th???c u???ng hi???n th??? tr??n recyclerView
        ArrayList<HangHoa> listHangHoa = hangHoaDAO.getAll();
        ThucUongHomeFragmentAdapter adapter = new ThucUongHomeFragmentAdapter(listHangHoa);
        recyclerViewThucUong.setAdapter(adapter);
    }

    private void initView(View view) {
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
        recyclerViewThucUong = view.findViewById(R.id.recyclerViewThucUong);
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
        // Hi???n th??? Slide image
        PhotoAdapter adapter = new PhotoAdapter(getListImage());

        vpSlideImage.setAdapter(adapter);
        vpSlideImage.setOffscreenPageLimit(2);
        indicator3.setViewPager(vpSlideImage);
    }

    @NonNull
    private ArrayList<Photo> getListImage() {
        ArrayList<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.slide_image1));
        list.add(new Photo(R.drawable.slide_image2));
        list.add(new Photo(R.drawable.slide_image3));
        list.add(new Photo(R.drawable.silde_image4));
        list.add(new Photo(R.drawable.slide_image5));

        return list;
    }

    @SuppressLint("SetTextI18n")
    private void welcomeUser() {
        NguoiDung nguoiDung = getNguoiDung();
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);

        // G??n d??? li???u cho view
        tvHi.setText("Hello, " + nguoiDung.getHoVaTen());
        civHinhAnh.setImageBitmap(bitmap);
    }

    private NguoiDung getNguoiDung() {
        // L???y m?? ng?????i d??ng t??? MainActivity th??ng qua h??m getKeyUser
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        // L??y ?????i t?????ng ng?????i d??ng theo m??
        return nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardBan:
                // M??? m??ng h??nh qu???n l?? b??n
                startActivity(new Intent(getContext(), QuanLyBanActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardLoaiThucUong:
                // M??? m??ng h??nh qu???n l?? lo???i h??ng
                startActivity(new Intent(getContext(), LoaiThucUongActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardThucUong:
                // M??? m??ng h??nh qu???n l?? th???c u???ng
                startActivity(new Intent(getContext(), ThucUongActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardNhanVien:
                if (getNguoiDung().isAdmin()) {
                    // Ng?????i d??ng c?? ch???c v??? ="Admin" -> M??? m??ng h??nh qu???n l?? nh??n vi??n
                    startActivity(new Intent(getContext(), NhanVienActivity.class));
                    (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                } else {
                    // Ng?????i dung c?? ch???c v??? = "NhanVien"
                    MyToast.error(getContext(), "Ch???c n??ng d??nh cho Admin");
                }
                break;
            case R.id.cardHoaDon:
                // M??? m??ng h??nh qu???n l?? ho?? ????n
                startActivity(new Intent(getContext(), HoaDonActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardDoanhThu:
                if (getNguoiDung().isAdmin()) {
                    // Ng?????i d??ng c?? ch???c v??? ="Admin" -> M??? m??ng h??nh qu???n l?? doanh thu
                    startActivity(new Intent(getContext(), DoanhThuActivity.class));
                    (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                } else {
                    // Ng?????i d??ng c?? ch???c v??? = "NhanVien"
                    MyToast.error(getContext(), "Ch???c n??ng d??nh cho Admin");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        welcomeUser();
        loadListThucUong();
    }
}