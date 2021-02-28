package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.ConsumerAccount;
import org.apache.ibatis.annotations.Param;

public interface ConsumerAccountMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ConsumerAccount record);

    int insertSelective(ConsumerAccount record);

    ConsumerAccount selectByPrimaryKey(Integer userId);

    ConsumerAccount selectByPhoneAndPassword(@Param("phone") String phone,
                                             @Param("password") String password);

    ConsumerAccount selectByPhone(@Param("phone") String phone);

    int updateByPrimaryKeySelective(ConsumerAccount record);

    int updateByPrimaryKey(ConsumerAccount record);
}