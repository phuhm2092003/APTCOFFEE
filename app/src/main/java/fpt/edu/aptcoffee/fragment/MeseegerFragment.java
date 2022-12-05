package fpt.edu.aptcoffee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.Objects;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.ThongBaoAdapter;
import fpt.edu.aptcoffee.dao.ThongBaoDAO;
import fpt.edu.aptcoffee.model.ThongBao;
import fpt.edu.aptcoffee.utils.MyToast;


public class MeseegerFragment extends Fragment {
    RecyclerView recyclerViewThongBao;
    ThongBaoDAO thongBaoDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meseeger, container, false);
        initView(view);
        thongBaoDAO = new ThongBaoDAO(getContext());
        loadData();

        return view;
    }

    private void initView(View view) {
        recyclerViewThongBao = view.findViewById(R.id.recyclerViewThongBao);
    }

    private void loadData() {
        // Show list Thông báo
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewThongBao.setLayoutManager(linearLayoutManager);
        ThongBaoAdapter adapter = new ThongBaoAdapter(getContext(), thongBaoDAO.getAll());
        recyclerViewThongBao.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        // Cập nhật lại thông báo về trạng thái đã xem
        for (ThongBao thongBao : thongBaoDAO.getAll()){
            thongBao.setTrangThai(ThongBao.STATUS_DA_XEM);
            if(thongBaoDAO.updateThongBao(thongBao)){
                Log.i("TAG", "TAG: Cập nhật thông báo thành công");
            }
        }

    }
}