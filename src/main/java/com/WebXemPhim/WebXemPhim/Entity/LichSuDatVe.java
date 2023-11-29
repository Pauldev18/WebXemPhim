package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lich_su_dat_ve")
public class LichSuDatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lsdv")
    private int idLSDV;
    @ManyToOne
    @JoinColumn(name = "id_ma_ve")
    private MaVe maVe;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "ngay_mua")
    private Date ngayMua;

    public LichSuDatVe() {
    }

    public LichSuDatVe(int idLSDV, MaVe maVe, User user, Date ngayMua) {
        this.idLSDV = idLSDV;
        this.maVe = maVe;
        this.user = user;
        this.ngayMua = ngayMua;
    }

    public int getIdLSDV() {
        return idLSDV;
    }

    public void setIdLSDV(int idLSDV) {
        this.idLSDV = idLSDV;
    }


    public MaVe getMaVe() {
        return maVe;
    }

    public void setMaVe(MaVe maVe) {
        this.maVe = maVe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }
}
