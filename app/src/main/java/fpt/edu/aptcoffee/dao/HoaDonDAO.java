package fpt.edu.aptcoffee.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpt.edu.aptcoffee.database.CoffeeDB;
import fpt.edu.aptcoffee.utils.XDate;
import fpt.edu.aptcoffee.model.HoaDon;

public class HoaDonDAO {
    CoffeeDB coffeeDB;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public HoaDonDAO(Context context) {
        this.coffeeDB = new CoffeeDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<HoaDon> get(String sql, String... choose) {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = coffeeDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, choose);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(cursor.getInt(cursor.getColumnIndex("maHoaDon")));
                hoaDon.setMaBan(cursor.getInt(cursor.getColumnIndex("maBan")));
                hoaDon.setMaNguoiDung(cursor.getString(cursor.getColumnIndex("maNguoiDung")));
                try {
                    hoaDon.setGioVao(XDate.toDateTime(cursor.getString(cursor.getColumnIndex("gioVao"))));
                    hoaDon.setGioRa(XDate.toDateTime(cursor.getString(cursor.getColumnIndex("gioRa"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                hoaDon.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
                list.add(hoaDon);
                Log.i("TAG", hoaDon.toString());
            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<HoaDon> getAll() {
        String sql = "SELECT * FROM HOADON";
        return get(sql);
    }

    public boolean insertHoaDon(HoaDon hoaDon) {
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maBan", hoaDon.getMaBan());
        values.put("maNguoiDung", hoaDon.getMaNguoiDung());
        values.put("gioVao", XDate.toStringDateTime(hoaDon.getGioVao()));
        values.put("gioRa", XDate.toStringDateTime(hoaDon.getGioRa()));
        values.put("trangThai", hoaDon.getTrangThai());
        long check = sqLiteDatabase.insert("HOADON", null, values);

        return check != -1;
    }

    public boolean updateHoaDon(HoaDon hoaDon) {
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maBan", hoaDon.getMaHoaDon());
        values.put("maNguoiDung", hoaDon.getMaNguoiDung());
        values.put("gioVao", XDate.toStringDateTime(hoaDon.getGioVao()));
        values.put("gioRa", XDate.toStringDateTime(hoaDon.getGioRa()));
        values.put("trangThai", hoaDon.getTrangThai());
        int check = sqLiteDatabase.update("HOADON", values, "maHoaDon=?", new String[]{String.valueOf(hoaDon.getMaHoaDon())});

        return check > 0;
    }

    public boolean deleteHoaDon(String maHoaDon) {
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        int check = sqLiteDatabase.delete("HOADON", "maHoaDon=?", new String[]{String.valueOf(maHoaDon)});

        return check > 0;
    }

    public HoaDon getByMaHoaDon(String maHoaDon) {
        String sql = "SELECT * FROM HOADON WHERE maHoaDon=?";
        ArrayList<HoaDon> list = get(sql, maHoaDon);

        return list.get(0);
    }
}
