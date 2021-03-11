package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.ThumbnailItem;
import com.pandasaza.base.model.entity.UnactiveUser;
import com.pandasaza.base.model.entity.User;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UnactiveUserApiRequest;
import com.pandasaza.base.model.network.response.UnactiveUserApiResponse;
import com.pandasaza.base.model.network.response.UserApiResponse;
import com.pandasaza.base.repository.UnactiveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UnactiveUserApiLogicService implements CrudInterface<UnactiveUserApiRequest, UnactiveUserApiResponse> {

    private final UnactiveUserRepository unactiveUserRepository;

    @Override
    public Header<UnactiveUserApiResponse> create(Header<UnactiveUserApiRequest> request) { return null; }

    @Override
    public Header<UnactiveUserApiResponse> read(Long id) {
        Optional<UnactiveUser> optional = unactiveUserRepository.findById(id);

        return optional
                .map(user -> response(user))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UnactiveUserApiResponse> update(Header<UnactiveUserApiRequest> request) { return null; }

    @Override
    public Header delete(Long id) {
        Optional<UnactiveUser> optional = unactiveUserRepository.findById(id);

        return optional.map(user->{
            unactiveUserRepository.delete(user);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<UnactiveUserApiResponse> response(UnactiveUser unactiveUser){

        List<String> authMethodsList = Stream.of(
                unactiveUser.getAuthMethods()
                        .split(",",-1))
                .collect(Collectors.toList());

        List<String> authHistoryList = Stream.of(
                unactiveUser.getAuthHistory()
                        .split(",",-1))
                .collect(Collectors.toList());

        UnactiveUserApiResponse unactiveUserApiResponse = UnactiveUserApiResponse.builder()
                .account(unactiveUser.getAccount())
                .uuId(unactiveUser.getUuId())
                .password(unactiveUser.getPassword())
                .phoneNumber(unactiveUser.getPhoneNumber())
                .status(unactiveUser.getStatus())
                .email(unactiveUser.getEmail())
                .registeredAt(unactiveUser.getRegisteredAt())
                .authHistory(authHistoryList)
                .authMethods(authMethodsList)
                .lastLoginAt(unactiveUser.getLastLoginAt())
                .nation(unactiveUser.getNation())
                .university(unactiveUser.getUniversity())
                .userUserId(unactiveUser.getUser().getUserId())
                .build();

        return Header.OK(unactiveUserApiResponse);
    }
}
