package com.WebXemPhim.WebXemPhim.Service;

import com.WebXemPhim.WebXemPhim.DTO.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface AccountService {
    AccountDTO login(String username, String password);
    ResponseEntity<Object> register(String tenUser, MultipartFile avatar, String sdt, int gioiTinh, Date ngaySinh, String taiKhoan, String matKhau, String gmail);
    ResponseEntity<Object> updateUser(String tenUser, MultipartFile avatar, String sdt, Integer gioiTinh, Date ngaySinh,String gmail, int IDUser);
    ResponseEntity<Object> forgetPass(String gmail, String newPass);
}
