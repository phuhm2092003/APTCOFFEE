package fpt.edu.aptcoffee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.HoaDonAdapter;
import fpt.edu.aptcoffee.adapter.NguoiDungAdapter;
import fpt.edu.aptcoffee.adapter.SpinnerAdapterLoaiHang;
import fpt.edu.aptcoffee.adapter.ThucUongAdapter;
import fpt.edu.aptcoffee.dao.HangHoaDAO;
import fpt.edu.aptcoffee.dao.HoaDonDAO;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.interfaces.ItemHangHoaOnClick;
import fpt.edu.aptcoffee.interfaces.ItemHoaDonOnClick;
import fpt.edu.aptcoffee.interfaces.ItemNguoiDungOnClick;
import fpt.edu.aptcoffee.model.HangHoa;
import fpt.edu.aptcoffee.model.HoaDon;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.utils.MyToast;

public class SearchFragment extends Fragment {
    EditText edtSearch;
    Spinner spFill;
    RecyclerView listSearch;
    HangHoaDAO hangHoaDAO;
    HoaDonDAO hoaDonDAO;
    NguoiDungDAO nguoiDungDAO;
    ImageView ivFilter;
    TextView tvNone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);

        hangHoaDAO = new HangHoaDAO(getContext());
        hoaDonDAO = new HoaDonDAO(getContext());
        nguoiDungDAO = new NguoiDungDAO(getContext());

        loadSpinnerFillter();
        initLayouListSearch();


        // Spinner l???c lo???i h??ng ch???n Item
        spFill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listSearch.setVisibility(View.GONE); // ???n
                tvNone.setVisibility(View.VISIBLE); // Hi???n th???
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Th???c hi???n t??m ki???m
                if (getTextSearch().isEmpty()) {
                    MyToast.error(getContext(), "Vui l??ng nh???p n???i dung t??m ki???m");
                } else {
                    // D??? li???u t??m ki???m
                    String dataSpinerFill = (String) spFill.getSelectedItem();
                    switch (dataSpinerFill) {
                        case "Ch???n n???i dung t??m ki???m....":
                            MyToast.error(getContext(), "Vui l??ng ch???n n???i dung t??m ki???m");
                            listSearch.setVisibility(View.GONE);
                            tvNone.setVisibility(View.VISIBLE);
                            break;
                        case "Ho?? ????n":
                            searchHoaDon();
                            break;
                        case "Th???c u???ng":
                            searchHangHoa();
                            break;
                        default:
                            searchNguoiDung();
                            break;
                    }
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        edtSearch = view.findViewById(R.id.editSearch);
        spFill = view.findViewById(R.id.spFill);
        listSearch = view.findViewById(R.id.listSearch);
        ivFilter = view.findViewById(R.id.ivFilter);
        tvNone = view.findViewById(R.id.tvNone);
        tvNone.setVisibility(View.VISIBLE);
    }

    private void initLayouListSearch() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listSearch.setLayoutManager(linearLayoutManager);
    }

    @NonNull
    private String getTextSearch() {
        return edtSearch.getText().toString().trim();
    }

    private void loadSpinnerFillter() {
        SpinnerAdapterLoaiHang spinnerAdapterLoaiHang = new SpinnerAdapterLoaiHang(getContext(), getListSpinerFillter());
        spFill.setAdapter(spinnerAdapterLoaiHang);
    }

    @NonNull
    private ArrayList<String> getListSpinerFillter() {
        // Danh s??ch danh m???c t??m ki???m
        ArrayList<String> list = new ArrayList<>();
        list.add("Ch???n n???i dung t??m ki???m....");
        list.add("Ho?? ????n");
        list.add("Th???c u???ng");
        list.add("Nh??n vi??n");

        return list;
    }

    private void searchNguoiDung() {
        // L???y danh s??ch theo n???i d??ng t??m ki???m
        ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
        for (NguoiDung nguoiDung : nguoiDungDAO.getAllPositionNhanVien()) {
            if (String.valueOf(nguoiDung.getHoVaTen()).contains(getTextSearch())) {
                listNguoiDung.add(nguoiDung);
            }
        }

        // Load d??? li???u cho danh s??ch ng?????i d??ng
        NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(listNguoiDung, new ItemNguoiDungOnClick() {
            @Override
            public void itemOclick(View view, NguoiDung nguoiDung) {

            }
        });
        listSearch.setAdapter(nguoiDungAdapter);
        /*
        * Ki???m tra s??? l?????ng danh s??ch ng?????i d??ng
        * N???u s??? l?????ng danh s??ch = 0: Hi???n th??? tvNone ???n danh s??ch
        * Ng?????c l???i s??? l?????ng danh s??ch > 0: Hi???n th??? danh s??ch, ???n tvNone
        * */
        if (listNguoiDung.size() == 0) {
            listSearch.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        } else {
            listSearch.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        }
    }

    private void searchHangHoa() {
        // L???y danh s??ch theo n???i d??ng t??m ki???m
        ArrayList<HangHoa> listHangHoa = new ArrayList<>();
        for (HangHoa hangHoa : hangHoaDAO.getAll()) {
            if (String.valueOf(hangHoa.getTenHangHoa()).contains(getTextSearch())) {
                listHangHoa.add(hangHoa);
            }
        }
        // Load danh s??ch th???c u???ng
        ThucUongAdapter thucUongAdapter = new ThucUongAdapter(listHangHoa, new ItemHangHoaOnClick() {
            @Override
            public void itemOclick(View view, HangHoa hangHoa) {

            }
        });
        /*
         * Ki???m tra s??? l?????ng danh s??ch h??ng ho??
         * N???u s??? l?????ng danh s??ch = 0: Hi???n th??? tvNone ???n danh s??ch
         * Ng?????c l???i s??? l?????ng danh s??ch > 0: Hi???n th??? danh s??ch, ???n tvNone
         * */
        if (listHangHoa.size() == 0) {
            listSearch.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        } else {
            listSearch.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        }
        listSearch.setAdapter(thucUongAdapter);
    }

    private void searchHoaDon() {
        // L???y danh s??ch theo n???i d??ng t??m ki???m
        ArrayList<HoaDon> listHoaDon = new ArrayList<>();
        for (HoaDon hoaDon : hoaDonDAO.getByTrangThai(HoaDon.DA_THANH_TOAN)) {
            if (String.valueOf(hoaDon.getMaHoaDon()).contains(getTextSearch())) {
                listHoaDon.add(hoaDon);
            }
        }
        // Load d??? li???u cho danh s??ch ho?? ????n
        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), listHoaDon, new ItemHoaDonOnClick() {
            @Override
            public void itemOclick(View view, HoaDon hoaDon) {

            }
        });
        listSearch.setAdapter(hoaDonAdapter);
        /*
         * Ki???m tra s??? l?????ng danh s??ch ho?? ????n
         * N???u s??? l?????ng danh s??ch = 0: Hi???n th??? tvNone ???n danh s??ch
         * Ng?????c l???i s??? l?????ng danh s??ch > 0: Hi???n th??? danh s??ch, ???n tvNone
         * */
        if (listHoaDon.size() == 0) {
            listSearch.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        } else {
            listSearch.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset form
        edtSearch.setText("");
        listSearch.setVisibility(View.GONE);
        tvNone.setVisibility(View.VISIBLE);
    }
}