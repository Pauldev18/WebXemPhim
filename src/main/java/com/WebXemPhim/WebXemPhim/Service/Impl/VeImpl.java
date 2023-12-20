package com.WebXemPhim.WebXemPhim.Service.Impl;

import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import com.WebXemPhim.WebXemPhim.Entity.MaVe;
import com.WebXemPhim.WebXemPhim.Repository.DatVeRepository;
import com.WebXemPhim.WebXemPhim.Repository.MaVeRepo;
import com.WebXemPhim.WebXemPhim.Service.QrCodeService;
import com.WebXemPhim.WebXemPhim.Service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class VeImpl implements VeService {
    @Autowired
    private MaVeRepo maVeRepo;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private DatVeRepository datVeRepository;
    @Override
    @Transactional
    public void updateTrangThaiVe(int idSuatChieu) {
        MaVe ve = maVeRepo.getMaVe(idSuatChieu);
        ve.setTrangThai(0);
        maVeRepo.save(ve);
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public MaVe genarateTicKet(int IDSuatChieu) {
        String secretKey = "MaVe" + UUID.randomUUID();
        byte[] qrCode = qrCodeService.generateQrCode(secretKey, 500, 500);
        MaVe newMaVe = new MaVe();
        newMaVe.setMaSoVe(qrCode);
        newMaVe.setTrangThai(1);
        Date currentDate = Calendar.getInstance().getTime();
        newMaVe.setCreatedAt(currentDate);
        DatVe datVe = datVeRepository.getTTSuatChieu(IDSuatChieu);
        newMaVe.setDatVe(datVe);
        maVeRepo.save(newMaVe);
        return newMaVe;
    }
}
