package fpt.edu.aptcoffee.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.aptcoffee.database.CoffeeDB;
import fpt.edu.aptcoffee.model.Ban;

public class BanDAO {
    CoffeeDB coffeeDB;
    public BanDAO(Context context){
        this.coffeeDB = new CoffeeDB(context);
    }

    //LAY DU LIEU
    @SuppressLint("Range")
    public ArrayList<Ban>get(String sql, String ... selectionArgs){
        ArrayList<Ban> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = coffeeDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Ban ban = new Ban();
                ban.setMaBan(cursor.getInt(cursor.getColumnIndex("maBan")));
                ban.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));

                list.add(ban);
                Log.i("TAG", ban.toString());
            }while (cursor.moveToNext());
        }
        return list;

    }

    //LAY TAT CA DU LIEU
    public ArrayList<Ban> getAll(){
        String sqlGetAll = "SELECT * FROM BAN";
        return get(sqlGetAll);
    }

    //THEM BAN MOI
    public boolean insertNguoiDung(Ban object){
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangThai", object.getTrangThai());

        long checkInsert = sqLiteDatabase.insert("BAN", null, values);
        return checkInsert != -1;
    }

    //XOA BAN
    public boolean deleteBan(String maBan){
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();

        int checkDelete = sqLiteDatabase.delete("BAN", "maBan=?", new String[]{maBan});
        return checkDelete>0;

    }

    public boolean updateBan(Ban ban){
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangThai", ban.getTrangThai());
        int check = sqLiteDatabase.update("BAN", values, "maBan=?", new String[]{String.valueOf(ban)});

        return check >0;
    }
}
