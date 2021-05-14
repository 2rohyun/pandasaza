package com.pandasaza.base.model.service;

import com.pandasaza.base.model.entity.ThumbnailItem;
import com.pandasaza.base.model.entity.UnactiveUser;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.enumclass.UserStatus;
import com.pandasaza.base.repository.UnactiveUserRepository;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnactiveUserService {

    private final UserRepository userRepository;

    private final UnactiveUserRepository unactiveUserRepository;

    public void create(){
        UnactiveUser unactiveUser = new UnactiveUser();

        List<User> user = userRepository.findAll();
        LocalDateTime today = LocalDateTime.now();

        user.forEach(u->{
            if(ChronoUnit.DAYS.between(u.getLastLoginAt(),today) > 100){
                unactiveUser.setUuId(u.getUserId());
                //unactiveUser.setAccount(u.getAccount());
                unactiveUser.setEmail(u.getEmail());
                unactiveUser.setAuthMethods(u.getAuthMethods());
                unactiveUser.setAuthHistory(u.getAuthHistory());
                //unactiveUser.setNation(u.getNation());
                unactiveUser.setPassword(u.getPassword());
                unactiveUser.setLastLoginAt(u.getLastLoginAt());
                //unactiveUser.setPhoneNumber(u.getPhoneNumber());
                unactiveUser.setRegisteredAt(u.getRegisteredAt());
                unactiveUser.setStatus(UserStatus.REGISTERED);
                unactiveUser.setUniversity(u.getUniversity());
                unactiveUser.setUser(u);
                //unactiveUser.setProfileIcon(u.getProfileIcon());

                unactiveUserRepository.save(unactiveUser);
            }
        });
    }

    public void delete(Long id){
        Optional<UnactiveUser> optional = unactiveUserRepository.findById(id);
        optional.ifPresent(unactiveUserRepository::delete);
    }
}
