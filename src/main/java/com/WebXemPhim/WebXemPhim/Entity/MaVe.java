package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ma_ve")
public class MaVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ma_ve")
    private int idMaVe;
    @Column(name = "masove")
    private String maSoVe;
    @Column(name = "trangthai")
    private int trangThai;
    @Column(name = "created_at")
    private Date createdAt;

    public MaVe() {
    }

    public MaVe(int idMaVe, String maSoVe, int trangThai, Date createdAt) {
        this.idMaVe = idMaVe;
        this.maSoVe = maSoVe;
        this.trangThai = trangThai;
        this.createdAt = createdAt;
    }

    public int getIdMaVe() {
        return idMaVe;
    }

    public void setIdMaVe(int idMaVe) {
        this.idMaVe = idMaVe;
    }

    public String getMaSoVe() {
        return maSoVe;
    }

    public void setMaSoVe(String maSoVe) {
        this.maSoVe = maSoVe;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
