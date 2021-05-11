package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.UserProfileApiRequest;
import com.pandasaza.base.model.network.response.UserProfileApiResponse;

public class UserProfileApiLogicService implements CrudInterface<UserProfileApiRequest, UserProfileApiResponse> {
    @Override
    public Header<UserProfileApiResponse> create(Header<UserProfileApiRequest> request) {
        return null;
    }

    @Override
    public Header<UserProfileApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<UserProfileApiResponse> update(Header<UserProfileApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
