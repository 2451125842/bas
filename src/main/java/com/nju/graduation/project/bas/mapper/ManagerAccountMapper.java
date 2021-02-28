package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.ConsumerAccount;
import com.nju.graduation.project.bas.domain.ManagerAccount;
import org.apache.ibatis.annotations.Param;

public interface ManagerAccountMapper {
    int deleteByPrimaryKey(Integer managerId);

    int insert(ManagerAccount record);

    int insertSelective(ManagerAccount record);

    ManagerAccount selectByPrimaryKey(Integer managerId);

    ManagerAccount selectByPhoneAndPassword(@Param("phone") String phone,
                                             @Param("password") String password);

    ManagerAccount selectByPhone(@Param("phone") String phone);

    int updateByPrimaryKeySelective(ManagerAccount record);

    int updateByPrimaryKey(ManagerAccount record);
}