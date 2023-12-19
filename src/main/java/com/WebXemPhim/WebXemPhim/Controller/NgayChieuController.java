package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.Entity.NgayChieu;
import com.WebXemPhim.WebXemPhim.Repository.NgayChieuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class NgayChieuController {
    private final NgayChieuRepo ngayChieuRepo;
    @Autowired
    public NgayChieuController(NgayChieuRepo ngayChieuRepo) {
        this.ngayChieuRepo = ngayChieuRepo;
    }
    @PostMapping("/newNgayChieu")
    public ResponseEntity<Object> createNgayChieu(@RequestParam("ngayChieu") Date ngayChieu)
    {
        NgayChieu newNgayChieu = new NgayChieu();
        newNgayChieu.setNgayChieu(ngayChieu);
        ngayChieuRepo.save(newNgayChieu);
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }
}
