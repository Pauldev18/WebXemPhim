package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import com.WebXemPhim.WebXemPhim.Entity.LichSuDatVe;
import com.WebXemPhim.WebXemPhim.Entity.MaVe;
import com.WebXemPhim.WebXemPhim.Entity.User;
import com.WebXemPhim.WebXemPhim.Repository.*;
import com.WebXemPhim.WebXemPhim.Service.Impl.ChoNgoiImpl;
import com.WebXemPhim.WebXemPhim.Service.Impl.VeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String  sendMaVe(@RequestParam("idUser") int idUser, @RequestParam("idSuatChieu") int idSuatChieu){
        MaVe ve = maVeRepo.getRandomVe();
        DatVe datVe = datVeRepository.getTTSuatChieu(idSuatChieu);
        User user = userRepo.findById(idUser);
        LichSuDatVe newLichSuDatVe = new LichSuDatVe();
        newLichSuDatVe.setMaVe(ve);
        newLichSuDatVe.setSuatChieu(datVe);
        newLichSuDatVe.setUser(user);
        lichSuDatVerepo.save(newLichSuDatVe);
        // update trang thai ghe
        choNgoi.updateTrangThai(datVe.getChoNgoi().getId_cho_ngoi());
        // update trang thái ve
        veImpl.updateTrangThaiVe(ve.getIdMaVe());

        return "thêm thành công";
    }
}
