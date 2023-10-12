package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.DTO.*;
import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import com.WebXemPhim.WebXemPhim.Repository.DatVeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.event.ListDataListener;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
public class DatVeController {

    @Autowired
    private DatVeRepository datVeRepository;
    @GetMapping("/datve")
    public List<DatVe> getById(@RequestParam("id") int id)
    {
        return datVeRepository.findByIdTinh(id);
    }
    @GetMapping("/gettinhbyngay")
    public ResponseEntity<Object> getTinhByNgay(@RequestParam("idNgayChieu") int id, HttpServletRequest request){
        String queryString = request.getQueryString(); // Lấy chuỗi truy vấn từ url

        if (queryString != null && queryString.split("&").length > 1) {
            return new ResponseEntity<Object>("Lỗi", HttpStatus.BAD_REQUEST);
        }
        List<DatVe> allVe = datVeRepository.getTinhByNgay(id);
        List<TinhByNgay> allTinh = allVe.stream().map(
                tinh -> {
                    TinhByNgay tinhByNgays = new TinhByNgay();
                    tinhByNgays.setTinh(tinh.getTinh().getTenTinh());
                    return tinhByNgays;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(allTinh, HttpStatus.OK);
    }
    @GetMapping("/getDiaDiemByTinhAndPhim")
    public ResponseEntity<List<DiaDiemByTinhAndPhim>> getDiaDiem(@RequestParam("idNgayChieu") int idNgayChieu, @RequestParam("idPhim") int idPhim){

        List<DatVe> allVe = datVeRepository.getDiaDiemByTinhAndPhim(idNgayChieu, idPhim);
        List<DiaDiemByTinhAndPhim> allDiaDiem = allVe.stream().map(
                diaDiem ->{
                    DiaDiemByTinhAndPhim diaDiems = new DiaDiemByTinhAndPhim();
                    diaDiems.setDiaDiem(diaDiem.getDiaDiem().getDia_chi());
                    return diaDiems;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(allDiaDiem, HttpStatus.OK);
    }
    @GetMapping("/getThoiGianByIdPhim")
    public ResponseEntity<List<NgayByIdPhim>> getNgay(@RequestParam("IDPhim") int idPhim){
        List<DatVe> allNgay = datVeRepository.findNgayByIdPhim(idPhim);
        List<NgayByIdPhim> ngays = allNgay.stream().map(ngay ->{
            NgayByIdPhim ngayByIdPhim = new NgayByIdPhim();
            ngayByIdPhim.setThoiGian(ngay.getNgayChieu().getNgayChieu());
            ngayByIdPhim.setId(ngay.getNgayChieu().getId());
            return ngayByIdPhim;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(ngays, HttpStatus.OK);
    }
    @GetMapping("rap")
    public ResponseEntity<List<RapDTO>> getAllRap(
            @RequestParam("IdPhim") int idPhim, @RequestParam("idNgayChieu") int idNgayChieu,
            @RequestParam("idTinh") int idTinh, @RequestParam("idDiaDiem") int idDiaDiem){
        List<DatVe> allRap = datVeRepository.findByRap(idPhim, idNgayChieu, idTinh, idDiaDiem);
        List<RapDTO> raps = allRap.stream().map(rap ->{
            RapDTO rapDTO = new RapDTO();
            rapDTO.setTenRap(rap.getLoaiRap().getLoai_rap());
            return rapDTO;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(raps, HttpStatus.OK);
    }
    @GetMapping("giochieu")
    public ResponseEntity<List<GioChieuDTO>> getGioChieu(
            @RequestParam("IdPhim") int idPhim, @RequestParam("idNgayChieu") int idNgayChieu,
            @RequestParam("idTinh") int idTinh, @RequestParam("idDiaDiem") int idDiaDiem, @RequestParam("idLoaiRap") int idLoaiRap
    ){
        List<DatVe> allGioChieu = datVeRepository.findByGioChieu(idPhim, idNgayChieu, idTinh, idDiaDiem, idLoaiRap);
        List<GioChieuDTO> times = allGioChieu.stream().map(time ->{
            GioChieuDTO gioChieuDTO = new GioChieuDTO();
            gioChieuDTO.setTime(time.getGioChieu().getGio_chieu());
            return gioChieuDTO;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(times, HttpStatus.OK);
    }
    @GetMapping("chongoi")
    public ResponseEntity<List<ChoNgoiDTO>> getChoNgoi(
            @RequestParam("IdPhim") int idPhim, @RequestParam("idNgayChieu") int idNgayChieu,
            @RequestParam("idTinh") int idTinh, @RequestParam("idDiaDiem") int idDiaDiem,
            @RequestParam("idLoaiRap") int idLoaiRap, @RequestParam("idGioChieu") int idGioChieu
    ){
        List<DatVe> allChoNgoi = datVeRepository.findByChoNgoi(idPhim, idNgayChieu, idTinh, idDiaDiem, idLoaiRap, idGioChieu);
        List<ChoNgoiDTO> slots = allChoNgoi.stream().map(
                slot ->{
                    ChoNgoiDTO choNgoiDTO = new ChoNgoiDTO();
                    choNgoiDTO.setChoNgoi(slot.getChoNgoi().getCho_ngoi());
                    return choNgoiDTO;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

}
