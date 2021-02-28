package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.ConsumerAccount;
import com.nju.graduation.project.bas.mapper.ConsumerAccountMapper;
import com.nju.graduation.project.bas.service.ConsumerAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shanhe
 * @className ConsumerAccountServiceImpl
 * @date 2021-02-26 22:58
 **/
@Service
public class ConsumerAccountServiceImpl implements ConsumerAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerAccountServiceImpl.class);

    @Autowired
    private ConsumerAccountMapper consumerAccountMapper;

    @Override
    public void modifySelective(ConsumerAccount consumerAccount) {
        try {
            consumerAccountMapper.updateByPrimaryKeySelective(consumerAccount);
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountServiceImpl.modifySelective error, consumerAccount={}\nerrorMessage={}", consumerAccount, e.getMessage());
        }
    }

    @Override
    public ConsumerAccount findByPhoneAndPassword(String phone, String password) {
        try {
            return consumerAccountMapper.selectByPhoneAndPassword(phone, password);
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountServiceImpl.modifySelective error, phone={}, password={}\nerrorMessage={}", phone, password, e.getMessage());
            return null;
        }
    }

    @Override
    public ConsumerAccount findByPhone(String phone) {
        try {
            return consumerAccountMapper.selectByPhone(phone);
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountServiceImpl.modifySelective error, phone={}\nerrorMessage={}", phone, e.getMessage());
            return null;
        }
    }

    @Override
    public int createNewAccount(ConsumerAccount consumerAccount) {
        try {
            return consumerAccountMapper.insert(consumerAccount);
        } catch (Exception e) {
            LOGGER.error("ConsumerAccountServiceImpl.modifySelective error, consumerAccount={}\nerrorMessage={}", consumerAccount, e.getMessage());
            return 0;
        }
    }
}
