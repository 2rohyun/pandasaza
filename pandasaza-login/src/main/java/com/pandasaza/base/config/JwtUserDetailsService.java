package com.pandasaza.base.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.pandasaza.base.model.dto.UserDto;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.model.service.UnactiveUserService;
import com.pandasaza.base.repository.UnactiveUserRepository;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UnactiveUserService unactiveUserService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException((account)));
    }

    @Transactional
    //TODO ( erase pandasaza-base sign up logic, handling unactive user logic into pandasaza-login module
    public Long save(UserDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        //TODO ( unactive user 에 대한 핸들링 )
        //unactiveUserService.create();

        return userRepository.save(User.builder()
                .account(infoDto.getAccount())
                .password(infoDto.getPassword())
                .email(infoDto.getEmail())
                .nation(infoDto.getNation())
                .registeredAt(LocalDateTime.now())
                .status(UserStatus.REGISTERED)
                .authMethods(
                        infoDto.getAuthMethods()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))
                )
                .authHistory(
                        infoDto.getAuthHistory()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","))
                )
                .phoneNumber(infoDto.getPhoneNumber())
                .university(infoDto.getUniversity())
                .lastLoginAt(infoDto.getLastLoginAt())
                .profileIcon(infoDto.getProfileIcon())
                .auth("ROLE_USER").build()).getUserId();

    }
}
