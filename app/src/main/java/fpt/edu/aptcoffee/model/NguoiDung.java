package fpt.edu.aptcoffee.model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Date;

public class NguoiDung {
    private int maNguoiDung;
    private String hoVaTen;
    private byte[] hinhAnh;
    private Date ngaySinh;
    private String email;
    private String chucVu;
    private String gioiTinh;
    private String matKhau;

    public NguoiDung() {
    }

    public NguoiDung(int maNguoiDung, String hoVaTen, byte[] hinhAnh, Date ngaySinh, String email, String chucVu, String gioiTinh, String matKhau) {
        this.maNguoiDung = maNguoiDung;
        this.hoVaTen = hoVaTen;
        this.hinhAnh = hinhAnh;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.matKhau = matKhau;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @NonNull
    @Override
    public String toString() {
        return "NguoiDung{" +
                "maNguoiDung=" + maNguoiDung +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", hinhAnh=" + Arrays.toString(hinhAnh) +
                ", ngaySinh=" + ngaySinh +
                ", email='" + email + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
