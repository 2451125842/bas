package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.domain.ConsumerAddress;
import com.nju.graduation.project.bas.mapper.ConsumerAddressMapper;
import com.nju.graduation.project.bas.service.ConsumerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shanhe
 * @className ConsumerAddressServiceImpl
 * @date 2021-02-27 10:34
 **/
@Service
public class ConsumerAddressServiceImpl implements ConsumerAddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerAddressServiceImpl.class);

    @Autowired
    private ConsumerAddressMapper consumerAddressMapper;

    @Override
    public int createNewAddress(ConsumerAddress consumerAddress) {
        try {
            return consumerAddressMapper.insert(consumerAddress);
        } catch (Exception e) {
            LOGGER.error("ConsumerAddressServiceImpl.createNewAddress error, consumerAddress={}\nerrorMessage={}", consumerAddress, e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean removeDeliveryAddressById(int id) {
        try {
            return consumerAddressMapper.deleteByPrimaryKey(id) <= 0;
        } catch (Exception e) {
            LOGGER.error("ConsumerAddressServiceImpl.removeDeliveryAddressById error, id={}\nerrorMessage={}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public int modifySelective(ConsumerAddress consumerAddress) {
        try {
            return consumerAddressMapper.updateByPrimaryKeySelective(consumerAddress);
        } catch (Exception e) {
            LOGGER.error("ConsumerAddressServiceImpl.modifySelective error, consumerAddress={}\nerrorMessage={}", consumerAddress, e.getMessage());
            return 0;
        }
    }

    @Override
    public List<ConsumerAddress> listByPage(int userId, int pageNum, int pageSize) {
        try {
            List<ConsumerAddress> list = consumerAddressMapper.listByPage(userId,pageNum*pageSize, pageSize);
            return list;
        } catch (Exception e) {
            LOGGER.error("ConsumerAddressServiceImpl.listByPage error, pageNum={}, pageSize={}\nerrorMessage={}", pageNum, pageSize, e.getMessage());
            return null;
        }
    }

    @Override
    public int countSumPage(int userId, int pageSize) {
        try {
            return (int)Math.ceil(Double.valueOf(consumerAddressMapper.countSum(userId))/Double.valueOf(pageSize));
        } catch (Exception e) {
            LOGGER.error("ConsumerAddressServiceImpl.countSumPage error, userId={}, pageSize={}\nerrorMessage={}", userId, pageSize, e.getMessage());
            return 0;
        }
    }
}
