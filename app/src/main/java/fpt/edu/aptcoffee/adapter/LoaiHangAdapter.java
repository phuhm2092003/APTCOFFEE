package fpt.edu.aptcoffee.adapter;

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
import fpt.edu.aptcoffee.model.LoaiHang;

public class LoaiHangAdapter extends RecyclerView.Adapter<LoaiHangAdapter.LoaiHangViewHolder>{

    ArrayList<LoaiHang> list;

    public LoaiHangAdapter(ArrayList<LoaiHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LoaiHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loai_hang, parent, false);
        return new LoaiHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiHangViewHolder holder, int position) {

        LoaiHang  loaiHang = list.get(position);

        if (loaiHang == null){
            return;
        }
        holder.tvTenLoaiHang.setText(loaiHang.getTenLoai());
        // Get bitmap từ mảng Byte[]
        Bitmap bitmap = BitmapFactory.decodeByteArray(loaiHang.getHinhAnh(),
                0,
                loaiHang.getHinhAnh().length);
        holder.ivHinhAnh.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public static class LoaiHangViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnh;
        TextView tvTenLoaiHang;
        public LoaiHangViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            tvTenLoaiHang = itemView.findViewById(R.id.tvTenLoaiHang);
        }
    }
}
