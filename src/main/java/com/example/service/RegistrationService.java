package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.EmailServicereg;
import com.example.util.OTPUtil;
import com.example.util.PhoneService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailServicereg emailService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    public void registerUser(User user){

        // ✅ Normalize phone FIRST
        String formattedPhone = user.getPhone().startsWith("+")
                ? user.getPhone()
                : "+91" + user.getPhone();

        user.setPhone(formattedPhone);

        // ✅ Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setStatus("PENDING");
        user.setEmailVerified(false);
        user.setPhoneVerified(false);

        // ✅ Save AFTER formatting
        userRepository.save(user);

        // ✅ Generate OTPs
        String emailOtp = OTPUtil.generateOTP();
        String phoneOtp = OTPUtil.generateOTP();

        // ✅ Store in Redis
        redisTemplate.opsForValue().set(
                "email_otp:" + user.getEmail(),
                emailOtp,
                15,
                TimeUnit.MINUTES
        );

        redisTemplate.opsForValue().set(
                "phone_otp:" + formattedPhone,
                phoneOtp,
                15,
                TimeUnit.MINUTES
        );

        // ✅ Send OTPs
        emailService.sendEmail(
                user.getEmail(),
                "Verify your email OTP",
                "Your OTP: " + emailOtp
        );

        phoneService.sendOTP(formattedPhone, phoneOtp);

        // ✅ Debug logs
        System.out.println("Email OTP: " + emailOtp);
        System.out.println("Phone OTP: " + phoneOtp);
        System.out.println("Stored Key: phone_otp:" + formattedPhone);
    }

    // ================= EMAIL VERIFY =================
    public boolean verifyEmailOTP(String email, String otp){

        String key = "email_otp:" + email;
        String redisOtp = redisTemplate.opsForValue().get(key);

        if(redisOtp == null){
            System.out.println("Email OTP expired or not found");
            return false;
        }

        if(!redisOtp.trim().equals(otp.trim())){
            System.out.println("Invalid Email OTP");
            return false;
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            System.out.println("User not found for email: " + email);
            return false;
        }

        User user = optionalUser.get();

        // ✅ Delete OTP AFTER validation
        redisTemplate.delete(key);

        user.setEmailVerified(true);
        activateIfReady(user);
        userRepository.save(user);

        return true;
    }

    // ================= PHONE VERIFY =================
    public boolean verifyPhoneOTP(String phone, String otp){

        String formattedPhone = phone.startsWith("+")
                ? phone
                : "+91" + phone;

        String key = "phone_otp:" + formattedPhone;

        String redisOtp = redisTemplate.opsForValue().get(key);

        // ✅ Debug logs
        System.out.println("------ PHONE VERIFY DEBUG ------");
        System.out.println("Formatted Phone: " + formattedPhone);
        System.out.println("Redis Key: " + key);
        System.out.println("Redis OTP: " + redisOtp);
        System.out.println("Entered OTP: " + otp);

        if(redisOtp == null){
            System.out.println("Phone OTP expired or not found");
            return false;
        }

        if(!redisOtp.trim().equals(otp.trim())){
            System.out.println("Invalid Phone OTP");
            return false;
        }

        Optional<User> optionalUser = userRepository.findByPhone(formattedPhone);

        if(optionalUser.isEmpty()){
            System.out.println("User NOT FOUND for phone: " + formattedPhone);
            return false;
        }

        User user = optionalUser.get();

        // ✅ Delete OTP AFTER everything is correct
        redisTemplate.delete(key);

        user.setPhoneVerified(true);
        activateIfReady(user);
        userRepository.save(user);

        return true;
    }

    // ================= ACTIVATE =================
    private void activateIfReady(User user){
        if(user.isEmailVerified() && user.isPhoneVerified()){
            user.setStatus("ACTIVE");
        }
    }
}