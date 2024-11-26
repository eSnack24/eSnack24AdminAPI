package org.esnack24api.esnack24adminapi.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    
    private final AdminRepository adminRepository;

        public UserDTO authenticate(String email, String password) {

        Optional<AdminEntity> result = userRepository.findByUemail(email);

        AdminEntity user = result.orElseThrow(() -> UserExceptions.BAD_AUTH.get());

        boolean match = passwordEncoder.matches(password, user.getUpw());

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPw(user.getUpw());

        return userDTO;
    }
}
