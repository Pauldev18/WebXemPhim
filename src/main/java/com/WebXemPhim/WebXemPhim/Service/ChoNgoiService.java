package com.WebXemPhim.WebXemPhim.Service;

import com.WebXemPhim.WebXemPhim.Entity.ChoNgoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface ChoNgoiService {
    void updateTrangThai(int idChoNgoi);
}
