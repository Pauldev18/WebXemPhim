package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dia_diem")
public class DiaDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_diem")
    private Long id_dia_diem;

    @Column(name = "dia_chi")
    private String dia_chi;


    // Thêm các trường dữ liệu và getter/setter cần thiết

    public Long getId_dia_diem() {
        return id_dia_diem;
    }

    public void setId_dia_diem(Long id_dia_diem) {
        this.id_dia_diem = id_dia_diem;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

}