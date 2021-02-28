package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.ConsumerAddress;

import java.util.List;

/**
 * @author shanhe
 * @className ConsumerAddressService
 * @date 2021-02-27 09:59
 **/
public interface ConsumerAddressService {
    int createNewAddress(ConsumerAddress consumerAddress);

    boolean removeDeliveryAddressById(int id);

    int modifySelective(ConsumerAddress consumerAddress);

    List<ConsumerAddress> listByPage(int userId, int pageNum, int pageSize);

    int countSumPage(int userId, int pageSize);
}
