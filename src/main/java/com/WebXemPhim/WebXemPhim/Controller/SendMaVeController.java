package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.DTO.ThongTinVe;
import com.WebXemPhim.WebXemPhim.Entity.*;
import com.WebXemPhim.WebXemPhim.Repository.*;
import com.WebXemPhim.WebXemPhim.Service.Impl.ChoNgoiImpl;
import com.WebXemPhim.WebXemPhim.Service.Impl.VeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@RestController
public class SendMaVeController {
    @Autowired
    private ChoNgoiRepo choNgoiRepo;
    @Autowired
    private MaVeRepo maVeRepo;
    @Autowired
    private DatVeRepository datVeRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LichSuDatVerepo lichSuDatVerepo;
    @Autowired
    private ChoNgoiImpl choNgoi;
    @Autowired
    private VeImpl veImpl;
    public SendMaVeController(ChoNgoiRepo choNgoiRepo, MaVeRepo maVeRepo, DatVeRepository datVeRepository, UserRepo userRepo, LichSuDatVerepo lichSuDatVerepo) {
        this.choNgoiRepo = choNgoiRepo;
        this.maVeRepo = maVeRepo;
        this.datVeRepository = datVeRepository;
        this.userRepo = userRepo;
        this.lichSuDatVerepo = lichSuDatVerepo;
    }

    @PostMapping("/sendMaVe")
    public ResponseEntity<Object> sendMaVe(@RequestParam("idUser") int idUser, @RequestParam("idSuatChieu") int idSuatChieu){
        // post vào lịch sưr đặt vé
       MaVe ve = maVeRepo.getMaVe(idSuatChieu);
       if(ve != null)
       {
       User user = userRepo.findById(idUser);
       if(user != null) {
           Date currentDate = Calendar.getInstance().getTime();
           LichSuDatVe lichSuDatVe = new LichSuDatVe();
           lichSuDatVe.setMaVe(ve);
           lichSuDatVe.setNgayMua(currentDate);
           lichSuDatVe.setUser(user);
           lichSuDatVerepo.save(lichSuDatVe);
           // set trạng thái ghế về 0
           ChoNgoi editTrangThai = choNgoiRepo.findById(ve.getDatVe().getChoNgoi().getId_cho_ngoi());
           editTrangThai.setTrangThai(0);
           choNgoiRepo.save(editTrangThai);
           // set trạng thái vé về 0
           ve.setTrangThai(0);
           maVeRepo.save(ve);
           // gui thong tin ve
           ThongTinVe thongTinVe = new ThongTinVe();
           thongTinVe.setDiaDiem(lichSuDatVe.getMaVe().getDatVe().getDiaDiem().getDia_chi());
           thongTinVe.setGioChieu(lichSuDatVe.getMaVe().getDatVe().getGioChieu().getGio_chieu());
           thongTinVe.setNgayChieu(lichSuDatVe.getMaVe().getDatVe().getNgayChieu().getNgayChieu());
           thongTinVe.setNgayMua(lichSuDatVe.getNgayMua());
           thongTinVe.setTenPhim(lichSuDatVe.getMaVe().getDatVe().getPhim().getTenPhim());
           thongTinVe.setTenGhe(lichSuDatVe.getMaVe().getDatVe().getChoNgoi().getCho_ngoi());
           thongTinVe.setTenRap(lichSuDatVe.getMaVe().getDatVe().getLoaiRap().getLoai_rap());
           thongTinVe.setMaVe(lichSuDatVe.getMaVe().getMaSoVe());
           thongTinVe.setAnhPhim(lichSuDatVe.getMaVe().getDatVe().getPhim().getAnhPhim());
           return new ResponseEntity<>(thongTinVe, HttpStatus.OK);
       }
       else{
           return new ResponseEntity<>("User không tồn tại", HttpStatus.NOT_FOUND);
       }
       }
       else{
           return new ResponseEntity<>("Không tìm thấy vé", HttpStatus.NOT_FOUND);
       }

    }
}
