package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Account;
import com.pandasaza.base.model.entity.UserProfile;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.AccountApiResponse;
import com.pandasaza.base.model.network.request.UserProfileApiRequest;
import com.pandasaza.base.model.network.response.UserProfileApiResponse;
import com.pandasaza.base.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileApiLogicService implements CrudInterface<UserProfileApiRequest, UserProfileApiResponse> {

    private final UserProfileRepository userProfileRepository;
    @Override
    public Header<UserProfileApiResponse> create(Header<UserProfileApiRequest> request) {
        UserProfileApiRequest userProfileApiRequest = request.getData();

        UserProfile userProfile = UserProfile.builder()
                .profileId(userProfileApiRequest.getProfileId())
                .registeredAt(userProfileApiRequest.getRegisteredAt())
                .profileImage(userProfileApiRequest.getProfileImage())
                .profileName(userProfileApiRequest.getProfileName())
                .updatedAt(userProfileApiRequest.getUpdatedAt())
                .build();
        UserProfile newUserProfile = userProfileRepository.save(userProfile);
        return response(newUserProfile);
    }

    @Override
    public Header<UserProfileApiResponse> read(Long id) {
        Optional<UserProfile> optional = userProfileRepository.findById(id);

        return optional
                .map(userProfile -> response(userProfile))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }


    @Override
    public Header<UserProfileApiResponse> update(Header<UserProfileApiRequest> request) {

        UserProfileApiRequest userProfileApiRequest = request.getData();

        Optional<UserProfile> optional = userProfileRepository.findById(userProfileApiRequest.getProfileId());

        return optional.map(userProfile -> {

            userProfile.setProfileImage(userProfileApiRequest.getProfileImage())
                    .setProfileName(userProfileApiRequest.getProfileName())
                    .setRegisteredAt(userProfileApiRequest.getRegisteredAt())
                    .setUpdatedAt(userProfileApiRequest.getUpdatedAt());

            return userProfile;
        })
                .map(userProfile -> userProfileRepository.save(userProfile))
                .map(updateUserProfile -> response(updateUserProfile))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    private Header<UserProfileApiResponse> response(UserProfile userProfile){

        UserProfileApiResponse userProfileApiResponse = UserProfileApiResponse.builder()
                .profileId(userProfile.getProfileId())
                .profileImage(userProfile.getProfileImage())
                .profileName(userProfile.getProfileName())
                .registeredAt(userProfile.getRegisteredAt())
                .updatedAt(userProfile.getUpdatedAt())
                .build();

        return Header.OK(userProfileApiResponse);
    }
}
