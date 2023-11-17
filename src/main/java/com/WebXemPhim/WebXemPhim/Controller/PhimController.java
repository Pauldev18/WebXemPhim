package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.Entity.Phim;
import com.WebXemPhim.WebXemPhim.Repository.PhimRepository;
import com.WebXemPhim.WebXemPhim.Service.IStorageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
@CrossOrigin
@RestController
public class PhimController {
    @Autowired
    private PhimRepository phimRepository;
    @Autowired
    private IStorageService iStorageService;

    public PhimController(PhimRepository phimRepository) {
        this.phimRepository = phimRepository;
    }
    @GetMapping("/phim")
    public List<Phim> getAllPhim(@RequestParam("idTinh") int id) {
        return phimRepository.findByTinhAndNgayChieu(id);
    }
    @GetMapping("/allphim")
    public List<Phim> getAll(){
        return phimRepository.findAll();
    }

    @PostMapping("/upfilm")
    public ResponseEntity<Object> upFilm(@RequestParam("tenPhim") String tenPhim,  @RequestParam("anhPhim") MultipartFile file,
                                          @RequestParam("theLoai") String theLoai, @RequestParam("thoiLuong") String thoiLuong,
                                          @RequestParam("khoiChieu") String khoiChieu, @RequestParam("daoDien") String daoDien,
                                          @RequestParam("dienVien") String dienVien,  @RequestParam("ngonNgu") String ngonNgu,
                                          @RequestParam("danhGia") String danhGia, @RequestParam("noiDung") String noiDung)
    {
        try {
            String generatedFileName = iStorageService.storeFile(file);
            Phim newPhim = new Phim();
            newPhim.setTenPhim(tenPhim);
            newPhim.setAnhPhim(generatedFileName);
            newPhim.setTheLoai(theLoai);
            newPhim.setThoiLuong(thoiLuong);
            newPhim.setKhoiChieu(khoiChieu);
            newPhim.setDaoDien(daoDien);
            newPhim.setDienVien(dienVien);
            newPhim.setNgonNgu(ngonNgu);
            newPhim.setDanhGia(danhGia);
            newPhim.setNoiDung(noiDung);

            phimRepository.save(newPhim);
            return ResponseEntity.status(HttpStatus.OK).body("upload phim thanh cong");
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi");
        }
    }

    @GetMapping("/getPhimByID/{IdPhim}")
    public Phim getFilmByID(@PathVariable int IdPhim){
        return phimRepository.findById(IdPhim);
    }
    @GetMapping("/getPhimByName")
    public List<Phim> getPhimByName(@RequestParam String name){
        return phimRepository.finByName(name);
    }
}
