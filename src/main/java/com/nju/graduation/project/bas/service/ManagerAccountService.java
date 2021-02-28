package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.ManagerAccount;

/**
 * @author shanhe
 * @className ManagerAccountService
 * @date 2021-02-24 17:13
 **/
public interface ManagerAccountService {
    ManagerAccount findByPhoneAndPassword(String phone, String password);

    ManagerAccount findByPhone(String phone);

    void modifySelective(ManagerAccount managerAccount);

    int createNewAccount(ManagerAccount managerAccount);
}
