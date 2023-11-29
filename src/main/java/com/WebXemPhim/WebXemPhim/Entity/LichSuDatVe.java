package com.WebXemPhim.WebXemPhim.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lich_su_dat_ve")
public class LichSuDatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lsdv")
    private int idLSDV;
    @ManyToOne
    @JoinColumn(name = "id_suat_chieu")
    private DatVe suatChieu;
    @ManyToOne
    @JoinColumn(name = "id_ma_ve")
    private MaVe maVe;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public LichSuDatVe() {
    }

    public LichSuDatVe(int idLSDV, DatVe suatChieu, MaVe maVe, User user) {
        this.idLSDV = idLSDV;
        this.suatChieu = suatChieu;
        this.maVe = maVe;
        this.user = user;
    }

    public int getIdLSDV() {
        return idLSDV;
    }

    public void setIdLSDV(int idLSDV) {
        this.idLSDV = idLSDV;
    }

    public DatVe getSuatChieu() {
        return suatChieu;
    }

    public void setSuatChieu(DatVe suatChieu) {
        this.suatChieu = suatChieu;
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
}
