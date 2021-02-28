package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.ConsumerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsumerAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerAddress record);

    int insertSelective(ConsumerAddress record);

    ConsumerAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsumerAddress record);

    int updateByPrimaryKeyWithBLOBs(ConsumerAddress record);

    int updateByPrimaryKey(ConsumerAddress record);

    List<ConsumerAddress> listByPage(@Param("userId") int userId,
                                     @Param("star") int star,
                                     @Param("size") int size);

    //TODO 优化
    int countSum(@Param("userId") int userId);
}