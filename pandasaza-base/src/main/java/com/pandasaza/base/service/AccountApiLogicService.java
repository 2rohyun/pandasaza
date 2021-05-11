package com.pandasaza.base.service;

import com.pandasaza.base.ifs.CrudInterface;
import com.pandasaza.base.model.entity.Account;
import com.pandasaza.base.model.entity.Dib;
import com.pandasaza.base.model.network.Header;
import com.pandasaza.base.model.network.request.AccountApiRequest;
import com.pandasaza.base.model.network.request.AccountApiResponse;
import com.pandasaza.base.model.network.response.DibApiResponse;
import com.pandasaza.base.repository.AccountRepository;
import com.pandasaza.base.repository.DibRepository;
import com.pandasaza.base.repository.ItemRepository;
import com.pandasaza.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountApiLogicService implements CrudInterface<AccountApiRequest, AccountApiResponse> {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    public Header<AccountApiResponse> create(Header<AccountApiRequest> request) {
        AccountApiRequest accountApiRequest = request.getData();

        Account account = Account.builder()
                .accountId(accountApiRequest.getAccountId())
                .phone(accountApiRequest.getPhone())
                .user(userRepository.getOne(accountApiRequest.getAccountId()))
                .registeredAt(accountApiRequest.getRegisteredAt())
                .build();
        Account newAccount = accountRepository.save(account);
        return response(newAccount);
    }

    @Override
    public Header<AccountApiResponse> read(Long id) {

        Optional<Account> optional = accountRepository.findById(id);
        return optional
                .map(account -> response(account))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<AccountApiResponse> update(Header<AccountApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        Optional<Account> optional = accountRepository.findById(id);

        return null;
    }

    private Header<AccountApiResponse> response(Account account){

        AccountApiResponse accountApiResponse = AccountApiResponse.builder()
                .accountId(account.getAccountId())
                .phone(account.getPhone())
                .user(account.getUser())
                .registeredAt(account.getRegisteredAt())
                .build();

        return Header.OK(accountApiResponse);
    }
}
