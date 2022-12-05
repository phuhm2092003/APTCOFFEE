package fpt.edu.aptcoffee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.model.ThongBao;
import fpt.edu.aptcoffee.utils.XDate;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {
    Context context;
    ArrayList<ThongBao> list;

    public ThongBaoAdapter(Context context, ArrayList<ThongBao> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = list.get(position);
        if(thongBao == null){
            return;
        }

        holder.tvNoiDung.setText(thongBao.getNoiDung());
        holder.tvNgayThongBao.setText(XDate.toStringDate(thongBao.getNgayThongBao()));

        if(thongBao.getTrangThai() == ThongBao.STATUS_CHUA_XEM){
            holder.ivHinhAnh.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_thong_bao_chua_xem));
        }else {
            holder.ivHinhAnh.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_thong_bao_da_xem));
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public static class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoiDung, tvNgayThongBao;
        RelativeLayout relativeLayout;
        ImageView ivHinhAnh;
        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            tvNgayThongBao = itemView.findViewById(R.id.tvNgayThongBao);
            relativeLayout = itemView.findViewById(R.id.layoutThongBao);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
        }
    }
}