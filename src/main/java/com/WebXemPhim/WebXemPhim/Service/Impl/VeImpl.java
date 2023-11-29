package com.WebXemPhim.WebXemPhim.Service.Impl;

import com.WebXemPhim.WebXemPhim.Entity.MaVe;
import com.WebXemPhim.WebXemPhim.Repository.MaVeRepo;
import com.WebXemPhim.WebXemPhim.Service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeImpl implements VeService {
    @Autowired
    private MaVeRepo maVeRepo;
    @Override
    @Transactional
    public void updateTrangThaiVe(int idMaVe) {
        MaVe ve = maVeRepo.findById(idMaVe);
        ve.setTrangThai(0);
        maVeRepo.save(ve);
    }
}
