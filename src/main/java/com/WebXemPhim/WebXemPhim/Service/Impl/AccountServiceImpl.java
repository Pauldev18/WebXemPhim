package com.WebXemPhim.WebXemPhim.Service.Impl;

import com.WebXemPhim.WebXemPhim.DTO.AccountDTO;
import com.WebXemPhim.WebXemPhim.Entity.Role;
import com.WebXemPhim.WebXemPhim.Entity.UserRole;
import com.WebXemPhim.WebXemPhim.Entity.Users;
import com.WebXemPhim.WebXemPhim.Repository.RoleRepo;
import com.WebXemPhim.WebXemPhim.Repository.UserRepo;
import com.WebXemPhim.WebXemPhim.Repository.UserRoleRepository;
import com.WebXemPhim.WebXemPhim.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepo userRepo;
    private final UserRoleRepository UserRoleRepository;
    private final RoleRepo roleRepository;

    @Autowired
    public AccountServiceImpl(UserRepo userRepo, com.WebXemPhim.WebXemPhim.Repository.UserRoleRepository userRoleRepository, RoleRepo roleRepository) {
        this.userRepo = userRepo;
        UserRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AccountDTO login(String username, String password) {
        Users user = userRepo.login(username, password);
        UserRole userRole = UserRoleRepository.findUserole(user.getIdUser());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setRole(userRole.getRole().getTenRole());
        accountDTO.setTenUser(user.getTenUser());
        accountDTO.setEmail(user.getGmail());
        accountDTO.setUserID(user.getIdUser());
        return accountDTO;
    }
}
