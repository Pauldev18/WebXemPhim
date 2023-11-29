package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;
    @Column(name = "ten_user")
    private String tenUser;
    @Column(name = "tai_khoan")
    private String taiKhoan;
    @Column(name = "mat_khau")
    private String matKhau;
    @Column(name ="gmail")
    private String gmail;

    public User() {
    }

    public User(int idUser, String tenUser, String taiKhoan, String matKhau, String gmail) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.gmail = gmail;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
