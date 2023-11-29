package com.WebXemPhim.WebXemPhim.Repository;

import com.WebXemPhim.WebXemPhim.Entity.MaVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MaVeRepo extends JpaRepository<MaVe, Integer> {
    @Query(value = "SELECT * FROM ma_ve WHERE trangthai = 1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MaVe getRandomVe();
    MaVe findById(int idMaVe);
}
