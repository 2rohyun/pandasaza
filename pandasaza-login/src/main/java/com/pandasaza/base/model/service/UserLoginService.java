package com.pandasaza.base.model.service;

import com.pandasaza.base.exception.AccountNotExistedException;
import com.pandasaza.base.exception.PasswordWrongException;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Builder
public class UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String account, String password) {
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new AccountNotExistedException(account));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        return user;
    }
}
