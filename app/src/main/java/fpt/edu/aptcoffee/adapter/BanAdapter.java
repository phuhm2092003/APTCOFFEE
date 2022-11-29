package fpt.edu.aptcoffee.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.interfaces.ItemLoaiHangOnClick;
import fpt.edu.aptcoffee.model.Ban;
import fpt.edu.aptcoffee.model.LoaiHang;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.BanViewHolder> {
    ArrayList<Ban> list;

    public BanAdapter(ArrayList<Ban> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ban, parent, false);
        return new BanViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        Ban ban = list.get(position);
        if (ban == null){
            return;
        }
        if(ban.getTrangThai() == Ban.CON_TRONG){
            holder.ivHinhAnh.setImageResource(R.drawable.ic_quan_ly_ban_24_black);
        }else {
            holder.ivHinhAnh.setImageResource(R.drawable.ic_quan_ly_ban_24_brow);
        }
        holder.tvMaBan.setText("BO"+ban.getMaBan());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class BanViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnh;
        TextView tvMaBan;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            tvMaBan = itemView.findViewById(R.id.tvMaBan);
        }
    }
}
