package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gia_tien")
public class GiaTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gia_tien")
    private int id_gia_tien;

    @Column(name = "gia_tien")
    private Double gia_tien;

    // Thêm các trường dữ liệu và getter/setter cần thiết

    public int getId_gia_tien() {
        return id_gia_tien;
    }

    public void setId_gia_tien(int id_gia_tien) {
        this.id_gia_tien = id_gia_tien;
    }

    public Double getGia_tien() {
        return gia_tien;
    }

    public void setGia_tien(Double gia_tien) {
        this.gia_tien = gia_tien;
    }
}
