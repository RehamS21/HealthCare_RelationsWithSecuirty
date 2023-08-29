package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    public void register(User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("ADMIN");
        authRepository.save(user);
    }
}
