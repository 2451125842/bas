package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.ConsumerAccount;

/**
 * @author shanhe
 * @className ConsumerAccountService
 * @date 2021-02-26 22:29
 **/
public interface ConsumerAccountService {

    void modifySelective(ConsumerAccount consumerAccount);

    ConsumerAccount findByPhoneAndPassword(String phone, String password);

    ConsumerAccount findByPhone(String phone);

    int createNewAccount(ConsumerAccount consumerAccount);
}
