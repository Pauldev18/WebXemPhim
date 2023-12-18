package com.WebXemPhim.WebXemPhim.Service;

import com.WebXemPhim.WebXemPhim.DTO.AccountDTO;
import org.springframework.security.core.userdetails.User;

public interface AccountService {
    AccountDTO login(String username, String password);
}
