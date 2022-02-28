package com.itcast.more.mapper.db1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/10/7 - 17:10
 */
@Repository
public interface Db1Mapper {

    int countUser();
}
