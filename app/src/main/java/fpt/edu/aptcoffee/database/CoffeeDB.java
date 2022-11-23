package fpt.edu.aptcoffee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.utils.ImageToByte;
import fpt.edu.aptcoffee.utils.XDate;

public class CoffeeDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "APTCoffee";
    public static final int DB_VERSION = 1;
    public Context context;
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
            "hinhAnh BLOB," +
            "tenLoai TEXT NOT NULL)";

    public static final String TABLE_HOADON = "CREATE TABLE HOADON(" +
            "maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT," +
            "maBan INTEGER REFERENCES BAN(maBan)," +
            "gioVao DATE NOT NULL," +
            "gioRa DATE NOT NULL,"+
            "trangThai INTEGER NOT NULL)";

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
        this.context = context;
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

        String insertBan = "INSERT INTO BAN(trangThai) VALUES(?)";
        for (int i = 0; i < 12 ; i++){
            sqLiteDatabase.execSQL(insertBan, new Object[]{0});
        }
        String insertNguoiDung = "INSERT INTO NGUOIDUNG(maNguoiDung, hoVaTen, hinhAnh, ngaySinh, email, chucVu, gioiTinh, matKhau) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ADMIN", "ADMIN", ImageToByte.drawableToByte(context, R.drawable.avatar_user_md), "01-01-2000", "admin@gmail.com", "Admin", "Nam", 1212});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ND1", "Nguyễn Viết Tín", ImageToByte.drawableToByte(context, R.drawable.avatar_user_md), "01-01-2000", "tinthq@gmail.com", "NhanVien", "Nam", 1212});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ND2", "Trần Hồ Quốc An", ImageToByte.drawableToByte(context, R.drawable.avatar_user_md), "01-01-2000", "anthq@gmail.com", "NhanVien", "Nam", 1212});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ND3", "Hồ Minh Phú", ImageToByte.drawableToByte(context, R.drawable.avatar_user_md), "01-01-2000", "phuhm@gmail.com", "NhanVien", "Nam", 1212});

        String insertLoaiHang = "INSERT INTO LOAIHANG(hinhAnh, tenLoai) VALUES(?, ?)";
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context,R.drawable.sample_data_loai_hang_caphe), "Cà phê"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context,R.drawable.sample_data_loai_hang_nuocep), "Nước ép"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context,R.drawable.sample_data_loai_hang_soda), "Soda"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context,R.drawable.sample_data_loai_hang_trasua), "Trà sữa"});

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
