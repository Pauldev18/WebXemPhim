package com.WebXemPhim.WebXemPhim.Repository;

import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DatVeRepository extends JpaRepository<DatVe, Integer> {
    @Query(value = "SELECT * FROM dat_ve WHERE id_tinh = ?1", nativeQuery = true)
    List<DatVe> findByIdTinh(int id);
    @Query(value = "SELECT * FROM dat_ve WHERE id_ngay_chieu = ?1", nativeQuery = true)
    List<DatVe> getTinhByNgay(int id);
    @Query(value = "SELECT * FROM dat_ve WHERE id_ngay_chieu = ?1 AND id_phim = ?2", nativeQuery = true)
    List<DatVe> getDiaDiemByTinhAndPhim(int idNgayChieu, int idPhim);
    @Query(value = "SELECT * FROM dat_ve WHERE id_phim = ?1", nativeQuery = true)
    List<DatVe> findNgayByIdPhim(int id);
    @Query(value = "SELECT * FROM dat_ve WHERE " +
            "id_phim = ?1 AND id_ngay_chieu = ?2 AND id_tinh = ?3 AND id_dia_diem = ?4", nativeQuery = true)
    List<DatVe> findByRap(int idPhim,int idNgayChieu, int idTinh, int idDiaDiem);

    @Query(value = "SELECT * FROM dat_ve WHERE " +
            "id_phim = ?1 AND id_ngay_chieu = ?2 AND id_tinh = ?3 AND id_dia_diem = ?4 AND id_loai_rap = ?5", nativeQuery = true)
    List<DatVe> findByGioChieu(int idPhim,int idNgayChieu, int idTinh, int idDiaDiem, int idLoaiRap);
    @Query(value = "SELECT * FROM dat_ve WHERE " +
            "id_phim = ?1 AND id_ngay_chieu = ?2 AND id_tinh = ?3 AND id_dia_diem = ?4 AND id_loai_rap = ?5 AND id_gio_chieu = ?6", nativeQuery = true)
    List<DatVe> findByChoNgoi(int idPhim,int idNgayChieu, int idTinh, int idDiaDiem, int idLoaiRap, int idGioChieu);


}
