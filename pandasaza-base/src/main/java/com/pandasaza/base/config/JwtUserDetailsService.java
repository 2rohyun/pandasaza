package com.pandasaza.base.config;

import com.pandasaza.base.model.dto.UserDto;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException((account)));
    }
}
