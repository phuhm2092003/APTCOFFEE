package fpt.edu.aptcoffee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoffeeDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "APTCoffee";
    public static final int DB_VERSION = 1;
    public static final String TABLE_BAN = "CREATE TABLE BAN(" +
            "maBan INTEGER PRIMARY KEY AUTOINCREMENT," +
            "trangThai INTEGER NOT NULL)";

    public static final String TABLE_NGUOIDUNG = "CREATE TABLE NGUOIDUNG(" +
            "maNguoiDung TEXT PRIMARY KEY," +
            "hoVaTen TEXT NOT NULL," +
            "hinhAnh BLOB," +
            "ngaySinh DATE NOT NULL," +
            "email TEXT NOT NULL," +
            "chucVu TEXT NOT NULL," +
            "gioiTinh TEXT NOT NULL," +
            "matKhau TEXT NOT NULL)";

    public static final String TABLE_LOAIHANG = "CREATE TABLE LOAIHANG(" +
            "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hinhAnh BLOB ," +
            "tenLoai TEXT NOT NULL)";

    public static final String TABLE_HOADON = "CREATE TABLE HOADON(" +
            "maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT," +
            "maBan INTEGER REFERENCES BAN(maBan)," +
            "maNguoiDung TEXT REFERENCES NGUOIDUNG(maNguoiDung)," +
            "gioVao DATE NOT NULL," +
            "gioRa DATE NOT NULL)";

    public static final String TABLE_HANGHOA = "CREATE TABLE HANGHOA(" +
            "maHangHoa INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hinhAnh BLOB," +
            "tenHangHoa TEXT NOT NULL," +
            "giaTien INTEGER NOT NULL, " +
            "maLoai INTEGER REFERENCES LOAIHANG(maLoai)," +
            "trangThai INTEGER NOT NULL)";

    public static final String TABLE_HOADONCHITIET = "CREATE TABLE HOADONCHITIET(" +
            "maHDCT INTEGER PRIMARY KEY AUTOINCREMENT," +
            "maHoaDon INTEGER REFERENCES HOADON(maHoaDon)," +
            "maHangHoa INTEGER REFERENCES HANGHOA(maHangHoa)," +
            "soLuong INTEGER NOT NULL," +
            "giaTien INTEGER NOT NULL, ghiChu TEXT," +
            "ngayXuatHoaDon DATE NOT NULL)";

    public CoffeeDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TẠO TABLE BÀN
        sqLiteDatabase.execSQL(TABLE_BAN);
        // TẠO TABLE NGƯỜI DUNG
        sqLiteDatabase.execSQL(TABLE_NGUOIDUNG);
        // TẠO TABLE LOẠI HÀNG
        sqLiteDatabase.execSQL(TABLE_LOAIHANG);
        // TẠO TABLE HÓA ĐƠN
        sqLiteDatabase.execSQL(TABLE_HOADON);
        // TẠO TABLE HÀNG HÓA
        sqLiteDatabase.execSQL(TABLE_HANGHOA);
        // TẠO TABLE HÓA ĐƠN CHI TIẾT
        sqLiteDatabase.execSQL(TABLE_HOADONCHITIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BAN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAIHANG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HOADON");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HANGHOA");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HOADONCHITIET");

            onCreate(sqLiteDatabase);
        }

    }
}
