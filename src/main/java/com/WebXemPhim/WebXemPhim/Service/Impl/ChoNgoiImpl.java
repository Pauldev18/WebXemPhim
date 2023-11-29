package com.WebXemPhim.WebXemPhim.Service.Impl;

import com.WebXemPhim.WebXemPhim.Entity.ChoNgoi;
import com.WebXemPhim.WebXemPhim.Repository.ChoNgoiRepo;
import com.WebXemPhim.WebXemPhim.Service.ChoNgoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChoNgoiImpl implements ChoNgoiService {
    @Autowired
    private ChoNgoiRepo choNgoiRepo;
    @Override
    @Transactional
    public void updateTrangThai(int idChoNgoi) {
        ChoNgoi update = choNgoiRepo.findById(idChoNgoi);
        update.setTrangThai(0);
        choNgoiRepo.save(update);
    }
}
