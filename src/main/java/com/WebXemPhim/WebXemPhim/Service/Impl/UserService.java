package com.WebXemPhim.WebXemPhim.Service.Impl;

import com.WebXemPhim.WebXemPhim.Entity.UserRole;
import com.WebXemPhim.WebXemPhim.Entity.Users;
import com.WebXemPhim.WebXemPhim.Repository.UserRepo;
import com.WebXemPhim.WebXemPhim.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserRoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepo.findByUserTen(username);
        UserRole role = roleRepository.findUserole(users.getIdUser());
        GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole().getTenRole());
        UserDetails userDetails = (UserDetails) new User(users.getTenUser(),
                users.getMatKhau(), Arrays.asList(authority));

        return userDetails;
    }
}
