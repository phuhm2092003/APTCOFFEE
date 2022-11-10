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
import fpt.edu.aptcoffee.model.NguoiDung;

public class NguoiDungDAO {
    CoffeeDB coffeeDB;
    public NguoiDungDAO(Context context){
        this.coffeeDB = new CoffeeDB(context);

    }
    @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyy");

    @SuppressLint("Range")
    public ArrayList<NguoiDung>get(String sql, String ... selectionArgs) {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = coffeeDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();


            do {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setMaNguoiDung(cursor.getString(cursor.getColumnIndex("maNguoiDung")));
                nguoiDung.setHoVaTen(cursor.getString(cursor.getColumnIndex("hoVaTen")));
                nguoiDung.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
                nguoiDung.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                nguoiDung.setChucVu(cursor.getString(cursor.getColumnIndex("chucVu")));
                nguoiDung.setGioiTinh(cursor.getString(cursor.getColumnIndex("gioiTinh")));
                nguoiDung.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("hinhAnh")));
                try {
                    nguoiDung.setNgaySinh(spf.parse(cursor.getString(cursor.getColumnIndex("ngaySinh"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                list.add(nguoiDung);
                Log.i("TAG", nguoiDung.toString());

            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<NguoiDung> getAll(){
        //Lay tat ca du lieu

        String sqlGetAll = "SELECT * FORM NGUOIDUNG";
        return get(sqlGetAll);
    }


    //THEM MOI NGUOI DUNG
    public boolean insertNguoiDung(NguoiDung object){

        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("hoVaTen", object.getHoVaTen());
        values.put("email", object.getEmail());
        values.put("hinhAnh", object.getHinhAnh());
        values.put("ngaySinh", String.valueOf(object.getNgaySinh()));
        values.put("chucVu", object.getChucVu());
        values.put("gioiTinh", object.getGioiTinh());
        values.put("matKhau", object.getMatKhau());

        long checkInsert = sqLiteDatabase.insert("NGUOIDUNG", null, values);
        return checkInsert != -1;
    }

    //CAP NHAT THONG TIN NGUOI DUNG

    public boolean updateNguoiDung(NguoiDung object){
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("hoVaTen", object.getHoVaTen());
        values.put("matKhau", object.getMatKhau());
        values.put("hinhAnh", object.getHinhAnh());
        values.put("email", object.getEmail());
        values.put("ngaySinh", String.valueOf(object.getNgaySinh()));
        values.put("chucVu", object.getChucVu());
        values.put("gioiTinh", object.getGioiTinh());

        int checkUpdate = sqLiteDatabase.update("NGUOIDUNG", values, "maNguoiDung=?", new String[]{String.valueOf(object.getMaNguoiDung())});
        return checkUpdate > 0;

    }

    //XOA NGUOI DUNG
    public boolean deleteNguoiDung(String maNguoiDung){
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();

        int checkDelete = sqLiteDatabase.delete("NGUOIDUNG", "maNguoiDung=?", new String[]{maNguoiDung});
        return checkDelete >0;
    }

    //LAY RA 1 NGUOI DUNG THEO MA ND
    public NguoiDung getByMaNguoiDung(String  maNguoiDung){
        String sqlGetMaNguoiDung = "SELECT * FROM NGUOIDUNG WHERE maNguoiDung=?";
        ArrayList<NguoiDung> list = get(sqlGetMaNguoiDung, maNguoiDung);
        return list.get(0);

    }

    public boolean checkLogin(String tenDangNhap, String matKhau){
        String sqlCheckLogin = "SELECT FROM NGUOIDUNG WHERE maNguoiDung=? AND matKhau=?";
        ArrayList<NguoiDung> list = get(sqlCheckLogin, tenDangNhap, matKhau);
        return list.size() != 0;
    }

}
