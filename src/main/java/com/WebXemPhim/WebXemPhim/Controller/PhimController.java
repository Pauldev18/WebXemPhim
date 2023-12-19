package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.DTO.PhimDTO;
import com.WebXemPhim.WebXemPhim.Entity.Phim;
import com.WebXemPhim.WebXemPhim.Repository.PhimRepository;
import com.WebXemPhim.WebXemPhim.Service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Date;
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
//    @GetMapping("/phim")
//    public List<Phim> getAllPhim(@RequestParam("idTinh") int id) {
//        return phimRepository.findByTinhAndNgayChieu(id);
//    }
    @GetMapping("/allphim")
    public List<Phim> getAll(){
        return phimRepository.findAll();
    }

    @PostMapping("/upfilm")
    public ResponseEntity<Object> upFilm(@RequestParam("tenPhim") String tenPhim, @RequestParam("anhPhim") MultipartFile file,
                                         @RequestParam("theLoai") String theLoai, @RequestParam("thoiLuong") String thoiLuong,
                                         @RequestParam("khoiChieu") String khoiChieu, @RequestParam("daoDien") String daoDien,
                                         @RequestParam("dienVien") String dienVien, @RequestParam("ngonNgu") String ngonNgu,
                                         @RequestParam("danhGia") String danhGia, @RequestParam("noiDung") String noiDung,
                                         @RequestParam("tinhTrang") int tinhTrang)
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
            newPhim.setTinhTrang(tinhTrang);
            phimRepository.save(newPhim);
            return ResponseEntity.status(HttpStatus.OK).body("upload phim thanh cong");
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi");
        }
    }
    @PutMapping("/editPhim/{IDPhim}")
    public ResponseEntity<Object> editPhim(@RequestBody PhimDTO phimDTO, @PathVariable int IDPhim){
        Phim editPhim = phimRepository.findByIdPhim(IDPhim);
       if(editPhim != null){
           editPhim.setAnhPhim(phimDTO.getAnhPhim());
           editPhim.setDanhGia(phimDTO.getDanhGia());
           editPhim.setTinhTrang(phimDTO.getTinhTrang());
           editPhim.setTenPhim(phimDTO.getTenPhim());
           editPhim.setTheLoai(phimDTO.getTheLoai());
           editPhim.setThoiLuong(phimDTO.getThoiLuong());
           editPhim.setKhoiChieu(phimDTO.getKhoiChieu());
           editPhim.setDienVien(phimDTO.getDienVien());
           editPhim.setDaoDien(phimDTO.getDaoDien());
           editPhim.setNgonNgu(phimDTO.getNgonNgu());
           editPhim.setNoiDung(phimDTO.getNoiDung());
           phimRepository.save(editPhim);
           return new ResponseEntity<>(editPhim, HttpStatus.OK);
       }else{
           return new ResponseEntity<>("Không tìm thấy phim", HttpStatus.NOT_FOUND);
       }

    }
    @DeleteMapping("/deletePhim/{IDPhim}")
    public ResponseEntity<Object> deletePhim(@PathVariable int IDPhim){
        try{
            Phim deletePhim = phimRepository.findByIdPhim(IDPhim);
            if(deletePhim != null){
                phimRepository.delete(deletePhim);
                return new ResponseEntity<>("delete success", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Không tìm thấy phim", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPhimByID/{IdPhim}")
    public Phim getFilmByID(@PathVariable int IdPhim){
        return phimRepository.findByIdPhim(IdPhim);
    }
    @GetMapping("/getPhimByName")
    public List<Phim> getPhimByName(@RequestParam("name") String name){
        return phimRepository.finByName(name);
    }
    @GetMapping("/getPhimByTinhTrang/{tinhTrang}")
    public List<Phim> getPhimByTinhTrang(@PathVariable int tinhTrang){
        return phimRepository.findByTinhTrang(tinhTrang);
    }
}
