package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "gio_chieu")
public class GioChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gio_chieu")
    private int id_gio_chieu;

    @Column(name = "gio_chieu")
    private Date gio_chieu;

    // Thêm các trường dữ liệu và getter/setter cần thiết

    public int getId_gio_chieu() {
        return id_gio_chieu;
    }

    public void setId_gio_chieu(int id_gio_chieu) {
        this.id_gio_chieu = id_gio_chieu;
    }

    public Date getGio_chieu() {
        return gio_chieu;
    }

    public void setGio_chieu(Date gio_chieu) {
        this.gio_chieu = gio_chieu;
    }
}