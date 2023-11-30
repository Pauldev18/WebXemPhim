package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.Entity.Users;
import com.WebXemPhim.WebXemPhim.Repository.UserRepo;
import com.WebXemPhim.WebXemPhim.Service.Impl.EmailService;
import com.WebXemPhim.WebXemPhim.Service.Impl.EmailTemplate;
import com.WebXemPhim.WebXemPhim.Service.Impl.OTPService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OTPController {
    @Autowired
    public OTPService otpService;

    @Autowired
    public EmailService emailService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/generateOtp")
    public ResponseEntity<String> generateOTP(@RequestParam("email") String email) throws MessagingException {
        Users checkUser = userRepo.findByGmail(email);
        if(checkUser != null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            int otp = otpService.generateOTP(username);

            // Truyền nội dung template trực tiếp (ví dụ: "<p>Hi {{user}}, your OTP is {{otpnum}}</p>")
            EmailTemplate template = new EmailTemplate("<p>Hi {{user}}, your OTP is {{otpnum}}</p>");

            Map<String, String> replacements = new HashMap<>();
            replacements.put("user", checkUser.getTenUser());
            replacements.put("otpnum", String.valueOf(otp));
            String message = template.getTemplate(replacements);
            emailService.sendOtpMessage(email, "Xác thực OTP", message);

            return new ResponseEntity<>("OTP generated and sent successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Không tìm thấy tài khoản", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/validateOtp")
    public ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnum) {

        final String SUCCESS = "Success";
        final String FAIL = "Fail";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        // Validate the Otp
        if (otpnum >= 0) {

            int serverOtp = otpService.getOtp(username);
            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(username);

                    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }
}
