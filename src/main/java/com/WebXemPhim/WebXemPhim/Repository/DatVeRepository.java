package com.WebXemPhim.WebXemPhim.Repository;

import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Query(value = "SELECT t.tinh  FROM phim p, ngay_chieu nc, tinh t, phim_ngay_chieu pnc, ngay_chieu_tinh nct WHERE t.id_tinh = ?1 AND nc.id_ngay_chieu = ?2 and p.id_phim = pnc.id_phim and nc.id_ngay_chieu = pnc.id_ngay_chieu and nc.id_ngay_chieu = nct.id_ngay_chieu and t.id_tinh = nct.id_tinh", nativeQuery = true)
    List<Map<String, Object>> getTinh(int idTinh, int idNgayChieu);



}
