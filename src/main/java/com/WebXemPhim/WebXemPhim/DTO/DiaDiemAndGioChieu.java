package com.WebXemPhim.WebXemPhim.DTO;

import java.util.Date;
import java.util.List;

public class DiaDiemAndGioChieu {
    private String diaDiem;
    private List<GioChieuDTO> gioChieus;

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public List<GioChieuDTO> getGioChieus() {
        return gioChieus;
    }

    public void setGioChieus(List<GioChieuDTO> gioChieus) {
        this.gioChieus = gioChieus;
    }
}
