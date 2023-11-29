package com.WebXemPhim.WebXemPhim.Repository;

import com.WebXemPhim.WebXemPhim.Entity.LichSuDatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuDatVerepo extends JpaRepository<LichSuDatVe, Integer> {
}
