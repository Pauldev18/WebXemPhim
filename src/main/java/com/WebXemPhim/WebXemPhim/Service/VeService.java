package com.WebXemPhim.WebXemPhim.Service;

import com.WebXemPhim.WebXemPhim.Entity.MaVe;
import org.springframework.http.ResponseEntity;

public interface VeService {
    void updateTrangThaiVe(int idMaVe);
    MaVe genarateTicKet(int IDSuatChieu);

}
