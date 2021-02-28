package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.ManagerAccount;
import com.nju.graduation.project.bas.mapper.ManagerAccountMapper;
import com.nju.graduation.project.bas.service.ManagerAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shanhe
 * @className ManagerAccountServiceImpl
 * @date 2021-02-24 17:22
 **/
@Service
@Transactional
public class ManagerAccountServiceImpl implements ManagerAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerAccountServiceImpl.class);

    @Autowired
    private ManagerAccountMapper managerAccountMapper;


    @Override
    public ManagerAccount findByPhoneAndPassword(String phone, String password) {
        try {
            LOGGER.info("ManagerAccountServiceImpl.findByPhoneAndPassword 开始检索数据库，phone={}, password={}", phone, password);
            ManagerAccount managerAccount = managerAccountMapper.selectByPhoneAndPassword(phone, password);
            return managerAccount;
        } catch (Exception e) {
            LOGGER.error("ManagerAccountServiceImpl.findByPhoneAndPassword error, phone={}, password={}\nerrorMessage={}", phone, password, e.getMessage());
            return null;
        }
    }

    @Override
    public ManagerAccount findByPhone(String phone) {
        try {
            LOGGER.info("ManagerAccountServiceImpl.findByPhoneAndPassword 开始检索数据库，phone={}", phone);
            ManagerAccount managerAccount = managerAccountMapper.selectByPhone(phone);
            return managerAccount;
        } catch (Exception e) {
            LOGGER.error("ManagerAccountServiceImpl.findByPhoneAndPassword error, phone={}\nerrorMessage={}", phone, e.getMessage());
            return null;
        }
    }

    @Override
    public void modifySelective(ManagerAccount managerAccount) {
        try {
            LOGGER.info("ManagerAccountServiceImpl.findByPhoneAndPassword 开始检索数据库，managerAccount={}", managerAccount);
            managerAccountMapper.updateByPrimaryKeySelective(managerAccount);
        } catch (Exception e) {
            LOGGER.error("ManagerAccountServiceImpl.findByPhoneAndPassword error, managerAccount={}\nerrorMessage={}", managerAccount, e.getMessage());
        }
    }

    @Override
    public int createNewAccount(ManagerAccount managerAccount) {
        try {
            LOGGER.info("ManagerAccountServiceImpl.findByPhoneAndPassword 开始检索数据库，managerAccount={}", managerAccount);
            return managerAccountMapper.insert(managerAccount);
        } catch (Exception e) {
            LOGGER.error("ManagerAccountServiceImpl.findByPhoneAndPassword error, managerAccount={}\nerrorMessage={}", managerAccount, e.getMessage());
            return 0;
        }
    }
}
