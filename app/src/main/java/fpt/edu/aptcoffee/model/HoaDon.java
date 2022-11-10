package fpt.edu.aptcoffee.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class HoaDon {
    private int maHoaDon;
    private int maBan;
    private int maNguoiDung;
    private Date gioVao;
    private Date gioRa;
    private int trangThai;

    public HoaDon() {
    }

    public HoaDon(int maBan, int maNguoiDung, Date gioVao, Date gioRa, int trangThai) {
        this.maBan = maBan;
        this.maNguoiDung = maNguoiDung;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.trangThai = trangThai;
    }

    public HoaDon(int maHoaDon, int maBan, int maNguoiDung, Date gioVao, Date gioRa, int trangThai) {
        this.maHoaDon = maHoaDon;
        this.maBan = maBan;
        this.maNguoiDung = maNguoiDung;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.trangThai = trangThai;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public Date getGioVao() {
        return gioVao;
    }

    public void setGioVao(Date gioVao) {
        this.gioVao = gioVao;
    }

    public Date getGioRa() {
        return gioRa;
    }

    public void setGioRa(Date gioRa) {
        this.gioRa = gioRa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @NonNull
    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", maBan=" + maBan +
                ", maNguoiDung=" + maNguoiDung +
                ", gioVao=" + gioVao +
                ", gioRa=" + gioRa +
                ", trangThai=" + trangThai +
                '}';
    }
}
